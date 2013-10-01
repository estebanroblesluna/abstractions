package com.modules.cache;

import java.io.IOException;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.MemcachedClient;

public class MemcachedCache implements Cache {

	private volatile MemcachedClient client;
	private volatile String serverAddresses = "127.0.0.1:11211";
	private volatile int ttl = 60 * 5000;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object get(String key) {
		Object value = this.getClient().get(key);
		return value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object put(String key, Object newValue) {
		this.getClient().set(key, this.ttl, newValue);
		return null;
	}

	public String getServerAddresses() {
		return serverAddresses;
	}

	public void setServerAddresses(String serverAddresses) {
		this.serverAddresses = serverAddresses;
	}

	private MemcachedClient getClient() {
		if (client == null) {
			synchronized (this) {
				if (client == null) {
					try {
						this.client = new MemcachedClient(new BinaryConnectionFactory(), AddrUtil.getAddresses(this.serverAddresses));
					} catch (IOException e) {
						throw new IllegalArgumentException("Can't connect", e);
					}
				}				
			}
		}
		return client;
	}

	public int getTtl() {
		return ttl;
	}

	public void setTtl(int ttl) {
		this.ttl = ttl;
	}
}
