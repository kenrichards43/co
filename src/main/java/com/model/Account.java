package com.model;


public class Account {
  private MonetaryAmount balance;
  private String id;

  public Account() {}

  public Account(MonetaryAmount balance, String id) {
    super();
    this.balance = balance;
    this.id = id;
  }

  public MonetaryAmount getBalance() {
    return balance;
  }

  public void setBalance(MonetaryAmount balance) {
    this.balance = balance;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

}
