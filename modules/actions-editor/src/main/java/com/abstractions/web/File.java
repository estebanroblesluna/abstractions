package com.abstractions.web;

public class File {

	private String name;
	private boolean editable;

	public File(String name, boolean editable) {
		this.setName(name);
		this.setEditable(editable);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

}
