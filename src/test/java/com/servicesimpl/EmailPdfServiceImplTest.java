package com.servicesimpl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import javax.servlet.http.HttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.dao.EmailPdfDao;
import com.model.AirlineTicket;

public class EmailPdfServiceImplTest {

  @Mock
  private EmailPdfDao emailPdfDao;
  
  @InjectMocks
  private EmailPdfServiceImpl emailPdfServiceImpl;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    emailPdfServiceImpl = new EmailPdfServiceImpl();
    emailPdfServiceImpl.setEmailPdfDao(emailPdfDao);
  }
  
  @Test
  public void testCreatePdfDocument(){
    emailPdfServiceImpl.createPdfDocument(any(String.class), any(AirlineTicket[].class));
    verify(emailPdfDao, times(1)).createPdfDocument(any(String.class), any(AirlineTicket[].class));
  }
  
  @Test
  public void testSendPdfEmail(){
    emailPdfServiceImpl.sendPdfEmail(any(HttpServletRequest.class), any(String.class));
    verify(emailPdfDao, times(1)).sendPdfEmail(any(HttpServletRequest.class), any(String.class));
  }
  
}
