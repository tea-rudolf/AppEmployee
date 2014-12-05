package ca.ulaval.glo4003.appemployee.domain.expense;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ca.ulaval.glo4003.appemployee.domain.exceptions.ExpenseNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.repository.ExpenseRepository;
import ca.ulaval.glo4003.appemployee.domain.time.PayPeriod;

public class ExpenseProcessorTest {

	@Mock
	private ExpenseRepository expenseRepositoryMock;

	@Mock
	private Expense expenseMock;

	@Mock
	private PayPeriod payPeriodMock;

	@InjectMocks
	private ExpenseProcessor expenseProcessor;

	private static final double AMOUNT = 150.50;
	private static final LocalDate DATE = new LocalDate("2014-12-10");
	private static final String EMAIL = "test@test.com";
	private static final String EMAIL2 = "test2@test.com";
	private static final String COMMENT = "dummy comment";
	private static final String EXPENSE_UID = "0001";
	private static final LocalDate BEFORE_START_DATE = new LocalDate("2014-12-07");
	private static final LocalDate PAYPERIOD_START_DATE = new LocalDate("2014-12-08");
	private static final LocalDate PAYPERIOD_END_DATE = new LocalDate("2014-12-21");
	private static final LocalDate AFTER_END_DATE = new LocalDate("2014-12-22");

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		expenseProcessor = new ExpenseProcessor(expenseRepositoryMock);
	}

	@Test
	public void canInstantiateExpenseProcessor() {
		assertNotNull(expenseProcessor);
	}

	@Test
	public void createExpenseStoresObjectInRepository() throws Exception {
		ArgumentCaptor<Expense> expenseArgumentCaptor = ArgumentCaptor.forClass(Expense.class);
		expenseProcessor.createExpense(AMOUNT, DATE, EMAIL, COMMENT);
		verify(expenseRepositoryMock, times(1)).store(expenseArgumentCaptor.capture());
	}

	@Test(expected = ExpenseNotFoundException.class)
	public void editExpenseThrowsExceptionWhenExpenseNotFound() throws Exception {
		when(expenseRepositoryMock.findByUid(EXPENSE_UID)).thenReturn(null);
		expenseProcessor.editExpense(EXPENSE_UID, AMOUNT, DATE, EMAIL, COMMENT);
	}

	@Test
	public void editExpenseUpdateObjectInRepository() throws Exception {
		when(expenseRepositoryMock.findByUid(EXPENSE_UID)).thenReturn(expenseMock);

		expenseProcessor.editExpense(EXPENSE_UID, AMOUNT, DATE, EMAIL, COMMENT);

		verify(expenseMock, times(1)).update(AMOUNT, DATE, EMAIL, COMMENT);
		verify(expenseRepositoryMock, times(1)).store(expenseMock);
	}

	@Test(expected = ExpenseNotFoundException.class)
	public void retrieveExpenseByUidThrowsExceptionWhenExpenseNotFound() throws Exception {
		when(expenseRepositoryMock.findByUid(EXPENSE_UID)).thenReturn(null);
		expenseProcessor.retrieveExpenseByUid(EXPENSE_UID);
	}

	@Test
	public void retrieveExpenseByUidReturnsExpenseWhenFound() throws Exception {
		when(expenseRepositoryMock.findByUid(EXPENSE_UID)).thenReturn(expenseMock);
		Expense actualExpense = expenseProcessor.retrieveExpenseByUid(EXPENSE_UID);
		assertEquals(expenseMock, actualExpense);
	}

	@Test
	public void retrieveUserExpensesForCurrentPayPeriodReturnsExpensesList() {
		List<Expense> expenses = new ArrayList<Expense>();
		expenses.add(expenseMock);
		when(expenseRepositoryMock.findAll()).thenReturn(expenses);
		when(expenseMock.getUserEmail()).thenReturn(EMAIL);
		when(expenseMock.getDate()).thenReturn(DATE);
		when(payPeriodMock.getStartDate()).thenReturn(PAYPERIOD_START_DATE);
		when(payPeriodMock.getEndDate()).thenReturn(PAYPERIOD_END_DATE);

		List<Expense> actualExpenses = expenseProcessor.retrieveUserExpensesForCurrentPayPeriod(EMAIL, payPeriodMock);

		assertEquals(expenses, actualExpenses);
	}

	@Test
	public void retrieveUserExpensesForCurrentPayPeriodDoNotReturnExpenseWhenUserExpensesNotFound() {
		List<Expense> expenses = new ArrayList<Expense>();
		expenses.add(expenseMock);
		when(expenseRepositoryMock.findAll()).thenReturn(expenses);
		when(expenseMock.getUserEmail()).thenReturn(EMAIL);
		when(expenseMock.getDate()).thenReturn(DATE);
		when(payPeriodMock.getStartDate()).thenReturn(PAYPERIOD_START_DATE);
		when(payPeriodMock.getEndDate()).thenReturn(PAYPERIOD_END_DATE);

		List<Expense> actualExpenses = expenseProcessor.retrieveUserExpensesForCurrentPayPeriod(EMAIL2, payPeriodMock);

		assertEquals(0, actualExpenses.size());
	}

	@Test
	public void retrieveUserExpensesForCurrentPayPeriodDoNotReturnExpenseWhenExpensesDateBeforeCurrentPayPeriod() {
		List<Expense> expenses = new ArrayList<Expense>();
		expenses.add(expenseMock);
		when(expenseRepositoryMock.findAll()).thenReturn(expenses);
		when(expenseMock.getUserEmail()).thenReturn(EMAIL);
		when(expenseMock.getDate()).thenReturn(BEFORE_START_DATE);
		when(payPeriodMock.getStartDate()).thenReturn(PAYPERIOD_START_DATE);
		when(payPeriodMock.getEndDate()).thenReturn(PAYPERIOD_END_DATE);

		List<Expense> actualExpenses = expenseProcessor.retrieveUserExpensesForCurrentPayPeriod(EMAIL, payPeriodMock);

		assertEquals(0, actualExpenses.size());
	}

	@Test
	public void retrieveUserExpensesForCurrentPayPeriodDoNotReturnExpenseWhenExpensesDateAfterCurrentPayPeriod() {
		List<Expense> expenses = new ArrayList<Expense>();
		expenses.add(expenseMock);
		when(expenseRepositoryMock.findAll()).thenReturn(expenses);
		when(expenseMock.getUserEmail()).thenReturn(EMAIL);
		when(expenseMock.getDate()).thenReturn(AFTER_END_DATE);
		when(payPeriodMock.getStartDate()).thenReturn(PAYPERIOD_START_DATE);
		when(payPeriodMock.getEndDate()).thenReturn(PAYPERIOD_END_DATE);

		List<Expense> actualExpenses = expenseProcessor.retrieveUserExpensesForCurrentPayPeriod(EMAIL, payPeriodMock);

		assertEquals(0, actualExpenses.size());
	}

}
