package com.modules.sql;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.core.api.Expression;
import com.core.api.Message;
import com.core.api.Processor;

public class SelectProcessor implements Processor {

	private static Log log = LogFactory.getLog(SelectProcessor.class);
	
	private SQLConnector connector;
	private Expression selectExpression;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Message process(Message message) {
		String sql = (String) this.selectExpression.evaluate(message);

		try {
			Object[] values = this.connector.select(sql);
			message.setPayload(values);
		} catch (SQLException e) {
			log.warn("Error executing SQL", e);
		}

		return message;
	}

	public SQLConnector getConnector() {
		return connector;
	}

	public void setConnector(SQLConnector connector) {
		this.connector = connector;
	}

	public Expression getSelectExpression() {
		return selectExpression;
	}

	public void setSelectExpression(Expression selectExpression) {
		this.selectExpression = selectExpression;
	}
}
