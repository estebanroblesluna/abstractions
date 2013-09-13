package com.abstractions.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abstractions.model.Flow;
import com.abstractions.service.FlowService;

@Controller
public class FlowController {

	@Autowired
	FlowService service;
	
	@RequestMapping(value = "/teams/{teamId}/applications/{applicationId}/flows", method = RequestMethod.GET)
	public ModelAndView home(@PathVariable("teamId") long teamId, @PathVariable("applicationId") long applicationId) {
		ModelAndView mv = new ModelAndView("flows");
		
		List<Flow> flows = this.service.getFlows(teamId, applicationId);
		mv.addObject("flows", flows);

		return mv;
	}
	
	@RequestMapping(value = "/teams/{teamId}/applications/{applicationId}/flows/add", method = RequestMethod.POST)
	public String addFlow(@PathVariable("teamId") long teamId, @PathVariable("applicationId") long applicationId, @ModelAttribute("form") AddFlowForm form) {
		this.service.addFlow(applicationId, form.getName(), form.getJson());
		return "redirect:/teams/" + teamId + "/applications/" + applicationId + "/flows/";
	}
	
	@RequestMapping(value = "/teams/{teamId}/applications/{applicationId}/flows/add", method = RequestMethod.GET)
	public ModelAndView add() {
		ModelAndView mv = new ModelAndView("addFlow");
		return mv;
	}

	@RequestMapping(value = "/teams/{teamId}/applications/{applicationId}/flows/remove", method = RequestMethod.POST)
	public String removeFlow(@PathVariable("teamId") long teamId, @PathVariable("applicationId") long applicationId, @ModelAttribute("form") RemoveForm form) {
		this.service.removeFlowsByIds(applicationId, form.getIdsToRemove());
		return "redirect:/teams/" + teamId + "/applications/" + applicationId + "/flows/";
	}
}
