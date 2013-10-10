package com.abstractions.service.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.abstractions.service.ServerService;

@Path("/server")
public class ServerRESTService {

	private ServerService service;
	
	public ServerRESTService(ServerService service) {
		this.service = service;
	}
	
	@POST
	@Path("/status")
	public String ping(@FormParam("server-key") String serverKey) {
		this.service.updateServerStatusWithKey(serverKey);
		return "OK";
	}
}
