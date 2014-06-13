package com.abstractions.service.core;

import java.io.InputStream;
import java.util.List;

import com.abstractions.model.Resource;

public interface ResourceService {

	public abstract boolean storeResource(long applicationId, String path, InputStream stream);

	public abstract InputStream getContentsOfResource(long applicationId, String path);

	public abstract List<String> listResources(long applicationId);

	public abstract void uncompressContent(long applicationId, InputStream stream);

	public abstract void deleteResource(long applicationId, String path);

	public abstract boolean resourceExists(long applicationId, String path);
	
	public abstract long getResourceLastModifiedDate(long applicationId, String path);
	
	public abstract Resource getResource(long applicationId, String path);
	
	//Folders
	
	public abstract void  deleteFolder(long applicationId, String path);
	
	public abstract boolean createFolder(long applicationId, String path);
	
	public abstract boolean folderExists(long applicationId, String path);
}