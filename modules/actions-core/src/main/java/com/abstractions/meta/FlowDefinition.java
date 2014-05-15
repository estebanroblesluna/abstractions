package com.abstractions.meta;

import com.abstractions.runtime.interpreter.Thread;
import com.abstractions.service.core.NamesMapping;
import com.abstractions.template.CompositeTemplate;

public class FlowDefinition extends CompositeDefinition {

  @Override
  protected CompositeTemplate basicCreateTemplate(String id, CompositeDefinition compositeDefinition, NamesMapping mapping) {
    return new CompositeTemplate(id, this, mapping);
  }

  @Override
  public Object accept(ElementDefinitionVisitor visitor) {
    return visitor.visitFlowDefinition(this);
  }

  @Override
  public void evaluateUsing(Thread thread) {
    //NO Evaluation
  }

  @Override
  public ElementDefinitionType getType() {
    return ElementDefinitionType.DEVELOPMENT_FLOW;
  }
}
