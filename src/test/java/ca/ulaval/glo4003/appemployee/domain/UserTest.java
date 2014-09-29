package ca.ulaval.glo4003.appemployee.domain;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.appemployee.domain.user.User;

@RunWith(MockitoJUnitRunner.class)
public class UserTest {
	private static final String VALID_PASSWORD = "1234";
	private static final String VALID_EMAIL = "test@test.com";

	@Test
	public void canValidateRightPassword() {
		User user = new User(VALID_EMAIL, VALID_PASSWORD, "EMPLOYEE");

		assertTrue(user.validatePassword(VALID_PASSWORD));
	}

	@Test
	public void cannotValidateWrongPassword() {
		User user = new User(VALID_EMAIL, VALID_PASSWORD, "EMPLOYEE");

		assertFalse(user.validatePassword("wrongPassword"));
	}
}
