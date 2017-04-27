package com.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.model.User;
import com.services.UserService;


public class RegistrationValidator implements Validator {

  @Autowired
  UserService userService;

  String ePattern =
      "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
  java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);

  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  public boolean supports(Class<?> clazz) {
    return User.class.isAssignableFrom(clazz);
  }

  public void validate(Object arg0, Errors errors) {
    User user = (User) arg0;
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fullName", "error.fullName",
        "Full Name is Required.");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "error.userName",
        "User email is required.");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userPassword", "error.userPassword",
        "User Password is required.");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "repeatUserPassword",
        "error.repeatUserPassword", "Repeat User Password is required.");

    if (!errors.hasErrors() && !user.getUserPassword().equals(user.getRepeatUserPassword())) {
      errors.rejectValue("repeatUserPassword", "error.passwordsDoNotMatch",
          "Both Passwords must match ");
    } else if (!errors.hasErrors() && !CollectionUtils.isEmpty(userService.isExistingUser(user))) {
      errors.rejectValue("userName", "error.userAlreadyExists", " User Already Exists ");
    } else if (!errors.hasErrors() && !p.matcher(user.getUserName()).matches()) {
      errors.rejectValue("userName", "error.userEmailAddressFormat",
          " User Name Email format is of type abcdef@example.com");
    }
  }
}
