package com.abstractions.web;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.util.MultiValueMap;

/**
 *
 * @author Guido J. Celada
 */
public class UserConnectionSignUp implements ConnectionSignUp {

    private JdbcUsersConnectionRepository repository;
    
    public UserConnectionSignUp(JdbcUsersConnectionRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public String execute(Connection<?> connection) {
        UserProfile profile = connection.fetchUserProfile();
        ConnectionRepository cr = repository.createConnectionRepository(profile.getUsername());
        MultiValueMap connections = cr.findAllConnections();
        if (connections.isEmpty())
            cr.addConnection(connection);        
        return profile.getUsername();
    }

}
