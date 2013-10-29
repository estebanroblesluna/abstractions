package com.abstractions.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.abstractions.service.core.ResourceService;
import com.modules.dust.DustConnector;

public class DustTemplateResourceProcessor implements ResourceProcessor {

	private static Log log = LogFactory.getLog(DustTemplateResourceProcessor.class);
	
	private ResourceService fileService;
	private DustConnector dustConnector;

	public DustTemplateResourceProcessor(ResourceService fileService, DustConnector dustConnector) {
		this.fileService = fileService;
		this.dustConnector = dustConnector;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abstractions.service.FileProcessor#process()
	 */
	@Override
	public List<ResourceChange> process(String filename, InputStream inputStream) {
		if (filename.endsWith(".tl")) {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			try {
				IOUtils.copy(inputStream, output);
			} catch (IOException e) {
				log.error("Error compiling template", e);
			}
			String compiledTemplate = this.dustConnector.compile(filename, new String(output.toByteArray()));
			return Arrays.asList(new ResourceChange[] {
					new ResourceChange(filename, new ByteArrayInputStream(compiledTemplate.getBytes()), ResourceAction.CREATE_OR_UPDATE)
			});
		}
		return Arrays.asList(new ResourceChange[] {
			new ResourceChange(filename, inputStream, ResourceAction.CREATE_OR_UPDATE)
		});
	}

	public ResourceService getFileService() {
		return fileService;
	}

	public void setFileService(ResourceService fileService) {
		this.fileService = fileService;
	}

	public DustConnector getDustConnector() {
		return dustConnector;
	}

	public void setDustConnector(DustConnector dustConnector) {
		this.dustConnector = dustConnector;
	}

}
