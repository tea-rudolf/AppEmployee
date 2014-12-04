package ca.ulaval.glo4003.appemployee.domain.user;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ca.ulaval.glo4003.appemployee.domain.exceptions.UserNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.repository.UserRepository;

public class UserProcessorTest {

	@Mock
	private UserRepository userRepositoryMock;

	@Mock
	private User userMock;

	@InjectMocks
	private UserProcessor userProcessor;

	private static final String EMAIL = "test@test.com";
	private static final String PASSWORD = "dummyPassword";
	private static final double WAGE = 150000.00;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		userProcessor = new UserProcessor(userRepositoryMock);
	}

	@Test
	public void canInstantiateUserProcessor() {
		assertNotNull(userProcessor);
	}

	@Test(expected = UserNotFoundException.class)
	public void retrieveUserByEmailThrowsExceptionWhenUserNotFound() {
		when(userRepositoryMock.findByEmail(EMAIL)).thenReturn(null);
		userProcessor.retrieveUserByEmail(EMAIL);
	}

	@Test
	public void retrieveUserByEmailReturnsUserWhenUserFound() {
		when(userRepositoryMock.findByEmail(EMAIL)).thenReturn(userMock);
		User actualUser = userProcessor.retrieveUserByEmail(EMAIL);
		assertEquals(userMock, actualUser);
	}

	@Test
	public void updateUserStoresUserInRepository() throws Exception {
		ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
		userProcessor.updateUser(EMAIL, PASSWORD, Role.SUPERVISOR, WAGE);
		verify(userRepositoryMock, times(1)).store(userArgumentCaptor.capture());
	}

	@Test(expected = UserNotFoundException.class)
	public void validateUserCredentialsThrowsExceptionWhenUserNotFound() {
		when(userRepositoryMock.findByEmail(EMAIL)).thenReturn(null);
		userProcessor.validateUserCredentials(EMAIL, PASSWORD);
	}

	@Test
	public void validateUserCredentialsReturnsTrueWhenRightPassword() {
		when(userRepositoryMock.findByEmail(EMAIL)).thenReturn(userMock);
		when(userMock.validatePassword(PASSWORD)).thenReturn(true);

		boolean isUserCredentialsValidated = userProcessor.validateUserCredentials(EMAIL, PASSWORD);

		assertTrue(isUserCredentialsValidated);
	}

	@Test
	public void validateUserCredentialsReturnFalseWhenWrongPassword() {
		when(userRepositoryMock.findByEmail(EMAIL)).thenReturn(userMock);
		when(userMock.validatePassword(PASSWORD)).thenReturn(false);

		boolean isUserCredentialsValidated = userProcessor.validateUserCredentials(EMAIL, PASSWORD);

		assertFalse(isUserCredentialsValidated);
	}

	@Test(expected = UserNotFoundException.class)
	public void retrieveUserRoleThrowsExceptionWhenUserNotFound() {
		when(userRepositoryMock.findByEmail(EMAIL)).thenReturn(null);
		userProcessor.retrieveUserRole(EMAIL);
	}

	@Test
	public void retrieveUserRoleReturnsUserRoleWhenUserFound() {
		when(userRepositoryMock.findByEmail(EMAIL)).thenReturn(userMock);
		when(userMock.getRole()).thenReturn(Role.EMPLOYEE);

		Role actualUserRole = userProcessor.retrieveUserRole(EMAIL);

		assertEquals(Role.EMPLOYEE, actualUserRole);
	}

}
