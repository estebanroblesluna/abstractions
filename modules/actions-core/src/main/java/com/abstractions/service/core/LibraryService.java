package com.abstractions.service.core;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.helper.Validate;
import org.springframework.transaction.annotation.Transactional;

import com.abstractions.meta.ElementDefinition;
import com.abstractions.model.Library;
import com.abstractions.repository.GenericRepository;

public class LibraryService {

	private GenericRepository repository;

	protected LibraryService() {
	}

	public LibraryService(GenericRepository repository) {
		Validate.notNull(repository);

		this.repository = repository;
	}

	@Transactional
	public List<Library> getCommonLibraries() {
		List<Library> libraries = new ArrayList<Library>();
		libraries.add((Library) this.repository.get(Library.class, 1l));
    libraries.add((Library) this.repository.get(Library.class, 2l));
    libraries.add((Library) this.repository.get(Library.class, 3l));
    return libraries;
	}
	
	 @Transactional
	  public List<Library> getAllLibraries() {
	    List<Library> libraries = new ArrayList<Library>();
	    libraries.add((Library) this.repository.get(Library.class, 1l));
	    libraries.add((Library) this.repository.get(Library.class, 2l));
	    libraries.add((Library) this.repository.get(Library.class, 3l));
      libraries.add((Library) this.repository.get(Library.class, 4l));
	    return libraries;
	  }

	@Transactional
	public void add(Library library) {
		this.repository.save(library);
	}

	@Transactional
	public Library get(long id) {
		return this.repository.get(Library.class, id);
	}

	@Transactional
	public List<Library> getLibraries() {
		return this.repository.get(Library.class, "displayName");
	}

	@Transactional
	public void remove(long id) {
		this.repository.delete(Library.class, id);
	}

	@Transactional
	public void addTo(long libraryId, ElementDefinition definition) {
		Library library = this.get(libraryId);
		library.addDefinition(definition);
		this.repository.save(definition);
		this.repository.save(library);
	}
        
    @Transactional
    public void update(Library library) {
        this.repository.update(library);
    }
}
