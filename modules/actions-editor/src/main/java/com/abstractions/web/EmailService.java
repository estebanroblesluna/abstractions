package com.abstractions.web;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.abstractions.model.User;
import com.abstractions.model.UserImpl;
import com.abstractions.service.UserServiceImpl;

/**
 * 
 * @author Guido J. Celada (celadaguido@gmail.com)
 */
public class EmailService {
	
	private static Log log = LogFactory.getLog(EmailService.class);
	
	@Value("${register.confirmationEmail.username}")
	private String username;
	
	@Value("${register.confirmationEmail.password}")
	private String password;
	
	@Value("${register.confirmationEmail.host}")
	private String emailHost;
	
	@Value("${register.confirmationEmail.port}")
	private int emailPort;
	
	@Value("${register.confirmationEmail.usesTSL}")
	private boolean usesTSL;
	
	@Value("${register.webhost}")
	private String host;
	
	private Properties props;
	
	private ThreadPoolTaskExecutor taskExecutor;
	
	private Session session;
	
    public EmailService() {
        this.taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setQueueCapacity(100);
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        taskExecutor.afterPropertiesSet();
    }
    
    public void init() {
        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", usesTSL);
        props.put("mail.smtp.host", emailHost);
        props.put("mail.smtp.port", emailPort);
        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            		protected PasswordAuthentication getPasswordAuthentication() {
            			return new PasswordAuthentication(username, password);
            		}
        		});
    }
			 
	public void sendRegistrationMail(User user) throws AddressException, MessagingException {	
        final Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(user.getEmail()));
        message.setSubject( user.getFirstName() +" "+ user.getLastName() + " thank you for your registration");
        message.setContent("Thank you "+ user.getFirstName() +" "+ user.getLastName() + " for your registration, <br>"+
                "Following are the details of your account,"+
                "<br/>*************************************** "
                +"<br/>Username : <b>" + user.getUsername() +"</b>"+
                "<br/>Full Name : <b>" + user.getFirstName() +" "+ user.getLastName() + "</b>"+
                "<br/>Click the link to activate your account : <br>" +
                "<a href='" + host + "register/confirm?username="+ user.getUsername() + "&token=" + UserServiceImpl.generateConfirmationToken(user) +
                "'><b>Activate Account</b></a>"+
                "<br/>*************************************** ", "text/html" );
        
        Runnable r = new Runnable() {
            public void run() {
            	try {
					Transport.send(message);
				} catch (MessagingException e) {
					log.error(e.getMessage());
				}
            }
        };
        taskExecutor.execute(r);
    }
	
	public void sendUserEnabledMail(User user) throws AddressException, MessagingException {	
        final Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(user.getEmail()));
        message.setSubject( user.getFirstName() +" "+ user.getLastName() + " your account is enabled");
        message.setContent("Dear "+ user.getFirstName() +" "+ user.getLastName() + ", your account is enabled, <br>"+
                "Now you can use LiquidML at full power:"+
                "<br/>Username : <b>" + user.getUsername() +"</b>"+
                "<br/>Use it now : <br>" +
                "<a href='" + host + "'><b>LiquidML</b></a>"
                , "text/html" );
        
        Runnable r = new Runnable() {
            public void run() {
            	try {
					Transport.send(message);
				} catch (MessagingException e) {
					log.error(e.getMessage());
				}
            }
        };
        taskExecutor.execute(r);
    }
}
