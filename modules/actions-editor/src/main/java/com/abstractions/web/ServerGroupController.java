package com.abstractions.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abstractions.model.ServerGroup;
import com.abstractions.service.ServerGroupService;

@Controller
public class ServerGroupController {
	
	@Autowired
	ServerGroupService service;
	
	@RequestMapping(value = "/teams/{teamId}/serverGroups", method = RequestMethod.GET)
	public ModelAndView home(@PathVariable("teamId") long teamId) {
		ModelAndView mv = new ModelAndView("serverGroups");

		List<ServerGroup> apps = this.service.getServerGroupsOf(teamId);
		mv.addObject("serverGroups", apps);
		mv.addObject("teamId", teamId);

		return mv;
	}

	@RequestMapping(value = "/teams/{teamId}/serverGroups/add", method = RequestMethod.GET)
	public ModelAndView addContact() {
		return new ModelAndView("addServerGroup");
	}

	@RequestMapping(value = "/teams/{teamId}/serverGroups/add", method = RequestMethod.POST)
	public String addContact(@PathVariable("teamId") long teamId,
			@ModelAttribute("form") AddApplicationForm form) {
		this.service.addServerGroup(teamId, form.getName());
		return "redirect:/teams/" + teamId + "/serverGroups/";
	}
}
