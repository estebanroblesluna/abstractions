package com.core.api;


public class Connection implements Element, Terminable {

	protected String type;
	protected Element source;
	protected Element target;

	public Connection() {
	}

	public Connection(Element source, Element target) {
		this.source = source;
		this.target = target;
	}

	public Element getSource() {
		return source;
	}

	public void setSource(Element source) {
		this.source = source;
	}

	public Element getTarget() {
		return target;
	}

	public void setTarget(Element target) {
		this.target = target;
	}

	public void terminate() {
		this.setSource(null);
		this.setTarget(null);
	}

	public String getType() {
		return type;
	}
}
