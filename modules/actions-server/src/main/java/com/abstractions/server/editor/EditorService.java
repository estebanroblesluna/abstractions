package com.abstractions.server.editor;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;

import com.abstractions.http.HttpStrategy;

public class EditorService {

	private static final Log log = LogFactory.getLog(EditorService.class);
	
	private final HttpStrategy strategy;
	private final String editorUrl;
	private final String serverKey;
	
	public EditorService(HttpStrategy strategy, String editorUrl, String serverKey) {
		this.strategy = strategy;
		this.editorUrl = editorUrl;
		this.serverKey = serverKey;
	}
	
	/**
	 * Notifies the editor that this server is alive
	 * and sends the server's status
	 */
	public void ping() {
		try {
			this.strategy
				.post(this.editorUrl + "/status")
				.addFormParam("server-key", this.serverKey)
				.execute();
		} catch (ClientProtocolException e) {
			log.warn("Error pinging server", e);
		} catch (IOException e) {
			log.warn("Error pinging server", e);
		}
	}
}
