package com.model;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;

import javax.validation.constraints.AssertTrue;

import org.junit.Test;

public class AirlineModelsTest {

  @Test
  public void testAccount() throws Exception {
    Account account = new Account();
    MonetaryAmount balance = new MonetaryAmount();
    account.setBalance(balance);
    account.setId("testId");
    assertTrue(account.getId().equals("testId"));
    assertTrue(account.getBalance().equals(balance));

    MonetaryAmount balance2 = new MonetaryAmount();
    Account account2 = new Account(balance2, "testId2");
    assertTrue(account2.getId().equals("testId2"));
    assertTrue(account2.getBalance().equals(balance2));

  }



  @Test
  public void testAirlineException() throws Exception {
    AirlineException airlineException = new AirlineException();
    airlineException.setMessage("message1");
    HashMap map = new HashMap();
    airlineException.setMap(map);

    assertTrue(airlineException.getMap().equals(map));
    assertTrue(airlineException.getMessage().equals("message1"));

    AirlineException airlineException2 = new AirlineException("message2");
    assertTrue(airlineException2.getMessage().equals("message2"));

  }



  @Test
  public void testAirlineOffer() throws Exception {
    AirlineOffer airlineOffer = new AirlineOffer();
    MonetaryAmount price = new MonetaryAmount();
    airlineOffer.setPrice(price);
    AirlineRoute route = new AirlineRoute();
    airlineOffer.setRoute(route);

    assertTrue(airlineOffer.getPrice().equals(price));
    assertTrue(airlineOffer.getRoute().equals(route));
  }


  @Test
  public void testAirlineRoute() throws Exception {
    AirlineRoute airlineRoute = new AirlineRoute();
    airlineRoute.setFrom("from");
    airlineRoute.setTo("to");

    assertTrue(airlineRoute.getFrom().equals("from"));
    assertTrue(airlineRoute.getTo().equals("to"));
  }


  @Test
  public void testAirlineTicket() throws Exception {
    AirlineTicket ticket = new AirlineTicket();
    ticket.setAmount(123);
    AirlineOffer details = new AirlineOffer();
    ticket.setDetails(details);

    assertTrue(ticket.getAmount() == 123);
    assertTrue(ticket.getDetails().equals(details));
  }


  @Test
  public void testBuyTicketRequest() throws Exception {
    BuyTicketRequest request = new BuyTicketRequest();
    request.setAccountId("accountId");
    request.setAmount(345);
    AirlineRoute route = new AirlineRoute();
    request.setRoute(route);
    assertTrue(request.getAccountId().equals("accountId"));
    assertTrue(request.getAmount() == 345);
    assertTrue(request.getRoute().equals(route));

  }



  @Test
  public void testCreateAccountRequest() throws Exception {
    CreateAccountRequest request = new CreateAccountRequest();
    request.setCurrency(Currency.USD);
    assertTrue(request.getCurrency().equals(Currency.USD));

    request = new CreateAccountRequest(Currency.AUD);
    assertTrue(request.getCurrency().equals(Currency.AUD));

  }


  @Test
  public void testDepositWithDrawRequest() throws Exception {
    DepositWithDrawRequest request = new DepositWithDrawRequest();
    request.setAccountId("accountId");
    MonetaryAmount monetaryAmount = new MonetaryAmount();
    request.setMonetaryAmount(monetaryAmount);

    assertTrue(request.getAccountId().equals("accountId"));
    assertTrue(request.getMonetaryAmount().equals(monetaryAmount));
  }


  @Test
  public void testExchangeCurrenciesRequest() throws Exception {
    ExchangeCurrenciesRequest request = new ExchangeCurrenciesRequest();
    request.setMonetaryAmount(1234);
    request.setTargetCurrency(Currency.AED);

    assertTrue(request.getMonetaryAmount() == 1234);
    assertTrue(request.getTargetCurrency().equals(Currency.AED));


    request = new ExchangeCurrenciesRequest(89, Currency.USD);
    assertTrue(request.getMonetaryAmount() == 89);
    assertTrue(request.getTargetCurrency().equals(Currency.USD));
  }


  @Test
  public void testMonetaryAmount() throws Exception {
    MonetaryAmount amount = new MonetaryAmount();
    amount.setAmount(12);
    amount.setCurrency(Currency.EUR);

    assertTrue(amount.getAmount() == 12);
    assertTrue(amount.getCurrency().equals(Currency.EUR));

  }


  @Test
  public void testUser() throws Exception {
    User user = new User();
    user.setFullName("fullName");
    user.setId(123);
    user.setUserName("userName");
    user.setUserPassword("userPassword");
    user.setRepeatUserPassword("repeat");
    user.setRestUserName("restUserName");
    
    assertTrue(user.getFullName().equals("fullName"));
    assertTrue(user.getId() == 123);
    assertTrue(user.getUserName().equals("userName"));
    assertTrue(user.getUserPassword().equals("userPassword"));
    assertTrue(user.getRepeatUserPassword().equals("repeat"));
    assertTrue(user.getRestUserName().equals("restUserName"));
    assertTrue(user.toString().equals(123 + "fullName" + "userName" + "userPassword"));
    
  }
  
  @Test
  public void testUserOrder() throws Exception {
    
    UserOrder order = new UserOrder();
    order.setUserName("userName");
    order.setRestUserName("restUserName");
    order.setAccountId("accountId");
    order.setAmount(12);
    order.setFrom("A");
    order.setTo("Z");
    order.setPriceAmount(123);
    order.setPriceCurrency("USD");
   
    assertTrue(order.getUserName().equals("userName"));
    assertTrue(order.getRestUserName().equals("restUserName"));
    assertTrue(order.getAccountId().equals("accountId"));
    assertTrue(order.getAmount() == 12);
    assertTrue(order.getFrom().equals("A"));
    assertTrue(order.getTo().equals("Z"));
    assertTrue(order.getPriceAmount() == 123 );
    assertTrue(order.getPriceCurrency().equals("USD"));

  }



}
