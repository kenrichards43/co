package com.services;

import javax.servlet.http.HttpServletRequest;

import com.model.AirlineTicket;

public interface EmailPdfService {

  public void createPdfDocument(String path, AirlineTicket[] tickets);

  public void sendPdfEmail(HttpServletRequest request, String to);

}
