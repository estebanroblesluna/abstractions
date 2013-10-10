package com.abstractions.service.rest;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jsoup.nodes.Attribute;

import com.core.api.Message;
import com.core.interpreter.DebuggableThread;
import com.core.interpreter.Interpreter;
import com.core.interpreter.InterpreterDelegate;
import com.service.core.ContextDefinition;
import com.service.core.DevelopmentContextHolder;
import com.service.core.InterpreterHolder;
import com.service.core.ObjectDefinition;
import com.service.core.ServiceException;

@Path("/interpreter")
public class InterpreterRESTService implements InterpreterDelegate {

	private static Log log = LogFactory.getLog(InterpreterRESTService.class);

	private DevelopmentContextHolder contextHolder;
	private InterpreterHolder interpreterHolder;
	
	private ExecutorService executor;
	
	public InterpreterRESTService(DevelopmentContextHolder contextHolder, InterpreterHolder interpreterHolder) {
		this.executor = Executors.newFixedThreadPool(10);
		
		this.contextHolder = contextHolder;
		this.interpreterHolder = interpreterHolder;
	}
	
	@POST
	@Path("/{contextId}/create")
	public Response createInterpreter(@PathParam("contextId") String contextId, @FormParam("initialProcessorId") String initialProcessorId) {
		ContextDefinition context = this.contextHolder.get(contextId);
		
		if (context == null) {
			return ResponseUtils.fail("Context not found");
		}
		
		ObjectDefinition startProcessor = context.getDefinition(initialProcessorId);

		if (startProcessor == null) {
			return ResponseUtils.fail("Start processor not found");
		}

		Interpreter interpreter = new Interpreter(context, startProcessor);
		this.interpreterHolder.put(interpreter);
		
		interpreter.setDelegate(this);
		
		return ResponseUtils.ok(
				new Attribute("id", interpreter.getId()));
	}
	
	@POST
	@Path("/{interpreterId}/run")
	public Response run(@PathParam("interpreterId") String interpreterId, @FormParam("initialMessage") String initialMessageAsJson) {
		Message initialMessage = this.parse(initialMessageAsJson);
		Interpreter interpreter = this.interpreterHolder.get(interpreterId);
		
		if (interpreter == null) {
			return ResponseUtils.fail("Interpreter not found");
		}
		
		try {
			interpreter.sync();
			interpreter.run(initialMessage);
			return ResponseUtils.ok();
		} catch (ServiceException e) {
			return ResponseUtils.fail("Error syncing context");
		}
	}
	
	@POST
	@Path("/{interpreterId}/debug")
	public Response debug(@PathParam("interpreterId") String interpreterId, @FormParam("initialMessage") String initialMessageAsJson) {
		Message initialMessage = this.parse(initialMessageAsJson);
		Interpreter interpreter = this.interpreterHolder.get(interpreterId);
		
		if (interpreter == null) {
			return ResponseUtils.fail("Interpreter not found");
		}
		
		try {
			interpreter.sync();
			interpreter.debug(initialMessage);
			return ResponseUtils.ok();
		} catch (ServiceException e) {
			return ResponseUtils.fail("Error syncing context");
		}
	}
	
	@POST
	@Path("/{interpreterId}/{threadId}/resume")
	public Response resume(@PathParam("interpreterId") String interpreterId, @PathParam("threadId") Long threadId) {
		Interpreter interpreter = this.interpreterHolder.get(interpreterId);
		
		if (interpreter == null) {
			return ResponseUtils.fail("Interpreter not found");
		}
		
		DebuggableThread thread = interpreter.getThread(threadId);
		thread.resume();
		
		return ResponseUtils.ok();
	}
	
	@POST
	@Path("/{interpreterId}/{threadId}/step")
	public Response step(@PathParam("interpreterId") String interpreterId, @PathParam("threadId") Long threadId) {
		Interpreter interpreter = this.interpreterHolder.get(interpreterId);
		
		if (interpreter == null) {
			return ResponseUtils.fail("Interpreter not found");
		}
		
		DebuggableThread thread = interpreter.getThread(threadId);
		thread.resume();
		
		return ResponseUtils.ok();
	}
	
	@POST
	@Path("/{interpreterId}/{threadId}/evaluate")
	public Response evaluate(@PathParam("interpreterId") String interpreterId, @PathParam("threadId") Long threadId, @FormParam("expression") String anExpression) {
		Interpreter interpreter = this.interpreterHolder.get(interpreterId);
		
		if (interpreter == null) {
			return ResponseUtils.fail("Interpreter not found");
		}
		
		DebuggableThread thread = interpreter.getThread(threadId);

		if (thread == null) {
			return ResponseUtils.fail("Thread not found");
		}

		String result = thread.evaluate(anExpression).toString();
		Message message = thread.getCurrentMessage();
		
		try {
			JSONObject messageAsJSON = this.serialize(message);

			JSONObject json = new JSONObject();
			json.put("evaluationResult", result);
			json.put("currentMessage", messageAsJSON);

			return ResponseUtils.ok(new Attribute("result", json.toString()));
		} catch (JSONException e) {
			log.warn("Error creating json", e);
			return ResponseUtils.fail("Error creating response");
		}
		
	}

