package ca.ulaval.glo4003.appemployee.web.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

import ca.ulaval.glo4003.appemployee.domain.user.Role;
import ca.ulaval.glo4003.appemployee.services.UserService;
import ca.ulaval.glo4003.appemployee.web.viewmodels.LoginFormViewModel;

public class HomeControllerTest {

	private static final String USER_EMAIL = "employee@employee.com";
	private static final String USER_PASSWORD = "employee";
	private static final String ALERT_ATTRIBUTE = "alert";
	private static final String ALERT_MESSAGE = "Invalid username and/or password.";
	private static final String HOME_VIEW = "home";
	private static final String SIMPLE_REDIRECT = "redirect:/";

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
		when(userServiceMock.isUserValid(USER_EMAIL, USER_PASSWORD))
				.thenReturn(true);
		when(userServiceMock.retrieveUserRole(USER_EMAIL)).thenReturn(
				Role.EMPLOYEE.toString());
		when(servletRequestMock.getSession()).thenReturn(sessionMock);

		ModelAndView returnedModel = homeController.login(
				loginFormViewModelMock, modelMapMock, sessionMock,
				servletRequestMock);

		assertEquals("home", returnedModel.getViewName());
	}

	@Test
	public void loginReturnsAlertIfWrongEmailOrPassword() {
		when(loginFormViewModelMock.getEmail()).thenReturn(USER_EMAIL);
		when(loginFormViewModelMock.getPassword()).thenReturn(USER_PASSWORD);
		when(userServiceMock.isUserValid(USER_EMAIL, USER_PASSWORD))
				.thenReturn(false);

		homeController.login(loginFormViewModelMock, modelMapMock, sessionMock,
				servletRequestMock);

		verify(modelMapMock, times(1)).addAttribute(ALERT_ATTRIBUTE,
				ALERT_MESSAGE);
	}

	@Test
	public void showLoginFormReturnsHomeViewForm() {
		String returnedForm = homeController.showLoginForm();
		assertEquals(HOME_VIEW, returnedForm);
	}

	@Test
	public void logoutRedirectsWhenSuccessful() {
		String returnedForm = homeController.logout(sessionStatusMock,
				modelMapMock);
		assertEquals(SIMPLE_REDIRECT, returnedForm);
	}
}
