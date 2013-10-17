package com.modules.dust.service;

import java.io.IOException;
import java.util.Collection;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.modules.dust.JsonBuilder;
import com.modules.dust.Template;
import com.modules.dust.store.TemplateStore;

@Component
@Path("/")
public class DustService {
	
	//@Autowired
	public TemplateStore templateStore;

	@POST
	@Path("/templates/{templateName}")
	public Response saveTemplate(@PathParam("templateName") String templateName, @FormParam("templateContent") String templateContent) throws JsonGenerationException, IOException {
		Template template = this.templateStore.storeTemplate(new Template(templateName, templateContent));
		return Response.status(201).entity(this.renderTemplate(new JsonBuilder(), template).getContent()).build();
	}
	
	@PUT
	@Path("/templates/{templateId}")
	public Response updateTemplate(@PathParam("templateId") String templateId, @FormParam("templateName") String templateName, @FormParam("templateContent") String templateContent) throws JsonGenerationException, IOException {
		Template template = this.templateStore.getTemplate(templateId);
		if (template == null) {
			return Response.status(404).entity("Template not found").build();
		}
		if (templateName != null) {
			template.setName(templateName);
		}
		if (templateContent != null) {
			template.setContent(templateContent);
		}
		this.templateStore.updateTemplate(template);
		return Response.ok(this.renderTemplate(new JsonBuilder(), template).getContent()).build();
	}
	
	@DELETE
	@Path("/templates/{templateId}")
	public Response deleteTemplate(@PathParam("templateId") String templateId, @FormParam("templateName") String templateName, @FormParam("templateContent") String templateContent) throws JsonGenerationException, IOException {
		Template template = this.templateStore.getTemplate(templateId);
		if (template == null) {
			return Response.status(404).entity("Template not found").build();
		}
		this.templateStore.deleteTemplate(template);
		return Response.ok("Template deleted").build();
	}
	
	@GET
	@Path("/templates")
	public Response getTemplates() throws JsonGenerationException, IOException {
		Collection<Template> templates = this.templateStore.getTemplates();
		JsonBuilder response = JsonBuilder.newWithArray("templates");
		for (Template template : templates) {
			this.renderTemplate(response, template);
		}
		response.endArray();
		return Response.ok(response.getContent()).build();
	}
	
	private JsonBuilder renderTemplate(JsonBuilder jsonBuilder, Template template) throws JsonGenerationException, IOException {
		jsonBuilder
			.startObject()
			.field("id", template.getId())
			.field("name", template.getName())
			.field("content", template.getContent())
			.endObject();
		return jsonBuilder;
	}

	@GET
	@Path("/templates/{templateNameOrId}")
	public Response getTemplate(@PathParam("templateNameOrId") String templateNameOrId) throws JsonGenerationException, IOException {
		Template template = this.templateStore.getTemplate(templateNameOrId);
		if (template == null) {
			return Response.status(404).entity("Template not found").build();
		}
		return Response.ok(this.renderTemplate(new JsonBuilder(), template).getContent()).build();
	}
	
	@GET
	@Path("/compiledTemplates/{templateName}")
	public Response getCompiledTemplate(@PathParam("templateName") String templateName) throws JsonGenerationException, IOException {
		Template template = this.templateStore.getCompiledTemplate(templateName);
		if (template == null) {
			return Response.status(404).entity("Template not found").build();
		}
		if (template.getContent().equals("")) {
			return Response.ok(JsonBuilder.newWithField("error").string("Template syntax error").getContent()).build();
		}
		return Response.ok(this.renderTemplate(JsonBuilder.newWithField("template"), template).getContent()).build();
	}
	
}
