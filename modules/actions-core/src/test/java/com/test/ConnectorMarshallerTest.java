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

  private ArrayList<Connector> connectors1;
  private ArrayList<Connector> connectors2;
  private ArrayList<Connector> connectors3;
  private JSONArray testJSON1;
  private JSONArray testJSON2;
  private JSONArray testJSON3;

  @Before
  public void setUp() {

    // setup connectors:

    // setup connectors one
    connectors1 = new ArrayList<Connector>();
    HashMap<String, String> configurations1 = new HashMap<String, String>();
    configurations1.put("configUno", "0001");
    configurations1.put("configDos", "0002");
    connectors1.add(new Connector("testing", "none", configurations1));

    // setup connectors two
    connectors2 = new ArrayList<Connector>();
    HashMap<String, String> configurations2 = new HashMap<String, String>();
    configurations2.put("time", "1002");
    configurations2.put("isTrue", "True");
    connectors2.add(new Connector("Cache", "connection to cache", configurations2));
    HashMap<String, String> configurations3 = new HashMap<String, String>();
    configurations3.put("ttl", "100");
    connectors2.add(new Connector("anotherOne", "connection to DB", configurations3));
    HashMap<String, String> configurations4 = new HashMap<String, String>();
    connectors2.add(new Connector("yes", "", configurations4));
    HashMap<String, String> configurations5 = new HashMap<String, String>();
    configurations5.put("1", "1");
    connectors2.add(new Connector("1", "1", configurations5));

    // setup connectors three
    connectors3 = new ArrayList<Connector>();

    // setup testing .json:

    // setup testJSON1
    String JSONstring1 = null;
    try {
      JSONstring1 = IOUtils.toString(this.getClass().getResourceAsStream("ConnectorJSONtesting.json")).replace("\n", "");
    } catch (IOException e1) {
      fail("JSONException when opening resource file ConnectorJSONtesting");
    }
    try {
      testJSON1 = new JSONArray(JSONstring1);
    } catch (JSONException e) {
      fail("JSONException when trying to parse simpleJSON to a JSONArray");
    }

    // setup testJSON2
    String JSONstring2 = null;
    try {
      JSONstring2 = IOUtils.toString(this.getClass().getResourceAsStream("ConnectorJSONtesting2.json")).replace("\n", "");
    } catch (IOException e1) {
      fail("JSONException when opening resource file ConnectorJSONtesting");
    }
    try {
      testJSON2 = new JSONArray(JSONstring2);
    } catch (JSONException e) {
      fail("JSONException when trying to parse simpleJSON to a JSONArray");
    }

    // setup testJSON3
    String JSONstring3 = null;
    try {
      JSONstring3 = IOUtils.toString(this.getClass().getResourceAsStream("ConnectorJSONtesting3.json")).replace("\n", "");
    } catch (IOException e1) {
      fail("JSONException when opening resource file ConnectorJSONtesting");
    }
    try {
      testJSON3 = new JSONArray(JSONstring3);
    } catch (JSONException e) {
      fail("JSONException when trying to parse simpleJSON to a JSONArray");
    }
  }

  @Test
  public void testOneMarshall() {

    // marshall the connector list
    String result = null;
    try {
      result = ConnectorMarshaller.marshall(connectors1);
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
    assertEquals(testJSON1, resultJSON);

  }

  @Test
  public void testOneUnmarshall() {

    // unmarshall the definitions
    ArrayList<Connector> connectorsResult = null;
    try {
      String auxStr = testJSON1.toString();
      connectorsResult = (ArrayList<Connector>) ConnectorMarshaller.unmarshall(auxStr);
    } catch (Exception e) {
      fail("Exception when trying to unmarshall connectors");
    }

    assertEquals(connectors1.size(), connectorsResult.size());
  }

  @Test
  public void testTwoMarshall() {

    // marshall the connector list
    String result = null;
    try {
      result = ConnectorMarshaller.marshall(connectors2);
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
    assertEquals(testJSON2, resultJSON);

  }

  @Test
  public void testTwoUnmarshall() {

    // unmarshall the definitions
    ArrayList<Connector> connectorsResult = null;
    try {
      String auxStr = testJSON2.toString();
      connectorsResult = (ArrayList<Connector>) ConnectorMarshaller.unmarshall(auxStr);
    } catch (Exception e) {
      fail("Exception when trying to unmarshall connectors");
    }

    assertEquals(connectors2.size(), connectorsResult.size());
  }

  @Test
  public void testThreeMarshall() {

    // marshall the connector list
    String result = null;
    try {
      result = ConnectorMarshaller.marshall(connectors3);
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
    assertEquals(testJSON3, resultJSON);

  }

  @Test
  public void testThreeUnmarshall() {

    // unmarshall the definitions
    ArrayList<Connector> connectorsResult = null;
    try {
      String auxStr = testJSON2.toString();
      connectorsResult = (ArrayList<Connector>) ConnectorMarshaller.unmarshall(auxStr);
    } catch (Exception e) {
      fail("Exception when trying to unmarshall connectors");
    }

    assertEquals(connectors2.size(), connectorsResult.size());
  }

  @Test
  public void testOneMarshalUnmarshall() {
    // marshall the simple connector list
    String result = null;
    try {
      result = ConnectorMarshaller.marshall(connectors1);
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

    assertEquals(connectorsResult.size(), connectors1.size());
  }

  @Test
  public void testTwoMarshalUnmarshall() {
    // marshall the simple connector list
    String result = null;
    try {
      result = ConnectorMarshaller.marshall(connectors2);
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

    assertEquals(connectorsResult.size(), connectors2.size());
  } 
  
  @Test
  public void testThreeMarshalUnmarshall() {
    // marshall the simple connector list
    String result = null;
    try {
      result = ConnectorMarshaller.marshall(connectors3);
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

    assertEquals(connectorsResult.size(), connectors3.size());
  }
}
