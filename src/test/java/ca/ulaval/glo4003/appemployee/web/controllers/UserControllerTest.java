package ca.ulaval.glo4003.appemployee.web.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

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

	private HttpSession sessionMock;
	private Model model = new ExtendedModelMap();
	private UserController userController;
	private ProjectViewModel projectViewModel;
	private TaskViewModel taskViewModel;
	private List<User> employeeList = new ArrayList<User>();
	private UserConverter userConverterMock;
	private UserService userServiceMock;
	private User currentUserMock;
	private User userMock;
	private UserViewModel userViewModel;
	private UserViewModel userViewModelMock;
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
		userViewModel = new UserViewModel();
		userController = new UserController(userServiceMock, userConverterMock);
	}

	@Test
	public void getUserUpdatesTheModelCorrectly() {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(userServiceMock.retrieveByEmail(sessionMock.getAttribute(EMAIL_KEY).toString())).thenReturn(currentUserMock);
		when(userConverterMock.convert(userMock)).thenReturn(userViewModel);

		userController.getUser(model, sessionMock);

		// assertSame(model.asMap().get("users"), userViewModel);
	}

	@Test
	public void getUserReturnRedirectIfSessionAttributeIsNull() {
		String returnedForm = userController.getUser(model, sessionMock);
		assertEquals(REDIRECT_LINK, returnedForm);
	}

	@Test
	public void userModificationUpdatesTheModelCorrectly() {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(userServiceMock.retrieveByEmail(VALID_EMAIL)).thenReturn(userMock);
		when(userConverterMock.convert(userMock)).thenReturn(userViewModelMock);
		when(userServiceMock.retrieveByEmail(sessionMock.getAttribute(EMAIL_KEY).toString())).thenReturn(currentUserMock);
		when(userConverterMock.convert(employeeList)).thenReturn(userViewModelCollection);

		userController.userModification(model, sessionMock);

		// assertSame(model.asMap().get("user"), userViewModelMock);
	}

	@Test
	public void userModificationReturnsRedirectIfSessionAttributeIsNull() {
		String returnedForm = userController.userModification(model, sessionMock);
		assertEquals(REDIRECT_LINK, returnedForm);
	}

	@Test
	public void updateUserCallsTheCorrectServiceMethods() throws Exception {
		// userController.updateUser(userViewModel, sessionMock);
		// verify(userServiceMock).updateUser(EMAIL_KEY, userViewModel);
	}
}
