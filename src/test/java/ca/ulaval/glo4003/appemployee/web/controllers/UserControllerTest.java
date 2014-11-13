package ca.ulaval.glo4003.appemployee.web.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import ca.ulaval.glo4003.appemployee.domain.exceptions.UserNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.services.UserService;
import ca.ulaval.glo4003.appemployee.web.converters.UserConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ProjectViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TaskViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.UserViewModel;

public class UserControllerTest {
	private static final String EMAIL_KEY = "email";
	private static final String VALID_EMAIL = "emp@company.com";
	private static final String REDIRECT_LINK = "redirect:/";
	private static final String USER_FORM = "editProfile";
	private static final String EDIT_PROFILE_REDIRECT = "redirect:/editProfile/";
	private static final String EDIT_USER_NOT_FOUND_REDIRECT = "redirect:/editProfile/userNotFoundError";
	private static final String USER_NOT_FOUND_LINK = "userNotFoundError";

	private HttpSession sessionMock;
	private UserController userController;
	private ProjectViewModel projectViewModel;
	private TaskViewModel taskViewModel;
	private List<User> employeeList = new ArrayList<User>();
	private UserConverter userConverterMock;
	private UserService userServiceMock;
	private User currentUserMock;
	private User userMock;
	private UserViewModel userViewModelMock;
	private Model modelMock;
	private ModelMap modelMapMock;
	private List<UserViewModel> userViewModelCollection = new ArrayList<UserViewModel>();

	@Before
	public void init() {
		sessionMock = mock(HttpSession.class);
		userViewModelMock = mock(UserViewModel.class);
		userConverterMock = mock(UserConverter.class);
		userServiceMock = mock(UserService.class);
		currentUserMock = mock(User.class);
		projectViewModel = new ProjectViewModel();
		projectViewModel.setUserEmail("");
		taskViewModel = new TaskViewModel();
		taskViewModel.setUserEmail("");
		userMock = mock(User.class);
		modelMock = mock(Model.class);
		modelMapMock = mock(ModelMap.class);
		userController = new UserController(userServiceMock, userConverterMock);
	}

	@Test
	public void getUserUpdatesTheModelCorrectly() {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(userServiceMock.retrieveByEmail(sessionMock.getAttribute(EMAIL_KEY).toString())).thenReturn(currentUserMock);
		when(userConverterMock.convert(userMock)).thenReturn(userViewModelMock);

		String returnedForm = userController.getUser(modelMock, sessionMock);

		assertSame(USER_FORM, returnedForm);
	}

	@Test
	public void getUserReturnRedirectIfSessionAttributeIsNull() {
		String returnedForm = userController.getUser(modelMock, sessionMock);
		assertEquals(REDIRECT_LINK, returnedForm);
	}

	@Test
	public void userModificationUpdatesTheModelCorrectly() {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(userServiceMock.retrieveByEmail(VALID_EMAIL)).thenReturn(userMock);
		when(userConverterMock.convert(userMock)).thenReturn(userViewModelMock);
		when(userServiceMock.retrieveByEmail(sessionMock.getAttribute(EMAIL_KEY).toString())).thenReturn(currentUserMock);
		when(userConverterMock.convert(employeeList)).thenReturn(userViewModelCollection);

		String returnedForm = userController.userModification(modelMock, sessionMock);

		assertEquals(USER_FORM, returnedForm);
	}

	@Test
	public void userModificationReturnsRedirectIfSessionAttributeIsNull() {
		String returnedForm = userController.userModification(modelMock, sessionMock);
		assertEquals(REDIRECT_LINK, returnedForm);
	}

	@Test
	public void updateUserReturnsValidFormIfSuccessful() throws Exception {
		when(userViewModelMock.getEmail()).thenReturn(VALID_EMAIL);
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		String returnedForm = userController.updateUser(userViewModelMock, sessionMock);
		assertEquals(returnedForm, EDIT_PROFILE_REDIRECT);
	}

	@Test
	public void updateUserReturnsErrorRedirectIfUserNotFound() throws Exception {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(userViewModelMock.getEmail()).thenReturn(VALID_EMAIL);
		doThrow(new UserNotFoundException()).when(userServiceMock).retrieveByEmail(VALID_EMAIL);
		String returnedForm = userController.updateUser(userViewModelMock, sessionMock);
		assertEquals(EDIT_USER_NOT_FOUND_REDIRECT, returnedForm);
	}

	@Test
	public void updateUserCallsCorrectRepositoryMethod() throws Exception {
		when(userViewModelMock.getEmail()).thenReturn(VALID_EMAIL);
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		userController.updateUser(userViewModelMock, sessionMock);
		verify(userServiceMock, times(1)).updatePassword(VALID_EMAIL, userViewModelMock);
	}

	@Test
	public void getErrorNoTaskSelectReturnsValidFormWhenCalled() {
		String returnedForm = userController.getErrorNoTaskSelected(modelMapMock, sessionMock);
		assertEquals(USER_NOT_FOUND_LINK, returnedForm);
	}
}
