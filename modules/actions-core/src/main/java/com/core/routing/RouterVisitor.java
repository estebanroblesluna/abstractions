package com.core.routing;


public interface RouterVisitor {

	Object visitAllRouter(AllRouter allRouter);

	Object visitChoiceRouter(ChoiceRouter choiceRouter);

	Object visitWireTapRouter(WireTapRouter wireTapRouter);

	//split
	//join
}
