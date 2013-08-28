package com.core.meta;

import java.util.ArrayList;
import java.util.List;

public class LibraryRepository {

	public List<Library> getLibraries() {
		List<Library> libraries = new ArrayList<Library>();
		libraries.add(Meta.getCommonLibrary());
		libraries.add(Meta.getModulesLibrary());
		return libraries;
	}
	
	public List<Release> getReleasesOfLibrary(String libraryName) {
		//TODO
		return new ArrayList<Release>();
	}
	
}
