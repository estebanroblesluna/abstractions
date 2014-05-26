package com.abstractions.meta;

import org.apache.commons.lang.Validate;

import com.abstractions.runtime.interpreter.Thread;

public class ConnectorDefinition extends ElementDefinition {

  @Override
  public Object accept(ElementDefinitionVisitor visitor) {
    return visitor.visitConnectorDefinition(this);
  }

  @Override
  public void evaluateUsing(Thread thread) {
    // no evaluation
  }

  @Override
  public ElementDefinitionType getType() {
    return ElementDefinitionType.CONNECTOR;
  }
}
