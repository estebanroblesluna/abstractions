package com.abstractions.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abstractions.model.Application;
import com.abstractions.service.ApplicationService;
import com.abstractions.service.TeamService;

@Controller
public class ApplicationController {

	@Autowired
	ApplicationService service;

	@Autowired
	TeamService teamService;

	@RequestMapping(value = "/teams/{teamId}/applications", method = RequestMethod.GET)
	public ModelAndView home(@PathVariable("teamId") long teamId) {
		ModelAndView mv = new ModelAndView("applications");

		List<Application> apps = this.service.getApplicationsOf(teamId);
		mv.addObject("applications", apps);

		return mv; 
	}

	@RequestMapping(value = "/teams/{teamId}/applications/add", method = RequestMethod.GET)
	public ModelAndView addContact() {
		return new ModelAndView("addApplication");
	}

	@RequestMapping(value = "/teams/{teamId}/applications/add", method = RequestMethod.POST)
	public String addContact(@PathVariable("teamId") long teamId, @ModelAttribute("form") AddApplicationForm form) {
		this.service.addApplication(teamId, form.getName());
		return "redirect:/teams/" + teamId + "/applications/";
	}
}
