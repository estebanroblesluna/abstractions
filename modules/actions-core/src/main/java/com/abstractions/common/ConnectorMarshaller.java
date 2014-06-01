package com.abstractions.common;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.map.jsontype.NamedType;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.transaction.annotation.Transactional;

import com.abstractions.meta.ConnectorDefinition;
import com.abstractions.model.Connector;

/**
 * 
 * @author Martin Aparicio Pons (martin.aparicio.pons@gmail.com)
 */
public class ConnectorMarshaller {

  /**
   * Marshalls a list of connector definitions as JSON.
   * 
   * @param connectors
   *          A List of connectors
   * @return the definitions as JSON
   * @throws IOException
   * @throws JsonMappingException
   * @throws JsonGenerationException
   */
  @Transactional
  public static String marshall(List<Connector> connectors) throws JsonGenerationException, JsonMappingException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.getSerializationConfig().addMixInAnnotations(Connector.class, ConnectorMixIn.class);
    ObjectWriter JSONwriter = mapper.writerWithType(new TypeReference<Collection<Connector>>() {
    }).withDefaultPrettyPrinter();
    return JSONwriter.writeValueAsString(connectors);
  }

  /**
   * Unmarshalls a JSON of connectors to a list of connectors.
   * 
   * @param definitionsAsJSON
   *          a JSON of connectors
   * @return a list of connectors
   * @throws IOException
   * @throws JsonMappingException
   * @throws JsonParseException
   */
  @Transactional
  public static List<Connector> unmarshall(String definitionsAsJSON) throws JsonParseException, JsonMappingException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.getDeserializationConfig().addMixInAnnotations(Connector.class, ConnectorMixIn.class);
    mapper.setVisibilityChecker(mapper.getSerializationConfig().getDefaultVisibilityChecker().withFieldVisibility(JsonAutoDetect.Visibility.ANY)
            .withGetterVisibility(JsonAutoDetect.Visibility.NONE).withSetterVisibility(JsonAutoDetect.Visibility.NONE)
            .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
    mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return mapper.readValue(definitionsAsJSON, new TypeReference<Collection<Connector>>() {
    });
  }

}
