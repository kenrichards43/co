package com.daoimpl;


import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import com.model.AirlineOffer;
import com.model.AirlineRoute;
import com.model.AirlineTicket;
import com.model.MonetaryAmount;
import com.utils.Constants;

public class EmailPdfDaoImplTest {

  private EmailPdfDaoImpl emailPdfDaoImpl = new EmailPdfDaoImpl();

  @Test
  public void testCreatePdfDocument() throws Exception {

    AirlineTicket ticket = new AirlineTicket();
    AirlineTicket tickets[] = {ticket};
    ticket.setAmount(2);
    AirlineOffer airlineOffer = new AirlineOffer();
    airlineOffer.setPrice(new MonetaryAmount());
    airlineOffer.setRoute(new AirlineRoute());
    ticket.setDetails(airlineOffer);
    // Need write and read permissions in the webapps directory of the tomcat.
    String file = Constants.DOWNLOADS_TEST_PATH + Constants.TEST_PDF_FILE_NAME;
    File testFile = new File(file);
    if (!testFile.exists()) {
      testFile.getParentFile().mkdirs();
      testFile.createNewFile();
    }

    long l1 = testFile.length();

    emailPdfDaoImpl.createPdfDocument(file, tickets);
    long l2 = testFile.length();

    assertTrue(testFile.exists());
    assertTrue(l2 - l1 > 0);
    if (testFile.exists()) {
      testFile.delete();
    }
  }



}
