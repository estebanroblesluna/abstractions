package com.abstractions.service.rest;

import java.io.InputStream;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

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
	@Qualifier("privateResourceService")
	private ResourceService privateResourceService;
	
	@Autowired
	@Qualifier("publicResourceService")
	private ResourceService publicResourceService;
	
	@Autowired
	private SnapshotService snapshotService;

	public ResourceRESTService() {
	}

	@GET
	@Path("{applicationId}/files/")
	public Response getAllFiles(@PathParam("applicationId") long applicationId) {
		try {
			JsonBuilder builder = JsonBuilder.newWithArray("files");
			for (String filename : this.privateResourceService.listResources(applicationId)) {
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
	@Path("{applicationId}/files/{filePath:.+}")
	public Response getFile(@PathParam("applicationId") long applicationId, @PathParam("filePath") String path) {
		InputStream result = this.privateResourceService.getContentsOfResource(applicationId, path);
		if (result == null) {
			return Response.status(404).entity("File not found").build();
		}
		return Response.ok(result).build();
	}
	
	@DELETE
	@Path("{applicationId}/files/{filePath:.+}")
	public Response deleteFile(@PathParam("applicationId") long applicationId, @PathParam("filePath") String path) {
		this.privateResourceService.deleteResource(applicationId, path);
		return Response.ok("File deleted").build();
	}

	@PUT
	@Path("{applicationId}/files/{filePath:.+}")
	public Response putFile(@PathParam("applicationId") long applicationId, @PathParam("filePath") String path, @RequestBody InputStream stream) {
		this.privateResourceService.storeResource(applicationId, path, stream);
		return Response.ok("File stored").build();
	}

	@PUT
	@Path("{applicationId}/files/compressed")
	public Response postCompressedFiles(@PathParam("applicationId") long applicationId, @RequestBody InputStream stream) {
		this.privateResourceService.uncompressContent(applicationId, stream);
		return Response.ok("Files uncompressed").build();
	}
	
	@GET
	@Path("{applicationId}/snapshots/{snapshotId}")
	public Response getSnapshot(@PathParam("applicationId") String applicationId, @PathParam("snapshotId") String snapshotId) {
		InputStream result = this.snapshotService.getContentsOfSnapshot(applicationId, snapshotId);
		if (result == null) {
			return Response.status(404).entity("File not found").build();
		}
		return Response.ok(result).build();
	}
}
