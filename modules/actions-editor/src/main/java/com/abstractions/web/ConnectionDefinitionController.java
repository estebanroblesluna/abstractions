package com.abstractions.web;

import com.abstractions.model.ConnectionDefinition;
import com.abstractions.model.Library;
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
@RequestMapping("/libraries/{libraryId}/definitions/addConnection")
public class ConnectionDefinitionController {
    @Autowired
    LibraryService libraryService;    
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView add(@PathVariable("libraryId") long libraryId) {
        ModelAndView mv = new ModelAndView("addConnectionDefinition");
        mv.addObject("libraryName",this.libraryService.get(libraryId).getDisplayName());
        return mv;
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@ModelAttribute("form") AddConnectionDefinitionForm form, @PathVariable("libraryId") long libraryId) {
        ElementDefinitionController.uploadFile(form.getIcon());
        ConnectionDefinition connectionDefinition = new ConnectionDefinition(form.getName());
        connectionDefinition = (ConnectionDefinition) ElementDefinitionController.createElementDefinition(form, connectionDefinition);
        connectionDefinition.setColor(form.getColor());
        connectionDefinition.setAcceptedTargetTypes(form.getAcceptedTargetTypes());
        connectionDefinition.setAcceptedTargetMax(form.getAcceptedTargetMax());
        connectionDefinition.setAcceptedSourceMax(form.getAcceptedSourceMax());
        connectionDefinition.setAcceptedSourceTypes(form.getAcceptedSourceTypes());
        Library l = this.libraryService.get(libraryId);
        l.addDefinition(connectionDefinition); 
        libraryService.update(l);
        return "redirect:/libraries/{libraryId}/definitions/";
    }
}
