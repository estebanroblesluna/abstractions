package com.abstractions.web;

import java.util.ArrayList;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.validation.Valid;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abstractions.service.CustomJdbcUserDetailsManager;

/**
 * Controller for Register user
 * @author Guido J. Celada
 */
@Controller
public class RegisterController {
	
	@Autowired
	CustomJdbcUserDetailsManager userManager; 
	
	@Autowired
	RegisterConfirmationEmail confirmationEmail;
	
	@RequestMapping(value="/register", method = RequestMethod.GET)
	public ModelAndView register() {
		 return new ModelAndView("register", "registerForm", new RegisterForm());
	}
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public ModelAndView register(@Valid @ModelAttribute("registerForm") RegisterForm form, BindingResult result) {
		ModelAndView mv = new ModelAndView("register", "registerForm", form);
		
		if(result.hasErrors())
			return mv;
            
		boolean enabled = false;
		boolean confirmed = false;
		String sha1password = DigestUtils.shaHex(form.getPassword());
		ArrayList<GrantedAuthorityImpl> auths = new ArrayList<GrantedAuthorityImpl>();
        auths.add(new GrantedAuthorityImpl("ROLE_USER"));
        
		CustomUser newUser = new CustomUser(form.getUsername(), sha1password, form.getEmail(), form.getFullName(), new Date(), enabled, confirmed, auths);
		
		try {
			userManager.createUser(newUser);
		} catch (UsernameExistsException e) {
			mv.addObject("usernameExistsError","Username already exists");
			return mv;
		} catch (EmailExistsException e) {
			mv.addObject("emailExistsError", "Email already exists");
			return mv;
		}
		
		try {
			confirmationEmail.sendRegistrationMail(newUser);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("redirect:/login/");
	}
	
	@RequestMapping(value="/register/confirm")
	public String confirm(@RequestParam("username") String username, @RequestParam("token") String token) {
		try {
			userManager.confirmUser(username, token);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/login/";
	}

}
