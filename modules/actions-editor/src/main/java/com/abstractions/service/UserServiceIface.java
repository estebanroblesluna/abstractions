package com.abstractions.service;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.abstractions.model.User;
import com.abstractions.web.EmailExistsException;
import com.abstractions.web.UsernameExistsException;

/**
*
* @author Guido J. Celada (celadaguido@gmail.com)
*/
@Service
public interface UserServiceIface extends UserDetailsService {

  public User getUser(long userId);
  public User getUserByUsername(String username) throws UsernameNotFoundException, DataAccessException;
  public User getLoggedUser();
  public void registerUser(String username, String password, String email, String firstName, String lastName) throws UsernameExistsException, EmailExistsException, MessagingException;
  public void confirmUser(String username, String token) throws Exception;
  public List<User> getConfirmedDisabledUsers();
  public void enableUser(String username);
}
