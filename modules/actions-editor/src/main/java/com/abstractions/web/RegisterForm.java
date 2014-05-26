package com.abstractions.web;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;


/**
* @author Guido J. Celada (celadaguido@gmail.com)
*/
public class RegisterForm {
	
	@NotEmpty(message="May not be empty") @Size(min=5, max=30, message="Size must be between 5 and 30") 
	private String username;
	@NotEmpty(message="May not be empty")
	private String password;
	@Email(message="Not a real email") @NotEmpty(message="May not be empty")
	private String email;
	@NotEmpty(message="May not be empty") @Size(min=5, max=100, message="Too short") 
	private String firstName; 
  @NotEmpty(message="May not be empty") @Size(min=5, max=100, message="Too short") 
  private String lastName;
	
	
  public String getLastName() {
    return lastName;
  }
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
    return firstName;
  }
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
	
}
