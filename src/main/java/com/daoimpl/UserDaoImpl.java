package com.daoimpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.dao.UserDao;
import com.model.User;
import com.model.UserOrder;

@Repository
public class UserDaoImpl implements UserDao {

  private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

  private SessionFactory sessionFactory;

  public void setSessionFactory(SessionFactory sf) {
    this.sessionFactory = sf;
  }

  public List<User> isExistingUser(User user) {
    Session session = this.sessionFactory.getCurrentSession();
    Query query = session.createQuery("from User user where user.userName like :userNameParam");
    query.setParameter("userNameParam", user.getUserName());
    List<User> users = query.list();
    return users;
  }

  public boolean isValidUser(User user) {
    Session session = this.sessionFactory.getCurrentSession();
    Query query =
        session
            .createQuery("from User user where user.userName like :userNameParam  and user.userPassword like :userNamePassword");
    query.setParameter("userNameParam", user.getUserName());
    query.setParameter("userNamePassword", user.getUserPassword());
    List<User> users = query.list();
    return users != null && users.size() > 0;
  }

  public void saveUser(User user) {
    Session session = this.sessionFactory.getCurrentSession();
    session.save(user);
  }

  public List<User> listAllUsers() {
    Session session = this.sessionFactory.getCurrentSession();
    Query query = session.createQuery("from User user ");
    return query.list();
  }


  public List<User> getUserByUserName() {
    Session session = this.sessionFactory.getCurrentSession();
    Query query = session.createQuery("from User user where user ");
    return query.list();
  }

  public void saveOrder(UserOrder userOrder) {
    Session session = this.sessionFactory.getCurrentSession();
    session.save(userOrder);
  }

  public List<UserOrder> listAllOrders() {
    Session session = this.sessionFactory.getCurrentSession();
    Query query = session.createQuery("from UserOrder userOrder ");
    return query.list();
  }

  public List<UserOrder> getOrderBy(String userName, String from, String to) {
    Session session = this.sessionFactory.getCurrentSession();
    String queryString = null;
    Query query =
        session
            .createQuery("from UserOrder order where order.userName like :userNameParam  " +
            		"and order.from like :fromParam and order.to like :toParam");
    query.setParameter("userNameParam",   StringUtils.isEmpty(from) ?  "'%'" : userName);
    query.setParameter("fromParam",   StringUtils.isEmpty(from) ?  "'%'" : from);
    query.setParameter("toParam",  StringUtils.isEmpty(from) ?  "'%'" : to);
    return query.list();
  }


}
