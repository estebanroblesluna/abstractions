package com.abstractions.service;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.helper.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abstractions.model.Team;
import com.abstractions.model.User;
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
		User user = this.repository.get(User.class, ownerID);
		if (user != null) {
			Team team = new Team(name, user);
			this.repository.save(user);
		}
	}
	
	@Transactional
	public List<Team> getTeamsOf(long currentUserId) {
		User user = this.repository.get(User.class, currentUserId);
		if (user != null) {
			return user.getTeams();
		} else {
			return new ArrayList<Team>();
		}
	}
}
