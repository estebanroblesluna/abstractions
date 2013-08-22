package com.common.expression;

import com.core.api.Message;
import com.core.api.Processor;

public class ScriptingProcessor implements Processor
{
  private ScriptingExpression expression;

  public ScriptingProcessor()
  {
	  this(ScriptingLanguage.GROOVY, "return true;");
  }

  public ScriptingProcessor(ScriptingLanguage language, String script)
  {
    this.expression = new ScriptingExpression(language, script);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Message process(Message message)
  {
    this.expression.evaluate(message);
    return message;
  }
  
  public String getScript() {
    return this.expression.getScript();
  }

  public void setScript(String script) {
    this.expression.setScript(script);
  }
}
