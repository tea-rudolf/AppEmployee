package ca.ulaval.glo4003.appemployee.persistence;

import static org.junit.Assert.assertEquals;
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

public class XMLUserRepositoryTest {

	private static final String VALID_EMAIL = "test@test.com";
	private static final String VALID_EMAIL2 = "test2@test.com";
	private static final String VALID_PASSWORD = "dummyPassword";
	private static final double VALID_WAGE = 100.00;

	@Mock
	private XMLGenericMarshaller<UserXMLAssembler> marshallerMock;

	@Mock
	private User userMock;

	@InjectMocks
	private XMLUserRepository repository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		repository = new XMLUserRepository(marshallerMock);
		when(userMock.getEmail()).thenReturn(VALID_EMAIL);
	}

	@Test
	public void findByEmailFindsUserByEmail() throws Exception {
		User dummyUser = new User(VALID_EMAIL, VALID_PASSWORD, Role.EMPLOYEE, VALID_WAGE);
		repository.store(dummyUser);

		assertEquals(dummyUser, repository.findByEmail(VALID_EMAIL));
	}

	@Test
	public void storeAddsUserToUserRepository() throws Exception {
		repository.store(userMock);

		assertEquals(userMock, repository.findByEmail(VALID_EMAIL));
	}

	@Test
	public void findByEmailsFindsUsersByTheirEmails() throws Exception {
		List<String> emails = new ArrayList<String>();
		User dummyUser = new User(VALID_EMAIL, VALID_PASSWORD, Role.EMPLOYEE, VALID_WAGE);
		User dummyUser2 = new User(VALID_EMAIL2, VALID_EMAIL2, Role.EMPLOYEE, VALID_WAGE);
		repository.store(dummyUser);
		repository.store(dummyUser2);
		emails.add(VALID_EMAIL);
		emails.add(VALID_EMAIL2);

		List<User> users = repository.findByEmails(emails);

		assertEquals(2, users.size());
	}

}
