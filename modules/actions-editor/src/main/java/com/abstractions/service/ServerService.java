package com.abstractions.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.helper.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abstractions.model.LoggingInfo;
import com.abstractions.model.ProfilingInfo;
import com.abstractions.model.Server;
import com.abstractions.model.ServerCommand;
import com.abstractions.model.ServerCommandState;
import com.abstractions.model.ServerGroup;
import com.abstractions.model.ServerStats;
import com.abstractions.model.User;
import com.abstractions.repository.GenericRepository;
import com.abstractions.utils.IdGenerator;

@Service
public class ServerService {

	private static final Log log = LogFactory.getLog(ServerService.class);

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
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<ServerCommand> getPendingCommands(String serverId, String serverKey) {
		Server server = this.getServer(serverId, serverKey);
		if (server != null) {
			return this.repository.getCommands(server.getId(), ServerCommandState.PENDING);
		} else {
			throw new IllegalArgumentException("Invalid server");
		}
	}

	@Transactional
	public void addProfilingInfo(Server server, ProfilingInfo info) {
		info.setServerId(server.getExternalId());
		this.repository.save(info);
	}

	@Transactional
	public void addLoggingInfo(Server server, LoggingInfo info) {
		if (!info.isEmpty()) {
			info.setServerId(server.getExternalId());
			this.repository.save(info);
		}
	}

	@Transactional
	public void updateCommandStatus(String serverId, String serverKey, Set<Long> successIds, Set<Long> failedIds) {
		Server server = this.getServer(serverId, serverKey);
		if (server != null) {
			//success commands
			for (Long id : successIds) {
				basicUpdateCommandState(serverId, server, id, ServerCommandState.FINISH_SUCCESSFULLY);
			}
			
			//failed commands
			for (Long id : failedIds) {
				basicUpdateCommandState(serverId, server, id, ServerCommandState.FINISH_WITH_ERRORS);
			}
		}		
	}

	private void basicUpdateCommandState(String serverId, Server server, Long id, ServerCommandState newState) {
		ServerCommand sc = this.repository.get(ServerCommand.class, id);
		if (sc.getDeploymentToServer().getServer().getId() == server.getId()) {
			sc.setState(newState);
			this.repository.update(sc);
		} else {
			log.warn("Command " + id + " doesn't belong to server " + serverId);
		}
	}

  @SuppressWarnings("unchecked")
  @Transactional
  public List<Server> getServersOfUser(User currentUser) {
    return this.repository.getServersOfUser(currentUser.getId());
  }
}
