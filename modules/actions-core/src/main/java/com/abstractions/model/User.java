package com.abstractions.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
*
* @author Guido J. Celada (celadaguido@gmail.com)
*/
public interface User extends UserDetails {
  public void addTeam(Team team);
  public String getFirstName();
  public void setFirstName(String firstName);
  public String getLastName();
  public void setLastName(String lastName);
  public List<Team> getTeams();
  public String getEmail();
  public void setEmail(String email);
  public Date getCreationDate();
  public void setCreationDate(Date creationDate);
  public boolean isConfirmed();
  public void setConfirmed(boolean confirmed);
  public boolean isEnabled();
  public void setEnabled(boolean enabled);
  public long getId();
  public void setId(long id);
  public void setTeams(List<Team> teams);
  public void setPassword(String password);
  public void setUsername(String username);
  public void setAuthorities(Collection<GrantedAuthority> authorities);
}
