package ca.ulaval.glo4003.appemployee.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.appemployee.domain.user.Role;
import ca.ulaval.glo4003.appemployee.domain.user.User;

@RunWith(MockitoJUnitRunner.class)
public class UserTest {
	private static final String VALID_PASSWORD = "1234";
	private static final String VALID_EMAIL = "test@test.com";
	private static final double WAGE = 10000.00;
	private static Role ROLE = Role.EMPLOYEE;

	private User user;

	@Before
	public void setUp() {
		user = new User(VALID_EMAIL, VALID_PASSWORD, ROLE, WAGE);
	}

	@Test
	public void canInstantiateUser() {
		assertNotNull(user);
	}

	@Test
	public void canValidateRightPassword() {
		assertTrue(user.validatePassword(VALID_PASSWORD));
	}

	@Test
	public void cannotValidateWrongPassword() {
		assertFalse(user.validatePassword("wrongPassword"));
	}
}
