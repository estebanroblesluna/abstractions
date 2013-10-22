package com.abstractions.api;

public interface Processor extends Element
{
  Message process(Message message);
}
