package com.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.dao.AirlineDao;
import com.model.Account;
import com.model.AirlineOffer;
import com.model.AirlineTicket;
import com.model.BuyTicketRequest;
import com.model.CreateAccountRequest;
import com.model.DepositWithDrawRequest;
import com.model.ExchangeCurrenciesRequest;
import com.model.MonetaryAmount;
import com.services.AirlineService;

public class AirlineServiceImpl implements AirlineService {

  @Autowired
  private AirlineDao airlineDao;

  public void setAirlineDao(AirlineDao airlineDao) {
    this.airlineDao = airlineDao;
  }

  public void reset() throws Exception {
    airlineDao.reset();
  }
 
  
  public void setRestUserName(String restUserName) throws Exception {
      airlineDao.setRestUserName(restUserName);
  }

  public AirlineOffer[] listAvailableOffers() throws Exception {
    return airlineDao.listAvailableOffers();
  }

  public AirlineTicket[] listAvailableTickets() throws Exception {
    return airlineDao.listAvailableTickets();
  }

  public Account[] listAllAccounts() throws Exception {
    return airlineDao.listAllAccounts();
  }


  public Account createNewAccount(CreateAccountRequest request) throws Exception {
    return airlineDao.createNewAccount(request);
  }

  public Account withDrawMoney(DepositWithDrawRequest request) throws Exception {
    return airlineDao.withDrawMoney(request);
  }


  public Account depositMoney(DepositWithDrawRequest request) throws Exception {
    return airlineDao.depositMoney(request);
  }


  public MonetaryAmount exchangeCurrency(ExchangeCurrenciesRequest request) throws Exception {
    return airlineDao.exchangeCurrency(request);
  }


  public AirlineTicket buyAirlineTicket(BuyTicketRequest request) throws Exception {
    return airlineDao.buyAirlineTicket(request);
  }

}
