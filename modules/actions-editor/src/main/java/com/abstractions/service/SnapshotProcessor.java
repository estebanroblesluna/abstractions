package com.abstractions.service;

import com.abstractions.model.Application;
import com.abstractions.model.ApplicationSnapshot;

public interface SnapshotProcessor {
	
	void process(Application application, ApplicationSnapshot snapshot) throws Exception;
	
}
