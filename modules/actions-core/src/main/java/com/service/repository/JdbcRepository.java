package com.service.repository;

import javax.sql.DataSource;

import org.jsoup.helper.Validate;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.service.core.ContextDefinition;
import com.service.core.NamesMapping;

public class JdbcRepository implements ContextRepository {

	private static final String INSERT_DIAGRAM = "INSERT INTO diagrams(diagram_id, jsonRepresentation) VALUES(?, ?)";
	private static final String UPDATE_DIAGRAM = "UPDATE diagrams SET jsonRepresentation = ? WHERE diagram_id = ?";
	private static final String GET_DIAGRAM    = "SELECT jsonRepresentation FROM diagrams WHERE diagram_id = ?";
	
	private JdbcTemplate template;
	private ContextDefinitionMarshaller marshaller;
	
	protected JdbcRepository() { }
	
	public JdbcRepository(DataSource dataSource, NamesMapping mapping) {
		Validate.notNull(dataSource);
		
		this.template = new JdbcTemplate(dataSource);
		this.marshaller = new ContextDefinitionMarshaller(mapping);
	}
	
	@Override
	public void save(ContextDefinition definition) throws MarshallingException {
		String json = this.marshaller.marshall(definition);
		try {
			this.template.update(INSERT_DIAGRAM, new Object[] { definition.getId(), json });
		} catch (DataAccessException e) {
			this.template.update(UPDATE_DIAGRAM, new Object[] { json, definition.getId() });
		}
	}

	@Override
	public ContextDefinition load(String contextId) throws MarshallingException {
		String jsonDefinition = this.getJsonDefinition(contextId);
		if (jsonDefinition == null) {
			return null;
		} else {
			ContextDefinition definition = this.marshaller.unmarshall(jsonDefinition);
			return definition;
		}
	}

	@Override
	public String getJsonDefinition(String contextId) {
		String jsonDefinition = this.template.queryForObject(GET_DIAGRAM, new Object[] { contextId }, String.class);
		return jsonDefinition;
	}
}
