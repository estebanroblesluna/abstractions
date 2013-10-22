package com.modules.sql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.abstractions.api.Expression;
import com.abstractions.api.Message;
import com.abstractions.api.Processor;
import com.abstractions.expression.ScriptingExpression;
import com.abstractions.expression.ScriptingLanguage;

public class SelectProcessor implements Processor {

	private static Log log = LogFactory.getLog(SelectProcessor.class);
	
	private SQLConnector connector;
	private Expression selectExpression;
	private List<Expression> parameterExpressions;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Message process(Message message) {
		String sql = (String) this.selectExpression.evaluate(message);

		try {
			Object[] parameters = this.getParameters(message);
			List<Object[]> values = this.getConnector().select(sql, parameters);
			message.setPayload(values);
		} catch (SQLException e) {
			log.warn("Error executing SQL", e);
		}

		return message;
	}

	private Object[] getParameters(Message message) {
		int size = this.parameterExpressions == null ? 0 : this.parameterExpressions.size();
		Object[] params = new Object[size];
		int i = 0;
		for (Expression expression : this.parameterExpressions) {
			Object param = expression.evaluate(message);
			params[i] = param;
			i++;
		}
		return params;
	}

	public SQLConnector getConnector() {
		if (this.connector == null) {
			this.connector = new SQLConnector();
		}
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

	public void setParameterExpressions(String parameterExpressions) {
		if (StringUtils.isBlank(parameterExpressions)) {
			return;
		}
		
		String[] paramsAsString = StringUtils.split(parameterExpressions, ';');
		List<Expression> expressions = new ArrayList<Expression>();
		for (String paramAsString : paramsAsString) {
			if (!StringUtils.isBlank(paramAsString)) {
				Expression expression = new ScriptingExpression(ScriptingLanguage.GROOVY, paramAsString);
				expressions.add(expression);
			}
		}
		
		this.parameterExpressions = expressions;
	}
}
