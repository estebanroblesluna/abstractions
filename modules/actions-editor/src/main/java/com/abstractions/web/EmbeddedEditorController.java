package com.abstractions.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class EmbeddedEditorController {
	
	@Autowired
	@Qualifier("staticResourcesBaseUrl")
	private String staticResourcesUrl;
	
	@RequestMapping("/editor/")
	public ModelAndView home(@RequestParam("file") String file) {
        ModelAndView mv = new ModelAndView("embeddedEditor");
        mv.addObject("staticResourcesUrl", this.staticResourcesUrl);
        mv.addObject("file", file);
	    return mv;
	}
	
	@RequestMapping("/simple-editor/")
	public ModelAndView home() {
        ModelAndView mv = new ModelAndView("simple-editor");
        mv.addObject("staticResourcesUrl", this.staticResourcesUrl);
	    return mv;
	}

}
