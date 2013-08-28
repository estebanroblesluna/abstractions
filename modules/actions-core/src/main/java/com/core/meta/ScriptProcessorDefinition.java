package com.core.meta;

public class ScriptProcessorDefinition extends ElementDefinition {

	private String script;

	public ScriptProcessorDefinition(String name, String script) {
		super(name);
		this.script = script;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	@Override
	public ElementDefinitionType getType() {
		return ElementDefinitionType.PROCESSOR;
	}
	
	@Override
	public Object accept(ElementDefinitionVisitor visitor) {
		return visitor.visitScriptProcessorDefinition(this);
	}
}
