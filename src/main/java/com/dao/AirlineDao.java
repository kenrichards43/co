package com.dao;

import com.model.Account;
import com.model.AirlineOffer;
import com.model.AirlineTicket;
import com.model.BuyTicketRequest;
import com.model.CreateAccountRequest;
import com.model.DepositWithDrawRequest;
import com.model.ExchangeCurrenciesRequest;
import com.model.MonetaryAmount;

public interface AirlineDao {

  public void reset() throws Exception;

  public void setRestUserName(String restUserName) throws Exception;
  
  public AirlineOffer[] listAvailableOffers() throws Exception;

  public AirlineTicket[] listAvailableTickets() throws Exception;

  public AirlineTicket buyAirlineTicket(BuyTicketRequest request) throws Exception;

  public Account createNewAccount(CreateAccountRequest request) throws Exception;

  public Account[] listAllAccounts() throws Exception;

  public Account depositMoney(DepositWithDrawRequest request) throws Exception;

  public Account withDrawMoney(DepositWithDrawRequest request) throws Exception;

  public MonetaryAmount exchangeCurrency(ExchangeCurrenciesRequest request) throws Exception;
}
