package com.modules.dust;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;

import com.abstractions.service.core.FileService;

public class ResourceBasedDustTemplateCompiler {

	private FileService fileService;
	private DustConnector dustConnector;
	
	public ResourceBasedDustTemplateCompiler(FileService fileService, DustConnector dustConnector) {
		this.fileService = fileService;
		this.dustConnector = dustConnector;
	}

	public void mergeAndCompileTemplatesAndJsResources(long applicationId, List<String> paths, String destPath) throws IOException {
		if (!this.fileService.resourceExists(applicationId, destPath)) {
			StringBuilder compiledContent = new StringBuilder();
			List<String> templatesToCompile = new ArrayList<String>();
			for (String path : paths) {
				if (path.endsWith(".js")) {
					if (!compiledContent.toString().isEmpty()) {
						compiledContent.append(";");
					}
					compiledContent.append(IOUtils.toString(this.fileService.getContentsOfFile(applicationId, path)));
				} else if (path.endsWith(".tl")) {
					String originalTemplate = IOUtils.toString(this.fileService.getContentsOfFile(applicationId, path));
					templatesToCompile.add(path);
					templatesToCompile.addAll(this.findDependentTemplates(originalTemplate));
				}
			}
			for (String path : templatesToCompile) {
				String templateName = this.getTemplateNameFromPath(path);
				this.dustConnector.putTemplate(templateName, IOUtils.toString(this.fileService.getContentsOfFile(applicationId, path)));
				compiledContent.append(this.dustConnector.getCompiledTemplate(templateName));
			}
			this.fileService.storeFile(applicationId, destPath, new ByteArrayInputStream(compiledContent.toString().getBytes()));
		}
	}
	
	public void mergeAndCompileStylesheets(long applicationId, List<String> paths, String destPath) throws IOException {
		if (!this.fileService.resourceExists(applicationId, destPath)) {
			StringBuilder compiledContent = new StringBuilder();
			for (String path : paths) {
				if (path.endsWith(".css")) {
					compiledContent.append(this.fileService.getContentsOfFile(applicationId, path));
				}
			}
			this.fileService.storeFile(applicationId, destPath, new ByteArrayInputStream(compiledContent.toString().getBytes()));
		}
	}
	
	private String getTemplateNameFromPath(String templatePath) {
		return templatePath.replace("/", "_").split("\\.")[0];
	}
	
	private Collection<? extends String> findDependentTemplates(String originalTemplate) {
		List<String> templates = new ArrayList<String>();
		Pattern pattern = Pattern.compile("\\{>([a-zA-Z0-9_]*)/\\}");
		Matcher matcher = pattern.matcher(originalTemplate);
		while (matcher.find()) {
	        templates.add(matcher.group(1).replace("_", "/") + ".tl");
	    }
		return templates;
	}

}
