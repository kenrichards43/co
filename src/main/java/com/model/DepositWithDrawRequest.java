package com.model;

public class DepositWithDrawRequest {
  private String accountId;
  private MonetaryAmount monetaryAmount;

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public MonetaryAmount getMonetaryAmount() {
    return monetaryAmount;
  }

  public void setMonetaryAmount(MonetaryAmount monetaryAmount) {
    this.monetaryAmount = monetaryAmount;
  }
}