	@Override
	public void stopInBreakPoint(final String interpreterId, final String threadId, final String contextId, final ObjectDefinition currentProcessor, final Message clonedMessage) {
		this.executor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					JSONObject json = new JSONObject();
					json.put("eventType", "breakpoint");
					json.put("interpreterId", interpreterId);
					json.put("threadId", threadId);
					json.put("contextId", contextId);
					json.put("processorId", currentProcessor.getId());
					
					JSONObject messageAsJSON = serialize(clonedMessage);
					json.put("currentMessage", messageAsJSON);
					
					sendEvent(contextId, json);
				} catch (JSONException e) {
					log.warn("Error creating json", e);
				}
			}
		});
	}

	@Override
	public void beforeStep(final String interpreterId, final String threadId, final String contextId, final ObjectDefinition currentProcessor, final Message clonedMessage) {
		this.executor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					JSONObject json = new JSONObject();
					json.put("eventType", "before-step");
					json.put("interpreterId", interpreterId);
					json.put("threadId", threadId);
					json.put("contextId", contextId);
					json.put("processorId", currentProcessor.getId());
					
					JSONObject messageAsJSON = serialize(clonedMessage);
					json.put("currentMessage", messageAsJSON);
					
					sendEvent(contextId, json);
				} catch (JSONException e) {
					log.warn("Error creating json", e);
				}
			}
		});
	}

	@Override
	public void afterStep(final String interpreterId, final String threadId, final String contextId, final ObjectDefinition currentProcessor, final Message clonedMessage) {
		this.executor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					JSONObject json = new JSONObject();
					json.put("eventType", "after-step");
					json.put("interpreterId", interpreterId);
					json.put("threadId", threadId);
					json.put("contextId", contextId);
					json.put("processorId", currentProcessor.getId());
					
					JSONObject messageAsJSON = serialize(clonedMessage);
					json.put("currentMessage", messageAsJSON);
					
					sendEvent(contextId, json);
				} catch (JSONException e) {
					log.warn("Error creating json", e);
				}
			}
		});
	}

	@Override
	public void finishInterpretation(final String interpreterId, final String threadId, final String contextId, final Message clonedMessage) {
		this.executor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					JSONObject json = new JSONObject();
					json.put("eventType", "finish-interpretation");
					json.put("interpreterId", interpreterId);
					json.put("threadId", threadId);
					json.put("contextId", contextId);
					
					JSONObject messageAsJSON = serialize(clonedMessage);
					json.put("currentMessage", messageAsJSON);
					
					sendEvent(contextId, json);
				} catch (JSONException e) {
					log.warn("Error creating json", e);
				}
			}
		});
	}
	
	@Override
	public void uncaughtException(final String interpreterId, final String threadId,
			final String contextId, final ObjectDefinition currentProcessor,
			final Message currentMessage, final Exception e) {
		this.executor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					JSONObject json = new JSONObject();
					json.put("eventType", "uncaught-exception");
					json.put("interpreterId", interpreterId);
					json.put("threadId", threadId);
					json.put("contextId", contextId);
					json.put("processorId", currentProcessor.getId());
					
					JSONObject messageAsJSON = serialize(currentMessage);
					json.put("currentMessage", messageAsJSON);
					
					String exceptionAsString = ExceptionUtils.getFullStackTrace(e);
					json.put("exception", exceptionAsString);
					
					sendEvent(contextId, json);
				} catch (JSONException e) {
					log.warn("Error creating json", e);
				}
			}
		});
	}
	
	private Message parse(String initialMessageAsJson) {
		Message message = new Message();
		
		if (!StringUtils.isEmpty(initialMessageAsJson)) {
			try {
				JSONObject json = new JSONObject(initialMessageAsJson);
				
				Object payload = json.get("payload");
				message.setPayload(payload);
				
				JSONArray array = json.getJSONArray("properties");
				for (int i = 0; i < array.length(); i++) {
					JSONObject propertyJSON = array.getJSONObject(i);
					String key = propertyJSON.getString("key");
					Object value = propertyJSON.get("value");
					message.putProperty(key, value);
				}
			} catch (JSONException e) {
				log.warn("Error parsing json", e);
			}
		}
		
		return message;
	}
	
	private JSONObject serialize(Message message) throws JSONException {
		JSONObject result = new JSONObject();
		
		String trunkedPayload = StringUtils.left(message.getPayload().toString(), 100);
		if (message.getPayload().toString().length() > 100) {
			trunkedPayload = trunkedPayload + "...";
		}
		result.put("payload", trunkedPayload);
		
		
		Map<String, Object> sortedProperties = new TreeMap<String, Object>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.toLowerCase().compareTo(o2.toLowerCase());
			}
		});
		sortedProperties.putAll(message.getProperties());

		JSONArray properties = new JSONArray();
		for (Map.Entry<String, Object> entry : sortedProperties.entrySet()) {
			JSONObject entryJSON = new JSONObject();
			entryJSON.put("key", entry.getKey());
			entryJSON.put("value", entry.getValue());
			
			properties.put(entryJSON);
		}
		
		result.put("properties", properties);

		return result;
	}
	
	private void sendEvent(String contextId, JSONObject json) {
		Broadcaster broadcaster = BroadcasterFactory.getDefault().lookup(contextId);
		if (broadcaster != null) {
			broadcaster.broadcast(json.toString());
		}
	}
}
