package com.daoimpl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Account;
import com.model.AirlineException;
import com.model.AirlineRoute;
import com.model.AirlineTicket;
import com.model.BuyTicketRequest;
import com.utils.Constants;
import com.utils.CustomResponseErrorHandler;

public class AirlineDaoImplIntegrationTest {


  private RestTemplate restTemplate = new RestTemplate();
  private String REST_SERVICE_URI = "https://api-forest.crossover.com/" + Constants.REST_USER_NAME;
  private ResponseEntity<String> response = null;
  private ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testPurchaseTickets() throws Exception {
    // Resetting the account.
    restTemplate.getForEntity(REST_SERVICE_URI + "/reset", String.class);



    // Retrieve account id for the existing account to purchase new tickets.
    restTemplate.setErrorHandler(new CustomResponseErrorHandler());
    try {
      response = restTemplate.getForEntity(REST_SERVICE_URI + "/paypallets/accounts", String.class);
    } catch (HttpClientErrorException ex) {
      System.out.println("AirlineException Occured in the listAvailable Accounts Section..");
      throw ex;
    } catch (ResourceAccessException ex) {
      throw (AirlineException) ex.getCause();
    }
    Account[] accounts = mapper.readValue(response.getBody(), Account[].class);



    // Purchase two tickets from London to Madrid.
    BuyTicketRequest request = new BuyTicketRequest();
    request.setAmount(2);
    request.setAccountId(accounts[0].getId());
    AirlineRoute airlineRoute = new AirlineRoute();
    airlineRoute.setFrom("London");
    airlineRoute.setTo("Madrid");
    request.setRoute(airlineRoute);

    restTemplate.setErrorHandler(new CustomResponseErrorHandler());
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      HttpEntity<String> entity =
          new HttpEntity<String>(mapper.writeValueAsString(request), headers);

      response =
          restTemplate.postForEntity(REST_SERVICE_URI + "/gammaairlines/tickets/buy", entity,
              String.class);
    } catch (HttpClientErrorException ex) {
      System.out.println("AirlineException buyAirlineTicket");
      throw ex;
    } catch (ResourceAccessException ex) {
      throw (AirlineException) ex.getCause();
    }


    // Verify if there are two tickets purchased from the account

    restTemplate.setErrorHandler(new CustomResponseErrorHandler());
    try {
      response =
          restTemplate.getForEntity(REST_SERVICE_URI + "/gammaairlines/tickets", String.class);
    } catch (HttpClientErrorException ex) {
      System.out.println("AirlineException Occured in the listAvailable Tickets Section..");
      throw ex;
    } catch (ResourceAccessException ex) {
      throw (AirlineException) ex.getCause();
    }

    AirlineTicket[] tickets = mapper.readValue(response.getBody(), AirlineTicket[].class);

    // End of interacting with the rest service.
    assertTrue(tickets.length == 1);
    assertTrue(tickets[0].getAmount() == 2);
    assertTrue(tickets[0].getDetails().getRoute().getFrom().equals("London"));
    assertTrue(tickets[0].getDetails().getRoute().getTo().equals("Madrid"));


  }


}
