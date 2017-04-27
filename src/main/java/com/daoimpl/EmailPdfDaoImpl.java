package com.daoimpl;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import com.dao.EmailPdfDao;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.model.AirlineTicket;
import com.utils.Constants;

public class EmailPdfDaoImpl implements EmailPdfDao {


  @Autowired
  private JavaMailSenderImpl mailSender;


  public void setMailSender(JavaMailSenderImpl mailSender) {
    this.mailSender = mailSender;
  }

  public void createPdfDocument(String FILE, AirlineTicket[] listAvailableTickets) {
    try {

      File testFile = new File(FILE);
      if (!testFile.exists()) {
        testFile.getParentFile().mkdirs();
        testFile.createNewFile();
      }
      Document document = new Document();
      PdfWriter.getInstance(document, new FileOutputStream(FILE));
      document.open();

      Paragraph preface = new Paragraph();
      preface.add(new Paragraph(" "));
      preface.add(new Paragraph(" "));
      Paragraph paragraph = new Paragraph("GAMMA AIRLINES TICKETS LIST (" + new Date() + " )");
      paragraph.setAlignment(Element.ALIGN_CENTER);
      preface.add(paragraph);
      preface.add(new Paragraph(" "));
      preface.add(new Paragraph(" "));
      document.add(preface);

      PdfPTable table = new PdfPTable(3);
      PdfPCell c1 = new PdfPCell(new Phrase("FROM"));
      c1.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(c1);
      c1 = new PdfPCell(new Phrase("TO"));
      c1.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(c1);
      c1 = new PdfPCell(new Phrase("# TICKETS"));
      c1.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(c1);
      table.setHeaderRows(1);

      if (listAvailableTickets != null && listAvailableTickets.length > 0) {
        for (AirlineTicket ticket : listAvailableTickets) {

          PdfPCell p1 = new PdfPCell(new Phrase(ticket.getDetails().getRoute().getFrom()));
          p1.setHorizontalAlignment(Element.ALIGN_CENTER);
          table.addCell(p1);

          PdfPCell p2 = new PdfPCell(new Phrase(ticket.getDetails().getRoute().getTo()));
          p2.setHorizontalAlignment(Element.ALIGN_CENTER);
          table.addCell(p2);

          PdfPCell p3 = new PdfPCell(new Phrase("" + ticket.getAmount()));
          p3.setHorizontalAlignment(Element.ALIGN_CENTER);
          table.addCell(p3);

        }
      }
      document.add(table);
      document.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void sendPdfEmail(final HttpServletRequest request, final String to) {
    mailSender.send(new MimeMessagePreparator() {
      public void prepare(MimeMessage mimeMessage) throws javax.mail.MessagingException {
        String dataDirectory =
            request.getSession().getServletContext().getRealPath(Constants.DOWNLOADS_PATH);
        Path file = Paths.get(dataDirectory, Constants.PDF_FILE_NAME);
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, Constants.UTF_8);
        message.setFrom(Constants.FROM_EMAIL);
        message.setTo(to);
        message.setSubject(Constants.MESSAGE_SUBJECT);
        message.setText(Constants.MESSAGE_TEXT, true);
        message.addAttachment(Constants.PDF_FILE_NAME, file.toFile());
      }
    });
  }

}
