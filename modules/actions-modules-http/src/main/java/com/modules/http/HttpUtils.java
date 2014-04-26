package com.modules.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;

import com.abstractions.api.Message;
import com.abstractions.utils.MessageUtils;

public class HttpUtils
{
  private static Log log = LogFactory.getLog(HttpUtils.class);

  public static final String DEV_HTTP_PARAM = "liquidML_key";
  
  public static Message readFrom(HttpResponse response, boolean streaming)
  {
    Message message = new Message();
    
    try
    {
      setMessagePayload(message, response.getEntity().getContent(), streaming);
    }
    catch (IllegalStateException e)
    {
      log.error("Error reading payload");
    }
    catch (IOException e)
    {
      log.error("Error reading payload");
    }
    
    setHeaders(message, response);
    return message;
  }
  
  public static Message readFrom(HttpServletRequest request, boolean streaming)
  {
    Message message = new Message();
    
    try
    {
      setMessagePayload(message, request.getInputStream(), streaming);
    }
    catch (IOException e)
    {
      log.error("Error reading payload");
    }
    
    message.putProperty(MessageUtils.HTTP_BASE_PROPERTY + ".contentType", request.getContentType());
    message.putProperty(MessageUtils.HTTP_BASE_PROPERTY + ".contentLength", request.getContentLength());
    message.putProperty(MessageUtils.HTTP_BASE_PROPERTY + ".contextPath", request.getContextPath());
    message.putProperty(MessageUtils.HTTP_BASE_PROPERTY + ".authType", request.getAuthType());
    message.putProperty(MessageUtils.HTTP_BASE_PROPERTY + ".characterEncoding", request.getCharacterEncoding());
    message.putProperty(MessageUtils.HTTP_BASE_PROPERTY + ".localAddress", request.getLocalAddr());
    message.putProperty(MessageUtils.HTTP_BASE_PROPERTY + ".locale", request.getLocale());
    message.putProperty(MessageUtils.HTTP_BASE_PROPERTY + ".localName", request.getLocalName());
    message.putProperty(MessageUtils.HTTP_BASE_PROPERTY + ".localPort", request.getLocalPort());
    message.putProperty(MessageUtils.HTTP_BASE_PROPERTY + ".method", request.getMethod());
    message.putProperty(MessageUtils.HTTP_BASE_PROPERTY + ".pathInfo", request.getPathInfo());
    message.putProperty(MessageUtils.HTTP_BASE_PROPERTY + ".pathTranslated", request.getPathTranslated());
    message.putProperty(MessageUtils.HTTP_BASE_PROPERTY + ".protocol", request.getProtocol());
    message.putProperty(MessageUtils.HTTP_BASE_PROPERTY + ".queryString", request.getQueryString());
    message.putProperty(MessageUtils.HTTP_BASE_PROPERTY + ".remoteAddress", request.getRemoteAddr());
    message.putProperty(MessageUtils.HTTP_BASE_PROPERTY + ".remoteHost", request.getRemoteHost());
    message.putProperty(MessageUtils.HTTP_BASE_PROPERTY + ".remotePort", request.getRemotePort());
    message.putProperty(MessageUtils.HTTP_BASE_PROPERTY + ".remoteUser", request.getRemoteUser());
    message.putProperty(MessageUtils.HTTP_BASE_PROPERTY + ".requestedSessionId", request.getRequestedSessionId());
    message.putProperty(MessageUtils.HTTP_BASE_PROPERTY + ".requestURI", request.getRequestURI());
    message.putProperty(MessageUtils.HTTP_BASE_PROPERTY + ".requestURL", request.getRequestURL().toString());
    message.putProperty(MessageUtils.HTTP_BASE_PROPERTY + ".scheme", request.getScheme());
    message.putProperty(MessageUtils.HTTP_BASE_PROPERTY + ".serverName", request.getServerName());
    message.putProperty(MessageUtils.HTTP_BASE_PROPERTY + ".serverPort", request.getServerPort());
    
    setAttributesAndParameters(message, request);
    
    return message;
  }

  private static void setAttributesAndParameters(Message message, HttpServletRequest request)
  {
    Map<String, String[]> parameters = request.getParameterMap();
    for (Map.Entry<String, String[]> entry : parameters.entrySet())
    {
      String name = entry.getKey();
      String[] values = entry.getValue();
      setAttribute(message, name, values);
    }
  }

  private static void setHeaders(Message message, HttpResponse response)
  {
    Header[] headers = response.getAllHeaders();
    for (int i = 0; i < headers.length; i++)
    {
      Header header = headers[i];
      String[] values = getValues(header.getElements());
      setAttribute(message, header.getName(), values);
    }
  }

	private static void setAttribute(Message message, String name, Object value) {
		Object finalValue = value;
		if ((value instanceof String[]) && (((String[]) value).length == 1)) {
			finalValue = ((String[]) value)[0];
		}
		message.putProperty(MessageUtils.HTTP_BASE_PROPERTY + "." + name, finalValue);
	}
  
  private static String[] getValues(HeaderElement[] elements)
  {
    String[] values = new String[elements.length];
    for (int i = 0; i < elements.length; i++)
    {
      HeaderElement element = elements[i];
      values[i] = element.getName();
    }
    return values;
  }

  private static void setMessagePayload(Message message,
                                        InputStream io,
                                        boolean streaming)
  {
    if (streaming)
    {
      message.setPayload(io);
    }
    else
    {
      try
      {
        String responseAsString = IOUtils.toString(io);
        IOUtils.closeQuietly(io);
        message.setPayload(responseAsString);
      }
      catch (IOException e)
      {
        log.error("Error reading payload");
      }
    }    
  }
}
