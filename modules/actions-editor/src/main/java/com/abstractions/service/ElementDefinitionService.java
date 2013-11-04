package com.abstractions.service;

import com.abstractions.model.ElementDefinition;
import com.abstractions.repository.GenericRepository;
import java.util.List;
import org.jsoup.helper.Validate;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Guido J. Celada
 */
public class ElementDefinitionService {
        private GenericRepository repository;
        private LibraryService libraryService;
	
	protected ElementDefinitionService() { }
	
	public ElementDefinitionService(GenericRepository repository, LibraryService libraryService) {
		Validate.notNull(repository);
		
		this.repository = repository;
                this.libraryService = libraryService;
	}

	@Transactional
	public void add(ElementDefinition elementDefinition) {
		this.repository.save(elementDefinition);
	}
        
        @Transactional
        public ElementDefinition get(long id){
            return this.repository.get(ElementDefinition.class, id);
        }
        
        @Transactional
        public List<ElementDefinition> getElementDefinitions(){
            return this.repository.get(ElementDefinition.class, "displayName");
        }
       
        @Transactional
        public void remove(long id){
            this.repository.delete(ElementDefinition.class, id);
        }
        
        @Transactional
        public List<ElementDefinition> getElementDefinitionsOf(long libraryId){
            return libraryService.get(libraryId).getDefinitions();
        }
}
