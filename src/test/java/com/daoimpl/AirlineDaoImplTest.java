package com.daoimpl;


import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.AirlineException;
import com.model.BuyTicketRequest;
import com.model.CreateAccountRequest;
import com.model.DepositWithDrawRequest;
import com.model.ExchangeCurrenciesRequest;


public class AirlineDaoImplTest {

  @Mock
  private RestTemplate restTemplate;

  @Mock
  private ObjectMapper mapper;

  @Mock
  private ResponseEntity<String> response;

  @InjectMocks
  private AirlineDaoImpl airlinedaoimpl;


  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    airlinedaoimpl = new AirlineDaoImpl();
    airlinedaoimpl.setMapper(mapper);
    airlinedaoimpl.setRestTemplate(restTemplate);
    airlinedaoimpl.setResponse(response);
  }

  @Test
  public void testReset() {
    // Exception 1
    airlinedaoimpl.setRestServiceURI("dummyURL");
    when(restTemplate.getForEntity(any(String.class), any(Class.class))).thenThrow(
        new HttpClientErrorException(HttpStatus.BAD_GATEWAY, "resetMethod"));
    try {
      airlinedaoimpl.reset();
    } catch (Exception e) {
      assertTrue(((HttpClientErrorException) e).getStatusCode().equals(HttpStatus.BAD_GATEWAY));
      assertTrue(((HttpClientErrorException) e).getStatusText().equals("resetMethod"));
    }

    // Exception 2
    reset(restTemplate);
    when(restTemplate.getForEntity(any(String.class), any(Class.class))).thenThrow(
        new ResourceAccessException(null) {
          public Throwable getCause() {
            AirlineException exp = new AirlineException();
            exp.setMessage("message1");
            return exp;
          }
        });

    try {
      airlinedaoimpl.reset();
    } catch (Exception e) {
      System.out.println(((AirlineException) e).getMessage());
      assertTrue(((AirlineException) e).getMessage().equals("message1"));
    }
  }



  @Test
  public void testListAvailableOffers() {
    airlinedaoimpl.setRestServiceURI("dummyURL");

    // No Exception
    when(restTemplate.getForEntity(any(String.class), any(Class.class))).thenReturn(response);
    when(response.getBody()).thenReturn("ValueString");
    try {
      airlinedaoimpl.listAvailableOffers();
    } catch (Exception ex) {
      System.out.println(ex.getClass());
    }

    try {
      Mockito.verify(mapper, times(1)).readValue(any(String.class), any(Class.class));
    } catch (Exception ex) {
    }


    // Exception 1
    reset(restTemplate, response);
    when(restTemplate.getForEntity(any(String.class), any(Class.class))).thenThrow(
        new HttpClientErrorException(HttpStatus.BAD_GATEWAY, "listAvailableOffers"));
    try {
      airlinedaoimpl.listAvailableOffers();
    } catch (Exception e) {
      assertTrue(((HttpClientErrorException) e).getStatusCode().equals(HttpStatus.BAD_GATEWAY));
      assertTrue(((HttpClientErrorException) e).getStatusText().equals("listAvailableOffers"));
    }

    // Exception 2
    reset(restTemplate, response);
    when(restTemplate.getForEntity(any(String.class), any(Class.class))).thenThrow(
        new ResourceAccessException(null) {
          public Throwable getCause() {
            AirlineException exp = new AirlineException();
            exp.setMessage("message1");
            return exp;
          }
        });

    try {
      airlinedaoimpl.listAvailableOffers();
    } catch (Exception e) {
      assertTrue(((AirlineException) e).getMessage().equals("message1"));
    }

  }


  @Test
  public void testListAvailableTickets() {
    airlinedaoimpl.setRestServiceURI("dummyURL");

    // No Exception
    when(restTemplate.getForEntity(any(String.class), any(Class.class))).thenReturn(response);
    when(response.getBody()).thenReturn("ValueString");
    try {
      airlinedaoimpl.listAvailableTickets();
    } catch (Exception ex) {
      System.out.println(ex.getClass());
    }

    try {
      Mockito.verify(mapper, times(1)).readValue(any(String.class), any(Class.class));
    } catch (Exception ex) {
    }


    // Exception 1
    reset(restTemplate, response);
    when(restTemplate.getForEntity(any(String.class), any(Class.class))).thenThrow(
        new HttpClientErrorException(HttpStatus.BAD_GATEWAY, "listAvailableTickets"));
    try {
      airlinedaoimpl.listAvailableTickets();
    } catch (Exception e) {
      assertTrue(((HttpClientErrorException) e).getStatusCode().equals(HttpStatus.BAD_GATEWAY));
      assertTrue(((HttpClientErrorException) e).getStatusText().equals("listAvailableTickets"));
    }

    // Exception 2
    reset(restTemplate, response);
    when(restTemplate.getForEntity(any(String.class), any(Class.class))).thenThrow(
        new ResourceAccessException(null) {
          public Throwable getCause() {
            AirlineException exp = new AirlineException();
            exp.setMessage("message1");
            return exp;
          }
        });

    try {
      airlinedaoimpl.listAvailableTickets();
    } catch (Exception e) {
      assertTrue(((AirlineException) e).getMessage().equals("message1"));
    }

  }



  @Test
  public void testListAllAccounts() {
    airlinedaoimpl.setRestServiceURI("dummyURL");

    // No Exception
    when(restTemplate.getForEntity(any(String.class), any(Class.class))).thenReturn(response);
    when(response.getBody()).thenReturn("ValueString");
    try {
      airlinedaoimpl.listAllAccounts();
    } catch (Exception ex) {
      System.out.println(ex.getClass());
    }

    try {
      Mockito.verify(mapper, times(1)).readValue(any(String.class), any(Class.class));
    } catch (Exception ex) {
    }


    // Exception 1
    reset(restTemplate, response);
    when(restTemplate.getForEntity(any(String.class), any(Class.class))).thenThrow(
        new HttpClientErrorException(HttpStatus.BAD_GATEWAY, "listAllAccounts"));
    try {
      airlinedaoimpl.listAllAccounts();
    } catch (Exception e) {
      assertTrue(((HttpClientErrorException) e).getStatusCode().equals(HttpStatus.BAD_GATEWAY));
      assertTrue(((HttpClientErrorException) e).getStatusText().equals("listAllAccounts"));
    }

    // Exception 2
    reset(restTemplate, response);
    when(restTemplate.getForEntity(any(String.class), any(Class.class))).thenThrow(
        new ResourceAccessException(null) {
          public Throwable getCause() {
            AirlineException exp = new AirlineException();
            exp.setMessage("message1");
            return exp;
          }
        });

    try {
      airlinedaoimpl.listAllAccounts();
    } catch (Exception e) {
      assertTrue(((AirlineException) e).getMessage().equals("message1"));
    }

  }


  @Test
  public void testCreateNewAccount() {
    airlinedaoimpl.setRestServiceURI("dummyURL");
    CreateAccountRequest request = new CreateAccountRequest();
    // No Exception
    when(restTemplate.postForEntity(any(String.class), any(HttpEntity.class), any(Class.class)))
        .thenReturn(response);
    when(response.getBody()).thenReturn("ValueString");
    try {
      airlinedaoimpl.createNewAccount(request);
    } catch (Exception ex) {
      System.out.println(ex.getClass());
    }

    try {
      verify(response, times(1)).getBody();
      verify(restTemplate, times(1)).postForEntity(any(String.class), any(HttpEntity.class),
          any(Class.class));
      verify(mapper, times(1)).writeValueAsString(any(CreateAccountRequest.class));
      verify(mapper, times(1)).readValue(any(String.class), any(Class.class));
    } catch (Exception ex) {
    }


    // Exception 1
    reset(restTemplate, response, mapper);
    when(restTemplate.postForEntity(any(String.class), any(HttpEntity.class), any(Class.class)))
        .thenThrow(new HttpClientErrorException(HttpStatus.BAD_GATEWAY, "createAccount"));
    try {
      airlinedaoimpl.createNewAccount(request);
    } catch (Exception ex) {
      System.out.println(ex.getClass());
    }

    try {
      verify(response, times(0)).getBody();
      verify(restTemplate, times(1)).postForEntity(any(String.class), any(HttpEntity.class),
          any(Class.class));
      verify(mapper, times(1)).writeValueAsString(any(CreateAccountRequest.class));
      verify(mapper, times(0)).readValue(any(String.class), any(Class.class));
    } catch (Exception ex) {
    }


    // Exception 2
    reset(restTemplate, response, mapper);
    when(restTemplate.postForEntity(any(String.class), any(HttpEntity.class), any(Class.class)))
        .thenThrow(new ResourceAccessException(null) {
          public Throwable getCause() {
            AirlineException exp = new AirlineException();
            exp.setMessage("message1");
            return exp;
          }
        });

    try {
      airlinedaoimpl.createNewAccount(request);
    } catch (Exception e) {
      assertTrue(((AirlineException) e).getMessage().equals("message1"));
    }

  }


  @Test
  public void testWithDrawMoney() {
    airlinedaoimpl.setRestServiceURI("dummyURL");
    DepositWithDrawRequest request = new DepositWithDrawRequest();

    // No Exception
    when(restTemplate.postForEntity(any(String.class), any(HttpEntity.class), any(Class.class)))
        .thenReturn(response);
    when(response.getBody()).thenReturn("withDrawMoney");
    try {
      airlinedaoimpl.withDrawMoney(request);
    } catch (Exception ex) {
      System.out.println(ex.getClass());
    }

    try {
      verify(response, times(1)).getBody();
      verify(restTemplate, times(1)).postForEntity(any(String.class), any(HttpEntity.class),
          any(Class.class));
      verify(mapper, times(1)).writeValueAsString(any(CreateAccountRequest.class));
      verify(mapper, times(1)).readValue(any(String.class), any(Class.class));
    } catch (Exception ex) {
    }


    // Exception 1
    reset(restTemplate, response, mapper);
    when(restTemplate.postForEntity(any(String.class), any(HttpEntity.class), any(Class.class)))
        .thenThrow(new HttpClientErrorException(HttpStatus.BAD_GATEWAY, "withDrawMoney"));
    try {
      airlinedaoimpl.withDrawMoney(request);
    } catch (Exception ex) {
      System.out.println(ex.getClass());
    }

    try {
      verify(response, times(0)).getBody();
      verify(restTemplate, times(1)).postForEntity(any(String.class), any(HttpEntity.class),
          any(Class.class));
      verify(mapper, times(1)).writeValueAsString(any(CreateAccountRequest.class));
      verify(mapper, times(0)).readValue(any(String.class), any(Class.class));
    } catch (Exception ex) {
    }


    // Exception 2
    reset(restTemplate, response, mapper);
    when(restTemplate.postForEntity(any(String.class), any(HttpEntity.class), any(Class.class)))
        .thenThrow(new ResourceAccessException(null) {
          public Throwable getCause() {
            AirlineException exp = new AirlineException();
            exp.setMessage("message1");
            return exp;
          }
        });

    try {
      airlinedaoimpl.withDrawMoney(request);
    } catch (Exception e) {
      assertTrue(((AirlineException) e).getMessage().equals("message1"));
    }

  }


  @Test
  public void testDepositMoney() {
    airlinedaoimpl.setRestServiceURI("dummyURL");
    DepositWithDrawRequest request = new DepositWithDrawRequest();

    // No Exception
    when(restTemplate.postForEntity(any(String.class), any(HttpEntity.class), any(Class.class)))
        .thenReturn(response);
    when(response.getBody()).thenReturn("depositMoney");
    try {
      airlinedaoimpl.depositMoney(request);
    } catch (Exception ex) {
      System.out.println(ex.getClass());
    }

    try {
      verify(response, times(1)).getBody();
      verify(restTemplate, times(1)).postForEntity(any(String.class), any(HttpEntity.class),
          any(Class.class));
      verify(mapper, times(1)).writeValueAsString(any(CreateAccountRequest.class));
      verify(mapper, times(1)).readValue(any(String.class), any(Class.class));
    } catch (Exception ex) {
    }


    // Exception 1
    reset(restTemplate, response, mapper);
    when(restTemplate.postForEntity(any(String.class), any(HttpEntity.class), any(Class.class)))
        .thenThrow(new HttpClientErrorException(HttpStatus.BAD_GATEWAY, "depositMoney"));
    try {
      airlinedaoimpl.depositMoney(request);
    } catch (Exception ex) {
      System.out.println(ex.getClass());
    }

    try {
      verify(response, times(0)).getBody();
      verify(restTemplate, times(1)).postForEntity(any(String.class), any(HttpEntity.class),
          any(Class.class));
      verify(mapper, times(1)).writeValueAsString(any(CreateAccountRequest.class));
      verify(mapper, times(0)).readValue(any(String.class), any(Class.class));
    } catch (Exception ex) {
    }

    // Exception 2
    reset(restTemplate, response, mapper);
    when(restTemplate.postForEntity(any(String.class), any(HttpEntity.class), any(Class.class)))
        .thenThrow(new ResourceAccessException(null) {
          public Throwable getCause() {
            AirlineException exp = new AirlineException();
            exp.setMessage("message1");
            return exp;
          }
        });

    try {
      airlinedaoimpl.depositMoney(request);
    } catch (Exception e) {
      assertTrue(((AirlineException) e).getMessage().equals("message1"));
    }
  }


  @Test
  public void testExchangeCurrency() {
    airlinedaoimpl.setRestServiceURI("dummyURL");
    ExchangeCurrenciesRequest request = new ExchangeCurrenciesRequest();

    // No Exception
    when(restTemplate.postForEntity(any(String.class), any(HttpEntity.class), any(Class.class)))
        .thenReturn(response);
    when(response.getBody()).thenReturn("depositMoney");
    try {
      airlinedaoimpl.exchangeCurrency(request);
    } catch (Exception ex) {
      System.out.println(ex.getClass());
    }

    try {
      verify(response, times(1)).getBody();
      verify(restTemplate, times(1)).postForEntity(any(String.class), any(HttpEntity.class),
          any(Class.class));
      verify(mapper, times(1)).writeValueAsString(any(CreateAccountRequest.class));
      verify(mapper, times(1)).readValue(any(String.class), any(Class.class));
    } catch (Exception ex) {
    }


    // Exception 1
    reset(restTemplate, response, mapper);
    when(restTemplate.postForEntity(any(String.class), any(HttpEntity.class), any(Class.class)))
        .thenThrow(new HttpClientErrorException(HttpStatus.BAD_GATEWAY, "depositMoney"));
    try {
      airlinedaoimpl.exchangeCurrency(request);
    } catch (Exception ex) {
      System.out.println(ex.getClass());
    }

    try {
      verify(response, times(0)).getBody();
      verify(restTemplate, times(1)).postForEntity(any(String.class), any(HttpEntity.class),
          any(Class.class));
      verify(mapper, times(1)).writeValueAsString(any(CreateAccountRequest.class));
      verify(mapper, times(0)).readValue(any(String.class), any(Class.class));
    } catch (Exception ex) {
    }

    // Exception 2
    reset(restTemplate, response, mapper);
    when(restTemplate.postForEntity(any(String.class), any(HttpEntity.class), any(Class.class)))
        .thenThrow(new ResourceAccessException(null) {
          public Throwable getCause() {
            AirlineException exp = new AirlineException();
            exp.setMessage("message1");
            return exp;
          }
        });

    try {
      airlinedaoimpl.exchangeCurrency(request);
    } catch (Exception e) {
      assertTrue(((AirlineException) e).getMessage().equals("message1"));
    }
  }


  @Test
  public void testBuyAirlineTicket() {
    airlinedaoimpl.setRestServiceURI("dummyURL");
    BuyTicketRequest request = new BuyTicketRequest();

    // No Exception
    when(restTemplate.postForEntity(any(String.class), any(HttpEntity.class), any(Class.class)))
        .thenReturn(response);
    when(response.getBody()).thenReturn("depositMoney");
    try {
      airlinedaoimpl.buyAirlineTicket(request);
    } catch (Exception ex) {
      System.out.println(ex.getClass());
    }

    try {
      verify(response, times(1)).getBody();
      verify(restTemplate, times(1)).postForEntity(any(String.class), any(HttpEntity.class),
          any(Class.class));
      verify(mapper, times(1)).writeValueAsString(any(CreateAccountRequest.class));
      verify(mapper, times(1)).readValue(any(String.class), any(Class.class));
    } catch (Exception ex) {
    }


    // Exception 1
    reset(restTemplate, response, mapper);
    when(restTemplate.postForEntity(any(String.class), any(HttpEntity.class), any(Class.class)))
        .thenThrow(new HttpClientErrorException(HttpStatus.BAD_GATEWAY, "depositMoney"));
    try {
      airlinedaoimpl.buyAirlineTicket(request);
    } catch (Exception ex) {
      System.out.println(ex.getClass());
    }

    try {
      verify(response, times(0)).getBody();
      verify(restTemplate, times(1)).postForEntity(any(String.class), any(HttpEntity.class),
          any(Class.class));
      verify(mapper, times(1)).writeValueAsString(any(CreateAccountRequest.class));
      verify(mapper, times(0)).readValue(any(String.class), any(Class.class));
    } catch (Exception ex) {
    }

    // Exception 2
    reset(restTemplate, response, mapper);
    when(restTemplate.postForEntity(any(String.class), any(HttpEntity.class), any(Class.class)))
        .thenThrow(new ResourceAccessException(null) {
          public Throwable getCause() {
            AirlineException exp = new AirlineException();
            exp.setMessage("message1");
            return exp;
          }
        });

    try {
      airlinedaoimpl.buyAirlineTicket(request);
    } catch (Exception e) {
      assertTrue(((AirlineException) e).getMessage().equals("message1"));
    }
  }

}
