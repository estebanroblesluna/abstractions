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
import com.abstractions.service.DeploymentService;
import com.abstractions.service.LibraryService;
import com.abstractions.service.ServerService;

@Controller
public class DeploymentController {

	@Autowired
	DeploymentService deploymentService;
	
	@Autowired
	ServerService serverService;
	
	@Autowired
	LibraryService libraryService;
	
	@RequestMapping(value = "/teams/{teamId}/applications/{applicationId}/snapshots/{snapshotId}/deployments", method = RequestMethod.GET)
	public ModelAndView home(@PathVariable("snapshotId") long snapshotId, @PathVariable("teamId") long teamId, @PathVariable("applicationId") long applicationId) {
		ModelAndView mv = new ModelAndView("deployments");
		List<Deployment> deployments = this.deploymentService.getDeployments(snapshotId);
		mv.addObject("deployments", deployments);
		return mv;
	}
	
	@RequestMapping(value = "/teams/{teamId}/applications/{applicationId}/snapshots/{snapshotId}/deployments/add", method = RequestMethod.GET)
	public ModelAndView addDeployment(@PathVariable("snapshotId") long snapshotId, @ModelAttribute("form") AddDeploymentForm form,  @PathVariable("teamId") long teamId, @PathVariable("applicationId") long applicationId) {
		ModelAndView mv = new ModelAndView("addDeployment");
		mv.addObject("servers", this.serverService.getServers());
		return mv;
	}
	
	@RequestMapping(value = "/teams/{teamId}/applications/{applicationId}/snapshots/{snapshotId}/deployments/add", method = RequestMethod.POST)
	public String createDeployment(@PathVariable("teamId") long teamId, @PathVariable("applicationId") long applicationId, @PathVariable("snapshotId") long snapshotId, @ModelAttribute("form") AddDeploymentForm form) {
		this.deploymentService.addDeployment(snapshotId, WebUser.getCurrentUserId(), new ArrayList<Long>(form.getServerIds()));
		return "redirect:/teams/" + teamId + "/applications/" + applicationId + "/snapshots/" + snapshotId + "/deployments/";
	}
	
	@RequestMapping(value = "/teams/{teamId}/applications/{applicationId}/snapshots/{snapshotId}/deployments/{deploymentId}", method = RequestMethod.GET)
	public ModelAndView viewDeployment(@PathVariable("teamId") long teamId, @PathVariable("applicationId") long applicationId, @PathVariable("deploymentId") long deploymentId) {
		ModelAndView mv = new ModelAndView("viewFlowDeployment");
		
		Flow flow = this.deploymentService.getFirstFlowOf(deploymentId);
		mv.addObject("flow", flow);
		mv.addObject("libraries", FlowController.getLibraries(libraryService));
		
		return mv;
	}
}
