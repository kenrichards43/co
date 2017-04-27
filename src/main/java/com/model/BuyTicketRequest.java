package com.model;

public class BuyTicketRequest {
  private String accountId;
  private int amount;
  private AirlineRoute route;

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

  public AirlineRoute getRoute() {
    return route;
  }

  public void setRoute(AirlineRoute route) {
    this.route = route;
  }

}
