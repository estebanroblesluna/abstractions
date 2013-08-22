package com.core.interpreter;

import com.core.api.Message;
import com.service.core.ObjectDefinition;

public class NullInterpreterDelegate implements InterpreterDelegate {

	@Override
	public void stopInBreakPoint(String interpreterId, String threadId,
			String contextId, ObjectDefinition currentProcessor,
			Message currentMessage) {
	}

	@Override
	public void beforeStep(String interpreterId, String threadId,
			String contextId, ObjectDefinition currentProcessor,
			Message currentMessage) {
	}

	@Override
	public void afterStep(String interpreterId, String threadId,
			String contextId, ObjectDefinition currentProcessor,
			Message currentMessage) {
	}

	@Override
	public void finishInterpretation(String interpreterId, String threadId,
			String contextId, Message currentMessage) {
	}

}
