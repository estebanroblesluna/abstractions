package com.abstractions.service.rest;

import java.io.InputStream;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.abstractions.service.FileService;
import com.modules.dust.JsonBuilder;

@Component
@Path("/fileStore")
public class FileRESTService {

	@Autowired
	private FileService fileService;

	public FileRESTService() {
	}

	@GET
	@Path("{applicationId}/files/")
	public Response getAllFiles(@PathParam("applicationId") String applicationId) {
		try {
			JsonBuilder builder = JsonBuilder.newWithArray("files");
			for (String filename : this.fileService.listFiles(applicationId)) {
				builder.string(filename);
			}
			builder.endArray();
			return Response.ok(builder.getContent()).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(404).entity("Files not found").build();
	}

	@GET
	@Path("{applicationId}/files/{filePath:.+}")
	public Response getFile(@PathParam("applicationId") String applicationId, @PathParam("filePath") String path) {
		InputStream result = this.fileService.getContentsOfFile(applicationId, path);
		if (result == null) {
			return Response.status(404).entity("File not found").build();
		}
		return Response.ok(result).build();
	}
	
	@DELETE
	@Path("{applicationId}/files/{filePath:.+}")
	public Response deleteFile(@PathParam("applicationId") String applicationId, @PathParam("filePath") String path) {
		this.fileService.deleteFile(applicationId, path);
		return Response.ok("File deleted").build();
	}

	@PUT
	@Path("{applicationId}/files/{filePath:.+}")
	public Response putFile(@PathParam("applicationId") String applicationId, @PathParam("filePath") String path, @RequestBody InputStream stream) {
		this.fileService.storeFile(applicationId, path, stream);
		return Response.ok("File stored").build();
	}

	@PUT
	@Path("{applicationId}/files/compressed")
	public Response postCompressedFiles(@PathParam("applicationId") String applicationId, @RequestBody InputStream stream) {
		this.fileService.uncompressFile(applicationId, stream);
		return Response.ok("Files uncompressed").build();
	}
}
