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

import com.abstractions.meta.AbstractionDefinition;
import com.abstractions.meta.ApplicationDefinition;
import com.abstractions.meta.ConnectionDefinition;
import com.abstractions.meta.ElementDefinition;
import com.abstractions.meta.MessageSourceDefinition;
import com.abstractions.meta.ProcessorDefinition;
import com.abstractions.meta.RouterDefinition;

/**
*
* @author Guido J. Celada (celadaguido@gmail.com)
*/
public class ElementDefinitionMarshaller {

	/**
	 * Marshalls a list of element definitions as JSON.
	 * 
	 * @param definitions A List of element definitions
	 * @return the definitions as JSON
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	@Transactional
	public static String marshall(List<ElementDefinition> definitions) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.getSerializationConfig().addMixInAnnotations(ElementDefinition.class, ElementDefinitionMixIn.class);
		ObjectWriter JSONwriter = mapper.writerWithType(new TypeReference<Collection<ElementDefinition>>() {}).withDefaultPrettyPrinter();
		return JSONwriter.writeValueAsString(definitions);
	}
	
	/**
	 * Unmarshalls a JSON of element definitions to a list of element definitions.
	 * 
	 * @param definitionsAsJSON a JSON of element definitions
	 * @return a list of element definitions
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@Transactional
	public static List<ElementDefinition> unmarshall(String definitionsAsJSON) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper(); 
		mapper.getDeserializationConfig().addMixInAnnotations(ElementDefinition.class, ElementDefinitionMixIn.class);
		mapper.setVisibilityChecker(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
		mapper.registerSubtypes(
	            new NamedType(AbstractionDefinition.class, "AbstractionDefinition"),
	            new NamedType(ApplicationDefinition.class, "ApplicationDefinition"),
	            new NamedType(ConnectionDefinition.class, "ConnectionDefinition"),
	            new NamedType(ProcessorDefinition.class, "ProcessorDefinition"),
	            new NamedType(MessageSourceDefinition.class, "MessageSourceDefinition"),
	            new NamedType(RouterDefinition.class, "RouterDefinition")
				);
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper.readValue(definitionsAsJSON,  new TypeReference<Collection<ElementDefinition>>() {});
	}
	
}
