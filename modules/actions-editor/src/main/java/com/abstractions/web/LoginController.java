package com.abstractions.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for CRUD of Login
 * @author Guido J. Celada (celadaguido@gmail.com)
 */
@Controller
public class LoginController {
 
	@Value("${social.login.enabled}")
    private boolean socialLoginEnabled;
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView("login");
		mv.addObject("socialLoginEnabled", socialLoginEnabled);
		return mv;
	}
 
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public ModelAndView logout() {
		return new ModelAndView("login");
	}
}
