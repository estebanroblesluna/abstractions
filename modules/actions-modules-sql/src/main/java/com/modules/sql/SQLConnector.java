package com.modules.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class SQLConnector {

	private DataSource dataSource;
	
	public Object[] select(String sql, Object... arguments) throws SQLException {
		Connection connection = this.dataSource.getConnection();
		PreparedStatement ps = connection.prepareStatement(sql);
		
		for (int i = 0; i < arguments.length; i++) {
			Object argument = arguments[i];
			ps.setObject(i + 1, argument);
		}
		
		ps.execute();
		
		ResultSet rs = ps.getResultSet();
		int columnCount = ps.getMetaData().getColumnCount();
		List<Object[]> result = new ArrayList<Object[]>();
		
		while (rs.next()) {
			Object[] values = new Object[columnCount];
			
			for (int i = 0; i < columnCount; i++) {
				Object value = rs.getObject(i + 1);
				values[i] = value;
			}
			
			result.add(values);
		}
		
		return result.toArray();
	}

}
