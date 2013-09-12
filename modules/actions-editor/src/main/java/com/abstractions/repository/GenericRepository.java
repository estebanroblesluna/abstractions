package com.abstractions.repository;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.jsoup.helper.Validate;
import org.springframework.stereotype.Repository;

@Repository
public class GenericRepository {

	private SessionFactory sessionFactory;

	protected GenericRepository() { }

	public GenericRepository(SessionFactory sessionFactory) {
		Validate.notNull(sessionFactory);
		
		this.sessionFactory = sessionFactory;
	}

	public void save(Object object) {
		this.sessionFactory.getCurrentSession().save(object);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> get(Class<?> theClass, String orderBy) {
		return this.sessionFactory
			.getCurrentSession()
			.createCriteria(theClass)
			.addOrder(Order.asc(orderBy))
			.list();
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Class<?> theClass, long id) {
		return (T) this.sessionFactory.getCurrentSession().get(theClass, id);
	}
}
