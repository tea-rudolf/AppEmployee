package ca.ulaval.glo4003.appemployee.domain;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserTest {

	@Test
	public void canValidateRightPassword() {
		String password = "1234";
		User user = new User("test@test.com", password);

		assertTrue(user.validatePassword(password));
	}

	@Test
	public void cannotValidateWrongPassword() {
		String password = "1234";
		User user = new User("test@test.com", password);

		assertFalse(user.validatePassword("wrongPassword"));
	}
}
