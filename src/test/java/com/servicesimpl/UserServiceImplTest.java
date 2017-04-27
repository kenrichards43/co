package com.servicesimpl;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.util.CollectionUtils;

import com.dao.UserDao;
import com.model.User;

public class UserServiceImplTest {


  @Mock
  private UserDao userDao;

  @InjectMocks
  private UserServiceImpl userServiceImpl;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    userServiceImpl = new UserServiceImpl();
    userServiceImpl.setUserDao(userDao);
  }

  @Test
  public void testIsExistingUser() {
    when(userDao.isExistingUser(any(User.class))).thenReturn(Arrays.asList(new User()));
    boolean b = !CollectionUtils.isEmpty(userServiceImpl.isExistingUser(any(User.class)));
    assertTrue(b);
    verify(userDao, times(1)).isExistingUser(any(User.class));
  }

  @Test
  public void testisValidUser() {
    when(userDao.isValidUser(any(User.class))).thenReturn(false);
    boolean b = userServiceImpl.isValidUser(any(User.class));
    assertTrue(!b);
    verify(userDao, times(1)).isValidUser(any(User.class));
  }

  @Test
  public void testsaveUser() {
    userServiceImpl.saveUser(any(User.class));
    verify(userDao, times(1)).saveUser(any(User.class));
  }

  @Test
  public void testlistAllUsers() {
    when(userDao.listAllUsers()).thenReturn(Arrays.asList(new User(), new User()));
    List l = userServiceImpl.listAllUsers();
    assertTrue(l.size() == 2);
    verify(userDao, times(1)).listAllUsers();
  }

}
