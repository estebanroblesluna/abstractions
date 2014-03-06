package com.abstractions.web;

import com.abstractions.model.Library;
import com.abstractions.service.core.LibraryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for CRUD of Library
 * @author Guido J. Celada
 */
@Controller
@RequestMapping("/libraries")
public class LibraryController {
    
    @Autowired
    LibraryService service;
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView mv = new ModelAndView("libraries");
        List<Library> libraries = service.getLibraries();
        mv.addObject("libraries",libraries);
        return mv;
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add() {
        return new ModelAndView("addLibrary");
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@ModelAttribute("form") AddLibraryForm form) {
        Library library = new Library(form.getName());
        library.setDisplayName(form.getDisplayName());
        this.service.add(library);
        return "redirect:/libraries/";
    }
    
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String remove(@ModelAttribute("form") RemoveForm form) {
        for (long id : form.getIdsToRemove()) 
            this.service.remove(id);
        return "redirect:/libraries/";
    }
}
