package ca.ulaval.glo4003.appemployee.services;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ca.ulaval.glo4003.appemployee.domain.exceptions.ExpenseNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.expense.Expense;
import ca.ulaval.glo4003.appemployee.domain.repository.ExpenseRepository;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ExpenseViewModel;

public class ExpenseServiceTest {

	@Mock
	private ExpenseRepository expenseRepositoryMock;

	@Mock
	private Expense expenseMock;

	@Mock
	private ExpenseViewModel expenseViewModelMock;

	private static final String UID = "1234";
	private static final double AMOUNT = 500.50;
	private static final String DATE = "2014-11-13";
	private static final String USER_EMAIL = "test@company.com";
	private static final String COMMENT = "this is a comment";

	@InjectMocks
	private ExpenseService expenseService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		expenseService = new ExpenseService(expenseRepositoryMock);
	}

	@Test
	public void canInstantiateService() {
		assertNotNull(expenseService);
	}

	@Test(expected = ExpenseNotFoundException.class)
	public void retrieveExpenseByUidThrowsExceptionWhenExpenseDoesNotExist() throws Exception {
		when(expenseRepositoryMock.findByUid(UID)).thenReturn(null);
		expenseService.retrieveExpenseByUid(UID);
	}

	@Test
	public void retrieveExpenseByUidFindsExpenseWhenExists() throws Exception {
		when(expenseRepositoryMock.findByUid(UID)).thenReturn(expenseMock);
		Expense expense = expenseService.retrieveExpenseByUid(UID);
		assertEquals(expenseMock, expense);
	}

	@Test
	public void saveExpenseCallsStoreRepository() throws Exception {
		when(expenseViewModelMock.getuId()).thenReturn(UID);
		when(expenseViewModelMock.getAmount()).thenReturn(AMOUNT);
		when(expenseViewModelMock.getDate()).thenReturn(DATE);
		when(expenseViewModelMock.getUserEmail()).thenReturn(USER_EMAIL);
		when(expenseViewModelMock.getComment()).thenReturn(COMMENT);

		expenseService.saveExpense(expenseViewModelMock);

		verify(expenseRepositoryMock, times(1)).store(any(Expense.class));
	}

}
