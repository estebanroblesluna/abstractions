package com.abstractions.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.helper.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abstractions.meta.ConnectorDefinition;
import com.abstractions.model.Application;
import com.abstractions.model.Connector;
import com.abstractions.model.Team;
import com.abstractions.repository.GenericRepository;

/**
 * 
 * @author Martin Aparicio Pons (martin.aparicio.pons@gmail.com)
 */
@Service
public class ConnectorService {

  private GenericRepository repository;
  private TeamService teamService;
  private DevelopmentEnvironmentService developmentEnvironmentService;


  protected ConnectorService() {
  }

  public ConnectorService(GenericRepository repository, TeamService teamService, DevelopmentEnvironmentService developmentEnvironmentService) {
    Validate.notNull(repository);
    Validate.notNull(teamService);
    Validate.notNull(developmentEnvironmentService);

    this.repository = repository;
    this.teamService = teamService;
    this.developmentEnvironmentService = developmentEnvironmentService;
  }

  @Transactional
  public List<ConnectorDefinition> getConnectorDefinitions() {
    return this.repository.get(ConnectorDefinition.class, "name");
  }
  
  @Transactional
  public Connector addConnector(long teamId, String name, String type, Map<String, String> configurations) {
    Team team = this.teamService.getTeam(teamId);
    Connector connector = new Connector(name, type, configurations);

    team.addConnector(connector);
    this.repository.save(connector);
    this.repository.save(team);
    
    for (Application application : team.getApplications()) {
      this.developmentEnvironmentService.addConnector(application.getId(), connector.getId(), connector, connector.getName());
    }
    
    return connector;
  }

  @Transactional
  public List<Connector> getConnectors(long teamId) {
    List<Connector> connectors = new ArrayList<Connector>();
    connectors.addAll(this.teamService.getTeam(teamId).getConnectors());
    return connectors;
  }

  @Transactional
  public void removeConnectorById(long teamId, long connectorId) {
    Team team = this.teamService.getTeam(teamId);
    Connector connector = this.repository.get(Connector.class, connectorId);
    this.repository.delete(Connector.class, connectorId);
    
    for (Application application : team.getApplications()) {
      this.developmentEnvironmentService.removeConnector(application.getId(), connectorId, null, connector.getName());
    }
  }
}
