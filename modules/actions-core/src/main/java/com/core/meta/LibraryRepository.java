package com.core.meta;

import java.util.ArrayList;
import java.util.List;

import com.abstractions.model.Library;
import com.abstractions.model.LibraryRelease;

public class LibraryRepository {

	public List<Library> getLibraries() {
		List<Library> libraries = new ArrayList<Library>();
		libraries.add(Meta.getCommonLibrary());
		libraries.add(Meta.getModulesLibrary());
		return libraries;
	}
	
	public List<LibraryRelease> getReleasesOfLibrary(String libraryName) {
		//TODO
		return new ArrayList<LibraryRelease>();
	}
	
}
