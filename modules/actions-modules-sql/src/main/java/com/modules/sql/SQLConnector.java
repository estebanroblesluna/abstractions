package com.modules.sql;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class SQLConnector {

	private volatile DataSource dataSource;
	
	private String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/amazon";
	private String username = "root";
	private String password = "";
	
	public List<Object[]> select(String sql, Object... arguments) throws SQLException {
		Connection connection = this.getDataSource().getConnection();
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet rs = null;
		
		try {
			for (int i = 0; i < arguments.length; i++) {
				Object argument = arguments[i];
				ps.setObject(i + 1, argument);
			}
			
			ps.execute();
			
			rs = ps.getResultSet();
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
			
			return result;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}

	private DataSource getDataSource() {
		if (this.dataSource == null) {
			synchronized (this) {
				if (this.dataSource == null) {
					ComboPooledDataSource ds = new ComboPooledDataSource();
					try {
						ds.setDriverClass(this.driver);
					} catch (PropertyVetoException e) {
						//log
					}
					ds.setJdbcUrl(this.url);
					ds.setUser(this.username);
					ds.setPassword(this.password);
					
					ds.setAcquireIncrement(4);
					ds.setMinPoolSize(8);
					ds.setMaxPoolSize(32);
					this.dataSource = ds;
				}
			}
		}
		
		return this.dataSource;
	}
	
	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
