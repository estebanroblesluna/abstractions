package com.modules.http;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.abstractions.api.Message;
import com.abstractions.utils.MessageUtils;

public class DevHttpMessageSource extends AbstractHttpMessageSource {

  public static final String FULL_DEV_KEY = MessageUtils.HTTP_BASE_PROPERTY + "." + HttpUtils.DEV_HTTP_PARAM;
  
	private Map<String, HttpMessageSource> messageSources;
	
	public DevHttpMessageSource() {
		this.messageSources = new ConcurrentHashMap<String, HttpMessageSource>();
	}
	
	public void addSource(HttpMessageSource source) {
		this.messageSources.put(source.getId(), source);
	}
	
	public Message newMessage(Message message) {
		String resolvedId = this.getIdFromQueryString(message);
		
		if (StringUtils.isBlank(resolvedId)) {
		  resolvedId = this.getIdFromCookie(message);
		}
		
		if (StringUtils.isBlank(resolvedId)) {
		  message.setPayload("No " + HttpUtils.DEV_HTTP_PARAM + " property found");
      return message;
		} else {
	    HttpMessageSource messageSource = this.messageSources.get(resolvedId);
	    if (messageSource != null) {
	      return messageSource.newMessage(message);
	    } else {
	      message.setPayload("No listener found for " + resolvedId);
	      return message;
	    }
		}
	}

	private String getIdFromCookie(Message message) {
    HttpServletRequest request = (HttpServletRequest) message.getProperty(MessageUtils.HTTP_REQUEST_PROPERTY);
    HttpServletResponse response = (HttpServletResponse) message.getProperty(MessageUtils.HTTP_RESPONSE_PROPERTY);

    for (Cookie cookie : request.getCookies()) {
      if (StringUtils.equals(FULL_DEV_KEY, cookie.getName())) {
        String value = cookie.getValue();
        if (StringUtils.isBlank(value)) {
          cookie.setMaxAge(0);
          cookie.setValue(null);
          response.addCookie(cookie);
        } else {
          return value;
        }
      }
    }
    
    return null;
  }

  private String getIdFromQueryString(Message message) {
		Object objectId = message.getProperty(FULL_DEV_KEY);
		String id = objectId == null ? "" : objectId.toString();
		
		if (StringUtils.isNotBlank(id)) {
      HttpServletResponse response = (HttpServletResponse) message.getProperty(MessageUtils.HTTP_RESPONSE_PROPERTY);
		  
      Cookie cookie1 = new Cookie(FULL_DEV_KEY, id);
      cookie1.setMaxAge(24*60*60); //1 day
      response.addCookie(cookie1);
		}
		
		return id;
	}
}
