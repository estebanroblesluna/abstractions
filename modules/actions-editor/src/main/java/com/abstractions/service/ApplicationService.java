package com.abstractions.service;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.helper.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abstractions.model.Application;
import com.abstractions.model.Team;
import com.abstractions.repository.GenericRepository;

@Service
public class ApplicationService {

	private GenericRepository repository;
	private TeamService teamService;
	
	protected ApplicationService() { }
	
	public ApplicationService(GenericRepository repository, TeamService teamService) {
		Validate.notNull(repository);
		Validate.notNull(teamService);
		
		this.repository = repository;
		this.teamService = teamService;
	}
	
	@Transactional
	public void addApplication(long teamId, String name) {
		Team team = this.teamService.getTeam(teamId);
		Application app = new Application(name, team);
		
		team.addApplication(app);
		this.repository.save(app);
		this.repository.save(team);
	}
	
	@Transactional
	public List<Application> getApplications() {
		return this.repository.get(Application.class, "name");
	}

	@Transactional
	public List<Application> getApplicationsOf(long teamId) {
		List<Application> applications = new ArrayList<Application>();
		applications.addAll(this.teamService.getTeam(teamId).getApplications());
		return applications;
	}

	@Transactional
	public void removeApplicationById(long id) {
		this.repository.delete(Application.class, id);
	}
        
    @Transactional
	public Application getApplication(long applicationId) {
		return this.repository.get(Application.class, applicationId);
	}
}
