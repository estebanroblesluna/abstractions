package com.abstractions.web;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

import org.springframework.beans.factory.annotation.Value;
import com.abstractions.service.CustomJdbcUserDetailsManager;

/**
 * 
 * @author Guido J. Celada (celadaguido@gmail.com)
 */
public class RegisterConfirmationEmail {
	
	@Value("${register.confirmationEmail.username}")
	String username;
	
	@Value("${register.confirmationEmail.password}")
	String password;
	
	@Value("${register.confirmationEmail.host}")
	String emailHost;
	
	@Value("${register.confirmationEmail.port}")
	int emailPort;
	
	@Value("${register.confirmationEmail.usesTSL}")
	boolean usesTSL;
			 
	public void sendRegistrationMail(CustomUser user) throws AddressException, MessagingException {
		 
        final String host = "http://localhost:8123/";
        
        Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", usesTSL);
		props.put("mail.smtp.host", emailHost);
		props.put("mail.smtp.port", emailPort);
		
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            		protected PasswordAuthentication getPasswordAuthentication() {
            			return new PasswordAuthentication(username, password);
            		}
        		});
 
        final Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(user.getEmail()));
        message.setSubject( user.getFullName() + " thank you for your registration");
        message.setContent("Thank you "+ user.getFullName() + " for your registration, <br>"+
                "Following are the details of your account,"+
                "<br/>*************************************** "
                +"<br/>Username : <b>" + user.getUsername() +"</b>"+
                "<br/>Full Name : <b>" + user.getFullName() + "</b>"+
                "<br/>Click the link to activate your account : <br>" +
                "<a href='" + host + "register/confirm?username="+ user.getUsername() + "&token=" + CustomJdbcUserDetailsManager.generateConfirmationToken(user) +
                "'><b>Activate Account</b></a>"+
                "<br/>*************************************** ", "text/html" );
        
        Runnable r = new Runnable() {
            public void run() {
            	try {
					Transport.send(message);
				} catch (MessagingException e) {
					e.printStackTrace();
				}
            }
        };
        new Thread(r).start(); //background task
    }
	
	
}
