package ca.ulaval.glo4003.appemployee.web.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.services.UserService;
import ca.ulaval.glo4003.appemployee.web.converters.UserConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ProjectViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TaskViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.UserViewModel;

public class UserControllerTest {
	private static final String EMAIL_KEY = "email";
	private static final String VALID_EMAIL = "employee@employee.com";
	private static final String REDIRECT_LINK = "redirect:/";
	private static final String PASSWORD = "password";
	private static final double WAGE = 0;
	private static final String EDIT_PROFILE_JSP = "editProfile";
	static final String EMPLOYEE_REDIRECT = "redirect:/employee";
	static final String USER_NOT_FOUND = "userNotFoundError";

	@Mock
	private ModelMap modelMapMock;

	@Mock
	private HttpSession sessionMock;

	@Mock
	private UserConverter userConverterMock;

	@Mock
	private UserService userServiceMock;

	@Mock
	private UserViewModel userViewModelMock;

	@InjectMocks
	private UserController userController;

	@InjectMocks
	private UserViewModel userViewModel;
	
	@InjectMocks
	private Model model = new ExtendedModelMap();
	
	@InjectMocks
	private ProjectViewModel projectViewModel;
	
	@InjectMocks
	private TaskViewModel taskViewModel;
	
	@InjectMocks
	private User user;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		projectViewModel = new ProjectViewModel();
		projectViewModel.setUserEmail("");
		taskViewModel = new TaskViewModel();
		taskViewModel.setUserEmail("");
		userViewModel = new UserViewModel();
		userViewModel.setEmail(EMAIL_KEY);
		userViewModel.setPassword(PASSWORD);
		userViewModel.setWage(WAGE);
		user = new User(EMAIL_KEY, PASSWORD, null, WAGE);
		userController = new UserController(userServiceMock, userConverterMock);
	}

	@Test
	public void getUserReturnsEditProfile() {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(userServiceMock.retrieveByEmail(sessionMock.getAttribute(EMAIL_KEY).toString())).thenReturn(user);
		when(userConverterMock.convert(user)).thenReturn(userViewModel);
		String returnedForm = userController.getUser(model, sessionMock);

		assertEquals(EDIT_PROFILE_JSP, returnedForm);
	}

	@Test
	public void getUserReturnRedirectIfSessionAttributeIsNull() {
		String returnedForm = userController.getUser(model, sessionMock);
		assertEquals(REDIRECT_LINK, returnedForm);
	}

	@Test
	public void userNewPasswordUpdatesReturnsEmployee() throws Exception {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(userViewModelMock.getEmail()).thenReturn(VALID_EMAIL);
		String returnedForm = userController.updatePassword(userViewModelMock, sessionMock);
		assertEquals(EMPLOYEE_REDIRECT, returnedForm);
	}

	@Test
	public void userModificationReturnsRedirectIfSessionAttributeIsNull() throws Exception {
		when(userViewModelMock.getEmail()).thenReturn(VALID_EMAIL);
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		String returnedForm = userController.updatePassword(userViewModelMock, sessionMock);
		assertEquals(EMPLOYEE_REDIRECT, returnedForm);
	}

	@Test
	public void updateUserCallsTheCorrectServiceMethods() throws Exception {
		when(userViewModelMock.getEmail()).thenReturn(VALID_EMAIL);
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		userController.updatePassword(userViewModelMock, sessionMock);
		verify(userServiceMock, times(1)).updatePassword(VALID_EMAIL, userViewModelMock);
	}

	@Test
	public void getErrorUserNotFoundReturnsUserNotFoundForm() {
		String returnedForm = userController.getErrorUserNotFound(modelMapMock, sessionMock);
		assertEquals(USER_NOT_FOUND, returnedForm);
	}
}
