package com.core.interpreter;

import com.abstractions.clazz.core.ObjectClazz;
import com.core.api.Message;

public interface InterpreterDelegate {

	void startInterpreting(String interpreterId, String threadId, String contextId, ObjectClazz currentProcessor, Message currentMessage);
	
	void stopInBreakPoint(String interpreterId, String threadId, String contextId, ObjectClazz currentProcessor, Message currentMessage);

	void beforeStep(String interpreterId, String threadId, String contextId, ObjectClazz currentProcessor, Message currentMessage);

	void afterStep(String interpreterId, String threadId, String contextId, ObjectClazz currentProcessor, Message currentMessage);

	void finishInterpretation(String interpreterId, String threadId, String contextId, Message currentMessage);

	/**
     *
     * @param interpreterId
     * @param threadId
     * @param contextId
     * @param currentProcessor
     * @param currentMessage
     * @param e
     */
    void uncaughtException(String interpreterId, String threadId, String contextId, ObjectClazz currentProcessor, Message currentMessage, Exception e);
}
