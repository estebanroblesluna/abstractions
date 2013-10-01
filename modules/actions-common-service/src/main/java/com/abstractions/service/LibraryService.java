package com.abstractions.service;

import java.util.List;

import org.jsoup.helper.Validate;
import org.springframework.transaction.annotation.Transactional;

import com.abstractions.model.Library;
import com.abstractions.repository.GenericRepository;

public class LibraryService {

	private GenericRepository repository;
	
	protected LibraryService() { }
	
	public LibraryService(GenericRepository repository) {
		Validate.notNull(repository);
		
		this.repository = repository;
	}
	
	@Transactional
	public List<Library> getCommonLibraries() {
		return this.repository.get(Library.class, "displayName");
	}

	@Transactional
	public void add(Library library) {
		this.repository.save(library);
	}
}