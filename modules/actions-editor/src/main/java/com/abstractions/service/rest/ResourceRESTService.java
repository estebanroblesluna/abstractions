package com.abstractions.service.rest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.abstractions.service.SnapshotService;
import com.abstractions.service.core.ResourceService;
import com.abstractions.utils.JsonBuilder;

@Component
@Path("/fileStore")
public class ResourceRESTService {

	private static Log log = LogFactory.getLog(ResourceRESTService.class);
	
	@Autowired
	@Qualifier("publicResourceService")
	private ResourceService publicResourceService;
	@Autowired
	@Qualifier("privateResourceService")
	private ResourceService privateResourceService;
	@Autowired
	private SnapshotService snapshotService;

	public ResourceRESTService() {
	}

	@GET
	@Path("{applicationId}/files/{resourceType}/")
	public Response getAllFiles(@PathParam("applicationId") long applicationId, @PathParam("resourceType") String resourceType) {
		ResourceService resourceService = resourceType.equals("public")? publicResourceService : privateResourceService;
		try {
			JsonBuilder builder = JsonBuilder.newWithArray("files");
			for (String filename : resourceService.listResources(applicationId)) {
				builder.string(filename);
			}
			builder.endArray();
			return Response.ok(builder.getContent()).build();
		} catch (Exception e) {
			log.error("Error building file list", e);
		}
		return Response.status(404).entity("Files not found").build();
	}

	@GET
	@Path("{applicationId}/files/{resourceType}/{filePath:.+}")
	public Response getFile(@PathParam("applicationId") long applicationId, @PathParam("filePath") String path, @PathParam("resourceType") String resourceType) {
		ResourceService resourceService = resourceType.equals("public")? publicResourceService : privateResourceService;
		InputStream result = resourceService.getContentsOfResource(applicationId, path);
		if (result == null) {
			return Response.status(404).entity("File not found").build();
		} 
		
		ResponseBuilder builder = Response.ok(result);
		if (path.endsWith(".css")) {
		  builder.type(MediaType.valueOf("text/css"));
		} else if (path.endsWith(".js")) {
      builder.type(MediaType.valueOf("application/javascript"));
    } else if (path.endsWith(".gif")) {
      builder.type(MediaType.valueOf("image/gif"));
    } else if (path.endsWith(".jpg") || path.endsWith(".jpeg")) {
      builder.type(MediaType.valueOf("image/jpeg"));
    } else if (path.endsWith(".png")) {
      builder.type(MediaType.valueOf("image/png"));
		}
		return builder.build();
	}
	
	
	@DELETE
	@Path("{applicationId}/files/{resourceType}/{filePath:.+}")
	public Response deleteFile(@PathParam("applicationId") long applicationId, @PathParam("filePath") String path, @PathParam("resourceType") String resourceType) {
		ResourceService resourceService = resourceType.equals("public")? publicResourceService : privateResourceService;
		resourceService.deleteResource(applicationId, path);
		return Response.ok("File deleted").build();
	}

	@PUT
	@Path("{applicationId}/files/{resourceType}/{filePath:.+}")
	public Response putFile(@PathParam("applicationId") long applicationId, @PathParam("filePath") String path, @RequestBody InputStream stream, @PathParam("resourceType") String resourceType) {
		ResourceService resourceService = resourceType.equals("public")? publicResourceService : privateResourceService;
		resourceService.storeResource(applicationId, path, stream);
		return Response.ok("File stored").build();
	}

	@PUT
	@Path("{applicationId}/files/{resourceType}/compressed")
	public Response postCompressedFiles(@PathParam("applicationId") long applicationId, @RequestBody InputStream stream, @PathParam("resourceType") String resourceType) {
		ResourceService resourceService = resourceType.equals("public")? publicResourceService : privateResourceService;
		resourceService.uncompressContent(applicationId, stream);
		return Response.ok("Files uncompressed").build();
	}
	
	@GET
	@Path("{applicationId}/snapshots/{snapshotId}")
	public Response getSnapshot(@PathParam("applicationId") long applicationId, @PathParam("snapshotId") long snapshotId) {
		InputStream result = new ByteArrayInputStream(this.snapshotService.getSnapshot(snapshotId).getZip());
		if (result == null) {
			return Response.status(404).entity("File not found").build();
		}
		return Response.ok(result).build();
	}
}
