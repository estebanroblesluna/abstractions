package com.abstractions.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abstractions.model.Property;
import com.abstractions.service.PropertiesService;

@Controller
@RequestMapping("/properties")
public class PropertyController {

	@Autowired
	PropertiesService service;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView("properties");
		
		List<Property> properties = this.service.getProperties();
		mv.addObject("properties", properties);

		return mv;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addContact(@ModelAttribute("form") AddPropertyForm form) {
		this.service.addProperty(form.getName(), form.getValue());
		return "redirect:/properties/";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add() {
		ModelAndView mv = new ModelAndView("addProperty");
		return mv;
	}

}
