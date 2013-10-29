package com.modules.dust;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.abstractions.expression.ScriptingExpression;
import com.abstractions.expression.ScriptingLanguage;

public class TemplateCompilationSpec {

	private long applicationId;
	private String cdnPath;
	private String templateName;
	private String resourceList;
	private String renderingSpecList;
	private String bodyTemplatePath;

	public TemplateCompilationSpec(long applicationId, String cdnPath, String templateName, String resourceList, String renderingSpecList, String bodyTemplatePath) {
		this.applicationId = applicationId;
		this.cdnPath = cdnPath;
		this.templateName = templateName;
		this.resourceList = resourceList;
		this.renderingSpecList = renderingSpecList;
		this.bodyTemplatePath = bodyTemplatePath;
	}

	public long getApplicationId() {
		return applicationId;
	}

	public String getCdnPath() {
		return cdnPath;
	}

	public String getTemplateName() {
		return templateName;
	}

	public String getResourceList() {
		return resourceList;
	}

	public String getRenderingSpecList() {
		return renderingSpecList;
	}

	public String getBodyTemplatePath() {
		return bodyTemplatePath;
	}
	
	public List<RenderingSpec> getRenderingList() {
		List<RenderingSpec> specs = new ArrayList<RenderingSpec>();
		for (String templateRendering : this.renderingSpecList.split(";")) {
			String[] parts = templateRendering.replaceAll("[\\(\\)]", "").split(",");
			specs.add(new RenderingSpec(parts[0], parts[1], new ScriptingExpression(ScriptingLanguage.GROOVY, parts[2])));
		}
		return specs;
	}
	
	public List<String> getTemplatesPaths() {
		List<String> paths = new ArrayList<String>();
		for (RenderingSpec spec : this.getRenderingList()) {
			paths.add(spec.getTemplatePath());
		}
		return paths;
	}
	
	public List<String> getJsResourcesPaths() {
		List<String> paths = new ArrayList<String>();
		paths.addAll(this.getTemplatesPaths());
		paths.addAll(this.getJsPaths());
		return paths;
	}
	
	public List<String> getJsPaths() {
		List<String> paths = new ArrayList<String>();
		for (String resourcePath : this.getResources()) {
			if (resourcePath.endsWith(".js")) {
				paths.add(resourcePath);
			}
		}
		return paths;
	}
	
	public List<String> getStylesheetsPaths() {
		List<String> paths = new ArrayList<String>();
		for (String resourcePath : this.getResources()) {
			if (resourcePath.endsWith(".css")) {
				paths.add(resourcePath);
			}
		}
		return paths;
	}
	
	private List<String> getResources() {
		return Arrays.asList(this.resourceList.split(";"));
	}

}
