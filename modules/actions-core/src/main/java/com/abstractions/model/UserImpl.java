package com.abstractions.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;

/**
*
* @author Guido J. Celada (celadaguido@gmail.com)
*/
//TODO connect with Social user
public class UserImpl implements User { 
  private static final long serialVersionUID = 1L;
  
  long id;
  private String firstName;
  private String lastName;
  private List<Team> teams;
  private String email;
  private Date creationDate;
  private boolean confirmed;
  private boolean enabled;
  private String password;
  private String username;
  private Collection<GrantedAuthority> authorities;

  protected UserImpl() {
    this.teams = new ArrayList<Team>();
    this.authorities = new HashSet<GrantedAuthority>();
  }
  
  public UserImpl(String username, String password, String email, String firstName, String lastName, Date creationDate, boolean enabled, boolean confirmed,
          Collection<GrantedAuthority> authorities) {
    if (StringUtils.isEmpty(email)) {
      throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
    }
    this.setPassword(password);
    this.setUsername(username);
    this.setAuthorities(authorities);
    this.setConfirmed(confirmed);
    this.setEmail(email);
    this.setFirstName(firstName);
    this.setLastName(lastName);
    this.setCreationDate(creationDate);
    this.teams = new ArrayList<Team>();
  }

  @Override
  public void addTeam(Team team) {
    Validate.notNull(team);

    this.teams.add(team);
  }

  @Override
  public String getFirstName() {
    return firstName;
  }

  @Override
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @Override
  public String getLastName() {
    return lastName;
  }

  @Override
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Override
  public List<Team> getTeams() {
    return Collections.unmodifiableList(this.teams);
  }

  @Override
  public String getEmail() {
    return email;
  }

  @Override
  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public Date getCreationDate() {
    return creationDate;
  }

  @Override
  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }

  @Override
  public boolean isConfirmed() {
    return confirmed;
  }

  @Override
  public void setConfirmed(boolean confirmed) {
    this.confirmed = confirmed;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  @Override
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  @Override
  public long getId() {
    return id;
  }

  @Override
  public void setId(long id) {
    this.id = id;
  }

  @Override
  public Collection<GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }
  
  @Override
  public void setTeams(List<Team> teams) {
    this.teams = teams;
  }

  @Override
  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public void setAuthorities(Collection<GrantedAuthority> authorities) {
    this.authorities = authorities;
  }
  
  @Override
  public boolean equals(Object rhs) {
      if (rhs instanceof UserImpl) {
          return username.equals(((UserImpl) rhs).username);
      }
      return false;
  }

  /**
   * Returns the hashcode of the {@code username}.
   */
  @Override
  public int hashCode() {
      return username.hashCode();
  }
}
