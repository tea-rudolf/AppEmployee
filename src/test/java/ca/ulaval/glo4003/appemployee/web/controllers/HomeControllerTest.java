package ca.ulaval.glo4003.appemployee.web.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpSession;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.appemployee.domain.repository.UserRepository;
import ca.ulaval.glo4003.appemployee.domain.user.Role;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.web.viewmodels.LoginFormViewModel;

public class HomeControllerTest {

	private static final String USER_EMAIL = "employee@employee.com";
	private static final String USER_PASSWORD = "employee";
	private static final String EMAIL_ATTRIBUTE = "email";
	private static final String ROLE_ATTRIBUTE = "role";
	private static final String ALERT_ATTRIBUTE = "alert";
	private static final String ALERT_MESSAGE = "Invalid username and/or password.";
	private static final String HOME_VIEW = "home";
	private static final String SIMPLE_REDIRECT = "redirect:/";

	@Mock
	private ExpensesController expensesControllerMock;

	@Mock
	private LoginFormViewModel loginFormViewModelMock;

	@Mock
	private ModelMap modelMapMock;

	@Mock
	private UserRepository userRepositoryMock;

	@Mock
	private User userMock;

	@Mock
	private SessionStatus sessionStatusMock;

	@InjectMocks
	private HomeController homeController;
	
	@Mock
	private HttpSession sessionMock;

	private Role role;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		role = Role.EMPLOYEE;
		homeController = new HomeController(userRepositoryMock);
	}

	@Test
	public void defaultUserReturnsNewLoginFormViewModel() {
		LoginFormViewModel returnedModel = homeController.defaultUser();
		assertNotNull(returnedModel);
	}

	@Test
	public void loginReturnsCorrectModelForm() {
		when(loginFormViewModelMock.getEmail()).thenReturn(USER_EMAIL);
		when(userRepositoryMock.findByEmail(USER_EMAIL)).thenReturn(userMock);
		when(loginFormViewModelMock.getPassword()).thenReturn(USER_PASSWORD);

		ModelAndView sampleForm = homeController.login(loginFormViewModelMock, modelMapMock, sessionMock);

		assertEquals("home", sampleForm.getViewName());
	}

	@Test
	public void loginAddsEmailAttributeToForm() {
		when(loginFormViewModelMock.getEmail()).thenReturn(USER_EMAIL);
		when(userRepositoryMock.findByEmail(USER_EMAIL)).thenReturn(userMock);
		when(loginFormViewModelMock.getPassword()).thenReturn(USER_PASSWORD);
		when(userMock.validatePassword(USER_PASSWORD)).thenReturn(true);

		homeController.login(loginFormViewModelMock, modelMapMock, sessionMock);

		verify(modelMapMock, times(1)).addAttribute(EMAIL_ATTRIBUTE, USER_EMAIL);
	}

	@Test
	public void loginAddsRoleAttributeToForm() {
		when(loginFormViewModelMock.getEmail()).thenReturn(USER_EMAIL);
		when(userRepositoryMock.findByEmail(USER_EMAIL)).thenReturn(userMock);
		when(loginFormViewModelMock.getPassword()).thenReturn(USER_PASSWORD);
		when(userMock.validatePassword(USER_PASSWORD)).thenReturn(true);
		when(userMock.getRole()).thenReturn(role);

		homeController.login(loginFormViewModelMock, modelMapMock, sessionMock);

		verify(modelMapMock, times(1)).addAttribute(ROLE_ATTRIBUTE, role);
	}

	@Test
	public void loginReturnsAlertIfWrongEmailOrPassword() {
		when(loginFormViewModelMock.getEmail()).thenReturn(USER_EMAIL);
		when(userRepositoryMock.findByEmail(USER_EMAIL)).thenReturn(userMock);
		when(loginFormViewModelMock.getPassword()).thenReturn(USER_PASSWORD);
		when(userMock.validatePassword(USER_PASSWORD)).thenReturn(false);

		homeController.login(loginFormViewModelMock, modelMapMock, sessionMock);

		verify(modelMapMock, times(1)).addAttribute(ALERT_ATTRIBUTE, ALERT_MESSAGE);
	}

	@Test
	public void getDisplayLoginFormReturnsHomeViewForm() {
		String returnedForm = homeController.displayLoginForm();
		assertEquals(HOME_VIEW, returnedForm);
	}

	@Test
	public void logoutRedirectsWhenSuccessful() {
		String returnedForm = homeController.logout(sessionStatusMock, modelMapMock);
		assertEquals(SIMPLE_REDIRECT, returnedForm);
	}
}
