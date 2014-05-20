package com.abstractions.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abstractions.service.UserServiceIface;

/**
 * @author Guido J. Celada (celadaguido@gmail.com)
 */
@Controller
public class AdminPanelController {
	
  @Autowired
  UserServiceIface service;
	
	@RequestMapping(value="/admin/enable-users", method = RequestMethod.GET)
	public ModelAndView enableUsers() {
		ModelAndView mv = new ModelAndView("admin-enable-users", "EnableUserForm", new EnableUserForm());
		mv.addObject("my_conf_users", service.getConfirmedDisabledUsers());
		return mv;
	}
	
	@RequestMapping(value="/admin/enable-users", method = RequestMethod.POST)
	public String receiveEnableUsers(@ModelAttribute("form") EnableUserForm form) {
		for (String username : form.getUsersToEnable()) {
			service.enableUser(username);
		}
		return "redirect:/admin/enable-users";
	}

}
