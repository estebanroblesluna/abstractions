package com.abstractions.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abstractions.model.Server;
import com.abstractions.model.ServerGroup;
import com.abstractions.model.Team;
import com.abstractions.service.ServerGroupService;
import com.abstractions.service.ServerService;
import com.abstractions.service.TeamService;
import com.abstractions.utils.IdGenerator;

@Controller
public class ServerController {

	private static final Log log = LogFactory.getLog(ServerController.class);
	
	@Autowired
	ServerService service;

	@Autowired
	TeamService teamService;

	@Autowired
	ServerGroupService serverGroupService;
	
	private String defaultServerAppDirectory;
	private String defaultEditorUrl;
	private String defaultServerWar;

	@RequestMapping(value = "/teams/{teamId}/serverGroups/{serverGroupId}/servers/", method = RequestMethod.GET)
	public ModelAndView home(@PathVariable("teamId") long teamId, @PathVariable("serverGroupId") long serverGroupId) {
		ModelAndView mv = new ModelAndView("servers");

		String script = "";
		InputStream io = null;
		try {
			io = this.getClass().getClassLoader().getResourceAsStream("ami-user-data.txt");
			script = IOUtils.toString(io);
		} catch (IOException e) {
			log.warn("Cant read ami script", e);
		} finally {
			IOUtils.closeQuietly(io);
		}

		List<Server> servers = this.service.getServers(teamId, serverGroupId);
		
		for (Server server : servers) {
			String serverScript = script.replaceAll("\n", "<br>");
			serverScript = serverScript.replaceAll("\\$\\$\\$server.apps.directory\\$\\$\\$", this.defaultServerAppDirectory);
			serverScript = serverScript.replaceAll("\\$\\$\\$server.editor.url\\$\\$\\$", this.defaultEditorUrl);
			serverScript = serverScript.replaceAll("\\$\\$\\$server.id\\$\\$\\$", server.getExternalId());
			serverScript = serverScript.replaceAll("\\$\\$\\$server.key\\$\\$\\$", server.getKey());
			serverScript = serverScript.replaceAll("\\$\\$\\$server.war.url\\$\\$\\$", this.defaultServerWar);
			

			server.setAmiScript(serverScript);
		}
		
		ServerGroup serverGroup = this.serverGroupService.getServerGroup(serverGroupId);
		mv.addObject("servers", servers);
		Team team = this.teamService.getTeam(teamId);
		mv.addObject("team", team);
		mv.addObject("serverGroup", serverGroup);
		return mv;
	}
	
	@RequestMapping(value = "/teams/{teamId}/serverGroups/{serverGroupId}/servers/add", method = RequestMethod.GET)
	public ModelAndView add(@PathVariable("teamId") long teamId, @PathVariable("serverGroupId") long serverGroupId) {
		
		ModelAndView mv = new ModelAndView("addServer");
		ServerGroup serverGroup = this.serverGroupService.getServerGroup(serverGroupId);
		
		Team team = this.teamService.getTeam(teamId);
		mv.addObject("team", team);
		mv.addObject("serverGroup", serverGroup);
		return mv;
	}
	
	@RequestMapping(value = "/teams/{teamId}/serverGroups/{serverGroupId}/servers/add", method = RequestMethod.POST)
	public String addServer(
			@ModelAttribute("form") AddServerForm form, 
			@PathVariable("teamId") long teamId, 
			@PathVariable("serverGroupId") long serverGroupId) {
		this.service.addServer(serverGroupId, form.getName(), form.getIpDNS(), form.getPort());
		return "redirect:/teams/" + teamId + "/serverGroups/" + serverGroupId + "/servers/";
	}

	public String getDefaultServerAppDirectory() {
		return defaultServerAppDirectory;
	}

	@Value("${server.default.app.directory}") 
	public void setDefaultServerAppDirectory(String defaultServerAppDirectory) {
		this.defaultServerAppDirectory = defaultServerAppDirectory;
	}

	public String getDefaultEditorUrl() {
		return defaultEditorUrl;
	}

	@Value("${server.default.editor.url}") 
	public void setDefaultEditorUrl(String defaultEditorUrl) {
		this.defaultEditorUrl = defaultEditorUrl;
	}

	public String getDefaultServerWar() {
		return defaultServerWar;
	}

	@Value("${server.default.server.war.url}") 
	public void setDefaultServerWar(String defaultServerWar) {
		this.defaultServerWar = defaultServerWar;
	}
}
