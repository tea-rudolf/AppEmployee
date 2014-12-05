package ca.ulaval.glo4003.appemployee.web.converters;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ca.ulaval.glo4003.appemployee.domain.user.Role;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.web.viewmodels.UserViewModel;

public class UserConverterTest {

	private static final String USER_EMAIL = "employee@employee.com";
	private static final String USER_PASSWORD = "employee";
	private static final double USER_WAGE = 25.00;
	private static final Role USER_ROLE = Role.EMPLOYEE;
	private static final String SECOND_EMAIL = "email";
	private static final String SECOND_PASSWORD = "password";
	private static final double SECOND_WAGE = 20.00;
	private static final Role SECOND_ROLE = Role.SUPERVISOR;
	private static final double EPSILON = 0.001;

	@Mock
	private User userMock;

	@Mock
	private UserViewModel userViewModelMock;

	@InjectMocks
	private UserConverter userConverter;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		userConverter = new UserConverter();
	}

	@Test
	public void convertUsersListToViewModelsConvertsAllOfThem() {
		User firstUser = createUser(USER_EMAIL, USER_PASSWORD, USER_WAGE, USER_ROLE);
		User secondUser = createUser(SECOND_EMAIL, SECOND_PASSWORD, SECOND_WAGE, SECOND_ROLE);
		List<User> users = new ArrayList<User>();
		users.add(firstUser);
		users.add(secondUser);

		UserViewModel[] viewModels = userConverter.convert(users).toArray(new UserViewModel[1]);

		assertEquals(USER_EMAIL, viewModels[0].getEmail());
		assertEquals(USER_PASSWORD, viewModels[0].getPassword());
		assertEquals(USER_WAGE, viewModels[0].getWage(), EPSILON);
		assertEquals(USER_ROLE.toString(), viewModels[0].getRole());

		assertEquals(SECOND_EMAIL, viewModels[1].getEmail());
		assertEquals(SECOND_PASSWORD, viewModels[1].getPassword());
		assertEquals(SECOND_WAGE, viewModels[1].getWage(), EPSILON);
		assertEquals(SECOND_ROLE.toString(), viewModels[1].getRole());
	}

	@Test
	public void convertUserToUserViewModel() {
		when(userMock.getEmail()).thenReturn(USER_EMAIL);
		when(userMock.getPassword()).thenReturn(USER_PASSWORD);
		when(userMock.getWage()).thenReturn(USER_WAGE);
		when(userMock.getRole()).thenReturn(USER_ROLE);

		userViewModelMock = userConverter.convert(userMock);

		assertEquals(userMock.getEmail(), userViewModelMock.getEmail());
		assertEquals(userMock.getPassword(), userViewModelMock.getPassword());
		assertEquals(userMock.getWage(), userViewModelMock.getWage(), EPSILON);
		assertEquals(userMock.getRole().toString(), userViewModelMock.getRole());
	}

	private User createUser(String email, String password, double wage, Role role) {
		User user = mock(User.class);
		given(user.getEmail()).willReturn(email);
		given(user.getPassword()).willReturn(password);
		given(user.getWage()).willReturn(wage);
		given(user.getRole()).willReturn(role);
		return user;
	}

}
