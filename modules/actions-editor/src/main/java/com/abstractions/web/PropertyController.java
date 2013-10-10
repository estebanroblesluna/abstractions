package com.abstractions.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abstractions.model.Property;
import com.abstractions.service.ApplicationService;
import com.abstractions.service.PropertiesService;
import com.abstractions.service.TeamService;

@Controller
public class PropertyController {

        @Autowired
	ApplicationService applicationService;
        
	@Autowired
	PropertiesService service;
        
        @Autowired
	TeamService teamService;
	
	@RequestMapping(value = "/teams/{teamId}/applications/{applicationId}/properties", method = RequestMethod.GET)
	public ModelAndView home(@PathVariable("teamId") long teamId, @PathVariable("applicationId") long applicationId) {
		ModelAndView mv = new ModelAndView("properties");
		String applicationName = this.applicationService.getApplication(applicationId).getName();
		List<Property> properties = this.service.getProperties(teamId, applicationId);
                String teamName = this.teamService.getTeam(teamId).getName();
		mv.addObject("teamName", teamName);
		mv.addObject("properties", properties);
                mv.addObject("applicationName", applicationName);
                
		return mv;
	}
	
	@RequestMapping(value = "/teams/{teamId}/applications/{applicationId}/properties/add", method = RequestMethod.POST)
	public String addContact(@PathVariable("teamId") long teamId, @PathVariable("applicationId") long applicationId, @ModelAttribute("form") AddPropertyForm form) {
		this.service.addProperty(applicationId, form.getName(), form.getValue(), form.getEnvironment());
		return "redirect:/teams/" + teamId + "/applications/" + applicationId + "/properties/";
	}
	
	@RequestMapping(value = "/teams/{teamId}/applications/{applicationId}/properties/add", method = RequestMethod.GET)
	public ModelAndView add(@PathVariable("teamId") long teamId, @PathVariable("applicationId") long applicationId) {
		ModelAndView mv = new ModelAndView("addProperty");
                String teamName = this.teamService.getTeam(teamId).getName();
                String applicationName = this.applicationService.getApplication(applicationId).getName();
		mv.addObject("teamName", teamName);
                mv.addObject("applicationName", applicationName);
		return mv;
	}

	@RequestMapping(value = "/teams/{teamId}/applications/{applicationId}/properties/remove", method = RequestMethod.POST)
	public String removeApplication(@PathVariable("teamId") long teamId, @PathVariable("applicationId") long applicationId, @ModelAttribute("form") RemoveForm form) {
		this.service.removePropertiesByIds(form.getIdsToRemove());
		return "redirect:/teams/" + teamId + "/applications/" + applicationId + "/properties/";
	}
}
