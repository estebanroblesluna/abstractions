package com.abstractions.instance.routing;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.abstractions.api.Connectable;
import com.abstractions.api.Connection;
import com.abstractions.api.Router;

public abstract class AbstractRouter implements Router, Connectable {

	private List<Connection> connections;

	protected AbstractRouter() {
		this.connections = new CopyOnWriteArrayList<Connection>();
	}

	@Override
	public void addConnection(Connection connection) {
		this.connections.add(connection);
	}

	@Override
	public void removeConnection(Connection connection) {
		this.connections.remove(connection);
	}

	@Override
	public List<Connection> getConnections() {
		return Collections.unmodifiableList(this.connections);
	}
}
