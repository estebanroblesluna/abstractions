package com.test;

import java.io.IOException;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.apache.commons.io.IOUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import com.abstractions.common.ElementDefinitionMarshaller;
import com.abstractions.meta.ElementDefinition;
import com.abstractions.meta.ProcessorDefinition;

/**
*
* @author Guido J. Celada (celadaguido@gmail.com)
*/
public class ElementDefinitionMarshallerTest extends TestCase {
	private ArrayList<ElementDefinition> definitions;
	private JSONArray simpleJSON;
	private JSONArray complexJSON;
	
	@Before
	public void setUp() {
		//setup definitions
		definitions = new ArrayList<ElementDefinition>();
		ProcessorDefinition processor = new ProcessorDefinition("testing");
		definitions.add(processor);
		
		//setup simpleJSON
		String JSONstring = null;
		try {
			JSONstring = IOUtils.toString(this.getClass().getResourceAsStream("jsonString1.json")).replace("\n","");
		} catch (IOException e1) {
			fail("JSONException when opening resource file jsonString");
		}
		try {
			simpleJSON = new JSONArray(JSONstring);
		} catch (JSONException e) {
			fail("JSONException when trying to parse simpleJSON to a JSONArray");
		}
		
		//setup complexJSON
		String JSONstring2 = null;
		try {
 			JSONstring2 = IOUtils.toString(this.getClass().getResourceAsStream("jsonString2.json")).replace("\n","");
		} catch (IOException e) {
			fail("JSONException when opening resource file jsonString2");
		}
		try {
			complexJSON = new JSONArray(JSONstring2);
		} catch (JSONException e) {
			fail("JSONException when trying to parse complexJSON to a JSONArray");
		}
	}
	
	@Test
	public void testSimpleMarshall() {
		
		//marshall the simple definition list
		String result = null;
		try {
			result = ElementDefinitionMarshaller.marshall(definitions);
		} catch (Exception e) {
			fail("Exception when trying to marshall definitions");
		}
		
		//transform it to jsonArray
		JSONArray resultJSON = null;
		try {
			resultJSON = new JSONArray(result);
		} catch (JSONException e) {
			fail("JSONException when trying to parse string to a JSONArray");
		}
		assertEquals(simpleJSON, resultJSON);
		
	}
	
	@Test
	public void testSimpleUnmarshall() {
		
		//unmarshall the definitions
		ArrayList<ElementDefinition> definitionsResult = null;
		try {
			String s = simpleJSON.toString();
			definitionsResult = (ArrayList<ElementDefinition>) ElementDefinitionMarshaller.unmarshall(s);
		} catch (Exception e) {
			fail("Exception when trying to unmarshall definitions");
		} 
		
		//assertEquals(definitions, definitionsResult);	
		assertEquals(definitions.size(), definitionsResult.size());	
	}
	
	
	@Test
	public void testComplexUnmarshall() {
		
		//unmarshall the definitions
		ArrayList<ElementDefinition> definitionsResult = null;
		try {
			definitionsResult = (ArrayList<ElementDefinition>) ElementDefinitionMarshaller.unmarshall(complexJSON.toString());
		} catch (Exception e) {
			fail("Exception when trying to unmarshall definitions");
		}
		
		assertEquals(11, definitionsResult.size());
	}
	
	@Test
	public void testSimpleMarshalUnmarshall() {
		//marshall the simple definition list
		String result = null;
		try {
			result = ElementDefinitionMarshaller.marshall(definitions);
		} catch (Exception e) {
			fail("Exception when trying to marshall definitions");
		}
		
		//transform it to jsonArray
		JSONArray resultJSON = null;
		try {
			resultJSON = new JSONArray(result);
		} catch (JSONException e) {
			fail("JSONException when trying to parse string to a JSONArray");
		}
		
		//unmarshall the definitions
		ArrayList<ElementDefinition> definitionsResult = null;
		try {
			String s = resultJSON.toString();
			definitionsResult = (ArrayList<ElementDefinition>) ElementDefinitionMarshaller.unmarshall(s);
		} catch (Exception e) {
			fail("Exception when trying to unmarshall definitions");
		} 
		
		assertEquals(definitionsResult.size(), definitions.size());
	}
}
