package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mysql.jdbc.StringUtils;

@Entity
@Table(name = "User")
public class User {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "full_name")
  private String fullName;

  @Column(name = "user_name")
  private String userName;

  @Column(name = "user_password")
  private String userPassword;
  
  @Column(name = "rest_user_name")
  private String restUserName;

  @Transient
  private String repeatUserPassword;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserPassword() {
    return userPassword;
  }

  public void setUserPassword(String userPassword) {
    this.userPassword = userPassword;
  }

  public String getRepeatUserPassword() {
    return repeatUserPassword;
  }

  public void setRepeatUserPassword(String repeatUserPassword) {
    this.repeatUserPassword = repeatUserPassword;
  }

  public String getRestUserName() {
    return restUserName;
  }

  public void setRestUserName(String restUserName) {
    this.restUserName = restUserName;
  }

  public String toString() {
    return "" + id + getValue(fullName) + getValue(userName) + getValue(userPassword);
  }

  private String getValue(String input) {
    return StringUtils.isNullOrEmpty(input) ? "" : input;
  }

}
