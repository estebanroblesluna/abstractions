package com.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import junit.framework.TestCase;

import org.apache.commons.io.IOUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import com.abstractions.common.ConnectorMarshaller;
import com.abstractions.model.Connector;

/**
 * 
 * @author Martin Aparicio Pons (martin.aparicio.pons@gmail.com)
 */
public class ConnectorMarshallerTest extends TestCase {

  private ArrayList<Connector> connectors;
  private JSONArray simpleJSON;

  @Before
  public void setUp() {
    // setup connectors
    connectors = new ArrayList<Connector>();
    HashMap<String, String> configurations = new HashMap<String, String>();
    configurations.put("configUno", "0001");
    configurations.put("configDos", "0002");
    connectors.add(new Connector("testing", "none", configurations));

    // setup testJSON
    String JSONstring = null;
    try {
      JSONstring = IOUtils.toString(this.getClass().getResourceAsStream("ConnectorJSONtesting.json")).replace("\n", "");
    } catch (IOException e1) {
      fail("JSONException when opening resource file ConnectorJSONtesting");
    }
    try {
      simpleJSON = new JSONArray(JSONstring);
    } catch (JSONException e) {
      fail("JSONException when trying to parse simpleJSON to a JSONArray");
    }
  }

  @Test
  public void testSimpleMarshall() {

    // marshall the simple definition list
    String result = null;
    try {
      result = ConnectorMarshaller.marshall(connectors);
    } catch (Exception e) {
      fail("Exception when trying to marshall connectors");
    }

    // transform it to jsonArray
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

    // unmarshall the definitions
    ArrayList<Connector> connectorsResult = null;
    try {
      String auxStr = simpleJSON.toString();
      connectorsResult = (ArrayList<Connector>) ConnectorMarshaller.unmarshall(auxStr);
    } catch (Exception e) {
      fail("Exception when trying to unmarshall connectors");
    }

    assertEquals(connectors, connectorsResult);
  }

  @Test
  public void testSimpleMarshalUnmarshall() {
    // marshall the simple connector list
    String result = null;
    try {
      result = ConnectorMarshaller.marshall(connectors);
    } catch (Exception e) {
      fail("Exception when trying to marshall connectors");
    }

    // transform it to jsonArray
    JSONArray resultJSON = null;
    try {
      resultJSON = new JSONArray(result);
    } catch (JSONException e) {
      fail("JSONException when trying to parse string to a JSONArray");
    }

    // unmarshall the connectors
    ArrayList<Connector> connectorsResult = null;
    try {
      String auxStr = resultJSON.toString();
      connectorsResult = (ArrayList<Connector>) ConnectorMarshaller.unmarshall(auxStr);
    } catch (Exception e) {
      fail("Exception when trying to unmarshall connectors");
    }

    assertEquals(connectorsResult.size(), connectors.size());
  }
}
