package com.abstractions.web;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;

/**
 *
 * @author Guido J. Celada
 */
@Service
public class SignInAdapterImpl implements SignInAdapter {

    @Override
    public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
        
        //Create a UsernamePasswordAuthenticationToken
        ArrayList<GrantedAuthorityImpl> auths = new ArrayList<GrantedAuthorityImpl>();
        auths.add(new GrantedAuthorityImpl("ROLE_USER"));
        auths.add(new GrantedAuthorityImpl("ROLE_SOCIAL_USER"));
        User user = new User(userId, "", true, true, true, true, auths);
        UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(user, "", auths);
        upat.setDetails(new WebAuthenticationDetails(request.getNativeRequest(HttpServletRequest.class)));
        
        //Set it up on the context
        SecurityContextHolder.getContext().setAuthentication(upat);
        
        //Set session
        request.setAttribute(
				HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
				SecurityContextHolder.getContext(),
				RequestAttributes.SCOPE_SESSION);
        return null; //the url
    }

}
