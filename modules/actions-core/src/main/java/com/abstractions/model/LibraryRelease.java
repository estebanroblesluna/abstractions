package com.abstractions.model;


public class LibraryRelease {

	private Library library;
	private String version;

	public LibraryRelease(Library library, String version) {
		this.library = library;
		this.version = version;
	}

	public Library getLibrary() {
		return library;
	}

	public String getVersion() {
		return version;
	}
}
