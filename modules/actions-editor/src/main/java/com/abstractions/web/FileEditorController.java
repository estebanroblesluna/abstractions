package com.abstractions.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/fileEditor")
public class FileEditorController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView("templateEditor");
		//mv.addObject("serviceBase", this.getDustServiceBaseUrl());
		//mv.addObject("resourcesBase", this.getDustResourceBaseUrl());
		return mv;
	}

	
	
}
