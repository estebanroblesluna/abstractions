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
import org.springframework.transaction.annotation.Transactional;

import com.abstractions.service.core.ResourceService;

public class ResourceBasedDustTemplateCompiler {

	private ResourceService publicResourceService;
	private ResourceService privateResourceService;
	private DustConnector dustConnector;
	
	public ResourceBasedDustTemplateCompiler() {
		
	}
	
	public ResourceBasedDustTemplateCompiler(ResourceService publicResourceService, ResourceService privateResourceService, DustConnector dustConnector) {
		this.publicResourceService = publicResourceService;
		this.privateResourceService = privateResourceService;
		this.dustConnector = dustConnector;
	}

	@Transactional
	public void mergeAndCompileTemplatesAndJsResources(long applicationId, List<String> paths, String destPath) throws IOException {
		if (!this.privateResourceService.resourceExists(applicationId, destPath)) {
			StringBuilder compiledContent = new StringBuilder();
			List<String> templatesToCompile = new ArrayList<String>();
			for (String path : paths) {
				if (path.endsWith(".js")) {
					if (!compiledContent.toString().isEmpty()) {
						compiledContent.append(";");
					}
					compiledContent.append(IOUtils.toString(this.privateResourceService.getContentsOfResource(applicationId, path)));
				} else if (path.endsWith(".tl")) {
					String originalTemplate = IOUtils.toString(this.privateResourceService.getContentsOfResource(applicationId, path));
					templatesToCompile.add(path);
					templatesToCompile.addAll(this.findDependentTemplates(originalTemplate));
				}
			}
			for (String path : templatesToCompile) {
				String templateName = this.getTemplateNameFromPath(path);
				this.dustConnector.putTemplate(templateName, IOUtils.toString(this.privateResourceService.getContentsOfResource(applicationId, path)));
				compiledContent.append(this.dustConnector.getCompiledTemplate(templateName));
			}
			this.publicResourceService.storeResource(applicationId, destPath, new ByteArrayInputStream(compiledContent.toString().getBytes()));
		}
	}
	
	@Transactional
	public void mergeAndCompileStylesheets(long applicationId, List<String> paths, String destPath) throws IOException {
		if (!this.privateResourceService.resourceExists(applicationId, destPath) && !paths.isEmpty()) {
			StringBuilder compiledContent = new StringBuilder();
			for (String path : paths) {
				if (path.endsWith(".css")) {
					compiledContent.append(this.privateResourceService.getContentsOfResource(applicationId, path));
				}
			}
			this.publicResourceService.storeResource(applicationId, destPath, new ByteArrayInputStream(compiledContent.toString().getBytes()));
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

	@Transactional
	public void mergeAndCompile(long applicationId, String cdnPath, String templateName, String templateBodyPath, String resourceList, String templateRenderingList) throws IOException {
		TemplateCompilationSpec compilationSpec = new TemplateCompilationSpec(applicationId, cdnPath, templateName, resourceList, templateRenderingList, templateBodyPath);
		this.buildAndCompileMasterTemplate(compilationSpec);		
	}

	@Transactional
	public String buildAndCompileMasterTemplate(TemplateCompilationSpec compilationSpec) throws IOException {
		String compiledMasterTemplate;
		String template = this.buildMasterTemplate(compilationSpec);
		this.dustConnector.putTemplate(compilationSpec.getTemplateName(), template);
		compiledMasterTemplate = this.dustConnector.getCompiledTemplate(compilationSpec.getTemplateName());
		this.privateResourceService.storeResource(compilationSpec.getApplicationId(), this.getMasterTemplateName(compilationSpec), new ByteArrayInputStream(compiledMasterTemplate.getBytes()));
		return compiledMasterTemplate;
	}
	
	private String buildMasterTemplate(TemplateCompilationSpec compilationSpec) throws IOException {
		String head = "";
		String body = IOUtils.toString(this.privateResourceService.getContentsOfResource(compilationSpec.getApplicationId(), compilationSpec.getBodyTemplatePath()));
		head = this.addStylesheets(compilationSpec, head, compilationSpec.getStylesheetsPaths());
		head = this.addJsAndTemplatesFiles(compilationSpec, head, compilationSpec.getJsAndTemplatesPaths());
		head = this.addRenderingScripts(compilationSpec, head);
		return "<html><head>" + head + "</head><body>" + body + "</body></html>";
	}
	
	private String addJsAndTemplatesFiles(TemplateCompilationSpec compilationSpec, String head, List<String> jsResourcesPath)	throws IOException {
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
		StringBuffer buffer = new StringBuffer(head);
		buffer.append( "<script type=\"text/javascript\">");
		buffer.append( "$(document).ready(function() {");
		for (RenderingSpec spec : compilationSpec.getRenderingList()) {
			buffer.append("dust.render(\"" + this.getTemplateNameFromPath(spec.getTemplatePath()) + "\", {" + this.getJsonPlaceholderForRenderingSpec(spec) + "|s}, ");
			buffer.append("function(err, out) { $('#" + spec.getRenderingElementId() + "').html(out)});");
		}
		buffer.append("});");
		buffer.append("</script>");
		return buffer.toString();
	}
	
	@Transactional
	public String getCompiledMasterTemplate(TemplateCompilationSpec compilationSpec) throws IOException {
		InputStream contents = null;
		contents = this.privateResourceService.getContentsOfResource(compilationSpec.getApplicationId(), this.getMasterTemplateName(compilationSpec));
		if (contents != null) {
			return IOUtils.toString(contents);
		}
		return null;
	}
	
	private String getCompiledJsPath(TemplateCompilationSpec compilationSpec) {
		return getTemplateName(compilationSpec.getTemplateName()) + ".js";
	}

	public String getTemplateName(String originalTemplateName) {
		return "_" + originalTemplateName.replaceAll(" ", "");
	}

	private String getCompiledStylesheetsPath(TemplateCompilationSpec compilationSpec) {
		return getTemplateName(compilationSpec.getTemplateName()) + ".css";
	}
	

	private String getMasterTemplateName(TemplateCompilationSpec compilationSpec) {
		return "_" + compilationSpec.getTemplateName() + "_master.tl";
	}


	private String getTemplateNameFromPath(String templatePath) {
		return templatePath.replace("/", "_").split("\\.")[0];
	}
	
	public String getJsonPlaceholderForRenderingSpec(RenderingSpec spec) {
		return "json_" + spec.getRenderingElementId();
	}

}
