package com.abstractions.service;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;

import com.abstractions.meta.ApplicationDefinition;
import com.abstractions.model.Application;
import com.abstractions.model.ApplicationSnapshot;
import com.abstractions.model.Flow;
import com.abstractions.service.core.NamesMapping;
import com.abstractions.service.repository.CompositeTemplateMarshaller;
import com.abstractions.template.CompositeTemplate;
import com.abstractions.template.ElementTemplate;
import com.modules.dust.ResourceBasedDustTemplateCompiler;

public class DustCompilerSnapshotProcessor implements SnapshotProcessor {

	private ResourceBasedDustTemplateCompiler templateCompiler;
	
  public DustCompilerSnapshotProcessor(ResourceBasedDustTemplateCompiler templateCompiler) {
    super();
    this.templateCompiler = templateCompiler;
  }

	@Autowired
	private NamesMapping namesMapping;
	
	@Override
	public void process(Application application, ApplicationSnapshot snapshot) throws Exception {
		for (Flow flow : snapshot.getFlows()) {
			ApplicationDefinition applicationDefinition = new ApplicationDefinition(application.getName());
			CompositeTemplate template = new CompositeTemplateMarshaller(this.namesMapping).unmarshall(applicationDefinition, flow.getJson());
			Iterator<ElementTemplate> elementIterator = template.getDefinitions().values().iterator();
			while (elementIterator.hasNext()) {
				ElementTemplate element = elementIterator.next();
				if (element.getMeta().getName().equals("RESOURCE_DUST_RENDERER")) {
					
					this.templateCompiler.mergeAndCompile(
						application.getId(),
						"http://localhost:8080/service/fileStore/2/files/",
						element.getProperties().get("name"),
						element.getProperty("bodyTemplatePath"),
						element.getProperty("resourcesList"),
						element.getProperty("templateRenderingList"));
				}
			}				
		}
	}

}
