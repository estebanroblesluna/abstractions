package com.modules.dust.store;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.modules.dust.Template;
import com.modules.dust.store.relational.TemplateSqlKeyValueStore;

public class TemplateSqlKeyValueStoreTestCase {

	private IndexedKeyValueStore<Template> indexedKeyValueStore;

	@Before
	public void setUp() throws ClassNotFoundException, SQLException {
		this.indexedKeyValueStore = new TemplateSqlKeyValueStore("jdbc:mysql://localhost/actions_templates?user=root", "com.mysql.jdbc.Driver");
	}
	
	@Test
	public void testPut() {
		Template t1 = new Template("t1", "content-t1");
		t1.setId("t1");
		this.indexedKeyValueStore.remove("templateByName", "t1");
		long size = this.indexedKeyValueStore.getFromIndex("templateByName").size();
		this.indexedKeyValueStore.put("templateByName", "t1", t1);
		assertEquals(size + 1, this.indexedKeyValueStore.getFromIndex("templateByName").size());
		Template t2 = this.indexedKeyValueStore.get("templateByName", "t1");
		assertEquals(t1.getId(), t2.getId());
		assertEquals(t1.getName(), t2.getName());
		assertEquals(t1.getContent(), t2.getContent());
		this.indexedKeyValueStore.remove("templateByName", "t1");
		assertEquals(size, this.indexedKeyValueStore.getFromIndex("templateByName").size());
		assertNull(this.indexedKeyValueStore.get("templateByName", "t1"));
	}

}
