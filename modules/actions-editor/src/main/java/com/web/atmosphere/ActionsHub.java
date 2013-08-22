package com.web.atmosphere;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.jersey.SuspendResponse;

@Path("/")
public class ActionsHub {

	private ExecutorService service;
	private Map<String, AtomicBoolean> running;
	
	public ActionsHub() {
		this.service = Executors.newFixedThreadPool(5);
		this.running = new ConcurrentHashMap<String, AtomicBoolean>();
	}
	
	@GET
	@Path("/{contextId}")
	@Produces("text/html;charset=ISO-8859-1")
	public SuspendResponse subscribe(@PathParam("contextId") Broadcaster contextIdBroadcaster) {
		return new SuspendResponse
			.SuspendResponseBuilder()
			.broadcaster(contextIdBroadcaster)
			.outputComments(true)
			.build();
	}
	
	@GET
	@Path("/{contextId}/start")
	public String start(final @PathParam("contextId") String contextId) {
		final AtomicBoolean runValue = new AtomicBoolean(true);
		this.running.put(contextId, runValue);
		
		
		this.service.execute(new Runnable() {
			@Override
			public void run() {
				int i = 0;
				while (runValue.get()) {
					i++;
					Broadcaster broadcaster = BroadcasterFactory.getDefault().lookup(contextId);
					if (broadcaster != null) {
						broadcaster.broadcast("Number: " + Integer.valueOf(i).toString());
					}
					
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		return "OK";
	}
	
	@GET
	@Path("/{contextId}/stop")
	public String stop(final @PathParam("contextId") String contextId) {
		AtomicBoolean value = this.running.remove(contextId);
		
		if (value != null) {
			value.set(false);
		}

		return "OK";
	}
}
