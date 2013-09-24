package com.abstractions.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.modules.dust.DustConnector;

public class DustTemplateFileProcessor implements FileProcessor {

	private FileService fileService;
	private DustConnector dustConnector;

	/* (non-Javadoc)
	 * @see com.abstractions.service.FileProcessor#process()
	 */
	@Override
	public void process(String applicationId) {
		for (String filename : this.fileService.listFiles(applicationId)) {
			InputStream inputStream = this.fileService.getContentsOfFile(filename, applicationId);
			if (filename.endsWith(".tl")) {
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				
				try {
					IOUtils.copy(inputStream, output);
				} catch (IOException e) {
					e.printStackTrace();
				}
				String compiledTemplate = this.dustConnector.compile(filename, new String(output.toByteArray()));
				this.fileService.storeFile(applicationId, filename, new ByteArrayInputStream(compiledTemplate.getBytes()));
			}			
		}
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
