package ca.ulaval.glo4003.appemployee.web.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.appemployee.services.UserService;
import ca.ulaval.glo4003.appemployee.web.viewmodels.LoginFormViewModel;

public class HomeControllerTest {

	private static final String USER_EMAIL = "emp@company.com";
	private static final String USER_ROLE = "EMPLOYEE";
	private static final String USER_PASSWORD = "emp";
	private static final String USER_WRONG_PASSWORD = "blablabla";
	private static final String ALERT_ATTRIBUTE = "alert";
	private static final String ALERT_MESSAGE = "Invalid username and/or password.";
	private static final String HOME_VIEW = "home";
	private static final String SIMPLE_REDIRECT = "redirect:/";
	static final Integer SESSION_IDLE_TRESHOLD_IN_SECONDS = 462;

	@Mock
	private LoginFormViewModel loginFormViewModelMock;

	@Mock
	private ModelMap modelMapMock;

	@Mock
	private UserService userServiceMock;

	@Mock
	private SessionStatus sessionStatusMock;

	@InjectMocks
	private HomeController homeController;

	@Mock
	private HttpSession sessionMock;

	@Mock
	private HttpServletRequest servletRequestMock;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		homeController = new HomeController(userServiceMock);
	}

	@Test
	public void defaultUserReturnsNewLoginFormViewModel() {
		LoginFormViewModel returnedModel = homeController.defaultUser();
		assertNotNull(returnedModel);
	}

	@Test
	public void loginReturnsCorrectModelForm() {
		when(loginFormViewModelMock.getEmail()).thenReturn(USER_EMAIL);
		when(loginFormViewModelMock.getPassword()).thenReturn(USER_PASSWORD);
		ModelAndView returnedModel = homeController.login(loginFormViewModelMock, modelMapMock, sessionMock, servletRequestMock);
		assertEquals("home", returnedModel.getViewName());
	}
	
	@Test
	public void loginSetsSessionIdleTreshold() {
		when(loginFormViewModelMock.getEmail()).thenReturn(USER_EMAIL);
		when(loginFormViewModelMock.getPassword()).thenReturn(USER_PASSWORD);
		when(userServiceMock.retrieveUserRole(USER_EMAIL)).thenReturn(USER_ROLE);
		when(userServiceMock.validateCredentials(USER_EMAIL, USER_PASSWORD)).thenReturn(true);
		when(servletRequestMock.getSession()).thenReturn(sessionMock);
		
		homeController.login(loginFormViewModelMock, modelMapMock, sessionMock, servletRequestMock);
		
		verify(sessionMock, times(1)).setMaxInactiveInterval(SESSION_IDLE_TRESHOLD_IN_SECONDS);
	}

	@Test(expected = Exception.class)
	public void loginReturnsAlertIfWrongEmailOrPassword() {
		doThrow(new Exception()).when(userServiceMock).validateCredentials(USER_EMAIL, USER_WRONG_PASSWORD);
		homeController.login(loginFormViewModelMock, modelMapMock, sessionMock, servletRequestMock);
		verify(modelMapMock, times(1)).addAttribute(ALERT_ATTRIBUTE, ALERT_MESSAGE);
	}

	@Test
	public void showLoginFormReturnsHomeViewForm() {
		String returnedForm = homeController.showLoginForm();
		assertEquals(HOME_VIEW, returnedForm);
	}

	@Test
	public void logoutRedirectsWhenSuccessful() {
		String returnedForm = homeController.logout(sessionStatusMock, modelMapMock);
		assertEquals(SIMPLE_REDIRECT, returnedForm);
	}
}
