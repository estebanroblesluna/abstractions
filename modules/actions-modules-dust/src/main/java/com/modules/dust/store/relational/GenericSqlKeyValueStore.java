package com.modules.dust.store.relational;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.modules.dust.store.IndexedKeyValueStore;

public abstract class GenericSqlKeyValueStore<T> implements
		IndexedKeyValueStore<T> {

	private Connection connection;

	public GenericSqlKeyValueStore(String connectionString, String driverName)
			throws ClassNotFoundException, SQLException {
		Class.forName(driverName);
		this.connection = DriverManager.getConnection(connectionString);
	}

	@Override
	public void put(String index, String key, T object) {
		try {
			PreparedStatement preparedStatement = this.connection.prepareStatement(
				"select * from key_value_table where name = ?");
			preparedStatement.setString(1, index);
			ResultSet r = preparedStatement.executeQuery();
			
			if (!r.next()) {
				preparedStatement = this.connection.prepareStatement(
					"insert into key_value_table (name) values (?)");
				preparedStatement.setString(1, index);
				preparedStatement.executeUpdate();
			}
			
			preparedStatement = this.connection.prepareStatement(
				"replace into key_value set " +
					" key_value_table_id = (select key_value_table_id from key_value_table where name = ?)," +
					"`key`  = ?," +
					"`value` = ?;");
			preparedStatement.setString(1, index);
			preparedStatement.setString(2, key);
			preparedStatement.setString(3, this.objectToString(object));
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public T get(String index, String key) {
		try {
			PreparedStatement preparedStatement = this.connection.prepareStatement(
					"select kv.* " +
					"from key_value kv " +
					"join key_value_table kvt on (kv.key_value_table_id = kvt.key_value_table_id) " +
					"where " +
					"	kvt.name = ? and " +
					"	kv.key = ?");
			preparedStatement.setString(1, index);
			preparedStatement.setString(2, key);
			ResultSet result = preparedStatement.executeQuery();
			if (result.next()) {
				return this.stringToObject(result.getString("value"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void remove(String index, String key) {
		try {
			PreparedStatement preparedStatement = this.connection.prepareStatement(
				"delete kv " +
				"from key_value kv " +
				"join key_value_table kvt on (kv.key_value_table_id = kvt.key_value_table_id) " +
				"where " +
				"	kv.key = ? and " +
				"	kvt.name = ?"
			);
			preparedStatement.setString(1, key);
			preparedStatement.setString(2, index);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Collection<T> getFromIndex(String index) {
		Collection<T> collection = new ArrayList<T>();
		try {
			PreparedStatement preparedStatement = this.connection.prepareStatement(
					"select kv.* " +
					"from key_value kv " +
					"join key_value_table kvt on (kv.key_value_table_id = kvt.key_value_table_id) " +
					"where " +
					"	kvt.name = ?");
			preparedStatement.setString(1, index);
			ResultSet result = preparedStatement.executeQuery();
			while (result.next()) {
				collection.add(this.stringToObject(result.getString("value")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return collection;
	}
	
	abstract String objectToString(T object);
	
	abstract T stringToObject(String string);

}
