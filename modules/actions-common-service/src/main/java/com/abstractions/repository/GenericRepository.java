package com.abstractions.repository;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
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
	
	public void delete(Class<?> theClass, long id) {
		String query = "delete from :class where id = :id".replace(":class", theClass.getCanonicalName());
	    Query q = this.sessionFactory.getCurrentSession().createQuery(query);
        q.setLong("id", id);
        q.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(Class<?> theClass, long id) {
		return (T) this.sessionFactory.getCurrentSession().get(theClass, id);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> get(Class<T> theClass, String assocProperty, long applicationSnapshotId) {
		return (List<T>) this.sessionFactory.getCurrentSession()
			.createCriteria(theClass)
			.createAlias(assocProperty, "a")
			.add(Restrictions.eq("a.id", applicationSnapshotId))
			.list();
	}
}
