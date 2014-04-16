package com.abstractions.service;

import com.abstractions.common.Icon;
import com.abstractions.repository.GenericRepository;
import org.jsoup.helper.Validate;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Guido J. Celada
 */
public class IconService {
    private GenericRepository repository;
    
    protected IconService() { }
    
    public IconService(GenericRepository repository) {
        Validate.notNull(repository);	
        this.repository = repository;
    }
    
    @Transactional
    public Icon get(long id){
        return this.repository.get(Icon.class, id);
    }
}
