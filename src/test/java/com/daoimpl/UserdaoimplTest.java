package com.daoimpl;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.util.CollectionUtils;

import com.model.User;

public class UserdaoimplTest {

  @Mock
  private SessionFactory sessionFactory;

  @Mock
  private Query query;

  @Mock
  private Session session;

  private User user;

  @InjectMocks
  private UserDaoImpl userdaoimpl;


  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    userdaoimpl = new UserDaoImpl();
    userdaoimpl.setSessionFactory(sessionFactory);
    when(sessionFactory.getCurrentSession()).thenReturn(session);
    when(session.createQuery(any(String.class))).thenReturn(query);
    user = new User();
  }

  @Test
  public void testisExistingUser() {
    user.setUserName("alpha");
    when(query.list()).thenReturn(null);
    boolean b = !CollectionUtils.isEmpty(userdaoimpl.isExistingUser(user));
    verify(sessionFactory, times(1)).getCurrentSession();
    verify(session, times(1)).createQuery(any(String.class));
    verify(query, times(1)).setParameter("userNameParam", user.getUserName());
    assertFalse(b);

    reset(query);
    when(query.list()).thenReturn(Arrays.asList(1, 2, 3));
    b = !CollectionUtils.isEmpty(userdaoimpl.isExistingUser(user));
    assertTrue(b);
  }


  @Test
  public void testisValidUser() {
    user.setUserName("alpha");
    user.setUserPassword("userpassword");
    when(query.list()).thenReturn(null);
    boolean b = userdaoimpl.isValidUser(user);
    verify(sessionFactory, times(1)).getCurrentSession();
    verify(session, times(1)).createQuery(any(String.class));
    verify(query, times(1)).setParameter("userNameParam", user.getUserName());
    verify(query, times(1)).setParameter("userNamePassword", user.getUserPassword());
    assertFalse(b);

    reset(query);
    when(query.list()).thenReturn(Arrays.asList(1, 2, 3));
    b = userdaoimpl.isValidUser(user);
    assertTrue(b);
  }


  @Test
  public void testsaveUser() {
    userdaoimpl.saveUser(user);
    verify(sessionFactory, times(1)).getCurrentSession();
    verify(session, times(1)).save(user);

  }

  @Test
  public void testlistAllUsers() {

    when(query.list()).thenReturn(Arrays.asList(new User(), new User()));
    List<User> users = userdaoimpl.listAllUsers();
    verify(sessionFactory, times(1)).getCurrentSession();
    verify(session, times(1)).createQuery(any(String.class));
    assertTrue(users.size() == 2);

  }

}
