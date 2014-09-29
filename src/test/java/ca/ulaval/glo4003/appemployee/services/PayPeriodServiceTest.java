package ca.ulaval.glo4003.appemployee.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.appemployee.domain.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.user.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class PayPeriodServiceTest {

	private static final String VALID_EMAIL = "test@test.com";
	private static final String INVALID_EMAIL = "crap@crap.com";
	private static final String VALID_PASSWORD = "1234";
	private static final String ERROR_MESSAGE = "message";

	private UserRepository userRepositoryMock;
	private User user;
	private PayPeriod payPeriodMock;
	private PayPeriodService payPeriodServiceMock;

	@Before
	public void init() {
		userRepositoryMock = mock(UserRepository.class);
		payPeriodMock = mock(PayPeriod.class);
		payPeriodServiceMock = mock(PayPeriodService.class);
		user = new User(VALID_EMAIL, VALID_PASSWORD, "EMPLOYEE");
		payPeriodServiceMock = new PayPeriodService(userRepositoryMock);
	}

	@Test
	public void getUserByEmailReturnsUserIfSuccessful() {
		when(userRepositoryMock.findByEmail(VALID_EMAIL)).thenReturn(user);
		User sampleUser = payPeriodServiceMock.getUserByEmail(VALID_EMAIL);
		assertEquals(sampleUser.getEmail(), VALID_EMAIL);
	}

	@Test
	public void getUserByEmailCallsCorrectMethodInRepository() {
		payPeriodServiceMock.getUserByEmail(VALID_EMAIL);
		verify(userRepositoryMock, times(1)).findByEmail(VALID_EMAIL);
	}

	@Test(expected = UserNotFoundException.class)
	public void getUserByEmailThrowsExceptionIfUserNotFound() {
		when(userRepositoryMock.findByEmail(INVALID_EMAIL)).thenThrow(new UserNotFoundException(ERROR_MESSAGE));
		payPeriodServiceMock.getUserByEmail(INVALID_EMAIL);
	}

	@Test
	public void updateUserCurrentPayPeriodCallsUpdateMethod() {
		payPeriodServiceMock.updateUserCurrentPayPeriod(user);
		verify(userRepositoryMock, times(1)).update(user);
	}

	@Test(expected = UserNotFoundException.class)
	public void updateUserCurrentPayPeriodShiftListThrowsExceptionWhenUserNotFound() {
		when(userRepositoryMock.findByEmail(INVALID_EMAIL)).thenThrow(new UserNotFoundException(ERROR_MESSAGE));
		payPeriodServiceMock.updateUserCurrentPayPeriodShiftList(INVALID_EMAIL, payPeriodMock);
	}

	@Test(expected = UserNotFoundException.class)
	public void updateUserCurrentPayPeriodExpensesThrowsExceptionWhenUserNotFound() {
		when(userRepositoryMock.findByEmail(INVALID_EMAIL)).thenThrow(new UserNotFoundException(ERROR_MESSAGE));
		payPeriodServiceMock.updateUserCurrentPayPeriodExpenses(INVALID_EMAIL, payPeriodMock);
	}
}
