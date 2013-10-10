package com.abstractions.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abstractions.model.Server;
import com.abstractions.model.ServerGroup;
import com.abstractions.model.Team;
import com.abstractions.service.ServerGroupService;
import com.abstractions.service.ServerService;
import com.abstractions.service.TeamService;

@Controller
public class ServerController {

	@Autowired
	ServerService service;
        
        @Autowired
	TeamService teamService;
        
        @Autowired
	ServerGroupService serverGroupService;

	@RequestMapping(value = "/teams/{teamId}/serverGroups/{serverGroupId}/servers/", method = RequestMethod.GET)
	public ModelAndView home(@PathVariable("teamId") long teamId, @PathVariable("serverGroupId") long serverGroupId) {
		ModelAndView mv = new ModelAndView("servers");
		
		List<Server> servers = this.service.getServers(teamId, serverGroupId);
                ServerGroup serverGroup = this.serverGroupService.getServerGroup(serverGroupId);
		mv.addObject("servers", servers);
		Team team = this.teamService.getTeam(teamId);
		mv.addObject("team", team);
                mv.addObject("serverGroup", serverGroup);
		return mv;
	}
	
	@RequestMapping(value = "/teams/{teamId}/serverGroups/{serverGroupId}/servers/add", method = RequestMethod.GET)
	public ModelAndView add(@PathVariable("teamId") long teamId, @PathVariable("serverGroupId") long serverGroupId) {
                ModelAndView mv = new ModelAndView("addServer");
                ServerGroup serverGroup = this.serverGroupService.getServerGroup(serverGroupId);
		Team team = this.teamService.getTeam(teamId);
		mv.addObject("team", team);
                mv.addObject("serverGroup", serverGroup);
		return mv;
	}
	
	@RequestMapping(value = "/teams/{teamId}/serverGroups/{serverGroupId}/servers/add", method = RequestMethod.POST)
	public String addServer(@ModelAttribute("form") AddServerForm form, @PathVariable("teamId") long teamId, @PathVariable("serverGroupId") long serverGroupId) {
		this.service.addServer(serverGroupId, form.getName(), form.getIpDNS(), form.getPort());
		return "redirect:/teams/" + teamId + "/serverGroups/" + serverGroupId + "/servers/";
	}
}
