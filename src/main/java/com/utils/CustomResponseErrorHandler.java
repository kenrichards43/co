package com.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

import com.model.AirlineException;

public class CustomResponseErrorHandler implements ResponseErrorHandler {
  private ResponseErrorHandler errorHandler = new DefaultResponseErrorHandler();

  public boolean hasError(ClientHttpResponse response) throws IOException {
    return errorHandler.hasError(response);
  }

  public void handleError(ClientHttpResponse response) throws IOException {
    AirlineException airlineException = new AirlineException();
    String theString = IOUtils.toString(response.getBody());

    Map<String, Object> properties = new HashMap<String, Object>();
    properties.put("code", response.getStatusCode().toString());
    properties.put("body", theString);
    properties.put("header", response.getHeaders());

    airlineException.setMessage(theString);
    airlineException.setMap(properties);
    throw airlineException;
  }

}
