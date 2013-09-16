package com.abstractions.service;

import org.jsoup.helper.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abstractions.model.User;
import com.abstractions.repository.GenericRepository;

@Service
public class UserService {

	private GenericRepository genericRepository;

	public UserService() {
	}

	public UserService(GenericRepository genericRepository) {
		Validate.notNull(genericRepository);

		this.genericRepository = genericRepository;
	}

	@Transactional
	public User getUser(long userId) {
		return this.genericRepository.get(User.class, userId);
	}

}
