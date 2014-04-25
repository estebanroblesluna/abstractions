package com.abstractions.web;

import java.util.ArrayList;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.security.SocialUser;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;

/**
 *
 * @author Guido J. Celada (celadaguido@gmail.com)
 */
@Service
public class SignInAdapterImpl implements SignInAdapter {

	private boolean enabled;
	
    @Override
    public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
        if (!this.enabled) {
        	return null;
        }
    	
        //Create a UsernamePasswordAuthenticationToken
        ArrayList<GrantedAuthorityImpl> auths = new ArrayList<GrantedAuthorityImpl>();
        auths.add(new GrantedAuthorityImpl("ROLE_USER"));
        auths.add(new GrantedAuthorityImpl("ROLE_SOCIAL"));
        User user = new SocialUser(userId, connection.getImageUrl() , true, true, true, true, auths);
        UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(user, "", auths);
        
        //Set it up on the context
        SecurityContextHolder.getContext().setAuthentication(upat);
        
        //Set session
        request.setAttribute(
				HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
				SecurityContextHolder.getContext(),
				RequestAttributes.SCOPE_SESSION);
        return null; //the url
    }

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
