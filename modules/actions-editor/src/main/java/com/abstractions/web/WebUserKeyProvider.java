package com.abstractions.web;

import com.abstractions.service.core.KeyProvider;

public class WebUserKeyProvider implements KeyProvider {

	@Override
	public String apply(String value) {
		return WebUser.getCurrentUserId() + value;
	}

}
