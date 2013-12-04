package com.abstractions.web;

import com.abstractions.common.Icon;
import com.abstractions.meta.ElementDefinition;
import com.abstractions.service.ElementDefinitionService;
import com.abstractions.service.LibraryService;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
//TODO: Refactor this to make a hierarchy of ElementDefinition controllers

@Controller
@RequestMapping("/libraries/{libraryId}/definitions")
public class ElementDefinitionController {

    @Autowired
    ElementDefinitionService service;
    @Autowired
    LibraryService libraryService;
    private static final Log log = LogFactory.getLog(ElementDefinitionController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home(@PathVariable("libraryId") long libraryId) {
        ModelAndView mv = new ModelAndView("definitions");
        List<ElementDefinition> definitions = service.getElementDefinitionsOf(libraryId);
        String libraryName = libraryService.get(libraryId).getDisplayName();
        mv.addObject("definitions", definitions);
        mv.addObject("libraryName", libraryName);
        return mv;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add(@PathVariable("libraryId") long libraryId) {
        ModelAndView mv = new ModelAndView("addDefinition");
        List<String> addLinks = new ArrayList<String>();
        addLinks.add("addConnection");
        addLinks.add("addMessageSource");
        addLinks.add("addProcessor");
        addLinks.add("addRouter");
        mv.addObject("addLinks", addLinks);
        String libraryName = libraryService.get(libraryId).getDisplayName();
        mv.addObject("libraryName", libraryName);
        return mv;
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String remove(@ModelAttribute("form") RemoveForm form) {
        for (long id : form.getIdsToRemove()) {
            this.service.remove(id);
        }
        return "redirect:/libraries/{libraryId}/definitions/";
    }

    @RequestMapping(value = "/show/{definitionId}", method = RequestMethod.GET)
    public ModelAndView show(@PathVariable("libraryId") long libraryId, @PathVariable("definitionId") long definitionId) {
        ElementDefinition elementDefinition = this.service.get(definitionId);

        String[] classNameArray = elementDefinition.getClass().toString().split("\\.");
        String className = classNameArray[classNameArray.length - 1];
        ModelAndView mv = new ModelAndView("show" + className); //creates for ex: showConnectionDefinition

        String libraryName = libraryService.get(libraryId).getDisplayName();
        mv.addObject("libraryName", libraryName);
        mv.addObject("definition", elementDefinition);
        return mv;
    }

    public static ElementDefinition createElementDefinition(AddElementDefinitionForm form, ElementDefinition elementDefinition) {
        elementDefinition.setName(form.getName());
        elementDefinition.setDisplayName(form.getDisplayName());
        elementDefinition.setIcon(new Icon(form.getIcon().getBytes()));
        if (form.getIsScript()) {
            elementDefinition.setScript(form.getImplementation());
        } else {
            elementDefinition.setClassName(form.getImplementation());
        }

        return elementDefinition;
    }
}
