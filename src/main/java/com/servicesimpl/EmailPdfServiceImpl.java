package com.servicesimpl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.dao.EmailPdfDao;
import com.model.AirlineTicket;
import com.services.EmailPdfService;

public class EmailPdfServiceImpl implements EmailPdfService {

  @Autowired
  private EmailPdfDao emailPdfDao;

  public void setEmailPdfDao(EmailPdfDao emailPdfDao) {
    this.emailPdfDao = emailPdfDao;
  }

  public void createPdfDocument(String path, AirlineTicket[] tickets) {
    emailPdfDao.createPdfDocument(path, tickets);
  }

  public void sendPdfEmail(HttpServletRequest request, String to) {
    emailPdfDao.sendPdfEmail(request, to);
  }


}
