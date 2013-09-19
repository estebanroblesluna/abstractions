package com.abstractions.service.rest;

import java.io.InputStream;

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
	@Path("/")
	public Response getAllFiles() {
		try {
			JsonBuilder builder = JsonBuilder.newWithArray("files");
			for (String filename : this.fileService.listFiles()) {
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
	@Path("/{filePath:.+}")
	public Response getFile(@PathParam("filePath") String path) {
		if (!path.isEmpty()) {
			InputStream result = this.fileService.getContentsOfFile(path);
			if (result == null) {
				return Response.status(404).entity("File not found").build();
			}
		} else {
			try {
				JsonBuilder builder = JsonBuilder.newWithArray("files");
				for (String filename : this.fileService.listFiles()) {
					builder.string(filename);
				}
				builder.endArray();
				return Response.ok(builder.getContent()).build();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@PUT
	@Path("/{filePath:.+}")
	public Response putFile(@PathParam("filePath") String path, @RequestBody InputStream stream) {
		this.fileService.storeFile(path, stream);
		return Response.ok("File stored").build();
	}
}
