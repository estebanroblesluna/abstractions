package com.abstractions.model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;

public class Resource {
	
	private byte[] data;
	private long applicationId;
	private long id;
	private Date lastModifiedDate;
	private String path;
	private String type;
	private boolean isSnapshot;
	protected Resource() {}; //Hibernate needs this
	
	/**
	 * @param data the data to set
	 */
	public Resource(long applicationId, String path, byte[] data, String type){
		this.setData(data);
		this.setApplicationId(applicationId);
		this.setPath(path);
		this.setLastModifiedDate(new Date());
		this.setType(type);
		this.setSnapshot(false);
	}

	/**
	 * @return the data
	 */
	public byte[] getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(byte[] data) {
		this.data = data;
	}

	/**
	 * @return the applicationId
	 */
	public long getApplicationId() {
		return applicationId;
	}

	/**
	 * @param applicationId the applicationId to set
	 */
	public void setApplicationId(long applicationId) {
		this.applicationId = applicationId;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		if(path.startsWith("/"))
			path = path.substring(1);	//Normalize the path
		this.path = path;
	}

	/**
	 * @return the lastModifiedDate
	 */
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	/**
	 * @param lastModifiedDate the lastModifiedDate to set
	 */
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the isSnapshot
	 */
	public boolean isSnapshot() {
		return isSnapshot;
	}

	/**
	 * @param isSnapshot the isSnapshot to set
	 */
	public void setSnapshot(boolean isSnapshot) {
		this.isSnapshot = isSnapshot;
	}

	public Resource makeSnapshot(){
		Resource clone = new Resource(this.applicationId,this.path,this.data,this.type);
		clone.lastModifiedDate = this.lastModifiedDate;
		clone.isSnapshot = true;
		return clone;
	}
	
	public boolean isDirectory(){
	  return this.path.substring(this.path.lastIndexOf("/")+1).equals(".");
	}
	
	public InputStream getInputStream(){
		return new ByteArrayInputStream(data);
	}
}
