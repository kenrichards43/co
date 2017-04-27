package com.daoimpl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.dao.AirlineDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Account;
import com.model.AirlineException;
import com.model.AirlineOffer;
import com.model.AirlineTicket;
import com.model.BuyTicketRequest;
import com.model.CreateAccountRequest;
import com.model.DepositWithDrawRequest;
import com.model.ExchangeCurrenciesRequest;
import com.model.MonetaryAmount;
import com.utils.Constants;
import com.utils.CustomResponseErrorHandler;


public class AirlineDaoImpl implements AirlineDao {

  private String restUserName = Constants.REST_USER_NAME;
  private String restServiceURI = "https://api-forest.crossover.com/";
  private RestTemplate restTemplate = new RestTemplate();
  private ObjectMapper mapper = new ObjectMapper();
  private ResponseEntity<String> response = null;

  public void setRestTemplate(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public void setMapper(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  public void setResponse(ResponseEntity<String> response) {
    this.response = response;
  }
  
  public String getRestServiceURI() {
    return restServiceURI + getRestUserName();
  }

  public void setRestServiceURI(String restServiceURI) {
    this.restServiceURI = restServiceURI;
  }

  public String getRestUserName() {
    return restUserName;
  }

  public void setRestUserName(String restUserName) {
    this.restUserName = restUserName;
  }

  public void reset() throws Exception {
    restTemplate.setErrorHandler(new CustomResponseErrorHandler());
    try {
      response = restTemplate.getForEntity(getRestServiceURI() + "/reset", String.class);
    } catch (HttpClientErrorException ex) {
      System.out.println("Reset account occured");
      throw ex;
    } catch (ResourceAccessException ex) {
      throw (AirlineException) ex.getCause();
    }

  }

  public AirlineOffer[] listAvailableOffers() throws Exception {
    restTemplate.setErrorHandler(new CustomResponseErrorHandler());
    try {
      response =
          restTemplate.getForEntity(getRestServiceURI() + "/gammaairlines/offers", String.class);
    } catch (HttpClientErrorException ex) {
      System.out.println("AirlineException Occured in the listAvailable Offers Section..");
      throw ex;
    } catch (ResourceAccessException ex) {
      throw (AirlineException) ex.getCause();
    }

    return mapper.readValue(response.getBody(), AirlineOffer[].class);
  }

  public AirlineTicket[] listAvailableTickets() throws Exception {
    restTemplate.setErrorHandler(new CustomResponseErrorHandler());
    try {
      response =
          restTemplate.getForEntity(getRestServiceURI() + "/gammaairlines/tickets", String.class);
    } catch (HttpClientErrorException ex) {
      System.out.println("AirlineException Occured in the listAvailable Tickets Section..");
      throw ex;
    } catch (ResourceAccessException ex) {
      throw (AirlineException) ex.getCause();
    }

    return mapper.readValue(response.getBody(), AirlineTicket[].class);
  }

  public Account[] listAllAccounts() throws Exception {
    restTemplate.setErrorHandler(new CustomResponseErrorHandler());
    try {
      response = restTemplate.getForEntity(getRestServiceURI() + "/paypallets/accounts", String.class);
    } catch (HttpClientErrorException ex) {
      System.out.println("AirlineException Occured in the listAvailable Accounts Section..");
      throw ex;
    } catch (ResourceAccessException ex) {
      throw (AirlineException) ex.getCause();
    }

    return mapper.readValue(response.getBody(), Account[].class);
  }


  public Account createNewAccount(CreateAccountRequest createAccount) throws Exception {
    restTemplate.setErrorHandler(new CustomResponseErrorHandler());
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      HttpEntity<String> request =
          new HttpEntity<String>(mapper.writeValueAsString(createAccount), headers);

      response =
          restTemplate.postForEntity(getRestServiceURI() + "/paypallets/account", request,
              String.class);
    } catch (HttpClientErrorException ex) {
      System.out.println("Exception Occured in creating account");
      throw ex;
    } catch (ResourceAccessException ex) {
      throw (AirlineException) ex.getCause();
    }

    return mapper.readValue(response.getBody(), Account.class);
  }

  public Account withDrawMoney(DepositWithDrawRequest deposit) throws Exception {
    restTemplate.setErrorHandler(new CustomResponseErrorHandler());
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      HttpEntity<String> request =
          new HttpEntity<String>(mapper.writeValueAsString(deposit), headers);

      response =
          restTemplate.postForEntity(getRestServiceURI() + "/paypallets/account/withdraw", request,
              String.class);
    } catch (HttpClientErrorException ex) {
      System.out.println("AirlineException withDrawMoneyException");
      throw ex;
    } catch (ResourceAccessException ex) {
      throw (AirlineException) ex.getCause();
    }

    return mapper.readValue(response.getBody(), Account.class);
  }


  public Account depositMoney(DepositWithDrawRequest deposit) throws Exception {
    restTemplate.setErrorHandler(new CustomResponseErrorHandler());
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      HttpEntity<String> request =
          new HttpEntity<String>(mapper.writeValueAsString(deposit), headers);

      response =
          restTemplate.postForEntity(getRestServiceURI() + "/paypallets/account/deposit", request,
              String.class);
    } catch (HttpClientErrorException ex) {
      System.out.println("AirlineException DepositMoney");
      throw ex;
    } catch (ResourceAccessException ex) {
      throw (AirlineException) ex.getCause();
    }

    return mapper.readValue(response.getBody(), Account.class);
  }


  public MonetaryAmount exchangeCurrency(ExchangeCurrenciesRequest request) throws Exception {
    restTemplate.setErrorHandler(new CustomResponseErrorHandler());
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      HttpEntity<String> entity =
          new HttpEntity<String>(mapper.writeValueAsString(request), headers);

      response =
          restTemplate.postForEntity(getRestServiceURI() + "/moneyexchange/exchange", entity,
              String.class);
    } catch (HttpClientErrorException ex) {
      System.out.println("AirlineException exchangeCurrency");
      throw ex;
    } catch (ResourceAccessException ex) {
      throw (AirlineException) ex.getCause();
    }

    return mapper.readValue(response.getBody(), MonetaryAmount.class);
  }


  public AirlineTicket buyAirlineTicket(BuyTicketRequest request) throws Exception {

    restTemplate.setErrorHandler(new CustomResponseErrorHandler());
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      HttpEntity<String> entity =
          new HttpEntity<String>(mapper.writeValueAsString(request), headers);

      response =
          restTemplate.postForEntity(getRestServiceURI() + "/gammaairlines/tickets/buy", entity,
              String.class);
    } catch (HttpClientErrorException ex) {
      System.out.println("AirlineException buyAirlineTicket");
      throw ex;
    } catch (ResourceAccessException ex) {
      throw (AirlineException) ex.getCause();
    }

    return mapper.readValue(response.getBody(), AirlineTicket.class);
  }


}
