package com.abstractions.service;

import java.io.InputStream;
import java.util.List;

public interface ResourceProcessor {

	public abstract List<ResourceChange> process(String resourcePath, InputStream inputStream);

}