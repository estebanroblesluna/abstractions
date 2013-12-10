package com.abstractions.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abstractions.model.Team;
import com.abstractions.service.TeamService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
@RequestMapping("/teams")
public class TeamController {

	@Autowired
	TeamService service;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView mv = new ModelAndView("teams");

		List<Team> teams = this.service.getTeamsOf(WebUser.getCurrentUserId());
		mv.addObject("teams", teams);

		return mv; 
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addTeam() {
		return new ModelAndView("addTeam");
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addTeam(@ModelAttribute("form") AddTeamForm form) {
		this.service.addTeam(form.getName(), WebUser.getCurrentUserId());
		return "redirect:/teams/";
	}
}
