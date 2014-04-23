package com.abstractions.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.stereotype.Service;

import com.abstractions.web.CustomUser;
import com.abstractions.web.EmailExistsException;
import com.abstractions.web.EmailService;
import com.abstractions.web.UsernameExistsException;

@Service
public class CustomUserService {
	
	private static Log log = LogFactory.getLog(CustomUserService.class);
	
	@Autowired
	CustomJdbcUserDetailsManager userManager; 
	
	@Autowired
	EmailService emailService;
	
	public void registerUser(String username, String password, String email, String fullName) throws UsernameExistsException, EmailExistsException, MessagingException {
		boolean enabled = false;
		boolean confirmed = false;
		String sha1password = DigestUtils.shaHex(password);
		ArrayList<GrantedAuthorityImpl> auths = new ArrayList<GrantedAuthorityImpl>();
		auths.add(new GrantedAuthorityImpl("ROLE_USER"));
        
		CustomUser newUser = new CustomUser(username, sha1password, email, fullName, new Date(), enabled, confirmed, auths);
		
		userManager.createUser(newUser);
		
		try {
			emailService.sendRegistrationMail(newUser);
		} catch (AddressException e) {
			log.error(e.getMessage());
		} 
	}
	
	public void confirmUser(String username, String token) {
		try {
			userManager.confirmUser(username, token);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	public List<CustomUser> getConfirmedUsers() {
		return userManager.getConfirmedUsers();
	}
	
	public void enableUser(String username) {
		userManager.enableUser(username);
		try {
			emailService.sendUserEnabledMail(userManager.loadUserByUsernameWithoutAuths(username));
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}
