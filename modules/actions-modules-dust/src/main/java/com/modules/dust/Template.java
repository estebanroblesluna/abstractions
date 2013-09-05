package com.modules.dust;


public class Template {

	private String id;
	private String name;
	private String content;
	private boolean compiled;
	
	public Template(String name, String content) {
		super();
		this.setName(name);
		this.setContent(content);
		this.setCompiled(false);
	}
	
	public Template(String name, String content, boolean compiled) {
		this(name, content);
		this.setCompiled(compiled);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isCompiled() {
		return compiled;
	}

	private void setCompiled(boolean compiled) {
		this.compiled = compiled;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}	
	
}
