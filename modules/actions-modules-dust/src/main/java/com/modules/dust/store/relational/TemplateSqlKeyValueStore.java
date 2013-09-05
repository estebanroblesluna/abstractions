package com.modules.dust.store.relational;

import java.sql.SQLException;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import com.modules.dust.JsonBuilder;
import com.modules.dust.Template;

public class TemplateSqlKeyValueStore extends GenericSqlKeyValueStore<Template> {

	public TemplateSqlKeyValueStore(String connectionString, String driverName)
			throws ClassNotFoundException, SQLException {
		super(connectionString, driverName);
	}

	@Override
	String objectToString(Template object) {
		try {
			return new JsonBuilder()
				.startObject()
				.field("name", object.getName())
				.field("content", object.getContent())
				.field("id", object.getId())
				.getContent();
		} catch (Exception e) {
			return "{}";
		}
	}

	@Override
	Template stringToObject(String string) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonFactory factory = mapper.getJsonFactory();
			JsonParser jp = factory.createJsonParser(string);
			JsonNode actualObj = mapper.readTree(jp);
			Template template = new Template(actualObj.get("name").asText(), actualObj.get("content").asText());
			template.setId(actualObj.get("id").asText());
			return template;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
