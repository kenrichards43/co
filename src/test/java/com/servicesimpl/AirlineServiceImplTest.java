package com.servicesimpl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dao.AirlineDao;
import com.model.BuyTicketRequest;
import com.model.CreateAccountRequest;
import com.model.DepositWithDrawRequest;
import com.model.ExchangeCurrenciesRequest;


public class AirlineServiceImplTest {

  @Mock
  private AirlineDao airlineDao;

  @InjectMocks
  private AirlineServiceImpl airlineServiceImpl;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    airlineServiceImpl = new AirlineServiceImpl();
    airlineServiceImpl.setAirlineDao(airlineDao);
  }

  @Test
  public void testReset() throws Exception {
    airlineServiceImpl.reset();
    verify(airlineDao, times(1)).reset();
  }

  @Test
  public void testlistAvailableOffers() throws Exception {
    airlineServiceImpl.listAvailableOffers();
    verify(airlineDao, times(1)).listAvailableOffers();
  }

  @Test
  public void testlistlistAvailableTickets() throws Exception {
    airlineServiceImpl.listAvailableTickets();
    verify(airlineDao, times(1)).listAvailableTickets();
  }

  @Test
  public void testlistAllAccounts() throws Exception {
    airlineServiceImpl.listAllAccounts();
    verify(airlineDao, times(1)).listAllAccounts();
  }

  @Test
  public void testcreateNewAccount() throws Exception {
    airlineServiceImpl.createNewAccount(any(CreateAccountRequest.class));
    verify(airlineDao, times(1)).createNewAccount(any(CreateAccountRequest.class));
  }

  @Test
  public void testwithDrawMoney() throws Exception {
    airlineServiceImpl.withDrawMoney(any(DepositWithDrawRequest.class));
    verify(airlineDao, times(1)).withDrawMoney(any(DepositWithDrawRequest.class));
  }

  @Test
  public void testdepositMoney() throws Exception {
    airlineServiceImpl.depositMoney(any(DepositWithDrawRequest.class));
    verify(airlineDao, times(1)).depositMoney(any(DepositWithDrawRequest.class));
  }

  @Test
  public void testexchangeCurrency() throws Exception {
    airlineServiceImpl.exchangeCurrency(any(ExchangeCurrenciesRequest.class));
    verify(airlineDao, times(1)).exchangeCurrency(any(ExchangeCurrenciesRequest.class));
  }

  @Test
  public void testbuyAirlineTicket() throws Exception {
    airlineServiceImpl.buyAirlineTicket(any(BuyTicketRequest.class));
    verify(airlineDao, times(1)).buyAirlineTicket(any(BuyTicketRequest.class));
  }

}
