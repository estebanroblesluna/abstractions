package com.abstractions.service.core;

import java.io.InputStream;
import java.util.List;

public interface ResourceService {

	public abstract void storeResource(long applicationId, String path, InputStream stream);

	public abstract InputStream getContentsOfResource(long applicationId, String path);

	public abstract List<String> listResources(long applicationId);

	public abstract void uncompressContent(long applicationId, InputStream stream);

	public abstract void deleteResource(long applicationId, String path);

	public abstract boolean resourceExists(long applicationId, String path);
	
	public abstract long getResourceLastModifiedDate(long applicationId, String path);

}