package com.abstractions.runtime.interpreter;

import com.abstractions.api.Message;
import com.abstractions.template.ElementTemplate;

public class NullInterpreterDelegate implements InterpreterDelegate {

	@Override
	public void stopInBreakPoint(String interpreterId, String threadId,
			String contextId, ElementTemplate currentProcessor,
			Message currentMessage) {
	}

	@Override
	public void beforeStep(String interpreterId, String threadId,
			String contextId, ElementTemplate currentProcessor,
			Message currentMessage) {
	}

	@Override
	public void afterStep(String interpreterId, String threadId,
			String contextId, ElementTemplate currentProcessor,
			Message currentMessage) {
	}

	@Override
	public void finishInterpretation(String interpreterId, String threadId,
			String contextId, Message currentMessage) {
	}

	@Override
	public void uncaughtException(String interpreterId, String threadId,
			String contextId, ElementTemplate currentProcessor,
			Message currentMessage, Exception e) {
	}

	@Override
	public void startInterpreting(String interpreterId, String threadId,
			String contextId, ElementTemplate currentProcessor,
			Message currentMessage) {
	}
}
