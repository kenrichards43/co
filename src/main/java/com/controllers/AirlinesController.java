package com.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.json.JSONArray;
import org.mockito.internal.util.collections.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.AirlineException;
import com.model.AirlineRoute;
import com.model.AirlineTicket;
import com.model.BuyTicketRequest;
import com.model.CreateAccountRequest;
import com.model.Currency;
import com.model.DepositWithDrawRequest;
import com.model.MonetaryAmount;
import com.model.User;
import com.model.UserOrder;
import com.services.AirlineService;
import com.services.EmailPdfService;
import com.services.UserService;
import com.utils.Constants;
import com.validators.LoginValidator;
import com.validators.RegistrationValidator;

/**
 * @author abcd
 * 
 */
@Controller
public class AirlinesController {

  @Autowired
  public UserService userService;

  @Autowired(required = true)
  @Qualifier(value = "userService")
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  @Autowired
  public AirlineService airlineService;

  @Autowired(required = true)
  @Qualifier(value = "airlineService")
  public void setAirlineService(AirlineService airlineService) {
    this.airlineService = airlineService;
  }

  @Autowired
  public EmailPdfService emailPdfService;

  @Autowired(required = true)
  @Qualifier(value = "emailPdfService")
  public void setEmailPdfService(EmailPdfService emailPdfService) {
    this.emailPdfService = emailPdfService;
  }

  @Autowired
  public RegistrationValidator registrationValidator;

  public void setRegistrationValidator(RegistrationValidator registrationValidator) {
    this.registrationValidator = registrationValidator;
  }

  @Autowired
  public LoginValidator loginValidator;

  public void setLoginValidator(LoginValidator loginValidator) {
    this.loginValidator = loginValidator;
  }

  @RequestMapping(value = "/getRegistrationPage", method = RequestMethod.GET)
  public String getRegistrationPage(ModelMap model) {
    model.addAttribute("user", new User());
    return "registrationPage";
  }


  @RequestMapping(value = "/getAdminDashBoard", method = RequestMethod.GET)
  public String getAdminDashBoard(ModelMap model) {
    return "adminDashBoard";
  }


  @RequestMapping(value = "/adminUsersPage", method = RequestMethod.GET)
  public String getAdminUsersPage(ModelMap model) {
    return "adminUsers";
  }


  @RequestMapping(value = "/adminOrdersPage", method = RequestMethod.GET)
  public String getAdminOrdersPage(ModelMap model) {
    return "adminOrders";
  }

