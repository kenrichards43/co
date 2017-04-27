package com.model;

public class ExchangeCurrenciesRequest {
  private int monetaryAmount;
  private Currency targetCurrency;

  public ExchangeCurrenciesRequest() {
    super();
  }

  public ExchangeCurrenciesRequest(int monetaryAmount, Currency targetCurrency) {
    super();
    this.monetaryAmount = monetaryAmount;
    this.targetCurrency = targetCurrency;
  }

  public int getMonetaryAmount() {
    return monetaryAmount;
  }

  public void setMonetaryAmount(int monetaryAmount) {
    this.monetaryAmount = monetaryAmount;
  }

  public Currency getTargetCurrency() {
    return targetCurrency;
  }

  public void setTargetCurrency(Currency targetCurrency) {
    this.targetCurrency = targetCurrency;
  }
}
