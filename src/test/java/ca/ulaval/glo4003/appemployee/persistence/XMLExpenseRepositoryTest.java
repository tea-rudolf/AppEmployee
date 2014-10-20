package ca.ulaval.glo4003.appemployee.persistence;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ca.ulaval.glo4003.appemployee.domain.expense.Expense;

public class XMLExpenseRepositoryTest {

	private static final String VALID_UID = "1234";

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
		when(expenseMock.getuId()).thenReturn(VALID_UID);
	}

	@Test
	public void findByuIdFindsExpenseById() throws Exception {
		Expense dummyExpense = new Expense(VALID_UID);
		repository.store(dummyExpense);

		assertEquals(dummyExpense, repository.findByUid(VALID_UID));
	}

	@Test
	public void storeExpenseToRepositoryAddsExpenseToRepo() throws Exception {
		repository.store(expenseMock);

		assertEquals(expenseMock, repository.findByUid(VALID_UID));
	}
}
