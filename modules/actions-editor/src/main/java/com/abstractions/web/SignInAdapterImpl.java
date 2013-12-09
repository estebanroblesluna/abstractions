package com.abstractions.web;

import java.util.ArrayList;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

/**
 *
 * @author Guido J. Celada
 */
@Service
public class SignInAdapterImpl implements SignInAdapter {

    @Override
    public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
        GrantedAuthorityImpl auth = new GrantedAuthorityImpl("ROLE_USER");
        ArrayList<GrantedAuthorityImpl> auths = new ArrayList<GrantedAuthorityImpl>();
        auths.add(auth);
        UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(userId, "", auths);
        SecurityContextHolder.getContext().setAuthentication(upat);
        return null; //the url
    }

}
