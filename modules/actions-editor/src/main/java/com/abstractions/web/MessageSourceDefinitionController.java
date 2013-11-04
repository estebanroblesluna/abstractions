package com.abstractions.web;

import com.abstractions.model.Library;
import com.abstractions.model.MessageSourceDefinition;
import com.abstractions.service.ElementDefinitionService;
import com.abstractions.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Guido J. Celada
 */
@Controller
@RequestMapping("/libraries/{libraryId}/definitions/addMessageSource")
public class MessageSourceDefinitionController {
    @Autowired
    LibraryService libraryService;    
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView add(@PathVariable("libraryId") long libraryId) {
        ModelAndView mv = new ModelAndView("addMessageSourceDefinition");
        mv.addObject("libraryName",this.libraryService.get(libraryId).getDisplayName());
        return mv;
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@ModelAttribute("form") AddMessageSourceDefinitionForm form, @PathVariable("libraryId") long libraryId) {
        ElementDefinitionController.uploadFile(form.getIcon());
        MessageSourceDefinition messageSourceDefinition = new MessageSourceDefinition(form.getName());
        messageSourceDefinition = (MessageSourceDefinition) ElementDefinitionController.createElementDefinition(form, messageSourceDefinition);
        Library l = this.libraryService.get(libraryId);
        l.addDefinition(messageSourceDefinition); 
        libraryService.update(l);
        return "redirect:/libraries/{libraryId}/definitions/";
    }
}
