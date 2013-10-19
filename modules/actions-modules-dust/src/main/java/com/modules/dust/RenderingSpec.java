package com.modules.dust;

import com.core.api.Expression;

public class RenderingSpec {
	private String templatePath;
	private String renderingElementId;
	private Expression dataExpression;
	
	public RenderingSpec(String templatePath, String renderingElementId,
			Expression dataExpression) {
		super();
		this.templatePath = templatePath;
		this.renderingElementId = renderingElementId;
		this.dataExpression = dataExpression;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public String getRenderingElementId() {
		return renderingElementId;
	}

	public Expression getDataExpression() {
		return dataExpression;
	}
}
