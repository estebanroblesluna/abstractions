package com.abstractions.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for CRUD of Login
 * @author Guido J. Celada
 */
@Controller
public class LoginController {
 
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public ModelAndView login() {
		return new ModelAndView("login");
	}
 
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public ModelAndView logout() {
		return new ModelAndView("login");
	}
}
