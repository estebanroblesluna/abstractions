package com.modules.dust;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DustConnector {

	private static Log log = LogFactory.getLog(DustConnector.class);

	private Map<String, String> templates;
	private Map<String, String> compiledTemplates;
	private final String dustLibrary;
	private final ScriptEngine engine;
	
	public DustConnector() {
		this.templates = new ConcurrentHashMap<String, String>();
		this.compiledTemplates = new ConcurrentHashMap<String, String>();
		
		ScriptEngineManager factory = new ScriptEngineManager();
		this.engine = factory.getEngineByName("JavaScript");
		 
		try {
			this.dustLibrary = IOUtils.toString(this.getClass().getClassLoader().getResourceAsStream("dust-full-1.2.3.min.js"));
		} catch (IOException e) {
			throw new IllegalStateException("Error loading dust library");
		}
		
		ScriptContext context = engine.getContext();
		context.setAttribute("name", "JavaScript", ScriptContext.ENGINE_SCOPE);
		
		try {
			this.engine.eval(this.dustLibrary);
		} catch (ScriptException e) {
			throw new IllegalStateException("Error loading dust library");
		}
	}
	
	public void putTemplate(String name, String template) {
		this.templates.put(name, template);
		String compiledTemplate = this.compile(name, template);
		this.compiledTemplates.put(name, compiledTemplate);
	}
	
	public String getTemplate(String name) {
		return this.templates.get(name);
	}

	private String compile(String name, String template) {
		String fullJavascript = "var compiled = dust.compile(\"" + template.replaceAll("\"", "\\\\\"") +"\", \"" + name + "\");"
				+ "\n"
				+ "compiled";
		return this.evaluate(fullJavascript).toString();
	}

	public Object evaluate(String javascript) {
		try {
			Object result = engine.eval(javascript);
			return result;
		} catch (ScriptException e) {
			log.error("Error evaluating script");
		}
		
		return null;
	}
	
	public String getDustLibrary() {
		return dustLibrary;
	}

	public String getCompiledTemplate(String name) {
		return this.compiledTemplates.get(name);
	}
}
