package com.abstractions.server.editor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.PathParam;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.abstractions.http.HttpStrategy;
import com.abstractions.model.ProfilingInfo;
import com.abstractions.model.ProfilingInfoJSONMarshaller;
import com.abstractions.server.core.ActionsServer;
import com.abstractions.server.core.StatisticsInfo;
import com.abstractions.service.rest.ResponseUtils;

public class EditorService {

	private static final Log log = LogFactory.getLog(EditorService.class);
	
	private final HttpStrategy strategy;
	private final String editorUrl;
	private final String serverId;
	private final String serverKey;
	private final ActionsServer actionsServer;
	
	public EditorService(HttpStrategy strategy, String editorUrl, String serverId, String serverKey, ActionsServer actionsServer) {
		this.strategy = strategy;
		
		this.editorUrl = editorUrl;
		this.serverKey = serverKey;
		this.serverId = serverId;

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
					.addFormParam("server-id", this.serverId)
					.addFormParam("server-key", this.serverKey)
					.addFormParam("statistics", statisticsAsJson)
					.executeAndClose();
		} catch (ClientProtocolException e) {
			log.warn("Error sending statistics " + this.editorUrl);
		} catch (IOException e) {
			log.warn("Error sending statistics " + this.editorUrl);
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
				.addFormParam("server-id", this.serverId)
				.addFormParam("server-key", this.serverKey)
				.execute();
			
			String json = IOUtils.toString(response.getEntity().getContent());
			JSONObject object = new JSONObject(json);
			this.processPendingDeployments(object);
			this.processPendingCommands(object);
		} catch (ClientProtocolException e) {
			log.warn("Error pinging server " + this.editorUrl);
		} catch (IOException e) {
			log.warn("Error pinging server " + this.editorUrl);
		} catch (JSONException e) {
			log.warn("Error pinging server " + this.editorUrl);
		} finally {
			this.strategy.close(response);
		}
	}
	
	public void sendProfiling() {
		Collection<String> applicationIds = this.actionsServer.getApplicationIds();

		JSONObject profilingInfo = new JSONObject();
		JSONArray infos = new JSONArray();

		try {
			for (String applicationId : applicationIds) {
				ProfilingInfo info = this.actionsServer.getProfilingInfo(applicationId);
				info.setApplicationId(applicationId);
				JSONObject applicationJSON = ProfilingInfoJSONMarshaller.toJSON(info);
				infos.put(applicationJSON);
			}
			
			profilingInfo.put("profiling", infos);
		} catch (JSONException e) {
			log.warn("Error generating json", e);
		}
		
		
		HttpResponse response = null;
		try {
			response = this.strategy
				.post(this.editorUrl + "/profiling")
				.addFormParam("server-id", this.serverId)
				.addFormParam("server-key", this.serverKey)
				.addFormParam("profiling-info", profilingInfo.toString())
				.execute();
			
			String json = IOUtils.toString(response.getEntity().getContent());
			JSONObject object = new JSONObject(json);
		} catch (ClientProtocolException e) {
			log.warn("Error sending profiling info " + this.editorUrl);
		} catch (IOException e) {
			log.warn("Error sending profiling info " + this.editorUrl);
		} catch (JSONException e) {
			log.warn("Error sending profiling info " + this.editorUrl);
		} finally {
			this.strategy.close(response);
		}
	}

	public void sendLogging() {
	}

	private void processPendingCommands(JSONObject object) throws JSONException {
		String commandsKey = "commands";
		
		if (object.has(commandsKey)) {
			JSONArray array = object.getJSONArray(commandsKey);
			for (int i = 0; i < array.length(); i++) {
				JSONObject command = array.getJSONObject(i);
				String name = command.getString("name");

				Map<String, String> args = new HashMap<String, String>();
				JSONObject argsJSON = command.getJSONObject("args");
				for (Iterator iterator = argsJSON.keys(); iterator.hasNext();) {
					String key = (String) iterator.next();
					String value = argsJSON.getString(key);
					args.put(key, value);
				}
				
				this.executeCommand(name, args);
			}
		}
	}

	private void executeCommand(String name, Map<String, String> args) {
		String applicationId = args.get("applicationId");
		String elementId = args.get("elementId");
		String objectId = args.get("elementId");
		String contextId = args.get("contextId");
		String deploymentId = args.get("deploymentId");

		if (StringUtils.equals(name, "ADD_PROFILER")) {
			this.actionsServer.addProfiler(applicationId, objectId);
			
		} else if (StringUtils.equals(name, "REMOVE_PROFILER")) {
			this.actionsServer.removeProfiler(applicationId, objectId);

		} else if (StringUtils.equals(name, "ADD_LOGGER")) {
			String beforeExpression = args.get("beforeExpression");
			String afterExpression = args.get("afterExpression");
			boolean isBeforeConditional = false;
			boolean isAfterConditional = false;

			try {
				isBeforeConditional = Boolean.valueOf(args.get("isBeforeConditional"));
			} catch (Exception e) {
				//IGNORE
			}

			try {
				isAfterConditional = Boolean.valueOf(args.get("isAfterConditional"));
			} catch (Exception e) {
				//IGNORE
			}
			
			String beforeConditionalExpressionValue = args.get("beforeConditionalExpressionValue");
			String afterConditionalExpressionValue = args.get("afterConditionalExpressionValue");
			
			this.actionsServer.addLogger(applicationId, elementId, beforeExpression, afterExpression, isBeforeConditional, isAfterConditional, beforeConditionalExpressionValue, afterConditionalExpressionValue);

		} else if (StringUtils.equals(name, "REMOVE_LOGGER")) {
			this.actionsServer.removeLogger(applicationId, objectId);

		} else if (StringUtils.equals(name, "ADD_LAZY_COMPUTED_CACHE")) {
			String memcachedURL = args.get("memcachedURL");
			String keyExpression = args.get("keyExpression");
			String cacheExpressions = args.get("cacheExpressions");
			int ttl = 1000 * 60;
			try {
				ttl = Integer.valueOf(args.get("ttl"));
			} catch (Exception e) {
				//IGNORE
			}
			
			this.actionsServer.addLazyComputedCache(applicationId, objectId, memcachedURL, keyExpression, cacheExpressions, ttl);

		} else if (StringUtils.equals(name, "ADD_LAZY_AUTOREFRESHABLE_CACHE")) {
			String memcachedURL = args.get("memcachedURL");
			String keyExpression = args.get("keyExpression");
			String cacheExpressions = args.get("cacheExpressions");
			int oldCacheEntryInMills = 1000 * 60;
			try {
				oldCacheEntryInMills = Integer.valueOf(args.get("oldCacheEntryInMills"));
			} catch (Exception e) {
				//IGNORE
			}
			
			this.actionsServer.addLazyAutorefreshableCache(applicationId, objectId, memcachedURL, keyExpression, cacheExpressions, oldCacheEntryInMills);

		} else {
			log.warn("Unrecognized command " + StringUtils.defaultString(name));
		}
	}

	public void processPendingDeployments(JSONObject object) throws JSONException {
		String key = "deployments";
		
		if (object.has(key)) {
			JSONArray array = object.getJSONArray(key);
			for (int i = 0; i < array.length(); i++) {
				JSONObject deployment = array.getJSONObject(i);
				long deploymentId = deployment.getLong("deploymentId");
				long applicationId = deployment.getLong("applicationId");
				this.startDeployment(deploymentId, applicationId);
			}
		}
	}
	
	
	/**
	 * Starts the deployment of deploymentId
	 * @param deploymentId
	 * @param applicationId 
	 */
	private void startDeployment(Long deploymentId, Long applicationId) {
		HttpResponse response = null;
		try {
			response = this.strategy
					.post(this.editorUrl + "/deployment/" + deploymentId + "/start")
					.addFormParam("server-id", this.serverId)
					.addFormParam("server-key", this.serverKey)
					.execute();
			
			InputStream io = response.getEntity().getContent();
			this.actionsServer.start(applicationId.toString(), io); 
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
					.addFormParam("server-id", this.serverId)
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
					.addFormParam("server-id", this.serverId)
					.addFormParam("server-key", this.serverKey)
					.executeAndClose();
		} catch (ClientProtocolException e) {
			log.warn("Error deploying " + deploymentId, e);
		} catch (IOException e) {
			log.warn("Error deploying " + deploymentId, e);
		}
	}
}
