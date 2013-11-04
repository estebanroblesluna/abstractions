package com.abstractions.web;

import com.abstractions.model.ElementDefinition;
import com.abstractions.service.ElementDefinitionService;
import com.abstractions.service.LibraryService;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Guido J. Celada
 */
@Controller
@RequestMapping("/libraries/{libraryId}/definitions")
public class ElementDefinitionController {
    
    @Autowired
    ElementDefinitionService service;
    
    @Autowired
    LibraryService libraryService;
    
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home(@PathVariable("libraryId") long libraryId) {
        ModelAndView mv = new ModelAndView("definitions");
        List<ElementDefinition> definitions = service.getElementDefinitionsOf(libraryId);
        String libraryName = libraryService.get(libraryId).getDisplayName();
        mv.addObject("definitions",definitions);
        mv.addObject("libraryName",libraryName);
        return mv;
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add(@PathVariable("libraryId") long libraryId) {
        ModelAndView mv = new ModelAndView("addDefinition");
        List<String> addLinks = new ArrayList<String>();
        addLinks.add("addConnection");
        addLinks.add("addFlow");
        addLinks.add("addMessageSource");
        addLinks.add("addProcessor");
        addLinks.add("addRouter");
        mv.addObject("addLinks",addLinks);
        String libraryName = libraryService.get(libraryId).getDisplayName();
        mv.addObject("libraryName",libraryName);
        return mv;
    }
    
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String remove(@ModelAttribute("form") RemoveForm form) {
        for (long id : form.getIdsToRemove()) 
            this.service.remove(id);
        return "redirect:/libraries/{libraryId}/definitions/";
    }
    
    @RequestMapping(value = "/show/{definitionId}", method = RequestMethod.GET)
    public ModelAndView show(@PathVariable("libraryId") long libraryId, @PathVariable("definitionId") long definitionId) {
        ElementDefinition elementDefinition = this.service.get(definitionId);
        
        String[] classNameArray = elementDefinition.getClass().toString().split("\\.");
        String className = classNameArray[classNameArray.length - 1];
        ModelAndView mv = new ModelAndView("show" + className); //creates for ex: showConnectionDefinition
         
        String libraryName = libraryService.get(libraryId).getDisplayName();
        mv.addObject("libraryName",libraryName);
        mv.addObject("definition", elementDefinition);
        return mv;
    }
    
    public static ElementDefinition createElementDefinition(AddElementDefinitionForm form, ElementDefinition elementDefinition){
        elementDefinition.setName(form.getName());
        elementDefinition.setDisplayName(form.getDisplayName());
        elementDefinition.setIcon("Resources/" + form.getIcon().getOriginalFilename());
        if (form.getIsScript())
            elementDefinition.setScript(form.getImplementation());
        else
            elementDefinition.setClassName(form.getImplementation());
        
        return elementDefinition;
    }
    
    public static boolean uploadFile(CommonsMultipartFile uploadedFile) {
        if (uploadedFile != null) {
            InputStream inputStream = null;
            OutputStream outputStream = null;

            MultipartFile file = uploadedFile;

            String fileName = file.getOriginalFilename();

            try {
                inputStream = file.getInputStream();

                java.io.File newFile = new java.io.File("src/main/webapp/editor/Resources/" + fileName);
                if (!newFile.exists()) {
                    newFile.createNewFile();
                }
                outputStream = new FileOutputStream(newFile);
                int read = 0;
                byte[] bytes = new byte[1024];

                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block  
                e.printStackTrace();
                return false;
            }
            return true;
        } else {
            System.out.println("uploadFile is NULL");
            return false;
        }
      
    }
    
    
}
