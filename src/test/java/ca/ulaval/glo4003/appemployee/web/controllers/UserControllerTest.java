package ca.ulaval.glo4003.appemployee.web.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import ca.ulaval.glo4003.appemployee.services.UserService;
import ca.ulaval.glo4003.appemployee.web.viewmodels.MessageViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.UserViewModel;

public class UserControllerTest {
	private static final String EMAIL_KEY = "email";
	private static final String VALID_EMAIL = "employee@employee.com";
	private static final String EDIT_PROFILE_JSP = "editProfile";
	private static final String HOME_REDIRECT = "redirect:/";

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
	public void updatePasswordRedirectsToHomePage() throws Exception {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		String returnedForm = userController.updatePassword(userViewModelMock, modelMock);
		assertEquals(HOME_REDIRECT, returnedForm);
	}

	@Test
	public void updatePasswordCallsTheCorrectServiceMethods() throws Exception {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		userController.updatePassword(userViewModelMock, modelMock);
		verify(userServiceMock, times(1)).editUser(userViewModelMock);
	}
	
	@Test
	public void updatePasswordReturnsAlertWhenSomethingWentWrong() throws Exception {
		doThrow(new Exception()).when(userServiceMock).editUser(userViewModelMock);
		userController.updatePassword(userViewModelMock, modelMock);
		verify(modelMock, times(1)).addAttribute(org.mockito.Matchers.eq("message"), org.mockito.Matchers.any(MessageViewModel.class));
	}

}
