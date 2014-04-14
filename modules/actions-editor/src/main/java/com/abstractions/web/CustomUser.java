package com.abstractions.web;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * 
 * @author Guido J. Celada (celadaguido@gmail.com)
 *
 */
public class CustomUser extends User {

	private static final long serialVersionUID = 1L;
	private String email;
	private String fullName;
	private Date creationDate;
	private boolean confirmed;
	
    public CustomUser(String username, String password, boolean enabled, boolean accountNonExpired,
            boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
    	
    	super(username, password, enabled, true, true, true, authorities);
    }
    
    public CustomUser(String username, String password, String email, String fullName, Date creationDate, boolean enabled, boolean confirmed, Collection<? extends GrantedAuthority> authorities) {
    	super(username, password, enabled, true, true, true, authorities);
    	 if (email == null || "".equals(email)) {
             throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
         }
    	this.setConfirmed(confirmed);
    	this.setEmail(email);
    	this.setFullName(fullName);
    	this.setCreationDate(creationDate);
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

}
