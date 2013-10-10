package com.abstractions.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abstractions.model.ServerGroup;
import com.abstractions.model.Team;
import com.abstractions.repository.GenericRepository;

@Service
public class ServerGroupService {

	private GenericRepository repository;
	private TeamService teamService;

	public ServerGroupService() {
		super();
	}

	public ServerGroupService(GenericRepository repository,
			TeamService teamService) {
		super();
		this.repository = repository;
		this.teamService = teamService;
	}

	@Transactional
	public void addServerGroup(long teamId, String name) {
		Team team = this.teamService.getTeam(teamId);
		ServerGroup server = new ServerGroup(name, team);

		team.addServerGroup(server);
		this.repository.save(server);
		this.repository.save(team);
	}

	@Transactional
	public List<ServerGroup> getServerGroups() {
		return this.repository.get(ServerGroup.class, "name");
	}

	@Transactional
	public List<ServerGroup> getServerGroupsOf(long teamId) {
		List<ServerGroup> serverGroups = new ArrayList<ServerGroup>();
		serverGroups.addAll(this.teamService.getTeam(teamId).getServerGroups());
		return serverGroups;
	}

    @Transactional
	public ServerGroup getServerGroup(long serverGroupId) {
		return this.repository.get(ServerGroup.class, serverGroupId);
	}

}
