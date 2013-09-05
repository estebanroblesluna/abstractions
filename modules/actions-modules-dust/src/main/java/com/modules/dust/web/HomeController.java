package com.modules.dust.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	private String serviceBase;
	private String resourcesBase;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView("home");
		mv.addObject("serviceBase", this.getServiceBase());
		mv.addObject("resourcesBase", this.getResourcesBase());
		return mv;
	}

	public String getServiceBase() {
		return serviceBase;
	}

	public void setServiceBase(String serviceBase) {
		this.serviceBase = serviceBase;
	}

	public String getResourcesBase() {
		return resourcesBase;
	}

	public void setResourcesBase(String editorBase) {
		this.resourcesBase = editorBase;
	}

}
