package com.core.interpreter;

import com.abstractions.clazz.core.ObjectClazz;
import com.core.api.Message;

public class NullInterpreterDelegate implements InterpreterDelegate {

	@Override
	public void stopInBreakPoint(String interpreterId, String threadId,
			String contextId, ObjectClazz currentProcessor,
			Message currentMessage) {
	}

	@Override
	public void beforeStep(String interpreterId, String threadId,
			String contextId, ObjectClazz currentProcessor,
			Message currentMessage) {
	}

	@Override
	public void afterStep(String interpreterId, String threadId,
			String contextId, ObjectClazz currentProcessor,
			Message currentMessage) {
	}

	@Override
	public void finishInterpretation(String interpreterId, String threadId,
			String contextId, Message currentMessage) {
	}

	@Override
	public void uncaughtException(String interpreterId, String threadId,
			String contextId, ObjectClazz currentProcessor,
			Message currentMessage, Exception e) {
	}

	@Override
	public void startInterpreting(String interpreterId, String threadId,
			String contextId, ObjectClazz currentProcessor,
			Message currentMessage) {
	}
}
