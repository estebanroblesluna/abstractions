package com.abstractions.web;


import javax.mail.MessagingException;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abstractions.service.CustomUserService;

/**
 * Controller for Register user
 * @author Guido J. Celada (celadaguido@gmail.com)
 */
@Controller
public class RegisterController {
	
	private static Log log = LogFactory.getLog(RegisterController.class);

	@Autowired
	CustomUserService service;
	
	@RequestMapping(value="/register", method = RequestMethod.GET)
	public ModelAndView register() {
		 return new ModelAndView("register", "registerForm", new RegisterForm());
	}
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public ModelAndView register(@Valid @ModelAttribute("registerForm") RegisterForm form, BindingResult result) {
		ModelAndView mv = new ModelAndView("register", "registerForm", form);
		
		if(result.hasErrors())
			return mv;

		try {
			service.registerUser(form.getUsername(), form.getPassword(), form.getEmail(), form.getFullName());
		} catch (UsernameExistsException e) {
			mv.addObject("usernameExistsError","Username already exists");
			return mv;
		} catch (EmailExistsException e) {
			mv.addObject("emailExistsError", "Email already exists");
			return mv;
		} catch (MessagingException e) {
			log.error(e.getMessage());
		}
		
		return new ModelAndView("redirect:/login/");
	}
	
	@RequestMapping(value="/register/confirm")
	public String confirm(@RequestParam("username") String username, @RequestParam("token") String token) {
		service.confirmUser(username, token);
		return "redirect:/login?confirmed=true/";
	}

}
