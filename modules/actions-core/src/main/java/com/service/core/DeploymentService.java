package com.service.core;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.codehaus.jettison.json.JSONObject;

import com.service.repository.ContextRepository;

public class DeploymentService {

	private static final Log log = LogFactory.getLog(DeploymentService.class);
	
	private HttpClient client;
	private ContextRepository repository;

	public DeploymentService(ContextRepository repository) {
		this.repository = repository;

		ThreadSafeClientConnManager connManager = new ThreadSafeClientConnManager();
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, 3000);
		HttpConnectionParams.setSoTimeout(params, 3000);

		connManager.setMaxTotal(100);
		connManager.setDefaultMaxPerRoute(10);

		this.client = new DefaultHttpClient(connManager, params);
		this.client.getParams().setParameter(
				ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
	}

	public boolean deploy(String contextId, String serverHost, String serverPort) {
		String contextJSON = this.repository.getJsonDefinition(contextId);
		String url = "http://" + serverHost + ":" + serverPort
				+ "/service/server/start";

		HttpPost post = new HttpPost(url);
		try {
			
			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			urlParameters.add(new BasicNameValuePair("contextDefinition", contextJSON));
			post.setEntity(new UrlEncodedFormEntity(urlParameters));
			
			HttpResponse response = this.client.execute(post);
			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode >= 200 && statusCode < 300) {
				InputStream is = response.getEntity().getContent();
				JSONObject json = new JSONObject(IOUtils.toString(is));
				return json.has("result") 
						&& StringUtils.equalsIgnoreCase(json.getString("result"), "success");
			} else {
				return false;
			}
		} catch (Exception e) {
			log.error("Error deploying context", e);
		}
		return false;
	}
}
