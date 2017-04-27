package com.model;

import java.io.IOException;
import java.util.Map;

public class AirlineException extends IOException {

  private String message;
  private Map map;

  public AirlineException() {}

  public AirlineException(String message) {
    this.message = message;
  }

  public Map getMap() {
    return map;
  }

  public void setMap(Map map) {
    this.map = map;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
