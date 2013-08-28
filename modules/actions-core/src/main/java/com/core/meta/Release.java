package com.core.meta;

public class Release {

	private Library library;
	private String version;

	public Release(Library library, String version) {
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
