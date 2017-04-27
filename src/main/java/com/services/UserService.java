package com.services;

import java.util.List;

import com.model.User;
import com.model.UserOrder;

public interface UserService {

  public List<User> isExistingUser(User user);

  public boolean isValidUser(User user);

  public void saveUser(User user);

  public List<User> listAllUsers();

  public void saveOrder(UserOrder userOrder);

  public List<UserOrder> listAllOrders();  
  
  public List<UserOrder> getOrderBy(String userName, String from, String to);

}
