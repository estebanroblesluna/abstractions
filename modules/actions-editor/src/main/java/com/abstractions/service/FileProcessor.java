package com.abstractions.service;

import java.io.InputStream;

public interface FileProcessor {

	public abstract InputStream process(String filename, InputStream inputStream);

}