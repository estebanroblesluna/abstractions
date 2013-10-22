package com.modules.dust;

import java.util.concurrent.atomic.AtomicBoolean;

import com.abstractions.api.Expression;
import com.abstractions.api.Message;
import com.abstractions.api.Processor;
import com.abstractions.utils.ExpressionUtils;
import com.abstractions.utils.IdGenerator;

public class DustRendererProcessor implements Processor {

	/**
	 * The name of the template
	 */
	private Expression template;
	
	/**
	 * The data to be passed into the template
	 */
	private Expression jsonData;
	
	/**
	 * The holder of the templates
	 */
	private DustConnector connector;
	
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
		String compiledTemplate = this.getConnector().getCompiledTemplate(this.name);
		
		if (compiledTemplate == null || this.dirty.get()) {
			String templateAsString = ExpressionUtils.evaluateNoFail(template, message, "");
			this.getConnector().putTemplate(name, templateAsString);
			compiledTemplate = this.getConnector().getCompiledTemplate(this.name);
			
			this.dirty.set(false);
		}
		
		if (compiledTemplate != null) {
			String json = ExpressionUtils.evaluateNoFail(this.jsonData, message, "{}");
			String result = this.evaluate(this.name, compiledTemplate, json);
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
}
