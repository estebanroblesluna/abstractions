package com.abstractions.service.core;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.abstractions.model.Resource;
import com.abstractions.repository.GenericRepository;

public class DatabaseResourceService implements ResourceService {

	private static Log log = LogFactory.getLog(DatabaseResourceService.class);
	private String resourceType;
	private GenericRepository repository;

	
	private HashMap<String,Object> makeSearchRestrictions(long applicationId, String path){
		HashMap<String, Object> restrictions = new HashMap<String,Object>();
		restrictions.put("applicationId", applicationId);
		restrictions.put("path", path);
		restrictions.put("type", this.resourceType);
		restrictions.put("isSnapshot", false);
		return restrictions;
	}
	
	private HashMap<String,Object> makeSearchRestrictions(long applicationId){
		HashMap<String, Object> restrictions = new HashMap<String,Object>();
		restrictions.put("applicationId", applicationId);
		restrictions.put("type", this.resourceType);
		restrictions.put("isSnapshot", false);
		return restrictions;
	}

	public DatabaseResourceService(GenericRepository repository, String resourceType) {
		this.repository = repository;
		this.resourceType = resourceType;
	}

	@Override
	@Transactional
	public void storeResource(long applicationId, String path, InputStream stream) {
		Resource resource;
		this.deleteResource(applicationId, path);
		try {
			resource = new Resource(applicationId, path, IOUtils.toByteArray(stream), this.resourceType);
			repository.save(resource);
		} catch (IOException e) {
			log.error("Error storing resource", e);
		}
	}

	@Override

	@Transactional(readOnly=true)
	public InputStream getContentsOfResource(long applicationId, String path){
		Resource resource = this.repository.findBy(Resource.class,
				this.makeSearchRestrictions(applicationId, path));
		if (resource == null) {
			return null;
		}
		return new ByteArrayInputStream(resource.getData());
	}

	@Override
	@Transactional(readOnly=true)
	public List<String> listResources(long applicationId){
		List<Resource> resources = this.repository.findAllBy(Resource.class, this.makeSearchRestrictions(applicationId));
		List<String> ret = new ArrayList<String>();
		for (Resource res : resources)
			ret.add(res.getPath());
		return ret;
	}

	@Override
	@Transactional
	public void uncompressContent(long applicationId, InputStream stream) {
		try {
			ZipInputStream zipInputStream = new ZipInputStream(stream);
			ZipEntry zipEntry = zipInputStream.getNextEntry();
			while (zipEntry != null) {
				if (zipEntry.getSize() == -1) {
					zipInputStream.closeEntry();
					zipEntry = zipInputStream.getNextEntry();
					continue;
				}
				this.storeResource(applicationId, zipEntry.getName().replace("." + File.separator, File.separator), zipInputStream);
				zipInputStream.closeEntry();
				zipEntry = zipInputStream.getNextEntry();
			}
			zipInputStream.close();
		} catch (Exception e) {
			log.error("Error uncompressing content", e);
		}
	}

	@Override
	@Transactional
	public void deleteResource(long applicationId, String path)
	{
		Resource toDelete = this.repository.findBy(Resource.class,
				this.makeSearchRestrictions(applicationId, path));
		if(toDelete != null)
			this.repository.delete(Resource.class,toDelete.getId());
	}
	
	public boolean resourceExists(long applicationId, String path){
		return this.repository.findBy(Resource.class,
				this.makeSearchRestrictions(applicationId, path)) != null;
	}
	
	public long getResourceLastModifiedDate(long applicationId, String path){
		return this.repository.findBy(Resource.class,
				this.makeSearchRestrictions(applicationId, path)).getLastModifiedDate().getTime();
	}

	@Override
	public Resource getResource(long applicationId, String path) {
		return this.repository.findBy(Resource.class,
				this.makeSearchRestrictions(applicationId, path));

	}

}
