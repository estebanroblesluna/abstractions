package com.abstractions.web;

import com.service.core.KeyProvider;

public class WebUserKeyProvider implements KeyProvider {

	@Override
	public String apply(String value) {
		return WebUser.getCurrentUserId() + value;
	}

}
