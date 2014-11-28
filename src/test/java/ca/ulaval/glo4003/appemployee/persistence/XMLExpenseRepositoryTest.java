package ca.ulaval.glo4003.appemployee.persistence;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ca.ulaval.glo4003.appemployee.domain.expense.Expense;

public class XMLExpenseRepositoryTest {

	private static final String VALID_UID = "1234";
	private static final double AMOUNT = 500.50;
	private static final LocalDate DATE = new LocalDate();
	private static final String USER_EMAIL = "test@company.com";
	private static final String COMMENT = "nothing to say";

	@Mock
	private XMLGenericMarshaller<ExpenseXMLAssembler> marshallerMock;

	@Mock
	private Expense expenseMock;

	@Mock
	private XMLUserRepository userRepository;

	@InjectMocks
	private XMLExpenseRepository repository;

	@Before
	public void init() throws Exception {
		MockitoAnnotations.initMocks(this);
		repository = new XMLExpenseRepository();
		when(expenseMock.getUid()).thenReturn(VALID_UID);
	}

	@Test
	public void findByUidFindsExpenseById() throws Exception {
		Expense dummyExpense = new Expense(AMOUNT, DATE, USER_EMAIL, COMMENT);
		repository.store(dummyExpense);

		assertEquals(dummyExpense, repository.findByUid(dummyExpense.getUid()));
	}

	@Test
	public void storeExpenseToRepositoryAddsExpenseToRepo() throws Exception {
		repository.store(expenseMock);

		assertEquals(expenseMock, repository.findByUid(VALID_UID));
	}
}