  @RequestMapping(value = "/listAllUsers", method = RequestMethod.GET)
  public void listAllUsers(HttpServletResponse resp) {
    List<User> users = userService.listAllUsers();
    ObjectMapper mapper = new ObjectMapper();
    try {
      String value = mapper.writeValueAsString(users);
      resp.getWriter().write(value);
      resp.getWriter().flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @RequestMapping(value = "/getUserbyUserName", method = RequestMethod.GET)
  public void getUserbyUserName(@RequestParam("userName") String userName, HttpServletResponse resp) {
    User user = new User();
    user.setUserName(userName);
    List<User> users = userService.isExistingUser(user);
    ObjectMapper mapper = new ObjectMapper();
    try {
      String value = mapper.writeValueAsString(users);
      resp.getWriter().write(value);
      resp.getWriter().flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @RequestMapping(value = "/registration", method = RequestMethod.POST)
  public String register(@Valid User user, BindingResult result, ModelMap model, HttpServletRequest request) {
    registrationValidator.validate(user, result);
    if (result.hasErrors()) {
      return "registrationPage";
    } else {

      try {
        airlineService.reset();
      } catch (Exception e) {
        e.printStackTrace();
      }
      // Set Rest User Name here.
      user.setRestUserName(Constants.REST_USER_NAME);
      userService.saveUser(user);
      return "redirect:/loginPage";
    }
  }

  @RequestMapping(value = "/loginPage", method = RequestMethod.GET)
  public String getLoginPage(@Valid User user, BindingResult result, ModelMap model) {
    model.addAttribute("user", new User());
    return "login";
  }


  // Tests done above here so far.
  @RequestMapping(value = "/dashboardthroughlogin", method = RequestMethod.POST)
  public String getDashBoardPageThroughLogin(HttpServletRequest request, @Valid User user, BindingResult result, ModelMap model) {
    loginValidator.validate(user, result);
    if (result.hasErrors()) {
      return "login";
    } else {
      
      user.setRestUserName(Constants.REST_USER_NAME);
      request.getSession().setAttribute("user", user);
      
      return "dashboard";
    }
  }

  // Rest Web Services related.
  @RequestMapping(value = "/dashboardPage", method = RequestMethod.GET)
  public String getDashBoardPage() {
    return "dashboard";
  }


  @RequestMapping(value = "/viewairlinesTicketsPurchased", method = RequestMethod.GET)
  public String viewairlinesTicketsPurchased(ModelMap model, HttpServletRequest request) {
    try {
      AirlineTicket[] listAvailableTickets = airlineService.listAvailableTickets();
      model.addAttribute("availableTickets", listAvailableTickets);
      String downloadPath =
          request.getSession().getServletContext()
          .getRealPath(Constants.DOWNLOADS_PATH + Constants.PDF_FILE_NAME);
      emailPdfService.createPdfDocument(downloadPath, listAvailableTickets);
    } catch (Exception e) {
    }
    return "ticketsPurchased";
  }
  
  @RequestMapping(value = "/viewTicketsPurchasedByallUsers", method = RequestMethod.GET)
  public void viewTicketsPurchasedByallUsers(HttpServletResponse resp) {
    try {
      List<UserOrder> userOrders = userService.listAllOrders();
      ObjectMapper mapper = new ObjectMapper();
      String value = mapper.writeValueAsString(userOrders);
      resp.getWriter().write(value);
      resp.getWriter().flush();
    } catch (Exception e) {
    }
  }
  
  @RequestMapping(value = "/viewTicketsPurchasedByOrder", method = RequestMethod.GET)
  public void viewTicketsPurchasedByOrder(HttpServletResponse resp, 
      @RequestParam("userName") String userName,
      @RequestParam("from") String from,
      @RequestParam("to") String to ) {
    
    try {
      List<UserOrder> userOrders = userService.getOrderBy(userName, from, to);
      ObjectMapper mapper = new ObjectMapper();
      String value = mapper.writeValueAsString(userOrders);
      resp.getWriter().write(value);
      resp.getWriter().flush();
    } catch (Exception e) {
    }
  }

  
  @RequestMapping(value = "/viewairlineofferspurchasetickets", method = RequestMethod.GET)
  public String viewAirlinesOffersPurchaseTickets(ModelMap model) {
    try {
      model.addAttribute("airlineOffers", airlineService.listAvailableOffers());
    } catch (Exception e) {
    }
    return "purchaseTickets";
  }

  @RequestMapping(value = "/opennewaccount", method = RequestMethod.GET)
  public String openaNewAccount(ModelMap model) {
    try {
      model.addAttribute("availableAccounts", airlineService.listAllAccounts());
    } catch (Exception e) {
    }
    return "openNewAccount";
  }

  @RequestMapping(value = "/getAllAccounts", method = RequestMethod.GET)
  public void getAllAccounts(ModelMap model, HttpServletResponse resp) {
    ObjectMapper mapper = new ObjectMapper();
    String value = null;
    try {
      model.addAttribute("availableAccounts", airlineService.listAllAccounts());
      value = mapper.writeValueAsString(model.get("availableAccounts"));

    } catch (Exception e) {
      if (e instanceof AirlineException) {
        value = (String) ((AirlineException) e).getMap().get("body");
      }
    }
    try {
      resp.getWriter().write(value);
      resp.getWriter().flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @RequestMapping(value = "/opennewaccount2", method = RequestMethod.POST)
  public void openaNewAccount2(@RequestParam("currency") String currency, HttpServletResponse resp) {
    ObjectMapper mapper = new ObjectMapper();
    String value = null;
    try {
      CreateAccountRequest request = new CreateAccountRequest();
      request.setCurrency(Currency.valueOf(currency.toUpperCase()));
      value = mapper.writeValueAsString(airlineService.createNewAccount(request));
    } catch (Exception e) {
      if (e instanceof AirlineException) {
        value = (String) ((AirlineException) e).getMap().get("body");
      }
    }

    resp.setContentType("application/JSON");
    try {
      resp.getWriter().write(value);
      resp.getWriter().flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  @RequestMapping(value = "/withdrawDeposit", method = RequestMethod.POST)
  public void withDrawDepositMoney(@RequestParam("amount") String amount,
      @RequestParam("withdrawDeposit") String withdrawDeposit,
      @RequestParam("currency") String currency, @RequestParam("accountId") String accountId,
      HttpServletResponse resp) {
    ObjectMapper mapper = new ObjectMapper();
    String value = null;
    try {
      DepositWithDrawRequest request = new DepositWithDrawRequest();
      request.setAccountId(accountId);
      MonetaryAmount monetaryAmount = new MonetaryAmount();
      int amt = Integer.parseInt(amount);
      monetaryAmount.setAmount(amt);
      monetaryAmount.setCurrency(Currency.valueOf(currency.toUpperCase()));
      request.setMonetaryAmount(monetaryAmount);

      if (withdrawDeposit.equals("withdraw")) {
        value = mapper.writeValueAsString(airlineService.withDrawMoney(request));
      } else {
        value = mapper.writeValueAsString(airlineService.depositMoney(request));
      }
    } catch (Exception e) {
      if (e instanceof AirlineException) {
        value = (String) ((AirlineException) e).getMap().get("body");
      }
    }
    resp.setContentType("application/JSON");
    try {
      resp.getWriter().write(value);
      resp.getWriter().flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @RequestMapping(value = "/purchaseTickets", method = RequestMethod.POST)
  public void purchaseTickets(HttpServletRequest req, @RequestParam("amount") String amount,
      @RequestParam("accountId") String accountId, @RequestParam("from") String from,
      @RequestParam("to") String to, HttpServletResponse resp) {
    ObjectMapper mapper = new ObjectMapper();
    String value = null;
    try {
      BuyTicketRequest request = new BuyTicketRequest();
      request.setAccountId(accountId);
      request.setAmount(Integer.parseInt(amount));
      AirlineRoute route = new AirlineRoute();
      route.setFrom(from);
      route.setTo(to);
      request.setRoute(route);
      value = mapper.writeValueAsString(airlineService.buyAirlineTicket(request));
      
      User user = (User)req.getSession().getAttribute("user");
      UserOrder order = new UserOrder();
      order.setUserName(user.getUserName());
      order.setRestUserName(user.getRestUserName());
      order.setAccountId(accountId);
      order.setFrom(from);
      order.setTo(to);
      order.setAmount(Integer.parseInt(amount));
      userService.saveOrder(order);

    } catch (Exception e) {
      if (e instanceof AirlineException) {
        value = (String) ((AirlineException) e).getMap().get("body");
      }
    }

    resp.setContentType("application/JSON");
    try {
      resp.getWriter().write(value);
      resp.getWriter().flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @RequestMapping(value = "/emailPdfDoc", method = RequestMethod.GET)
  public String emailPDFResource(@Valid final User user, final HttpServletRequest request,
      HttpServletResponse response) {
    emailPdfService.sendPdfEmail(request, user.getUserName());
    return "emailPdf";
  }

  @RequestMapping("/downloadPdf")
  public void downloadPDFResource(HttpServletRequest request, HttpServletResponse response) {
    String dataDirectory =
        request.getSession().getServletContext().getRealPath(Constants.DOWNLOADS_PATH);
    Path file = Paths.get(dataDirectory, Constants.PDF_FILE_NAME);
    if (Files.exists(file)) {
      response.setContentType("application/pdf");
      response.addHeader("Content-Disposition", "attachment; filename=" + Constants.PDF_FILE_NAME);
      try {
        Files.copy(file, response.getOutputStream());
        response.getOutputStream().flush();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }

}
