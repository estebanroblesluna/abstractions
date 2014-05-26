package com.abstractions.web;

import java.util.HashMap;

import com.abstractions.service.ConnectorService;
import com.abstractions.service.TeamService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author Martin Aparicio Pons (martin.aparicio.pons@gmail.com)
 */
@Controller
public class ConnectorController {

  @Autowired
  ConnectorService service;

  @Autowired
  TeamService teamService;

  @RequestMapping(value = "/teams/{teamId}/connectors", method = RequestMethod.GET)
  public ModelAndView home(@PathVariable("teamId") long teamId) {
    ModelAndView mv = new ModelAndView("connectors");
    String teamName = this.teamService.getTeam(teamId).getName();
    mv.addObject("teamName", teamName);
    mv.addObject("connectors", this.teamService.getTeam(teamId).getConnectors());
    return mv;
  }

  @RequestMapping(value = "/teams/{teamId}/connectors/add", method = RequestMethod.POST)
  public String addContact(@PathVariable("teamId") long teamId, @ModelAttribute("form") AddConnectorForm form) {
    HashMap<String, String> configurations = new HashMap<String, String>();
    for (int it = 0; it < form.getConfigurationNames().size(); it++) {
      String name = form.getConfigurationNames().get(it);
      String value = form.getConfigurationValues().get(it);
      configurations.put(name, value);
    }
    this.service.addConnector(teamId, form.getName(), form.getType(), configurations);
    return "redirect:/teams/" + teamId + "/connectors/";
  }

  @RequestMapping(value = "/teams/{teamId}/connectors/add", method = RequestMethod.GET)
  public ModelAndView add(@PathVariable("teamId") long teamId) {
    ModelAndView mv = new ModelAndView("addConnector");
    String teamName = this.teamService.getTeam(teamId).getName();
    mv.addObject("teamName", teamName);
    mv.addObject("teamId", teamId);
    return mv;
  }

  @RequestMapping(value = "/teams/{teamId}/connectors/remove", method = RequestMethod.POST)
  public String removeConnectors(@PathVariable("teamId") long teamId, @ModelAttribute("form") RemoveForm form) {
    for (long id : form.getIdsToRemove()) {
      this.service.removeConnectorById(id);
    }
    return "redirect:/teams/" + teamId + "/connectors/";
  }
}
