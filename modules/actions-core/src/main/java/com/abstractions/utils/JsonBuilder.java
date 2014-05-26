package com.abstractions.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;

public class JsonBuilder {

  private StringWriter writer;
  private JsonGenerator generator;
  private int objectNesting;

  public JsonBuilder() throws IOException {
    this.writer = new StringWriter();
    this.generator = new JsonFactory().createJsonGenerator(writer);
    this.objectNesting = 0;
  }
  
  public static JsonBuilder newWithObject() throws IOException {
    JsonBuilder jsonBuilder = new JsonBuilder();
    jsonBuilder.startObject();
    return jsonBuilder;
  }
  
  public static JsonBuilder newWithObject(String fieldName) throws IOException {
	  JsonBuilder jsonBuilder = newWithObject();
	  jsonBuilder.objectField(fieldName);
	  return jsonBuilder;
  }
  
  public static JsonBuilder newWithField(String fieldName) throws IOException {
	  JsonBuilder jsonBuilder = new JsonBuilder();
	  jsonBuilder.startObject();
	  jsonBuilder.field(fieldName);
	  return jsonBuilder;
  }
  
  public static JsonBuilder newWithArray(String fieldName) throws JsonGenerationException, IOException {
	  JsonBuilder jsonBuilder = new JsonBuilder();
	  jsonBuilder.startObject();
	  jsonBuilder.arrayField(fieldName);
	  return jsonBuilder;
  }
  
  public JsonBuilder field(String fieldName, String value) throws JsonGenerationException, IOException {
    this.generator.writeStringField(fieldName, value);
    return this;
  }
  
  public JsonBuilder field(String fieldName, BigDecimal value) throws JsonGenerationException, IOException {
    this.generator.writeNumberField(fieldName, value);
    return this;
  }
  
  public JsonBuilder objectField(String fieldName) throws JsonGenerationException, IOException {
    this.generator.writeObjectFieldStart(fieldName);
    this.objectNesting++;
    return this;
  }
  
  public JsonBuilder arrayField(String fieldName) throws JsonGenerationException, IOException {
    this.generator.writeArrayFieldStart(fieldName);
    return this;
  }
  
  public JsonBuilder endObject() throws JsonGenerationException, IOException {
    this.generator.writeEndObject();
    this.objectNesting--;
    return this;
  }
  
  public JsonBuilder startArray() throws JsonGenerationException, IOException {
	  this.generator.writeStartArray();
	  return this;
  }
  
  public JsonBuilder endArray() throws JsonGenerationException, IOException {
	  this.generator.writeEndArray();
	  return this;
  }
  
  public String getContent() throws JsonGenerationException, IOException {
    while (this.objectNesting > 0) {
      this.endObject();
    }
    this.generator.close();
    return this.writer.getBuffer().toString();
  }
  
  public JsonBuilder field(String fieldName) throws JsonGenerationException, IOException {
	  this.generator.writeFieldName(fieldName);
	  return this;
  }
  
  public JsonBuilder string(String fieldName) throws JsonGenerationException, IOException {
	  this.generator.writeString(fieldName);
	  return this;
  }

  public JsonBuilder startObject() throws JsonGenerationException, IOException {
    this.generator.writeStartObject();
    this.objectNesting++;
    return this;
  }

  public JsonBuilder jsonField(String fieldName, String jsonContent) throws JsonGenerationException, IOException {
    this.generator.writeFieldName(fieldName);
    this.generator.writeRaw(":" + jsonContent);
    return this;
  }
  
  
}
