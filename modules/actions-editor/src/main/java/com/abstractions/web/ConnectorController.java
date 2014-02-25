package com.abstractions.web;

import com.abstractions.service.ApplicationService;
import com.abstractions.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/connectors")
public class ConnectorController {

        @Autowired
	ApplicationService applicationService;
        
        @Autowired
	TeamService teamService;
    
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView mv = new ModelAndView("connectors");
//                String applicationName = this.applicationService.getApplication(applicationId).getName();
//                String teamName = this.teamService.getTeam(teamId).getName();
//                mv.addObject("teamName", teamName);
//                mv.addObject("teamId", teamId);
//		mv.addObject("applicationId", applicationId);
//                mv.addObject("applicationName", applicationName);
		return mv;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add() {
		ModelAndView mv = new ModelAndView("addConnector");
		return mv;
	}
}
