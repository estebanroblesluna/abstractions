package com.abstractions.expression;

public class ScriptingLanguage
{
  public static final ScriptingLanguage GROOVY = new ScriptingLanguage("groovy");
  public static ScriptingLanguage GROOVY() { return GROOVY; }
  private String name;
  
  public ScriptingLanguage(String name)
  {
    this.name = name;
  }
  
  public String getName()
  {
    return this.name;
  }
}
