package com.core.api;

public interface Processor extends Element
{
  Message process(Message message);
}
