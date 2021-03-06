package com.abstractions.service.core;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.NullInputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
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
	public boolean storeResource(long applicationId, String path, InputStream stream) {
	  if(this.folderExists(applicationId, path))
	    return false;
	  path = this.fixFilePath(path);
		//Create folders if not created yet.
	  int lastSlash = path.lastIndexOf("/");
	  if(lastSlash != -1)
	    this.createFolder(applicationId,path.substring(0,lastSlash));
	  this.deleteResource(applicationId,path);    //Avoid duplicates
		this.storeFile(applicationId,path,stream);
		return true;
		
	}

	private void storeFile(long applicationId, String path, InputStream stream) {
    Resource resource;
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
			  if(zipEntry.isDirectory())
			    this.createFolder(applicationId, zipEntry.getName().replace("." + File.separator, File.separator));
			  else
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
	
	@Override
  @Transactional(readOnly=true)
	public boolean resourceExists(long applicationId, String path){
	  path = this.fixFilePath(path);
		return this.repository.findBy(Resource.class,
				this.makeSearchRestrictions(applicationId, path)) != null;
	}
	
	@Override
  @Transactional(readOnly=true)
	public long getResourceLastModifiedDate(long applicationId, String path){
		return this.repository.findBy(Resource.class,
				this.makeSearchRestrictions(applicationId, path)).getLastModifiedDate().getTime();
	}

	@Override
	@Transactional(readOnly=true)
	public Resource getResource(long applicationId, String path) {
		return this.repository.findBy(Resource.class,
				this.makeSearchRestrictions(applicationId, path));

	}
	
	@Override
	@Transactional
	public boolean createFolder(long applicationId, String path){
	  if(this.resourceExists(applicationId, this.fixFilePath(path)))
	    return false;
	  path = this.fixFolderPath(path);
	  String [] folders = path.split("/");
    StringBuffer partialPath = new StringBuffer();
    InputStream emptyIS = new StringBufferInputStream("");
    for (int i =0; i<folders.length; i++){
      partialPath.append(folders[i]).append("/.");
      if(!this.resourceExists(applicationId,partialPath.toString()))
        this.storeFile(applicationId, partialPath.toString(), emptyIS);
      partialPath.delete(partialPath.length()-1,partialPath.length());
    }
    return true;
	}
	
	private String fixFolderPath(String path){
	   if(!path.endsWith("/"))
	     return path + "/";
	   return path;
	}
	
	private String fixFilePath(String path){
    if(path.endsWith("/"))
      return path.substring(0, path.length()-1);
    return path;
 }
	
	@Override
	@Transactional(readOnly=true)
	public boolean folderExists(long applicationId, String path){
	  return this.resourceExists(applicationId, path+".");
	}
	
	@Override
	@Transactional
	public void deleteFolder(long applicationId, String path){
	  this.repository.deleteFolder(applicationId,this.resourceType, this.fixFolderPath(path));
	}

}
