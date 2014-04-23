package com.abstractions.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abstractions.service.CustomJdbcUserDetailsManager;

/**
 * @author Guido J. Celada (celadaguido@gmail.com)
 */
@Controller
public class AdminPanelController {
	
	@Autowired
	CustomJdbcUserDetailsManager userManager; 
	
	@Autowired
	EmailService emailService;
	
	private static Log log = LogFactory.getLog(AdminPanelController.class);

	
	@RequestMapping(value="/admin/enable-users", method = RequestMethod.GET)
	public ModelAndView enableUsers() {
		ModelAndView mv = new ModelAndView("admin-enable-users", "EnableUserForm", new EnableUserForm());
		mv.addObject("my_conf_users", userManager.getConfirmedUsers());
		return mv;
	}
	
	@RequestMapping(value="/admin/enable-users", method = RequestMethod.POST)
	public String receiveEnableUsers(@ModelAttribute("form") EnableUserForm form) {
		for (String username : form.getUsersToEnable()) {
			userManager.enableUser(username);
			try {
				emailService.sendUserEnabledMail(userManager.loadUserByUsernameWithoutAuths(username));
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		return "redirect:/admin/enable-users";
	}

}
