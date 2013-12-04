package com.abstractions.web;

import com.abstractions.model.Library;
import com.abstractions.meta.ProcessorDefinition;
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
@RequestMapping("/libraries/{libraryId}/definitions/addProcessor")
public class ProcessorDefinitionController {
    @Autowired
    LibraryService libraryService;     
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView add(@PathVariable("libraryId") long libraryId) {
        ModelAndView mv = new ModelAndView("addProcessorDefinition");
        mv.addObject("libraryName",this.libraryService.get(libraryId).getDisplayName());
        return mv;
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@ModelAttribute("form") AddProcessorDefinitionForm form, @PathVariable("libraryId") long libraryId) {
        ProcessorDefinition processorDefinition = new ProcessorDefinition(form.getName());
        processorDefinition = (ProcessorDefinition) ElementDefinitionController.createElementDefinition(form, processorDefinition);
        Library l = this.libraryService.get(libraryId);
        l.addDefinition(processorDefinition); 
        libraryService.update(l);
        return "redirect:/libraries/{libraryId}/definitions/";
    }
}
