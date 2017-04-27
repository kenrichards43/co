package com.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.model.User;
import com.services.UserService;


public class LoginValidator implements Validator {

  @Autowired
  UserService userService;

  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  public boolean supports(Class<?> clazz) {
    return User.class.isAssignableFrom(clazz);
  }

  public void validate(Object arg0, Errors errors) {
    User user = (User) arg0;
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "error.userName",
        "User email is required.");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userPassword", "error.userPassword",
        "User Password is required.");

    if (!errors.hasErrors() && !userService.isValidUser(user)) {
      errors
          .rejectValue("userName", "error.userName", " Incorrect User Name/Password combination ");
    }
  }
}
