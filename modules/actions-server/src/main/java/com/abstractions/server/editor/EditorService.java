package com.abstractions.server.editor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.abstractions.http.HttpStrategy;
import com.abstractions.server.core.ActionsServer;
import com.abstractions.server.core.StatisticsInfo;

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
	 * Sends statistics to editor
	 */
	public void sendStatistics() {
		Map<String, StatisticsInfo> statisticsPerApplication = this.actionsServer.getStatistics();
		
		try {
			String statisticsAsJson = this.toJSON(statisticsPerApplication).toString();
			this.strategy
					.post(this.editorUrl + "/statistics")
					.addFormParam("server-key", this.serverKey)
					.addFormParam("statistics", statisticsAsJson)
					.execute();
		} catch (ClientProtocolException e) {
			log.warn("Error sending statistics");
		} catch (IOException e) {
			log.warn("Error sending statistics");
		} catch (JSONException e) {
			log.warn("Error sending statistics. Creating JSON");
		}
	}
	
	private JSONObject toJSON(Map<String, StatisticsInfo> statisticsPerApplication) throws JSONException {
		JSONObject root = new JSONObject();
		
		for (Map.Entry<String, StatisticsInfo> entry : statisticsPerApplication.entrySet()) {
			root.put(entry.getKey(), this.toJSON(entry.getValue()));
		}
		
		return root;
	}

	private JSONObject toJSON(StatisticsInfo value) throws JSONException {
		JSONObject statistics = new JSONObject();
		
		statistics.put("date", value.getDate().getTime());
		statistics.put("uncaughtExceptions", value.getUncaughtExceptions());
		
		JSONObject receivedMessages = new JSONObject();
		for (Map.Entry<String, Long> entry : value.getReceivedMessages().entrySet()) {
			receivedMessages.put(entry.getKey(), entry.getValue());
		}
		statistics.put("receivedMessages", receivedMessages);

		return statistics;
	}

	/**
	 * Notifies the editor that this server is alive
	 * and sends the server's status
	 */
	public void ping() {
		HttpResponse response = null;
		try {
			response = this.strategy
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
		} finally {
			this.strategy.close(response);
		}
	}
	
	
	/**
	 * Starts the deployment of deploymentId
	 * @param deploymentId
	 */
	private void startDeployment(Long deploymentId) {
		HttpResponse response = null;
		try {
			response = this.strategy
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
		} finally {
			this.strategy.close(response);
		}
	}

	private void notifyFailure(Long deploymentId) {
		try {
			this.strategy
					.post(this.editorUrl + "/deployment/" + deploymentId + "/end-failure")
					.addFormParam("server-key", this.serverKey)
					.executeAndClose();
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
					.executeAndClose();
		} catch (ClientProtocolException e) {
			log.warn("Error deploying " + deploymentId, e);
		} catch (IOException e) {
			log.warn("Error deploying " + deploymentId, e);
		}
	}
}
