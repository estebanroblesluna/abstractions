package com.abstractions.meta;

public interface ElementDefinitionVisitor {

	Object visitConnectionDefinition(ConnectionDefinition connectionDefinition);

	Object visitMessageSourceDefinition(MessageSourceDefinition messageSourceDefinition);

	Object visitProcessorDefinition(ProcessorDefinition processorDefinition);

	Object visitRouterDefinition(RouterDefinition routerDefinition);

	Object visitAbstractionDefinition(AbstractionDefinition abstractionDefinition);

	Object visitApplicationDefinition(ApplicationDefinition applicationDefinition);

  Object visitFlowDefinition(FlowDefinition flowDefinition);

  Object visitConnectorDefinition(ConnectorDefinition connectorDefinition);
}
