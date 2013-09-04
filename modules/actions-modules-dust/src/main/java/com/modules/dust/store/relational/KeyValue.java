package com.modules.dust.store.relational;

public class KeyValue {

	private long id;
	private String key;
	private String value;
	private KeyValueTable table;

	protected KeyValue() {
	}
	
	public KeyValue(String key, String value, KeyValueTable table) {
		super();
		this.setKey(key);
		this.setValue(value);
		this.setTable(table);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public KeyValueTable getTable() {
		return table;
	}

	public void setTable(KeyValueTable table) {
		this.table = table;
	}

}
