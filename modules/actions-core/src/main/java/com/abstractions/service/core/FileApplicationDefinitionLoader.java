package com.abstractions.service.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.helper.Validate;

import com.abstractions.common.ElementDefinitionMarshaller;
import com.abstractions.meta.ApplicationDefinition;
import com.abstractions.meta.ElementDefinition;
import com.abstractions.model.Library;
import com.abstractions.service.repository.CompositeTemplateMarshaller;
import com.abstractions.service.repository.MarshallingException;
import com.abstractions.template.CompositeTemplate;

public class FileApplicationDefinitionLoader implements ApplicationDefinitionLoader {

	private static final Log log = LogFactory.getLog(FileApplicationDefinitionLoader.class);

	private final String baseDirectory;
  private final PropertiesLoader propertiesLoader;

	public FileApplicationDefinitionLoader(String baseDirectory, PropertiesLoader propertiesLoader) {
		Validate.notNull(baseDirectory);
		
		this.baseDirectory = baseDirectory;
    this.propertiesLoader = propertiesLoader;
	}
	
	@Override
	public ApplicationDefinition load(long applicationId, NamesMapping mapping) {
		ApplicationDefinition appDefinition = new ApplicationDefinition("App " + applicationId, this.propertiesLoader);
		appDefinition.setId(applicationId);
		
		File applicationDirectory = new File(this.baseDirectory + "/" + applicationId);

		if (applicationDirectory.exists()) {
			NamesMapping appMapping = appDefinition.getMapping(); 
			this.loadMapping(appMapping, applicationDirectory);
			
			File flowDirectory = new File(applicationDirectory, "flows");
			for (File flowFile : flowDirectory.listFiles()) {
				this.readFlowFromFile(appDefinition, flowFile);
			}
		}
		
		return appDefinition;
	}

	private void loadMapping(NamesMapping mapping, File applicationDirectory) {
		File mappingFile = new File(applicationDirectory, "files/_definitions/commons.json");

		if (mappingFile.exists()) {
			InputStream io = null;
			
			try {
				io = new FileInputStream(mappingFile);
				String definitionsAsJSON = IOUtils.toString(io);
				List<ElementDefinition> elements = ElementDefinitionMarshaller.unmarshall(definitionsAsJSON);
				
				Library library = new Library("elements");
				
				for (ElementDefinition definition : elements) {
					library.addDefinition(definition);
				}
				
				library.createMappings(mapping);
			} catch (IOException e) {
				log.warn("Error parsing mapping", e);
			} finally {
				IOUtils.closeQuietly(io);
			}
		}
	}
	
	private void readFlowFromFile(ApplicationDefinition appDefinition, File flowFile) {
		try {
			String flowDefinition = FileUtils.readFileToString(flowFile);

			CompositeTemplateMarshaller marshaller = new CompositeTemplateMarshaller(appDefinition.getMapping());
			CompositeTemplate composite = marshaller.unmarshall(appDefinition, flowDefinition);
			appDefinition.addDefinition(composite);
		} catch (IOException e) {
			log.warn("Error reading flow (ignoring for now) " + flowFile.getAbsolutePath(), e);
		} catch (MarshallingException e) {
			log.error("Error reading definition", e);
		}
	}
}
