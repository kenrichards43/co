package com.validators;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;

import com.model.User;
import com.services.UserService;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationValidatorTest {

  @Mock
  UserService userService;

  RegistrationValidator registrationValidator;

  private User user;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    registrationValidator = new RegistrationValidator();
    registrationValidator.setUserService(userService);
    user = new User();
  }


  @Test
  public void testEmptyFullName() {
    user.setUserName("userName");
    user.setUserPassword("userPassword");
    user.setRepeatUserPassword("userPassword");
    BindException errors = new BindException(user, "user");

    ValidationUtils.invokeValidator(registrationValidator, user, errors);
    assertTrue(errors.hasErrors());
    assertTrue(errors.hasFieldErrors("fullName"));

  }

  @Test
  public void testEmptyUserName() {
    user.setFullName("fullName");
    user.setUserPassword("userPassword");
    user.setRepeatUserPassword("userPassword");
    BindException errors = new BindException(user, "user");
    ValidationUtils.invokeValidator(registrationValidator, user, errors);
    assertTrue(errors.hasErrors());
    assertTrue(errors.hasFieldErrors("userName"));

  }

  @Test
  public void testEmptyUserPassword() {
    user.setFullName("fullName");
    user.setUserName("userName");
    user.setRepeatUserPassword("repeatUserPassword");
    BindException errors = new BindException(user, "user");
    ValidationUtils.invokeValidator(registrationValidator, user, errors);
    assertTrue(errors.hasErrors());
    assertTrue(errors.hasFieldErrors("userPassword"));

  }

  @Test
  public void testEmptyRepeatUserPassword() {
    user.setFullName("fullName");
    user.setUserName("userName");
    user.setUserPassword("userPassword");
    BindException errors = new BindException(user, "user");
    ValidationUtils.invokeValidator(registrationValidator, user, errors);
    assertTrue(errors.hasErrors());
    assertTrue(errors.hasFieldErrors("repeatUserPassword"));

  }

  @Test
  public void testNonEqualpasswords() {
    user.setFullName("fullName");
    user.setUserName("userName");
    user.setUserPassword("userPassword");
    user.setRepeatUserPassword("repeatUserPassword");
    BindException errors = new BindException(user, "user");
    ValidationUtils.invokeValidator(registrationValidator, user, errors);
    assertTrue(errors.hasErrors());
    assertTrue(errors.hasFieldErrors("repeatUserPassword"));

  }


  @Test
  public void testEmailFormat() {
    user.setFullName("fullName");
    user.setUserName("userName");
    user.setUserPassword("userPassword");
    user.setRepeatUserPassword("userPassword");
    BindException errors = new BindException(user, "user");
    ValidationUtils.invokeValidator(registrationValidator, user, errors);
    assertTrue(errors.hasErrors());
    assertTrue(errors.hasFieldErrors("userName"));

    user.setUserName("userName@example.com");
    errors = new BindException(user, "user");
    ValidationUtils.invokeValidator(registrationValidator, user, errors);
    assertFalse(errors.hasErrors());
    assertFalse(errors.hasFieldErrors("userName"));

  }


  @Test
  public void testDuplicateUserName() {
    user.setFullName("fullName");
    user.setUserName("userName");
    user.setUserPassword("userPassword");
    user.setRepeatUserPassword("userPassword");
    BindException errors = new BindException(user, "user");
    Mockito.when(userService.isExistingUser(any(User.class))).thenReturn(Arrays.asList(new User()));
    ValidationUtils.invokeValidator(registrationValidator, user, errors);
    assertTrue(errors.hasErrors());
    assertTrue(errors.hasFieldErrors("userName"));
  }

}
