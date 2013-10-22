package com.modules.dust;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.abstractions.api.Message;
import com.abstractions.api.Processor;
import com.abstractions.expression.ScriptingExpression;
import com.abstractions.expression.ScriptingLanguage;
import com.abstractions.utils.ExpressionUtils;
import com.abstractions.utils.IdGenerator;
import com.service.core.FileService;

public class ResourceBasedDustRendererProcessor implements Processor {

	/**
	 * Path to get the template
	 */
	private String bodyTemplatePath;
	
	/**
	 * Template resource list
	 */
	private String resourcesList;
	
	/**
	 * Template rendering list formed by triples (templateId,destRenderingElementId,dataExpression) separated by ","
	 */
	private String templateRenderingList;
	
	/**
	 * The holder of the templates
	 */
	private DustConnector connector;
	
	private FileService fileService;
	
	private final AtomicBoolean dirty;

	private String name;
	
	private static Log log = LogFactory.getLog(ResourceBasedDustRendererProcessor.class);
	
	public ResourceBasedDustRendererProcessor() {
		this.dirty = new AtomicBoolean(false);
		this.name = IdGenerator.getNewId();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Message process(Message message) {
		try {
			String template = this.buildMasterTemplate(message);
			this.getConnector().putTemplate(this.name, template);
			String compiledMasterTemplate = this.getConnector().getCompiledTemplate(this.name);
			message.setPayload(this.evaluate(this.name, compiledMasterTemplate, "{\"cdn\":\"" + this.getCDNPath(message) + "\"}"));
		} catch (IOException e) {
			this.log.error("Error compiling master template", e);
		}
		return message;
	}

	private String getCDNPath(Message message) {
		return "http://localhost:8080/service/fileStore/2/files/";
	}

	private String getApplicationIdFromMessage(Message message) {
		// TODO replace for real getter method
		return "2";
	}

	private String buildMasterTemplate(Message message) throws IOException {
		String head = "";
		String body = IOUtils.toString(this.getFileService().getContentsOfFile(this.getApplicationIdFromMessage(message), this.getBodyTemplatePath()));
		head = this.addResources(head);
		head = this.addTemplates(message, head);
		head = this.addRenderingScripts(message, head);
		return "<html><head>" + head + "</head><body>" + body + "</body></html>";
	}

	private String addRenderingScripts(Message message, String head) {
		head += "<script type=\"text/javascript\">";
		head += "$(document).ready(function() {";
		for (RenderingSpec spec : this.getRenderingList()) {
			String json = ExpressionUtils.evaluateNoFail(spec.getDataExpression(), message, "{}");
			head += "dust.render(\"" + this.getTemplateNameFromPath(spec.getTemplatePath()) + "\", " + json + ", "  +
					"function(err, out) { $('#" + spec.getRenderingElementId() + "').html(out)});";
		}
		head += "});";
		head += "</script>";
		return head;
	}

	private String addTemplates(Message message, String head)	throws IOException {
		List<String> templates = new ArrayList<String>();
		for (RenderingSpec spec : this.getRenderingList()) {
			templates.add(spec.getTemplatePath());
		}
		while (!templates.isEmpty()) {
			String templatePath = templates.remove(0);
			if (this.isDev()) {
				this.ensureTemplateCompiled(templatePath, message);
				String originalTemplate = IOUtils.toString(this.getFileService().getContentsOfFile(this.getApplicationIdFromMessage(message), templatePath));
				templates.addAll(this.findDependentTemplates(originalTemplate));
			}
			head += "<script type=\"text/javascript\" src=\"{cdn}" + this.getCompiledTemplateNameFromPath(templatePath) + "\"></script>";
		}
		return head;
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

	private String addResources(String head) {
		for (String resourcePath : this.getResources()) {
			if (resourcePath.endsWith(".css")) {
				head += "<link rel=\"stylesheet\" src=\"{cdn}" + resourcePath + "\" />";
			} else if (resourcePath.endsWith(".js")) {
				head += "<script type=\"text/javascript\" src=\"{cdn}" + resourcePath + "\"></script>";
			}
		}
		return head;
	}

	private boolean isDev() {
		// TODO return false if in PROD
		return true;
	}

	private String ensureTemplateCompiled(String templatePath, Message message) throws IOException {
		String destPath = this.getCompiledTemplateNameFromPath(templatePath);
		InputStream compiledTemplateInputStream = this.getFileService().getContentsOfFile(this.getApplicationIdFromMessage(message), destPath);
		String compiledTemplate = null;
		if (compiledTemplateInputStream != null) {
			compiledTemplate = IOUtils.toString(compiledTemplateInputStream);
		}
		if (compiledTemplate == null) {
			String templateName = this.getTemplateNameFromPath(templatePath);
			String template = IOUtils.toString(this.getFileService().getContentsOfFile(this.getApplicationIdFromMessage(message), templatePath));
			this.getConnector().putTemplate(templateName, template);
			compiledTemplate = this.getConnector().getCompiledTemplate(templateName);
			this.getFileService().storeFile(this.getApplicationIdFromMessage(message), destPath, new ByteArrayInputStream(compiledTemplate.getBytes())); 
		}
		return compiledTemplate;
	}

	private String getTemplateNameFromPath(String templatePath) {
		return templatePath.replace("/", "_").split("\\.")[0];
	}
	
	private String getCompiledTemplateNameFromPath(String templatePath) {
		return templatePath.split("\\.")[0] + ".ctl";
	}

	private String evaluate(String name, String compiledTemplate, String json) {
		String fullJavascript = compiledTemplate
				+ "\n"
				+ "var result = \"\";"
				+ "\n"
				+ "dust.render(\"" + name + "\", "+ json + ", function(err, out) { result = out });"
				+ "\n"
				+ "result";
		
		return this.getConnector().evaluate(fullJavascript).toString();
	}

	public DustConnector getConnector() {
		if (this.connector == null) {
			this.connector = new DustConnector();
		}
		return connector;
	}

	public void setConnector(DustConnector connector) {
		this.connector = connector;
	}

	public FileService getFileService() {
		if (this.fileService == null) {
			this.fileService = new FileService("./files");
		}
		return this.fileService;
	}

	public String getBodyTemplatePath() {
		return bodyTemplatePath;
	}

	public void setBodyTemplatePath(String masterTemplate) {
		this.bodyTemplatePath = masterTemplate;
	}

	public String getResourcesList() {
		return resourcesList;
	}

	public void setResourcesList(String resourcesList) {
		this.resourcesList = resourcesList;
	}

	public String getTemplateRenderingList() {
		return templateRenderingList;
	}

	public void setTemplateRenderingList(String templateRenderingList) {
		this.templateRenderingList = templateRenderingList;
	}
	
	private List<String> getResources() {
		return Arrays.asList(this.resourcesList.split(";"));
	}
	
	private List<RenderingSpec> getRenderingList() {
		List<RenderingSpec> specs = new ArrayList<RenderingSpec>();
		for (String templateRendering : templateRenderingList.split(";")) {
			String[] parts = templateRendering.replaceAll("[\\(\\)]", "").split(",");
			specs.add(new RenderingSpec(parts[0], parts[1], new ScriptingExpression(ScriptingLanguage.GROOVY, parts[2])));
		}
		return specs;
	}
	
}
