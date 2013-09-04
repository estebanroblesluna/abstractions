package com.modules.dust.store;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

import com.modules.dust.DustConnector;
import com.modules.dust.Template;

public class KeyValueBasedTemplateStore implements TemplateStore {

	private static final String TEMPLATE_BY_ID = "templateById";
	private static final String TEMPLATE_BY_NAME = "templateByName";
	private static final String COMPILED_TEMPLATE_BY_NAME = "compiledTemplateByName";
	private IndexedKeyValueStore<Template> keyStore;
	
	public KeyValueBasedTemplateStore(IndexedKeyValueStore<Template> keyStore) {
		this.keyStore = keyStore;
	}

	/* (non-Javadoc)
	 * @see com.modules.dust.store.TemplateStore#storeTemplate(com.modules.dust.Template)
	 */
	@Override
	public Template storeTemplate(Template template) {
		if (template.getId() == null) {
			template.setId(UUID.randomUUID().toString());
		}
		this.keyStore.put(TEMPLATE_BY_NAME, template.getName(), template);
		this.keyStore.put(TEMPLATE_BY_ID, template.getId(), template);
		return template;
	}

	/* (non-Javadoc)
	 * @see com.modules.dust.store.TemplateStore#getTemplate(java.lang.String)
	 */
	@Override
	public Template getTemplate(String templateName) {
		Template template = this.keyStore.get(TEMPLATE_BY_NAME, templateName);
		if (template != null) {
			return template;
		}
		return this.keyStore.get(TEMPLATE_BY_ID, templateName);
	}
	
	/* (non-Javadoc)
	 * @see com.modules.dust.store.TemplateStore#getTemplates()
	 */
	@Override
	public Collection<Template> getTemplates() {
		Collection<Template> templates = this.keyStore.getFromIndex(TEMPLATE_BY_ID);
		if (templates == null) {
			return new HashSet<Template>();
		}
		return templates;
	}

	/* (non-Javadoc)
	 * @see com.modules.dust.store.TemplateStore#getCompiledTemplate(java.lang.String)
	 */
	@Override
	public Template getCompiledTemplate(String templateName) {
		if (this.keyStore.get(COMPILED_TEMPLATE_BY_NAME, templateName) == null) {
			Template template = this.getTemplate(templateName);
			if (template != null) {
				DustConnector connector = new DustConnector();
				connector.putTemplate(template.getName(), template.getContent());
				this.keyStore.put(COMPILED_TEMPLATE_BY_NAME, template.getName(), 
						new Template(template.getName(), connector.getCompiledTemplate(templateName), true));
			}
		}
		if (this.keyStore.get(COMPILED_TEMPLATE_BY_NAME, templateName) != null) {
			return this.keyStore.get(COMPILED_TEMPLATE_BY_NAME, templateName);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.modules.dust.store.TemplateStore#updateTemplate(com.modules.dust.Template)
	 */
	@Override
	public Template updateTemplate(Template template) {
		Template originalTemplate = this.keyStore.get(TEMPLATE_BY_ID, template.getId());
		this.keyStore.remove(COMPILED_TEMPLATE_BY_NAME, originalTemplate.getName());
		if (!originalTemplate.getName().equals(template.getName())) {
			this.keyStore.remove(TEMPLATE_BY_NAME, originalTemplate.getName());
		}
		this.keyStore.put(TEMPLATE_BY_NAME, template.getName(), template);
		this.keyStore.put(TEMPLATE_BY_ID, template.getId(), template);
		return template;
	}
	
	/* (non-Javadoc)
	 * @see com.modules.dust.store.TemplateStore#deleteTemplate(com.modules.dust.Template)
	 */
	@Override
	public void deleteTemplate(Template template) {
		this.keyStore.remove(TEMPLATE_BY_NAME, template.getName());
		this.keyStore.remove(TEMPLATE_BY_ID, template.getId());
		this.keyStore.remove(COMPILED_TEMPLATE_BY_NAME, template.getName());
	}

}
