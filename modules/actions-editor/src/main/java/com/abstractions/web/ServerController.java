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
import com.abstractions.service.ServerService;

@Controller
public class ServerController {

	@Autowired
	ServerService service;

	@RequestMapping(value = "/teams/{teamId}/serverGroups/{serverGroupId}/servers/", method = RequestMethod.GET)
	public ModelAndView home(@PathVariable("teamId") long teamId, @PathVariable("serverGroupId") long serverGroupId) {
		ModelAndView mv = new ModelAndView("servers");
		
		List<Server> servers = this.service.getServers(teamId, serverGroupId);
		mv.addObject("servers", servers);
		
		return mv;
	}
	
	@RequestMapping(value = "/teams/{teamId}/serverGroups/{serverGroupId}/servers/add", method = RequestMethod.GET)
	public ModelAndView add() {
		
		return new ModelAndView("addServer");
	}
	
	@RequestMapping(value = "/teams/{teamId}/serverGroups/{serverGroupId}/servers/add", method = RequestMethod.POST)
	public String addContact(@ModelAttribute("form") AddServerForm form, @PathVariable("teamId") long teamId, @PathVariable("serverGroupId") long serverGroupId) {
		this.service.addServer(serverGroupId, form.getName(), form.getIpDNS());
		return "redirect:/servers/";
	}
}
