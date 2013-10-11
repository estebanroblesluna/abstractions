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
import com.abstractions.model.Team;
import com.abstractions.service.ServerGroupService;
import com.abstractions.service.TeamService;

@Controller
public class ServerGroupController {
	
	@Autowired
	ServerGroupService service;
	
	@Autowired
	TeamService teamService;
	
	@RequestMapping(value = "/teams/{teamId}/serverGroups", method = RequestMethod.GET)
	public ModelAndView home(@PathVariable("teamId") long teamId) {
		ModelAndView mv = new ModelAndView("serverGroups");

		List<ServerGroup> apps = this.service.getServerGroupsOf(teamId);
		Team team = this.teamService.getTeam(teamId);
		
		mv.addObject("serverGroups", apps);
		mv.addObject("teamId", teamId);
		mv.addObject("team", team);

		return mv;
	}

	@RequestMapping(value = "/teams/{teamId}/serverGroups/add", method = RequestMethod.GET)
	public ModelAndView addContact(@PathVariable("teamId") long teamId) {
		ModelAndView mv = new ModelAndView("addServerGroup");
                Team team = this.teamService.getTeam(teamId);
                mv.addObject("team", team);
                return mv;
	}

	@RequestMapping(value = "/teams/{teamId}/serverGroups/add", method = RequestMethod.POST)
	public String addContact(@PathVariable("teamId") long teamId,
			@ModelAttribute("form") AddServerGroupForm form) {
		this.service.addServerGroup(teamId, form.getName(), form.getEnvironment());
		return "redirect:/teams/" + teamId + "/serverGroups/";
	}
}
