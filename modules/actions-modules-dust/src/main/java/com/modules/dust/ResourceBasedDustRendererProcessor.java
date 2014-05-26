package com.modules.dust;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.abstractions.api.Message;
import com.abstractions.api.Processor;
import com.abstractions.service.core.ResourceService;
import com.abstractions.utils.ExpressionUtils;
import com.abstractions.utils.IdGenerator;
import com.abstractions.utils.JsonBuilder;
import com.abstractions.utils.MessageUtils;
import com.modules.dust.util.DustApplicationContext;

public class ResourceBasedDustRendererProcessor implements Processor {

  
  
	/**
	 * Path to get the template
	 */
	private String bodyTemplatePath;
	
	/**
	 * Template resource list
	 */
	private String resourcesList;
	
	/**
	 * Template rendering list formed by triples (templateId,destRenderingElementId,dataExpression) separated by ","
	 */
	private String templateRenderingList;
	
	/**
	 * The holder of the templates
	 */
	private DustConnector connector;
	
	private ResourceService resourceService;

	private String name;

	private ResourceBasedDustTemplateCompiler templateCompiler;
	
	private static Log log = LogFactory.getLog(ResourceBasedDustRendererProcessor.class);
	
	public ResourceBasedDustRendererProcessor() {
		this.setName(IdGenerator.getNewId());
		this.templateCompiler = DustApplicationContext.getInstance().geResourceBasedDustTemplateCompiler();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Message process(Message message) {
		try {
			TemplateCompilationSpec templateCompilationSpec = new TemplateCompilationSpec(this.getApplicationIdFromMessage(message), this.getCDNPath(message), this.getName(), this.getResourcesList(), this.getTemplateRenderingList(), this.getBodyTemplatePath());
			String compiledMasterTemplate = this.templateCompiler.getCompiledMasterTemplate(templateCompilationSpec	);
			if (compiledMasterTemplate == null) {
				compiledMasterTemplate = this.templateCompiler.buildAndCompileMasterTemplate(templateCompilationSpec);
			}
			message.setPayload(this.evaluate(this.getName(), compiledMasterTemplate, this.setContentToRender(message, templateCompilationSpec)));
		} catch (IOException e) {
			log.error("Error compiling master template", e);
		}
		return message;
	}
	
	public String getCDNPath(Message message) {
		return message.getProperties().get(	MessageUtils.APPLICATION_CDN_PROPERTY).toString();
	}

	long getApplicationIdFromMessage(Message message) {
		return Long.parseLong(message.getProperties().get(	MessageUtils.APPLICATION_ID_PROPERTY).toString());
	}


	private String setContentToRender(Message message, TemplateCompilationSpec templateCompilationSpec) {
	  try {
      String cdnPath = this.getCDNPath(message);
      JsonBuilder jsonBuilder = JsonBuilder.newWithObject()
        .field("cdn", cdnPath);
      for (RenderingSpec spec : templateCompilationSpec.getRenderingList()) {
      	String json = ExpressionUtils.evaluateNoFail(spec.getDataExpression(), message, "{}");
      	JSONObject jsonObject = new JSONObject(json);
      	jsonObject.put("cdn", cdnPath);
      	jsonBuilder.jsonField(this.templateCompiler.getJsonPlaceholderForRenderingSpec(spec), "\"" + jsonObject.toString().replace("\"", "\\\"") + "\"");
      }
      return jsonBuilder.getContent();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (JSONException e) {
      e.printStackTrace();
    }
	  return "";
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

	public DustConnector getConnector() {
		if (this.connector == null) {
			this.connector = new DustConnector();
		}
		return connector;
	}

	public void setConnector(DustConnector connector) {
		this.connector = connector;
	}

	public String getBodyTemplatePath() {
		return bodyTemplatePath;
	}

	public void setBodyTemplatePath(String masterTemplate) {
		this.bodyTemplatePath = masterTemplate;
	}

	public String getResourcesList() {
		return resourcesList;
	}

	public void setResourcesList(String resourcesList) {
		this.resourcesList = resourcesList;
	}

	public String getTemplateRenderingList() {
		return templateRenderingList;
	}

	public void setTemplateRenderingList(String templateRenderingList) {
		this.templateRenderingList = templateRenderingList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
