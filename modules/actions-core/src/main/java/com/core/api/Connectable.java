package com.core.api;

import java.util.List;

public interface Connectable {

	void addConnection(Connection connection);
	
	void removeConnection(Connection connection);
	
	List<Connection> getConnections();
}
