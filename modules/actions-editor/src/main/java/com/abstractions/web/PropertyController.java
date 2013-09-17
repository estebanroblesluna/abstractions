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
import com.abstractions.service.PropertiesService;

@Controller
public class PropertyController {

	@Autowired
	PropertiesService service;
	
	@RequestMapping(value = "/teams/{teamId}/applications/{applicationId}/properties", method = RequestMethod.GET)
	public ModelAndView home(@PathVariable("teamId") long teamId, @PathVariable("applicationId") long applicationId) {
		ModelAndView mv = new ModelAndView("properties");
		
		List<Property> properties = this.service.getProperties(teamId, applicationId);
		mv.addObject("properties", properties);

		return mv;
	}
	
	@RequestMapping(value = "/teams/{teamId}/applications/{applicationId}/properties/add", method = RequestMethod.POST)
	public String addContact(@PathVariable("teamId") long teamId, @PathVariable("applicationId") long applicationId, @ModelAttribute("form") AddPropertyForm form) {
		this.service.addProperty(applicationId, form.getName(), form.getValue());
		return "redirect:/teams/" + teamId + "/applications/" + applicationId + "/properties/";
	}
	
	@RequestMapping(value = "/teams/{teamId}/applications/{applicationId}/properties/add", method = RequestMethod.GET)
	public ModelAndView add() {
		ModelAndView mv = new ModelAndView("addProperty");
		return mv;
	}

	@RequestMapping(value = "/teams/{teamId}/applications/{applicationId}/properties/remove", method = RequestMethod.POST)
	public String removeApplication(@PathVariable("teamId") long teamId, @PathVariable("applicationId") long applicationId, @ModelAttribute("form") RemoveForm form) {
		this.service.removePropertiesByIds(form.getIdsToRemove());
		return "redirect:/teams/" + teamId + "/applications/" + applicationId + "/properties/";
	}
}