package com.abstractions.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abstractions.model.Server;
import com.abstractions.service.ServerService;

@Controller
@RequestMapping("/servers")
public class ServerController {

	@Autowired
	ServerService service;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView("servers");
		
		List<Server> servers = this.service.getServers();
		mv.addObject("servers", servers);
		
		return mv;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add() {
		return new ModelAndView("addServer");
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addContact(@ModelAttribute("form") AddServerForm form) {
		this.service.addServer(form.getName(), form.getIpDNS());
		return "redirect:/servers/";
	}
}
