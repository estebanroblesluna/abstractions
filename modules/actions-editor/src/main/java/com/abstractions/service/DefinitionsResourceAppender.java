package com.abstractions.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.abstractions.common.ElementDefinitionMarshaller;
import com.abstractions.meta.ElementDefinition;
import com.abstractions.model.Resource;

public class DefinitionsResourceAppender implements ResourceAppender {

	private static final Log log = LogFactory.getLog(DefinitionsResourceAppender.class);
	
	private ElementDefinitionService elementDefinitionService;
	
	public DefinitionsResourceAppender(ElementDefinitionService elementDefinitionService) {
		this.elementDefinitionService = elementDefinitionService;
	}
	
	@Override
	public List<Resource> getResources() {
		List<ElementDefinition> definitions = this.elementDefinitionService.getElementDefinitions();
		List<Resource> resources = new ArrayList<Resource>();
		
		try {
			String marshalledDefinitions = ElementDefinitionMarshaller.marshall(definitions);
			
			Resource definitionsResource = new Resource(-1, "_definitions/commons.json", marshalledDefinitions.getBytes(), null);
			resources.add(definitionsResource);
			log.info("Finished generating definitions resources");
		} catch (JsonGenerationException e) {
			log.error("Error marshalling defintions into snapshot", e);
		} catch (JsonMappingException e) {
			log.error("Error marshalling defintions into snapshot", e);
		} catch (IOException e) {
			log.error("Error marshalling defintions into snapshot", e);
		}

		return resources;
	}
}
