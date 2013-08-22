package com.modules.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.Validate;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import com.core.api.Expression;
import com.core.api.Message;
import com.core.api.ProcessingException;
import com.core.api.Processor;

public class HttpFetcherProcessor implements Processor
{
  private HttpClient client;

  private Expression urlExpression;
  private FetchMode fetchMode;
  private boolean streaming;
  private Map<String, Expression> parameters;

  public HttpFetcherProcessor()
  {
    this(3000);
  }
  
  public HttpFetcherProcessor(int timeout)
  {
    this(timeout, false, "", 0);
  }
  
  public HttpFetcherProcessor(int timeout, boolean useProxy, String proxyHost, int proxyPort)
  {
    ThreadSafeClientConnManager connManager = new ThreadSafeClientConnManager();
    HttpParams params = new BasicHttpParams();
    HttpConnectionParams.setConnectionTimeout(params, timeout);
    HttpConnectionParams.setSoTimeout(params, timeout);
    
    connManager.setMaxTotal(100);
    connManager.setDefaultMaxPerRoute(10);
 
    this.client = new DefaultHttpClient(connManager, params);
    this.streaming = true;
    this.parameters = new HashMap<String, Expression>();
    
    if (useProxy)
    {
      HttpHost proxy = new HttpHost(proxyHost, proxyPort);
      this.client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
    }
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public Message process(Message message)
  {
    try
    {
      HttpUriRequest request = this.buildRequest(message);
      HttpResponse response = this.execute(request);
      Message httpResponseAsMessage = this.readMessageFrom(response);
      
      message.overrideAllFrom(httpResponseAsMessage);
      
      return message;
    }
    catch (ClientProtocolException e)
    {
      throw new ProcessingException(e);
    }
    catch (IOException e)
    {
      throw new ProcessingException(e);
    }
  }

  private HttpResponse execute(HttpUriRequest request) throws ClientProtocolException, IOException
  {
    return this.client.execute(request);
  }
  
  private HttpUriRequest buildRequest(Message message)
  {
    String url = this.urlExpression.evaluate(message).toString();

    HttpUriRequest request;
    switch (this.fetchMode)
    {
    case GET:
      request = new HttpGet(url); 
      break;
    case POST:
      HttpPost post = new HttpPost(url); 
      
      List<NameValuePair> data = new ArrayList<NameValuePair>();
      for (Entry<String, Expression> entry : this.parameters.entrySet())
      {
        String value = entry.getValue().evaluate(message).toString();
        data.add(new BasicNameValuePair(entry.getKey(), value));
      }
      
      try
      {
        post.setEntity(new UrlEncodedFormEntity(data, HTTP.UTF_8));
      }
      catch (UnsupportedEncodingException e)
      {
        //log
      }
      
      request = post;
      break;
    case DELETE:
      request = new HttpDelete(url); 
      break;
    case PUT:
      request = new HttpPut(url); 
      break;
    default:
      request = new HttpGet(url); 
      break;
    }
    
    return request;
  }
  
  public void addParamter(String parameterName, Expression value)
  {
    Validate.notNull(parameterName);
    Validate.notNull(value);

    this.parameters.put(parameterName, value);
  }
  
  private Message readMessageFrom(HttpResponse response)
  {
    return HttpUtils.readFrom(response, this.isStreaming());
  }
  
  public void setUrlExpression(Expression expression)
  {
    this.urlExpression = expression;
  }

  public void setFetchMode(FetchMode mode)
  {
    this.fetchMode = mode;
  }

  public boolean isStreaming()
  {
    return streaming;
  }

  public void setStreaming(boolean streaming)
  {
    this.streaming = streaming;
  }
}
