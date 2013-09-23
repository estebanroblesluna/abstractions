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

import com.abstractions.service.FileService;

@Controller
@RequestMapping("/fileEditor")
public class FileEditorController {

	
	@Autowired @Qualifier("staticResourcesBaseUrl")
	private String staticResourcesUrl;
	
	@Autowired @Qualifier("fileStorageServiceBaseUrl")
	private String fileStorageServiceBaseUrl;
	
	@Autowired
	private FileService fileService;

	public FileEditorController() {
	}

	public FileEditorController(FileService fileService) {
		this.fileService = fileService;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView("fileEditor");
		mv.addObject("staticResourcesUrl", this.staticResourcesUrl);
		mv.addObject("fileStorageServiceBaseUrl", this.fileStorageServiceBaseUrl);
		List<File> files = new ArrayList<File>();
		for (String filename : this.fileService.listFiles()) {
			files.add(new File(filename.substring(1), this.isEditable(filename)));
		}
		mv.addObject("files", files);
		return mv;
	}

	private boolean isEditable(String filename) {
		return filename.endsWith(".css") || filename.endsWith(".js") || filename.endsWith(".tl") || filename.endsWith(".xml");
	}
	
	@RequestMapping(value = "/file/{filename}", method = RequestMethod.GET)
	public ModelAndView fileSelected(@PathVariable("filename") String filename) {
		ModelAndView mv = new ModelAndView("fileEditor");
		mv.addObject("staticResourcesUrl", this.staticResourcesUrl);
		mv.addObject("files", this.fileService.listFiles());
		return mv;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ModelAndView uploadFile(FileUploadForm fileUploadForm, BindingResult result) {
		ModelAndView mv = new ModelAndView("fileEditor");
		mv.addObject("staticResourcesUrl", this.staticResourcesUrl);
		try {
			this.fileService.uncompressFile(fileUploadForm.getFile().getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mv;
	}

}
