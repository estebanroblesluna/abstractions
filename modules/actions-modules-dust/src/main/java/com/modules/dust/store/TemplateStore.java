package com.modules.dust.store;

import java.util.Collection;

import com.modules.dust.Template;

public interface TemplateStore {

	public abstract Template storeTemplate(Template template);

	public abstract Template getTemplate(String templateName);

	public abstract Collection<Template> getTemplates();

	public abstract Template getCompiledTemplate(String templateName);

	public abstract Template updateTemplate(Template template);

	public abstract void deleteTemplate(Template template);

}