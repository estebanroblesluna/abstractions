package com.abstractions.service;

import java.util.List;

import org.jsoup.helper.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abstractions.model.Server;
import com.abstractions.repository.GenericRepository;

@Service
public class ServerService {

	private GenericRepository repository;
	
	protected ServerService() { }
	
	public ServerService(GenericRepository repository) {
		Validate.notNull(repository);
		
		this.repository = repository;
	}
	
	@Transactional
	public void addServer(String name, String ipDNS) {
		Server app = new Server(name, ipDNS);
		this.repository.save(app);
	}
	
	@Transactional
	public List<Server> getServers() {
		return this.repository.get(Server.class, "name");
	}
}
