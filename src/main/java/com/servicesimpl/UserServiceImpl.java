package com.servicesimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.UserDao;
import com.model.User;
import com.model.UserOrder;
import com.services.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

  @Autowired
  private UserDao userDao;

  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

  @Transactional
  public List<User> isExistingUser(User user) {
    return userDao.isExistingUser(user);
  }

  @Transactional
  public boolean isValidUser(User user) {
    return userDao.isValidUser(user);
  }

  @Transactional
  public void saveUser(User user) {
    userDao.saveUser(user);
  }

  @Transactional
  public List<User> listAllUsers() {
    return userDao.listAllUsers();
  }

  @Transactional
  public void saveOrder(UserOrder userOrder) {
      userDao.saveOrder(userOrder);
  }
  
  @Transactional
  public List<UserOrder> listAllOrders() {
    return userDao.listAllOrders();
  }
  
  @Transactional
  public List<UserOrder> getOrderBy(String userName, String from, String to) {
    return userDao.getOrderBy(userName, from, to);
  }
  
  
}
