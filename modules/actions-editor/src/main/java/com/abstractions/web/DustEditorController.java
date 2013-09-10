package com.abstractions.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/templateEditor")
public class DustEditorController {

	@Autowired @Qualifier("dustServiceBaseUrl")
	private String dustServiceBaseUrl;
	@Autowired @Qualifier("dustResourcesBaseUrl")
	private String dustResourcesBaseUrl;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView("templateEditor");
		mv.addObject("serviceBase", this.getDustServiceBaseUrl());
		mv.addObject("resourcesBase", this.getDustResourceBaseUrl());
		return mv;
	}

	public String getDustServiceBaseUrl() {
		return dustServiceBaseUrl;
	}

	public void setDustServiceBaseUrl(String dustServiceBaseUrl) {
		this.dustServiceBaseUrl = dustServiceBaseUrl;
	}

	public String getDustResourceBaseUrl() {
		return dustResourcesBaseUrl;
	}

	public void setDustResourceBaseUrl(String dustResourceBaseUrl) {
		this.dustResourcesBaseUrl = dustResourceBaseUrl;
	}

}
