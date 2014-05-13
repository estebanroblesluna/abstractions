package com.abstractions.server.service.rest;

import java.io.InputStream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.abstractions.service.core.ResourceService;

@Component
@Path("/fileStore")
public class ServerResourceRESTService {

  @Autowired
  @Qualifier("publicResourceService")
  private ResourceService publicResourceService;

  @GET
  @Path("{applicationId}/files/public/{filePath:.+}")
  public Response getFile(@PathParam("applicationId") long applicationId, @PathParam("filePath") String path, @PathParam("resourceType") String resourceType) {
    InputStream result = this.publicResourceService.getContentsOfResource(applicationId, path);
    if (result == null) {
      return Response.status(404).entity("File not found").build();
    }
    return Response.ok(result).build();
  }
  
}
