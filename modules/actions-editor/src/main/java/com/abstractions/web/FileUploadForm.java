package com.abstractions.web;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadForm {

	private MultipartFile file;

	public FileUploadForm() {
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

}
