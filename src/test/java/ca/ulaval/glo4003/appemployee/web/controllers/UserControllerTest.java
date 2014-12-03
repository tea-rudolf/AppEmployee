package ca.ulaval.glo4003.appemployee.web.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import ca.ulaval.glo4003.appemployee.services.UserService;
import ca.ulaval.glo4003.appemployee.web.viewmodels.UserViewModel;

public class UserControllerTest {
	private static final String EMAIL_KEY = "email";
	private static final String VALID_EMAIL = "employee@employee.com";
	private static final String EDIT_PROFILE_JSP = "editProfile";
	private static final String EMPLOYEE_JSP = "employee";

	@Mock
	private Model modelMock;

	@Mock
	private HttpSession sessionMock;

	@Mock
	private UserService userServiceMock;

	@Mock
	private UserViewModel userViewModelMock;

	@InjectMocks
	private UserController userController;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		userController = new UserController(userServiceMock);
	}

	@Test
	public void showUserProfileFormReturnsEditProfileFormIfSessionAttributeIsNotNull() {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(userServiceMock.retrieveUserViewModel(VALID_EMAIL)).thenReturn(userViewModelMock);

		String returnedForm = userController.showUserProfileForm(modelMock, sessionMock);

		assertEquals(EDIT_PROFILE_JSP, returnedForm);
	}

	@Test
	public void updatePasswordRedirectsToEmployeePage() throws Exception {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		String returnedForm = userController.updatePassword(userViewModelMock);
		assertEquals(EMPLOYEE_JSP, returnedForm);
	}

	@Test
	public void updatePasswordCallsTheCorrectServiceMethods() throws Exception {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		userController.updatePassword(userViewModelMock);
		verify(userServiceMock, times(1)).editUser(userViewModelMock);
	}

}
