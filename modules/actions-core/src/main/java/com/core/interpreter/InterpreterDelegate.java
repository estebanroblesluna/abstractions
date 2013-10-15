package com.core.interpreter;

import com.core.api.Message;
import com.service.core.ObjectDefinition;

public interface InterpreterDelegate {

	void stopInBreakPoint(String interpreterId, String threadId, String contextId, ObjectDefinition currentProcessor, Message currentMessage);

	void beforeStep(String interpreterId, String threadId, String contextId, ObjectDefinition currentProcessor, Message currentMessage);

	void afterStep(String interpreterId, String threadId, String contextId, ObjectDefinition currentProcessor, Message currentMessage);

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
    void uncaughtException(String interpreterId, String threadId, String contextId, ObjectDefinition currentProcessor, Message currentMessage, Exception e);
}
