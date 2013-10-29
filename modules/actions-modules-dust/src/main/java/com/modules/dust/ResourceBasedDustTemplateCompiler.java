package com.modules.dust;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;

import com.abstractions.service.core.ResourceService;

public class ResourceBasedDustTemplateCompiler {

	private ResourceService resourceService;
	private DustConnector dustConnector;
	
	public ResourceBasedDustTemplateCompiler(ResourceService fileService, DustConnector dustConnector) {
		this.resourceService = fileService;
		this.dustConnector = dustConnector;
	}

	public void mergeAndCompileTemplatesAndJsResources(long applicationId, List<String> paths, String destPath) throws IOException {
		if (!this.resourceService.resourceExists(applicationId, destPath)) {
			StringBuilder compiledContent = new StringBuilder();
			List<String> templatesToCompile = new ArrayList<String>();
			for (String path : paths) {
				if (path.endsWith(".js")) {
					if (!compiledContent.toString().isEmpty()) {
						compiledContent.append(";");
					}
					compiledContent.append(IOUtils.toString(this.resourceService.getContentsOfResource(applicationId, path)));
				} else if (path.endsWith(".tl")) {
					String originalTemplate = IOUtils.toString(this.resourceService.getContentsOfResource(applicationId, path));
					templatesToCompile.add(path);
					templatesToCompile.addAll(this.findDependentTemplates(originalTemplate));
				}
			}
			for (String path : templatesToCompile) {
				String templateName = this.getTemplateNameFromPath(path);
				this.dustConnector.putTemplate(templateName, IOUtils.toString(this.resourceService.getContentsOfResource(applicationId, path)));
				compiledContent.append(this.dustConnector.getCompiledTemplate(templateName));
			}
			this.resourceService.storeResource(applicationId, destPath, new ByteArrayInputStream(compiledContent.toString().getBytes()));
		}
	}
	
	public void mergeAndCompileStylesheets(long applicationId, List<String> paths, String destPath) throws IOException {
		if (!this.resourceService.resourceExists(applicationId, destPath)) {
			StringBuilder compiledContent = new StringBuilder();
			for (String path : paths) {
				if (path.endsWith(".css")) {
					compiledContent.append(this.resourceService.getContentsOfResource(applicationId, path));
				}
			}
			this.resourceService.storeResource(applicationId, destPath, new ByteArrayInputStream(compiledContent.toString().getBytes()));
		}
	}
	
	private Collection<? extends String> findDependentTemplates(String originalTemplate) {
		List<String> templates = new ArrayList<String>();
		Pattern pattern = Pattern.compile("\\{>([a-zA-Z0-9_]*)/\\}");
		Matcher matcher = pattern.matcher(originalTemplate);
		while (matcher.find()) {
	        templates.add(matcher.group(1).replace("_", "/") + ".tl");
	    }
		return templates;
	}

	public void mergeAndCompile(String templateBodyPath, String resourcesList, String templateRenderingList) {
		
		
	}

	public String buildAndCompileMasterTemplate(TemplateCompilationSpec compilationSpec) throws IOException {
		String compiledMasterTemplate;
		String template = this.buildMasterTemplate(compilationSpec);
		this.dustConnector.putTemplate(compilationSpec.getTemplateName(), template);
		compiledMasterTemplate = this.dustConnector.getCompiledTemplate(compilationSpec.getTemplateName());
		this.resourceService.storeResource(compilationSpec.getApplicationId(), this.getMasterTemplateName(compilationSpec), new ByteArrayInputStream(compiledMasterTemplate.getBytes()));
		return compiledMasterTemplate;
	}
	
	String buildMasterTemplate(TemplateCompilationSpec compilationSpec) throws IOException {
		String head = "";
		String body = IOUtils.toString(this.resourceService.getContentsOfResource(compilationSpec.getApplicationId(), compilationSpec.getBodyTemplatePath()));
		head = this.addStylesheets(compilationSpec, head, compilationSpec.getStylesheetsPaths());
		head = this.addJsFiles(compilationSpec, head, compilationSpec.getJsPaths());
		head = this.addRenderingScripts(compilationSpec, head);
		return "<html><head>" + head + "</head><body>" + body + "</body></html>";
	}
	
	private String addJsFiles(TemplateCompilationSpec compilationSpec, String head, List<String> jsResourcesPath)	throws IOException {
		this.mergeAndCompileTemplatesAndJsResources(compilationSpec.getApplicationId(), jsResourcesPath, this.getCompiledJsPath(compilationSpec));
		head += "<script type=\"text/javascript\" src=\"{cdn}" + this.getCompiledJsPath(compilationSpec) + "\"></script>";
		return head;
	}

	private String addStylesheets(TemplateCompilationSpec compilationSpec, String head, List<String> resources) throws IOException {
		List<String> stylesheets = new ArrayList<String>();
		for (String resourcePath : resources) {
			if (resourcePath.endsWith(".css")) {
				stylesheets.add(resourcePath);				
			}
		}
		if (stylesheets.isEmpty()) {
			return head;
		}
		this.mergeAndCompileStylesheets(compilationSpec.getApplicationId(), compilationSpec.getStylesheetsPaths(), this.getCompiledStylesheetsPath(compilationSpec));
		head += "<link rel=\"stylesheet\" src=\"{cdn}" + this.getCompiledStylesheetsPath(compilationSpec) + "\" />";
		return head;
	}

	private String addRenderingScripts(TemplateCompilationSpec compilationSpec, String head) {
		head += "<script type=\"text/javascript\">";
		head += "$(document).ready(function() {";
		for (RenderingSpec spec : compilationSpec.getRenderingList()) {
			head += "dust.render(\"" + this.getTemplateNameFromPath(spec.getTemplatePath()) + "\", {" + this.getJsonPlaceholderForRenderingSpec(spec) + "|s}, "  +
					"function(err, out) { $('#" + spec.getRenderingElementId() + "').html(out)});";
		}
		head += "});";
		head += "</script>";
		return head;
	}
	
	public String getCompiledMasterTemplate(TemplateCompilationSpec compilationSpec) throws IOException {
		InputStream contents = null;
		contents = this.resourceService.getContentsOfResource(compilationSpec.getApplicationId(), this.getMasterTemplateName(compilationSpec));
		if (contents != null) {
			return IOUtils.toString(contents);
		}
		return null;
	}
	
	private String getCompiledJsPath(TemplateCompilationSpec compilationSpec) {
		return compilationSpec.getTemplateName().replaceAll(" ", "") + ".js";
	}

	private String getCompiledStylesheetsPath(TemplateCompilationSpec compilationSpec) {
		return compilationSpec.getTemplateName().replaceAll(" ", "") + ".css";
	}
	

	private String getMasterTemplateName(TemplateCompilationSpec compilationSpec) {
		return compilationSpec.getTemplateName() + "_master.tl";
	}


	private String getTemplateNameFromPath(String templatePath) {
		return templatePath.replace("/", "_").split("\\.")[0];
	}
	
	public String getJsonPlaceholderForRenderingSpec(RenderingSpec spec) {
		return "json_" + spec.getRenderingElementId();
	}

}
