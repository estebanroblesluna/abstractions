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
import com.abstractions.service.UserService;

@Controller
@RequestMapping("/teams")
public class TeamController {

	@Autowired
	TeamService service;

	@Autowired
	UserService userService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView mv = new ModelAndView("teams");

		List<Team> teams = this.service.getTeamsOf(this.userService.getCurrentUser().getId());
		mv.addObject("teams", teams);

		return mv; 
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addTeam() {
		return new ModelAndView("addTeam");
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addTeam(@ModelAttribute("form") AddTeamForm form) {
		this.service.addTeam(form.getName(), this.userService.getCurrentUser().getId());
		return "redirect:/teams/";
	}
}
