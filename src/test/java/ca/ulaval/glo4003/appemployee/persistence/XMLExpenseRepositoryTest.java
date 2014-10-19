package ca.ulaval.glo4003.appemployee.persistence;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.appemployee.domain.expense.Expense;
import ca.ulaval.glo4003.appemployee.domain.user.User;

public class XMLExpenseRepositoryTest {

	private static final String USER_EMAIL = "employee@employee.com"; // cet employee a 2 expenses dans le repo

	private static final Integer LIST_SIZE = 2;
	private static final double EPSILON = 0.001;

	private XMLExpenseRepository xmlExpenseRepository;
	private XMLUserRepository xmlUserRepositoryMock;
	private User userMock;

	@Before
	public void init() throws Exception {
		userMock = mock(User.class);
		xmlUserRepositoryMock = mock(XMLUserRepository.class);
		xmlExpenseRepository = new XMLExpenseRepository();
	}

	@Test
	public void findAllExpensesByUserReturnsExpensesList() {
		when(xmlUserRepositoryMock.findByEmail(USER_EMAIL)).thenReturn(userMock);

		List<Expense> sampleExpenseList = xmlExpenseRepository.findAllExpensesByUser(USER_EMAIL);

		assertEquals(sampleExpenseList.size(), LIST_SIZE, EPSILON);
	}

}
