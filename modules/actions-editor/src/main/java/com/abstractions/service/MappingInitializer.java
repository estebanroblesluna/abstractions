package com.abstractions.service;

import java.util.List;

import com.abstractions.model.Library;
import com.core.meta.Meta;
import com.service.core.NamesMapping;

public class MappingInitializer {

	private NamesMapping mapping;
	private LibraryService libraryService;

	public void initializeMapping() {
		List<Library> libraries = this.libraryService.getCommonLibraries();
		
		for (Library library : libraries) {
			library.createMappings(mapping);
		}
	}
	
	public void setupInitialLibraries() {
		Library common = Meta.getCommonLibrary();
		Library modules = Meta.getModulesLibrary();
		
		this.libraryService.add(common);
		this.libraryService.add(modules);
	}
	
	public NamesMapping getMapping() {
		return mapping;
	}

	public void setMapping(NamesMapping mapping) {
		this.mapping = mapping;
	}

	public LibraryService getLibraryService() {
		return libraryService;
	}

	public void setLibraryService(LibraryService libraryService) {
		this.libraryService = libraryService;
	}
}
