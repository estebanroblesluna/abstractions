package com.abstractions.model;

import org.springframework.security.core.GrantedAuthority;

/**
*
* @author Guido J. Celada (celadaguido@gmail.com)
*/
public class Authority implements GrantedAuthority {

  private static final long serialVersionUID = 1L;
  public static String ADMIN = "ROLE_ADMIN";
  public static String USER = "ROLE_USER";
  public static String SOCIAL = "ROLE_SOCIAL";

  long id;
  private String authority;

  public Authority(String authority) {
    setAuthority(authority);
  }

  protected Authority() {
  }

  @Override
  public String getAuthority() {
    return authority;
  }

  public void setAuthority(String authority) {
    this.authority = authority;
  }
}
