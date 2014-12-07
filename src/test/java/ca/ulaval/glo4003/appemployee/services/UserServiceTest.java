package ca.ulaval.glo4003.appemployee.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ca.ulaval.glo4003.appemployee.domain.exceptions.UserNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.user.Role;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.domain.user.UserProcessor;
import ca.ulaval.glo4003.appemployee.web.converters.UserConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.UserViewModel;

public class UserServiceTest {

	private List<User> users = new ArrayList<User>();
	private List<String> emails = new ArrayList<String>();

	private static final String EMAIL = "emp@company.com";
	private static final String PASSWORD = "password";
	private static final double WAGE = 25.00;
	private static final Role ROLE = Role.EMPLOYEE;

	@Mock
	private User userMock;

	@Mock
	private UserViewModel userViewModelMock;

	@Mock
	private UserConverter userConverterMock;

	@Mock
	private UserProcessor userProcessorMock;

	@InjectMocks
	private UserService userService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		userService = new UserService(userProcessorMock, userConverterMock);
	}

	@Test
	public void retrieveUserByEmailCallsCorrectProcessorMethod() {
		userService.retrieveUserByEmail(EMAIL);
		verify(userProcessorMock, times(1)).retrieveUserByEmail(EMAIL);
	}

	@Test(expected = UserNotFoundException.class)
	public void retrieveUserByEmailThrowsExceptionWhenUserIsNotFound() {
		doThrow(new UserNotFoundException("")).when(userProcessorMock).retrieveUserByEmail(EMAIL);
		userService.retrieveUserByEmail(EMAIL);
	}

	@Test
	public void retrieveUsersByEmailReturnsListOfUsersWhenEmailsAreValid() {
		when(userMock.getEmail()).thenReturn(EMAIL);
		when(userService.retrieveUserByEmail(EMAIL)).thenReturn(userMock);
		emails.add(EMAIL);
		users.add(userMock);

		List<User> returnedUsers = userService.retrieveUsersByEmail(emails);

		assertEquals(users, returnedUsers);
	}

	@Test
	public void editUserCallsCorrectProcessorMethod() throws Exception {
		when(userViewModelMock.getEmail()).thenReturn(EMAIL);
		when(userViewModelMock.getPassword()).thenReturn(PASSWORD);
		when(userViewModelMock.getWage()).thenReturn(WAGE);
		when(userViewModelMock.getRole()).thenReturn(ROLE.toString());

		userService.editUser(userViewModelMock);

		verify(userProcessorMock, times(1)).updateUser(EMAIL, PASSWORD, ROLE, WAGE);
	}

	@Test
	public void validateCredentialsReturnsFalseWhenWrongCredentials() {
		when(userProcessorMock.validateUserCredentials(EMAIL, PASSWORD)).thenReturn(false);
		boolean isUserCredentialsValidated = userService.validateCredentials(EMAIL, PASSWORD);
		assertFalse(isUserCredentialsValidated);
	}

	@Test
	public void validateCredentialsReturnsTrueWhenGoodCredentials() throws Exception {
		when(userProcessorMock.validateUserCredentials(EMAIL, PASSWORD)).thenReturn(true);
		boolean isUserCredentialsValidated = userService.validateCredentials(EMAIL, PASSWORD);
		assertTrue(isUserCredentialsValidated);
	}

	@Test
	public void retrieveUserRoleCallsCorrectProcessorMethod() {
		when(userProcessorMock.retrieveUserRole(EMAIL)).thenReturn(ROLE);
		userService.retrieveUserRole(EMAIL);
		verify(userProcessorMock, times(1)).retrieveUserRole(EMAIL);
	}

	@Test
	public void retrieveUserRoleReturnsCorrectUserRole() {
		when(userProcessorMock.retrieveUserRole(EMAIL)).thenReturn(ROLE);
		String returnedRole = userService.retrieveUserRole(EMAIL);
		assertEquals(ROLE.toString(), returnedRole);
	}

	@Test
	public void retrieveUserViewModelReturnsCorrectViewModel() {
		when(userProcessorMock.retrieveUserByEmail(EMAIL)).thenReturn(userMock);
		when(userConverterMock.convert(userMock)).thenReturn(userViewModelMock);

		UserViewModel returnedModel = userService.retrieveUserViewModel(EMAIL);

		assertEquals(userViewModelMock, returnedModel);
	}

}
