package com.server.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogInfo {

	private Map<String, List<String>> lines;
	
	public LogInfo() {
		this.lines = new HashMap<String, List<String>>();
	}
	
	public void addLogLines(String elementId, List<String> lines) {
		this.lines.put(elementId, lines);
	}

	public Map<String, List<String>> getLines() {
		return Collections.unmodifiableMap(this.lines);
	}
}
