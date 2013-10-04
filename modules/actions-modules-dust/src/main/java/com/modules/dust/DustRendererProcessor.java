package com.modules.dust;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.io.IOUtils;

import com.core.api.Expression;
import com.core.api.Message;
import com.core.api.Processor;
import com.core.utils.ExpressionUtils;
import com.core.utils.IdGenerator;
import com.service.core.FileService;

public class DustRendererProcessor implements Processor {

	/**
	 * The name of the template
	 */
	private Expression template;
	
	/**
	 * Path to get the template
	 */
	private String templatePath;
	
	/**
	 * The data to be passed into the template
	 */
	private Expression jsonData;
	
	/**
	 * The holder of the templates
	 */
	private DustConnector connector;
	
	private FileService fileService;
	
	private final String name;
	private final AtomicBoolean dirty;
	
	public DustRendererProcessor() {
		this.name = IdGenerator.getNewId();
		this.dirty = new AtomicBoolean(false);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Message process(Message message) {
		/*
		String compiledTemplate = this.getConnector().getCompiledTemplate(this.name);
		
		if (compiledTemplate == null || this.dirty.get()) {
			String templateAsString = ExpressionUtils.evaluateNoFail(template, message, "");
			this.getConnector().putTemplate(name, templateAsString);
			compiledTemplate = this.getConnector().getCompiledTemplate(this.name);
			
			this.dirty.set(false);
		}
		*/
		
		String compiledTemplate = null;
		try {
			compiledTemplate = IOUtils.toString(this.getFileService().getContentsOfFile("2", this.templatePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (compiledTemplate != null) {
			String json = ExpressionUtils.evaluateNoFail(this.jsonData, message, "{}");
			String result = this.evaluate(this.templatePath, compiledTemplate, json);
			message.setPayload(result);
		}
		
		return message;
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

	public Expression getTemplate() {
		return template;
	}

	public void setTemplate(Expression template) {
		this.template = template;
		this.dirty.set(true);
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

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templateName) {
		this.templatePath = templateName;
	}
}
