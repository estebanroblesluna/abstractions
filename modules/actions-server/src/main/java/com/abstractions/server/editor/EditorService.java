package com.abstractions.server.editor;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.abstractions.http.HttpStrategy;
import com.abstractions.server.core.ActionsServer;

public class EditorService {

	private static final Log log = LogFactory.getLog(EditorService.class);
	
	private final HttpStrategy strategy;
	private final String editorUrl;
	private final String serverKey;
	private final ActionsServer actionsServer;
	
	public EditorService(HttpStrategy strategy, String editorUrl, String serverKey, ActionsServer actionsServer) {
		this.strategy = strategy;
		this.editorUrl = editorUrl;
		this.serverKey = serverKey;
		this.actionsServer = actionsServer;
	}
	
	/**
	 * Notifies the editor that this server is alive
	 * and sends the server's status
	 */
	public void ping() {
		try {
			HttpResponse response = this.strategy
				.post(this.editorUrl + "/status")
				.addFormParam("server-key", this.serverKey)
				.execute();
			
			String json = IOUtils.toString(response.getEntity().getContent());
			JSONObject object = new JSONObject(json);
			String key = "deploymentIds";
			
			if (object.has(key)) {
				JSONArray array = object.getJSONArray(key);
				for (int i = 0; i < array.length(); i++) {
					long deploymentId = array.getLong(i);
					this.startDeployment(deploymentId);
				}
			}
		} catch (ClientProtocolException e) {
			log.warn("Error pinging server");
		} catch (IOException e) {
			log.warn("Error pinging server");
		} catch (JSONException e) {
			log.warn("Error pinging server");
		}
	}
	
	
	/**
	 * Starts the deployment of deploymentId
	 * @param deploymentId
	 */
	private void startDeployment(Long deploymentId) {
		try {
			HttpResponse response = this.strategy
					.post(this.editorUrl + "/deployment/" + deploymentId + "/start")
					.addFormParam("server-key", this.serverKey)
					.execute();
			
			InputStream io = response.getEntity().getContent();
			this.actionsServer.start(deploymentId.toString(), io); //TODO this should be the application id
			this.notifySuccess(deploymentId);

		} catch (ClientProtocolException e) {
			log.warn("Error deploying " + deploymentId, e);
			this.notifyFailure(deploymentId);
		} catch (IOException e) {
			log.warn("Error deploying " + deploymentId, e);
			this.notifyFailure(deploymentId);
		}
	}

	private void notifyFailure(Long deploymentId) {
		try {
			this.strategy
					.post(this.editorUrl + "/deployment/" + deploymentId + "/end-failure")
					.addFormParam("server-key", this.serverKey)
					.execute();
		} catch (ClientProtocolException e) {
			log.warn("Error deploying " + deploymentId, e);
		} catch (IOException e) {
			log.warn("Error deploying " + deploymentId, e);
		}
	}
	private void notifySuccess(Long deploymentId) {
		try {
			this.strategy
					.post(this.editorUrl + "/deployment/" + deploymentId + "/end-success")
					.addFormParam("server-key", this.serverKey)
					.execute();
		} catch (ClientProtocolException e) {
			log.warn("Error deploying " + deploymentId, e);
		} catch (IOException e) {
			log.warn("Error deploying " + deploymentId, e);
		}
	}
}
