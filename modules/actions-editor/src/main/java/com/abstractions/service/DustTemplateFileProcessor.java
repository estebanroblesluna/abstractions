package com.abstractions.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.modules.dust.DustConnector;

public class DustTemplateFileProcessor implements FileProcessor {

	private static Log log = LogFactory.getLog(DustTemplateFileProcessor.class);
	
	private FileService fileService;
	private DustConnector dustConnector;

	public DustTemplateFileProcessor(FileService fileService, DustConnector dustConnector) {
		this.fileService = fileService;
		this.dustConnector = dustConnector;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.abstractions.service.FileProcessor#process()
	 */
	@Override
	public InputStream process(String filename, InputStream inputStream) {
		if (filename.endsWith(".tl")) {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			try {
				IOUtils.copy(inputStream, output);
			} catch (IOException e) {
				log.error("Error compiling template", e);
			}
			String compiledTemplate = this.dustConnector.compile(filename, new String(output.toByteArray()));
			return new ByteArrayInputStream(compiledTemplate.getBytes());
		}
		return inputStream;
	}

	public FileService getFileService() {
		return fileService;
	}

	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}

	public DustConnector getDustConnector() {
		return dustConnector;
	}

	public void setDustConnector(DustConnector dustConnector) {
		this.dustConnector = dustConnector;
	}

}
