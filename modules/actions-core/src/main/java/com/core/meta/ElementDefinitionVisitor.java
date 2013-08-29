package com.core.meta;

public interface ElementDefinitionVisitor {

	Object visitConnectionDefinition(ConnectionDefinition connectionDefinition);

	Object visitMessageSourceDefinition(MessageSourceDefinition messageSourceDefinition);

	Object visitProcessorDefinition(ProcessorDefinition processorDefinition);

	Object visitRouterDefinition(RouterDefinition routerDefinition);

	Object visitFlowDefinition(FlowDefinition flowDefinition);
}
