package com.modules.dust;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.abstractions.api.Message;
import com.abstractions.api.Processor;
import com.abstractions.expression.ScriptingExpression;
import com.abstractions.expression.ScriptingLanguage;
import com.abstractions.service.core.FileService;
import com.abstractions.utils.ExpressionUtils;
import com.abstractions.utils.IdGenerator;

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

	private ResourceBasedDustTemplateCompiler templateCompiler;
	
	private static Log log = LogFactory.getLog(ResourceBasedDustRendererProcessor.class);
	
	public ResourceBasedDustRendererProcessor() {
		this.dirty = new AtomicBoolean(false);
		this.setName(IdGenerator.getNewId());
		this.templateCompiler = new ResourceBasedDustTemplateCompiler(this.getFileService(), new DustConnector());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Message process(Message message) {
		try {
			String compiledMasterTemplate = this.getCompiledMasterTemplate(message);
			if (compiledMasterTemplate == null) {
				compiledMasterTemplate = this.buildAndCompileMasterTemplate(message);
			}
			message.setPayload(this.evaluate(this.getName(), compiledMasterTemplate, this.setContentToRender(message)));
		} catch (IOException e) {
			log.error("Error compiling master template", e);
		}
		return message;
	}

	private String getCompiledMasterTemplate(Message message) throws IOException {
		InputStream contents = null;
		contents = this.getFileService().getContentsOfFile(this.getApplicationIdFromMessage(message), this.getMasterTemplateName());
		if (contents != null) {
			return IOUtils.toString(contents);
		}
		return null;
	}

	private String getMasterTemplateName() {
		return this.getName() + "_master.tl";
	}

	private String setContentToRender(Message message) {
		StringBuilder content = new StringBuilder("{");
		content.append("\"cdn\":\"" + this.getCDNPath(message) + "\"");
		for (RenderingSpec spec : this.getRenderingList()) {
			String json = ExpressionUtils.evaluateNoFail(spec.getDataExpression(), message, "{}");
			content.append(",\"" + this.getJsonPlaceholderForRenderingSpec(spec) + "\":\"" + json.replace("\"", "\\\"") + "\"");
		}
		content.append("}");
		return content.toString();
	}

	private String buildAndCompileMasterTemplate(Message message) throws IOException {
		String compiledMasterTemplate;
		String template = this.buildMasterTemplate(message);
		this.getConnector().putTemplate(this.getName(), template);
		compiledMasterTemplate = this.getConnector().getCompiledTemplate(this.getName());
		this.getFileService().storeFile(this.getApplicationIdFromMessage(message), this.getMasterTemplateName(), new ByteArrayInputStream(compiledMasterTemplate.getBytes()));
		return compiledMasterTemplate;
	}

	private String getCDNPath(Message message) {
		return "http://localhost:8080/service/fileStore/2/files/";
	}

	private long getApplicationIdFromMessage(Message message) {
		// TODO replace for real getter method
		return 2;
	}

	private String buildMasterTemplate(Message message) throws IOException {
		String head = "";
		String body = IOUtils.toString(this.getFileService().getContentsOfFile(this.getApplicationIdFromMessage(message), this.getBodyTemplatePath()));
		head = this.addStylesheets(message, head);
		head = this.addJsFiles(message, head);
		head = this.addRenderingScripts(message, head);
		return "<html><head>" + head + "</head><body>" + body + "</body></html>";
	}

	private String addRenderingScripts(Message message, String head) {
		head += "<script type=\"text/javascript\">";
		head += "$(document).ready(function() {";
		for (RenderingSpec spec : this.getRenderingList()) {
			head += "dust.render(\"" + this.getTemplateNameFromPath(spec.getTemplatePath()) + "\", {" + this.getJsonPlaceholderForRenderingSpec(spec) + "|s}, "  +
					"function(err, out) { $('#" + spec.getRenderingElementId() + "').html(out)});";
		}
		head += "});";
		head += "</script>";
		return head;
	}
	
	private String getJsonPlaceholderForRenderingSpec(RenderingSpec spec) {
		return "json_" + spec.getRenderingElementId();
	}

	private String addJsFiles(Message message, String head)	throws IOException {
		this.templateCompiler.mergeAndCompileTemplatesAndJsResources(this.getApplicationIdFromMessage(message), this.getJsResourcesPaths(), this.getCompiledJsPath());
		head += "<script type=\"text/javascript\" src=\"{cdn}" + this.getCompiledJsPath() + "\"></script>";
		return head;
	}

	private String addStylesheets(Message message, String head) throws IOException {
		List<String> stylesheets = new ArrayList<String>();
		List<String> resources = this.getResources();
		for (String resourcePath : resources) {
			if (resourcePath.endsWith(".css")) {
				stylesheets.add(resourcePath);				
			}
		}
		if (stylesheets.isEmpty()) {
			return head;
		}
		this.templateCompiler.mergeAndCompileStylesheets(this.getApplicationIdFromMessage(message), this.getStylesheetsPaths(), this.getCompiledStylesheetsPath());
		head += "<link rel=\"stylesheet\" src=\"{cdn}" + this.getCompiledStylesheetsPath() + "\" />";
		return head;
	}
	
	private String getCompiledJsPath() {
		return this.getName().replaceAll(" ", "") + ".js";
	}

	private String getCompiledStylesheetsPath() {
		return this.getName().replaceAll(" ", "") + ".css";
	}

	private boolean isDev() {
		// TODO return false if in PROD
		return true;
	}

	private String getTemplateNameFromPath(String templatePath) {
		return templatePath.replace("/", "_").split("\\.")[0];
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
	
	private List<String> getTemplatesPaths() {
		List<String> paths = new ArrayList<String>();
		for (RenderingSpec spec : this.getRenderingList()) {
			paths.add(spec.getTemplatePath());
		}
		return paths;
	}
	
	private List<String> getJsResourcesPaths() {
		List<String> paths = new ArrayList<String>();
		paths.addAll(this.getTemplatesPaths());
		paths.addAll(this.getJsPaths());
		return paths;
	}
	
	private List<String> getJsPaths() {
		List<String> paths = new ArrayList<String>();
		for (String resourcePath : this.getResources()) {
			if (resourcePath.endsWith(".js")) {
				paths.add(resourcePath);
			}
		}
		return paths;
	}
	
	private List<String> getStylesheetsPaths() {
		List<String> paths = new ArrayList<String>();
		for (String resourcePath : this.getResources()) {
			if (resourcePath.endsWith(".css")) {
				paths.add(resourcePath);
			}
		}
		return paths;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
