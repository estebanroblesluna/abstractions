package com.abstractions.service;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.helper.Validate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abstractions.model.Authority;
import com.abstractions.model.User;
import com.abstractions.model.UserImpl;
import com.abstractions.repository.UserRepository;
import com.abstractions.web.EmailExistsException;
import com.abstractions.web.EmailService;
import com.abstractions.web.UsernameExistsException;

/**
*
* @author Guido J. Celada (celadaguido@gmail.com)
*/
@Service
public class UserServiceImpl implements UserService {
  
  private UserRepository userRepository;
  private EmailService emailService;

  private static Log log = LogFactory.getLog(UserServiceImpl.class);

  @Value("${register.salt}")
  static String salt;

  public UserServiceImpl() {
  }

  public UserServiceImpl(UserRepository userRepository, EmailService emailService) {
    Validate.notNull(userRepository);
    Validate.notNull(emailService);
    
    this.userRepository = userRepository;
    this.emailService = emailService;
  }

  @Transactional
  public User getUser(long userId) {
    return this.userRepository.get(UserImpl.class, userId);
  }

  @Transactional
  public User getUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
    User user = null;
    try{
      user = this.userRepository.getUserByUsernameWithAuthorities(username);
    } catch (NullPointerException e) {
        throw new UsernameNotFoundException("user not found");
    }
    if (user == null) throw new UsernameNotFoundException("user not found");
    return user;
  }

  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
    return getUserByUsername(username);
  }

  @Transactional
  public User getCurrentUser() {
    return getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
  }

  @Transactional
  private boolean emailExists(String email) {
    return (userRepository.findBy(UserImpl.class, "email", email) != null);
  }
  
  @Transactional
  private boolean usernameExists(String username) {
    return (userRepository.findBy(UserImpl.class, "username", username) != null);
  }
  
  @Transactional
  public void registerUser(String username, String password, String email, String firstName, String lastName) throws UsernameExistsException, EmailExistsException,
          MessagingException {
    boolean enabled = false;
    boolean confirmed = false;
    String sha1password = DigestUtils.shaHex(password);
    Collection<GrantedAuthority> auths = new HashSet<GrantedAuthority>();
    auths.add(new Authority(Authority.USER));

    UserImpl newUser = new UserImpl(username, sha1password, email, firstName, lastName, new Date(), enabled, confirmed, auths);

    if (usernameExists(username)) throw new UsernameExistsException();
    if (emailExists(username)) throw new EmailExistsException();
    
    userRepository.save(newUser);
    
    try {
      emailService.sendRegistrationMail(newUser);
    } catch (AddressException e) {
      log.error(e.getMessage());
    }
  }

  public static String generateConfirmationToken(User user) {
    return DigestUtils.shaHex(user.getUsername() + user.getCreationDate() + salt);
  }

  @Transactional
  public void confirmUser(String username, String token) throws Exception {
    User user = getUserByUsername(username);
    if (generateConfirmationToken(user).equals(token))
      userRepository.update(user);
    else
      throw new Exception("Invalid confirmation token");
  }

  @Transactional
  public List<User> getConfirmedDisabledUsers() {
    return userRepository.getConfirmedDisabledUsers();
  }

  @Transactional
  public void enableUser(String username) {
    User user = getUserByUsername(username);
    user.setEnabled(true);
    userRepository.update(user);
    try {
      emailService.sendUserEnabledMail(user);
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }

}
