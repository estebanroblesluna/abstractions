package com.abstractions.web;

import com.abstractions.model.Library;
import com.abstractions.model.RouterDefinition;
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
@RequestMapping("/libraries/{libraryId}/definitions/addRouter")
public class RouterDefinitionController {
    @Autowired
    LibraryService libraryService;    
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView add(@PathVariable("libraryId") long libraryId) {
        ModelAndView mv = new ModelAndView("addRouterDefinition");
        mv.addObject("libraryName",this.libraryService.get(libraryId).getDisplayName());
        return mv;
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@ModelAttribute("form") AddRouterDefinitionForm form, @PathVariable("libraryId") long libraryId) {
        ElementDefinitionController.uploadFile(form.getIcon());
        RouterDefinition routerDefinition = new RouterDefinition(form.getName());
        routerDefinition = (RouterDefinition) ElementDefinitionController.createElementDefinition(form, routerDefinition);
        routerDefinition.setRouterEvaluatorImplementation(form.getRouterEvaluatorImplementation());
        routerDefinition.setRouterEvaluatorScript(form.getIsRouterEvaluatorScript());
        Library l = this.libraryService.get(libraryId);
        l.addDefinition(routerDefinition); 
        libraryService.update(l);
        return "redirect:/libraries/{libraryId}/definitions/";
    }
}
