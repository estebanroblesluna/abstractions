package com.abstractions.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jsoup.helper.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abstractions.model.Server;
import com.abstractions.model.ServerGroup;
import com.abstractions.model.ServerStats;
import com.abstractions.repository.GenericRepository;
import com.abstractions.utils.IdGenerator;

@Service
public class ServerService {

	private GenericRepository repository;
	private ServerGroupService serverGroupService;

	public ServerService(GenericRepository repository, ServerGroupService serverGroupService) {
		Validate.notNull(repository);
		Validate.notNull(serverGroupService);
		
		this.repository = repository;
		this.serverGroupService = serverGroupService;
	}

	protected ServerService() {
	}

	@Transactional
	public void addServer(long serverGroupId, String name, String ipDNS, int port) {
		ServerGroup serverGroup = this.serverGroupService.getServerGroup(serverGroupId);
		Server server = new Server(name, ipDNS);
		server.setPort(port);
		server.setKey(IdGenerator.getNewId());
		
		serverGroup.addServer(server);
		this.repository.save(server);
		this.repository.save(serverGroup);
	}

	@Transactional
	public List<Server> getServers(long teamId, long serverGroupId) {
		ServerGroup serverGroup = this.serverGroupService.getServerGroup(serverGroupId);

		List<Server> servers = new ArrayList<Server>();
		servers.addAll(serverGroup.getServers());
		return servers;
	}
	
	@Transactional
	public List<Server> getServers() {
		return this.repository.get(Server.class, "name");
	}

	@Transactional
	public Server getServer(long serverId) {
		return this.repository.get(Server.class, serverId);
	}

	@Transactional
	public void updateServerStatusWithKey(String serverId, String serverKey) {
		Server server = this.getServer(serverId, serverKey);
		if (server != null) {
			server.setLastUpdate(new Date());
			this.repository.save(server);
		}
	}

	@Transactional
	public Server getServer(String serverId, String serverKey) {
		Server server = this.repository.findByAnd(Server.class, "externalId", serverId, "key", serverKey);
		return server;
	}

	@Transactional
	public void updateStatistics(String serverId, String serverKey, String contextId, Date date,
			long uncaughtExceptions, Map<String, Long> receivedMessages) {

		Server server = this.getServer(serverId, serverKey);
		if (server != null) {
			ServerStats stats = new ServerStats(server, contextId, date, uncaughtExceptions, receivedMessages);
			this.repository.save(stats);
		}
	}
}
