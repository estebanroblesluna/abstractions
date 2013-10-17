package com.core.interpreter;

import com.core.api.Message;
import com.service.core.ObjectDefinition;

public interface InterpreterDelegate {

	void startInterpreting(String interpreterId, String threadId, String contextId, ObjectDefinition currentProcessor, Message currentMessage);
	
	void stopInBreakPoint(String interpreterId, String threadId, String contextId, ObjectDefinition currentProcessor, Message currentMessage);

	void beforeStep(String interpreterId, String threadId, String contextId, ObjectDefinition currentProcessor, Message currentMessage);

	void afterStep(String interpreterId, String threadId, String contextId, ObjectDefinition currentProcessor, Message currentMessage);

	void finishInterpretation(String interpreterId, String threadId, String contextId, Message currentMessage);

	void uncaughtException(String interpreterId, String threadId, String contextId, ObjectDefinition currentProcessor, Message currentMessage, Exception e);
}
