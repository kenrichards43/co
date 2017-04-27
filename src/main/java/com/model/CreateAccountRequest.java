package com.model;

public class CreateAccountRequest {
  private Currency currency;

  public CreateAccountRequest() {
    super();
  }

  public CreateAccountRequest(Currency currency) {
    super();
    this.currency = currency;
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }
}
