package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "User_Order")
public class UserOrder {
  
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  
  @Column(name = "user_name")
  private String userName;
  
  @Column(name = "rest_user_name")
  private String restUserName;
  
  @Column(name = "account_id")
  private String accountId;
  
  @Column(name = "amount")
  private int amount;
  
  @Column(name = "route_from")
  private String from;
  
  @Column(name = "route_to")
  private String to;
  
  @Column(name = "price_amount")
  private int priceAmount;
  
  @Column(name = "price_currency")
  private String priceCurrency;
  
  
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getUserName() {
    return userName;
  }
  public void setUserName(String userName) {
    this.userName = userName;
  }
  public String getRestUserName() {
    return restUserName;
  }
  public void setRestUserName(String restUserName) {
    this.restUserName = restUserName;
  }
  public String getAccountId() {
    return accountId;
  }
  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }
  public int getAmount() {
    return amount;
  }
  public void setAmount(int amount) {
    this.amount = amount;
  }
  public String getFrom() {
    return from;
  }
  public void setFrom(String from) {
    this.from = from;
  }
  public String getTo() {
    return to;
  }
  public void setTo(String to) {
    this.to = to;
  }
  public int getPriceAmount() {
    return priceAmount;
  }
  public void setPriceAmount(int priceAmount) {
    this.priceAmount = priceAmount;
  }
  public String getPriceCurrency() {
    return priceCurrency;
  }
  public void setPriceCurrency(String priceCurrency) {
    this.priceCurrency = priceCurrency;
  }
}
