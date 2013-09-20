package com.abstractions.web;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abstractions.service.FileService;

@Controller
@RequestMapping("/fileEditor")
public class FileEditorController {

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
		return mv;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ModelAndView uploadFile(FileUploadForm fileUploadForm, BindingResult result) {
		ModelAndView mv = new ModelAndView("fileEditor");
		try {
			this.fileService.uncompressFile(fileUploadForm.getFile().getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mv;
	}

}
