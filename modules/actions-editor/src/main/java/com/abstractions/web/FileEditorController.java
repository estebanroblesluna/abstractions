package com.abstractions.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abstractions.service.core.ResourceService;

@Controller
@RequestMapping("/")
public class FileEditorController {

	@Autowired
	@Qualifier("staticResourcesBaseUrl")
	private String staticResourcesUrl;

	@Autowired
	@Qualifier("fileStorageServiceBaseUrl")
	private String fileStorageServiceBaseUrl;
	
	@Autowired
	@Qualifier("privateResourceService")
	private ResourceService privateResourceService;
	
	@Autowired
	@Qualifier("publicResourceService")
	private ResourceService publicResourceService;

	public FileEditorController() {
	}


	public FileEditorController(ResourceService privateResourceService, ResourceService publicResourceService) {
		this.privateResourceService = privateResourceService;
		this.publicResourceService = publicResourceService;
	}

	@RequestMapping(value = "/teams/{teamId}/applications/{applicationId}/files/", method = RequestMethod.GET)
	public ModelAndView home(@PathVariable("applicationId") long applicationId) {
		ModelAndView mv = new ModelAndView("fileEditor");
		this.addCommonObjects(mv, applicationId);
		return mv;
	}

	private boolean isEditable(String filename) {
		return filename.endsWith(".css") || filename.endsWith(".js") || filename.endsWith(".tl") || filename.endsWith(".xml") || filename.endsWith(".html") ||  filename.endsWith(".htm");
	}

	@RequestMapping(value = "/teams/{teamId}/applications/{applicationId}/files/{filename}", method = RequestMethod.GET)
	public ModelAndView fileSelected(@PathVariable("applicationId") long applicationId, @PathVariable("filename") String filename) {
		ModelAndView mv = new ModelAndView("fileEditor");
		this.addCommonObjects(mv, applicationId);
		return mv;
	}

	private void addCommonObjects(ModelAndView mv, long applicationId) {
		mv.addObject("staticResourcesUrl", this.staticResourcesUrl);
		mv.addObject("fileStorageServiceBaseUrl", this.fileStorageServiceBaseUrl);
		mv.addObject("applicationId", applicationId);
		
		List<File> files = new ArrayList<File>();

		for (String filename : this.publicResourceService.listResources(applicationId)) {
			files.add(new File(filename.substring(1), this.isEditable(filename)));
		}
		mv.addObject("files", files);
	}

	@RequestMapping(value = "/teams/{teamId}/applications/{applicationId}/files/upload", method = RequestMethod.POST)
	public String uploadFile(@PathVariable("teamId") String teamId, @PathVariable("applicationId") long applicationId, FileUploadForm fileUploadForm, BindingResult result) {
		try {
			this.publicResourceService.uncompressContent(applicationId, fileUploadForm.getFile().getInputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/teams/" + teamId + "/applications/" + applicationId + "/files/";
	}

}
