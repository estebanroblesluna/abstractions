package com.abstractions.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abstractions.model.Deployment;
import com.abstractions.model.Flow;
import com.abstractions.service.ApplicationService;
import com.abstractions.service.DeploymentService;
import com.abstractions.service.core.LibraryService;
import com.abstractions.service.ServerService;
import com.abstractions.service.TeamService;

@Controller
public class DeploymentController {

	@Autowired
	DeploymentService deploymentService;

	@Autowired
	ServerService serverService;

	@Autowired
	LibraryService libraryService;

	@Autowired
	ApplicationService applicationService;

	@Autowired
	TeamService teamService;

	@RequestMapping(value = "/teams/{teamId}/applications/{applicationId}/snapshots/{snapshotId}/deployments", method = RequestMethod.GET)
	public ModelAndView home(@PathVariable("snapshotId") long snapshotId,
			@PathVariable("teamId") long teamId,
			@PathVariable("applicationId") long applicationId) {
		ModelAndView mv = new ModelAndView("deployments");
		List<Deployment> deployments = this.deploymentService
				.getDeployments(snapshotId);
		String teamName = this.teamService.getTeam(teamId).getName();
		String applicationName = this.applicationService.getApplication(
				applicationId).getName();
		mv.addObject("teamName", teamName);
		mv.addObject("deployments", deployments);
		mv.addObject("applicationName", applicationName);
		return mv;
	}

	@RequestMapping(value = "/teams/{teamId}/applications/{applicationId}/snapshots/{snapshotId}/deployments/add", method = RequestMethod.GET)
	public ModelAndView addDeployment(
			@PathVariable("snapshotId") long snapshotId,
			@ModelAttribute("form") AddDeploymentForm form,
			@PathVariable("teamId") long teamId,
			@PathVariable("applicationId") long applicationId) {
		ModelAndView mv = new ModelAndView("addDeployment");
		String teamName = this.teamService.getTeam(teamId).getName();
		String applicationName = this.applicationService.getApplication(
				applicationId).getName();
		mv.addObject("teamName", teamName);
		mv.addObject("servers", this.serverService.getServers());
		mv.addObject("applicationName", applicationName);
		return mv;
	}

	@RequestMapping(value = "/teams/{teamId}/applications/{applicationId}/snapshots/{snapshotId}/deployments/add", method = RequestMethod.POST)
	public String createDeployment(@PathVariable("teamId") long teamId,
			@PathVariable("applicationId") long applicationId,
			@PathVariable("snapshotId") long snapshotId,
			@ModelAttribute("form") AddDeploymentForm form) {
		this.deploymentService.addDeployment(snapshotId,
				WebUser.getCurrentUserId(),
				new ArrayList<Long>(form.getServerIds()));
		return "redirect:/teams/" + teamId + "/applications/" + applicationId
				+ "/snapshots/" + snapshotId + "/deployments/";
	}

	@RequestMapping(value = "/teams/{teamId}/applications/{applicationId}/snapshots/{snapshotId}/deployments/{deploymentId}/flow/{flowId}", method = RequestMethod.GET)
	public ModelAndView viewDeployment(@PathVariable("teamId") long teamId,
			@PathVariable("applicationId") long applicationId,
			@PathVariable("deploymentId") long deploymentId,
			@PathVariable("flowId") long flowId) {
		ModelAndView mv = new ModelAndView("viewFlowDeployment");

		List<Flow> flows = this.deploymentService.getFlows(deploymentId);
		Flow flow = null;
		for (Flow iFlow : flows) {
			if ((long) iFlow.getId() == flowId) {
				flow = iFlow;
				break;
			}
		}
		mv.addObject("flow", flow);
		mv.addObject("libraries", FlowController.getLibraries(libraryService));
		
		return mv;
	}
}
