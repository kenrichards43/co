package com.model;

public class AirlineTicket {
  private int amount;
  private AirlineOffer details;

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public AirlineOffer getDetails() {
    return details;
  }

  public void setDetails(AirlineOffer details) {
    this.details = details;
  }
}
