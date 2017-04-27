package com.validators;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;

import com.model.User;
import com.services.UserService;

@RunWith(MockitoJUnitRunner.class)
public class LoginValidatorTest {

  @Mock
  UserService userService;

  LoginValidator loginValidator;

  private User user;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    loginValidator = new LoginValidator();
    loginValidator.setUserService(userService);
    user = new User();
  }

  @Test
  public void testEmptyUserName() {
    user.setUserPassword("userPassword");
    BindException errors = new BindException(user, "user");
    ValidationUtils.invokeValidator(loginValidator, user, errors);
    assertTrue(errors.hasErrors());
    assertTrue(errors.hasFieldErrors("userName"));

  }

  @Test
  public void testEmptyUserPassword() {
    user.setUserName("userName");
    BindException errors = new BindException(user, "user");
    ValidationUtils.invokeValidator(loginValidator, user, errors);
    assertTrue(errors.hasErrors());
    assertTrue(errors.hasFieldErrors("userPassword"));

  }

  @Test
  public void test_Incorrect_userName_UserPassword() {
    user.setUserName("incorrectUserName");
    user.setUserPassword("incorrectUserPassword");
    BindException errors = new BindException(user, "user");
    Mockito.when(userService.isValidUser(any(User.class))).thenReturn(false);
    ValidationUtils.invokeValidator(loginValidator, user, errors);
    assertTrue(errors.hasErrors());
    assertTrue(errors.hasFieldErrors("userName"));

  }

}
