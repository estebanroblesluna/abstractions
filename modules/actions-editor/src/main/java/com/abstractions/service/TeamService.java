package com.abstractions.service;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.helper.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abstractions.model.Team;
import com.abstractions.model.User;
import com.abstractions.model.UserImpl;
import com.abstractions.repository.GenericRepository;

@Service
public class TeamService {

	private GenericRepository repository;
	
	protected TeamService() { }
	
	public TeamService(GenericRepository repository) {
		Validate.notNull(repository);
		
		this.repository = repository;
	}
	
	@Transactional
	public void addTeam(String name, long ownerID) {
	  UserImpl user = this.repository.get(UserImpl.class, ownerID);
		if (user != null) {
			Team team = new Team(name, user);
			this.repository.save(team);
			
			team.addUser(user);
			this.repository.save(user);
		}
	}
	
	@Transactional
	public List<Team> getTeamsOf(long currentUserId) {
	  UserImpl user = this.repository.get(UserImpl.class, currentUserId);
		if (user != null) {
			return user.getTeams();
		} else {
			return new ArrayList<Team>();
		}
	}

	@Transactional
	public Team getTeam(long teamId) {
		return this.repository.get(Team.class, teamId);
	}
}
