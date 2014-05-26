package com.abstractions.repository;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.jsoup.helper.Validate;
import org.springframework.stereotype.Repository;

import com.abstractions.model.User;

@Repository
public class UserRepository extends GenericRepository {

  public UserRepository() {
  }

  public UserRepository(SessionFactory sessionFactory) {
    Validate.notNull(sessionFactory);

    this.setSessionFactory(sessionFactory);
  }

  public User getUserByUsernameWithAuthorities(String username) {
    String query = "from UserImpl as user " + 
                   "inner join fetch user.authorities as auth " + 
                   "where username = :username";
    Query q = this.getSessionFactory().getCurrentSession().createQuery(query);
    q.setString("username", username);
    return (User) q.uniqueResult();
  }
  
  @SuppressWarnings("unchecked")
  public List<User> getConfirmedDisabledUsers() {
    String query = "from UserImpl as user " + 
                   "where confirmed = true "+
                   "and enabled = false";
    Query q = this.getSessionFactory().getCurrentSession().createQuery(query);
    return (List<User>) q.list();
  }
}
