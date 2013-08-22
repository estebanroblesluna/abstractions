package com.common.expression;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

import org.jsoup.helper.Validate;

import com.core.api.Message;
import com.core.api.ProcessingException;

public class ScriptingExpression extends AbstractExpression
{
  private ScriptEngine engine;
  private String script;
  
  public ScriptingExpression(ScriptingLanguage language, String script)
  {
    Validate.notNull(language);
    
    ScriptEngineManager manager = new ScriptEngineManager();
    this.engine = manager.getEngineByName(language.getName());
    this.script = script;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public Object evaluate(Message message, String[] namedArguments, Object... arguments)
  {
    ScriptContext newContext = new SimpleScriptContext();
    Bindings engineScope = newContext.getBindings(ScriptContext.ENGINE_SCOPE);

    engineScope.put("message", message);
    
    if (namedArguments != null) {
    	for (int i = 0; i < namedArguments.length; i++) {
			String name = namedArguments[i];
			engineScope.put(name, arguments[i]);
		}
    }

    try
    {
      Object result = engine.eval(this.script, engineScope);
      return result;
    }
    catch (ScriptException e)
    {
      throw new ProcessingException(e);
    }
  }

  public String getScript() {
    return script;
  }

  public void setScript(String script) {
    this.script = script;
  }
}
