package com.core.api;

public class ProcessingException extends RuntimeException
{
  private static final long serialVersionUID = -436720831476834244L;

  public ProcessingException(Exception e)
  {
    super(e);
  }

  public ProcessingException(String message)
  {
    super(message);
  }
}
