package com.abstractions.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abstractions.model.ApplicationSnapshot;
import com.abstractions.service.ApplicationService;
import com.abstractions.service.SnapshotService;

@Controller
public class ApplicationSnapshotController {

	@Autowired
	SnapshotService service;
	
        @Autowired
	ApplicationService applicationService;
        
	@RequestMapping(value = "/teams/{teamId}/applications/{applicationId}/snapshots", method = RequestMethod.GET)
	public ModelAndView home(@PathVariable("teamId") long teamId, @PathVariable("applicationId") long applicationId) {
		ModelAndView mv = new ModelAndView("snapshots");
		String applicationName = this.applicationService.getApplication(applicationId).getName();
		List<ApplicationSnapshot> snapshots = this.service.getSnapshots(applicationId);
		mv.addObject("snapshots", snapshots);
		mv.addObject("teamId", teamId);
		mv.addObject("applicationId", applicationId);
                mv.addObject("applicationName", applicationName);
		return mv;
	}
	
	@RequestMapping(value = "/teams/{teamId}/applications/{applicationId}/snapshots/generate", method = RequestMethod.GET)
	public String addContact(@PathVariable("teamId") long teamId, @PathVariable("applicationId") long applicationId) {
		this.service.generateSnapshot(applicationId);
		return "redirect:/teams/" + teamId + "/applications/" + applicationId + "/snapshots/";
	}
}
