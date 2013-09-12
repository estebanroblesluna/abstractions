package com.abstractions.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abstractions.model.Application;
import com.abstractions.service.ApplicationService;

@Controller
@RequestMapping("/applications")
public class ApplicationController {

	@Autowired
	ApplicationService service;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView("applications");

		List<Application> apps = this.service.getApplications();
		mv.addObject("applications", apps);

		return mv; 
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addContact() {
		return new ModelAndView("addApplication");
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addApplication(@ModelAttribute("form") AddApplicationForm form) {
		this.service.addApplication(form.getName());
		return "redirect:/applications/";
	}
	
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public String removeApplication(@ModelAttribute("form") RemoveForm form) {
		for (long id : form.getIdsToRemove()) {
			this.service.removeApplicationById(id);
		}
		return "redirect:/applications/";
	}
}
