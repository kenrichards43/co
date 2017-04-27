package com.controllers;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.model.AirlineException;
import com.model.AirlineTicket;
import com.model.BuyTicketRequest;
import com.model.CreateAccountRequest;
import com.model.DepositWithDrawRequest;
import com.model.User;
import com.model.UserOrder;
import com.services.AirlineService;
import com.services.EmailPdfService;
import com.services.UserService;
import com.validators.LoginValidator;
import com.validators.RegistrationValidator;

public class AirlinesControllerTest {
  @Mock
  private UserService userService;

  @Mock
  private RegistrationValidator registrationValidator;

  @Mock
  private LoginValidator loginValidator;

  @Mock
  private AirlineService airlineService;

  @Mock
  private EmailPdfService emailPdfService;

  @Mock
  @Autowired
  MockHttpSession session;

  @Mock
  @Autowired
  MockHttpServletRequest request;

  @Mock
  @Autowired
  MockHttpServletResponse response;

  @InjectMocks
  private AirlinesController airlinesController;


  private MockMvc mockMvc;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(airlinesController).build();
    airlinesController.setAirlineService(airlineService);
    airlinesController.setEmailPdfService(emailPdfService);
    airlinesController.setUserService(userService);

    when(registrationValidator.supports(any(Class.class))).thenReturn(true);
    when(loginValidator.supports(any(Class.class))).thenReturn(true);
  }

  @Test
  public void testGetRegistrationPage() throws Exception {
    this.mockMvc.perform(get("/getRegistrationPage").accept(MediaType.TEXT_PLAIN))
        .andExpect(status().isOk()).andExpect(view().name("registrationPage"))
        .andExpect(model().attributeExists("user"));
  }

  @Test
  public void testGetAdminDashBoard() throws Exception {
    this.mockMvc.perform(get("/getAdminDashBoard").accept(MediaType.TEXT_PLAIN))
        .andExpect(status().isOk()).andExpect(view().name("adminDashBoard"));
  }

  @Test
  public void testGetAdminUsersPage() throws Exception {
    this.mockMvc.perform(get("/adminUsersPage").accept(MediaType.TEXT_PLAIN))
        .andExpect(status().isOk()).andExpect(view().name("adminUsers"));
  }

  @Test
  public void testGetAdminOrdersPage() throws Exception {
    this.mockMvc.perform(get("/adminOrdersPage").accept(MediaType.TEXT_PLAIN))
        .andExpect(status().isOk()).andExpect(view().name("adminOrders"));
  }

  @Test
  public void testregister_hasErrors() throws Exception {
    doAnswer(new Answer() {
      public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
        Errors errors = (Errors) invocationOnMock.getArguments()[1];
        errors.reject("error");
        return null;
      }
    }).when(registrationValidator).validate(anyObject(), any(Errors.class));

    this.mockMvc.perform(post("/registration").accept(MediaType.TEXT_PLAIN))
        .andExpect(status().isOk()).andExpect(view().name("registrationPage"));

  }

  @Test
  public void testregister_hasNoErrors() throws Exception {
    doAnswer(new Answer() {
      public Object answer(InvocationOnMock invocation) throws Throwable {
        return null;
      }
    }).when(registrationValidator).validate(anyObject(), any(Errors.class));

    this.mockMvc.perform(post("/registration").accept(MediaType.TEXT_PLAIN))
        .andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/loginPage"));
    verify(airlineService, times(1)).reset();
    verify(userService, times(1)).saveUser(any(User.class));
  }



  @Test
  public void testListAllUsers() throws Exception {
    this.mockMvc.perform(get("/listAllUsers").accept(MediaType.TEXT_PLAIN)).andExpect(
        status().isOk());
    verify(userService, times(1)).listAllUsers();
  }
  
  @Test
  public void testListAllOrderrs() throws Exception {
    this.mockMvc.perform(get("/viewTicketsPurchasedByallUsers").accept(MediaType.TEXT_PLAIN)).andExpect(
        status().isOk());
    verify(userService, times(1)).listAllOrders();
  }


  @Test
  public void testGetUserbyUserName() throws Exception {
    this.mockMvc.perform(
        get("/getUserbyUserName").param("userName", "userName").accept(MediaType.TEXT_PLAIN))
        .andExpect(status().isOk());
    verify(userService, times(1)).isExistingUser(any(User.class));
  }



  @Test
  public void testgetLoginPage() throws Exception {
    this.mockMvc.perform(get("/loginPage").accept(MediaType.TEXT_PLAIN)).andExpect(status().isOk())
        .andExpect(view().name("login")).andExpect(model().attributeExists("user"));
  }


  @Test
  public void testgetDashBoardPageThroughLogin() throws Exception {

    // loginvalidator has errors.
    doAnswer(new Answer() {
      public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
        Errors errors = (Errors) invocationOnMock.getArguments()[1];
        errors.reject("error");
        return null;
      }
    }).when(loginValidator).validate(anyObject(), any(Errors.class));

    this.mockMvc.perform(post("/dashboardthroughlogin").accept(MediaType.TEXT_PLAIN))
        .andExpect(status().isOk()).andExpect(view().name("login"));

    // loginvalidator has no errors.
    reset(loginValidator);
    this.mockMvc.perform(post("/dashboardthroughlogin").accept(MediaType.TEXT_PLAIN))
        .andExpect(status().isOk()).andExpect(view().name("dashboard"));
  }



  @Test
  public void testgetDashBoardPage() throws Exception {
    this.mockMvc.perform(get("/dashboardPage").accept(MediaType.TEXT_PLAIN))
        .andExpect(status().isOk()).andExpect(view().name("dashboard"));
  }


  @Test
  public void testviewairlinesTicketsPurchased() throws Exception {
    this.mockMvc.perform(get("/viewairlinesTicketsPurchased").accept(MediaType.TEXT_PLAIN))
        .andExpect(status().isOk()).andExpect(view().name("ticketsPurchased"));
    verify(emailPdfService, times(1)).createPdfDocument(any(String.class),
        any(AirlineTicket[].class));
  }



  @Test
  public void testviewAirlinesOffersPurchaseTickets() throws Exception {
    this.mockMvc.perform(get("/viewairlineofferspurchasetickets").accept(MediaType.TEXT_PLAIN))
        .andExpect(status().isOk()).andExpect(view().name("purchaseTickets"))
        .andExpect(model().hasNoErrors());
    verify(airlineService, times(1)).listAvailableOffers();

  }


  @Test
  public void testopenaNewAccount() throws Exception {
    this.mockMvc.perform(get("/opennewaccount").accept(MediaType.TEXT_PLAIN))
        .andExpect(status().isOk()).andExpect(view().name("openNewAccount"))
        .andExpect(model().hasNoErrors());
    verify(airlineService, times(1)).listAllAccounts();
  }


  @Test
  public void testgetAllAccounts() throws Exception {
    // MvcResult result =
    this.mockMvc.perform(
        get("/getAllAccounts").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
        .andExpect(status().isOk());
    verify(airlineService, times(1)).listAllAccounts();

    reset(airlineService);
    HashMap map = new HashMap();
    map.put("body", "BODY");
    AirlineException ex = new AirlineException();
    ex.setMap(map);

    when(airlineService.listAllAccounts()).thenThrow(ex);
    this.mockMvc.perform(
        get("/getAllAccounts").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
        .andExpect(status().isOk());
    verify(airlineService, times(1)).listAllAccounts();

  }

  @Test
  public void testopenaNewAccount2() throws Exception {
    this.mockMvc.perform(
        post("/opennewaccount2").param("currency", "usd").accept(
            MediaType.parseMediaType("application/json;charset=UTF-8"))).andExpect(status().isOk());
    verify(airlineService, times(1)).createNewAccount(any(CreateAccountRequest.class));
  }



  @Test
  public void testwithDrawDepositMoney() throws Exception {
    this.mockMvc.perform(
        post("/withdrawDeposit").param("amount", "2").param("withdrawDeposit", "withdraw")
            .param("currency", "usd").param("accountId", "accountId")
            .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))).andExpect(
        status().isOk());
    verify(airlineService, times(1)).withDrawMoney(any(DepositWithDrawRequest.class));


    this.mockMvc.perform(
        post("/withdrawDeposit").param("amount", "2").param("withdrawDeposit", "deposit")
            .param("currency", "usd").param("accountId", "accountId")
            .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))).andExpect(
        status().isOk());
    verify(airlineService, times(1)).depositMoney(any(DepositWithDrawRequest.class));
  }


  @Test
  public void testpurchaseTickets() throws Exception {
    this.mockMvc.perform(
        post("/purchaseTickets").param("amount", "2").param("from", "from").param("to", "to")
            .param("accountId", "accountId").with(new RequestPostProcessor() { 
              public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
               request.getSession().setAttribute("user", new User()); 
                return request;
             }})
            .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))).andExpect(
        status().isOk());
    verify(airlineService, times(1)).buyAirlineTicket(any(BuyTicketRequest.class));
    verify(userService, times(1)).saveOrder(any(UserOrder.class));
  }

  @Test
  public void testemailPDFResource() throws Exception {
    this.mockMvc
        .perform(
            get("/emailPdfDoc").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
        .andExpect(status().isOk()).andExpect(view().name("emailPdf"));
    verify(emailPdfService, times(1))
        .sendPdfEmail(any(HttpServletRequest.class), any(String.class));
  }

  @Test
  public void testDownloadPDFResource() throws Exception {
    this.mockMvc.perform(
        get("/downloadPdf").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
        .andExpect(status().isOk());
  }


}
