package com.modules.dust;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.core.api.Expression;
import com.core.api.Message;
import com.core.api.Processor;
import com.core.utils.ExpressionUtils;
import com.core.utils.IdGenerator;
import com.service.core.FileService;

public class ResourceBasedDustRendererProcessor implements Processor {

	/**
	 * Path to get the template
	 */
	private String templateList;
	
	/**
	 * Path to put the compiled template
	 */
	private String compiledTemplatePath;
	
	/**
	 * The data to be passed into the template
	 */
	private Expression jsonData;
	
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
		String compiledTemplate = null;
		String lastTemplateName = this.getTemplates().isEmpty() ? this.name : this.getTemplateNameFromPath(this.getTemplates().get(this.getTemplates().size() - 1));
		try {
			InputStream compiledTemplateInputStream = this.getFileService().getContentsOfFile("2", this.compiledTemplatePath);
			if (compiledTemplateInputStream != null) {
				compiledTemplate = IOUtils.toString(compiledTemplateInputStream);
			}
			if (compiledTemplate == null || this.dirty.get()) {
				compiledTemplate = "";
				for (String templatePath : this.getTemplates()) {
					String template = IOUtils.toString(this.getFileService().getContentsOfFile("2", templatePath));
					String templateName = this.getTemplateNameFromPath(templatePath);
					this.getConnector().putTemplate(templateName, template);
					if (!compiledTemplate.isEmpty()){
						compiledTemplate += ";";
					}
					compiledTemplate += this.getConnector().getCompiledTemplate(templateName);
				}
				this.dirty.set(false);
				this.getFileService().storeFile("2", this.compiledTemplatePath, new ByteArrayInputStream(compiledTemplate.getBytes()));
			}
		} catch (IOException e) {
			log.error("Error reading template", e);
		}
		if (compiledTemplate != null && this.getTemplates().size() == 1) {
			String json = ExpressionUtils.evaluateNoFail(this.jsonData, message, "{}");
			String result = this.evaluate(lastTemplateName, compiledTemplate, json);
			message.setPayload(result);
		}
		return message;
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

	public Expression getJsonData() {
		return jsonData;
	}

	public void setJsonData(Expression jsonData) {
		this.jsonData = jsonData;
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

	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}

	public String getTemplateList() {
		return templateList;
	}

	public void setTemplateList(String templateName) {
		this.templateList = templateName;
	}
	
	private List<String> getTemplates() {
		return Arrays.asList(this.templateList.split(";"));
	}

	public String getCompiledTemplatePath() {
		return compiledTemplatePath;
	}

	public void setCompiledTemplatePath(String compiledTemplatePath) {
		this.compiledTemplatePath = compiledTemplatePath;
	}
}
