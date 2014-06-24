package com.abstractions.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RemoveForm {

	private String objectsToRemove;

	public String getObjectsToRemove() {
		return objectsToRemove;
	}

	public void setObjectsToRemove(String applicationsToRemoveById) {
		this.objectsToRemove = applicationsToRemoveById;
	}
	
	public Collection<Long> getIdsToRemove() {
		List<Long> ids = new ArrayList<Long>();
		for (String id : this.objectsToRemove.split(",")) {
		  try{
		    ids.add(Long.parseLong(id));
		  } catch (NumberFormatException e){
		    if(id != "")
		      throw e; 
		  }
		}
		return ids;
	}
	
}
