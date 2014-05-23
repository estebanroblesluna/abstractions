package com.abstractions.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.abstractions.model.Application;
import com.abstractions.model.Property;
import com.abstractions.model.Team;
import com.abstractions.model.Connector;
import com.abstractions.repository.GenericRepository;

import org.jsoup.helper.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Martin Aparicio Pons (martin.aparicio.pons@gmail.com)
 */

@Service
public class ConnectorService {

  private GenericRepository repository;
  private TeamService teamService;

  protected ConnectorService() {
  }

  public ConnectorService(GenericRepository repository, TeamService teamService) {
    Validate.notNull(repository);
    Validate.notNull(teamService);

    this.repository = repository;
    this.teamService = teamService;
  }

  @Transactional
  public void addConnector(long teamId, String name, String type, Map<String, String> configurations) {
    Team team = this.teamService.getTeam(teamId);
    Connector connector = new Connector(name, type, configurations);

    team.addConnector(connector);
    this.repository.save(connector);
    this.repository.save(team);
  }

  @Transactional
  public List<Connector> getConnectors(long teamId) {
    List<Connector> connectors = new ArrayList<Connector>();
    connectors.addAll(this.teamService.getTeam(teamId).getConnectors());
    return connectors;
  }

  @Transactional
  public void removeConnectorById(long id) {
    this.repository.delete(Connector.class, id);
  }
}
