package ca.ulaval.glo4003.appemployee.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ca.ulaval.glo4003.appemployee.domain.expense.Expense;
import ca.ulaval.glo4003.appemployee.domain.expense.ExpenseProcessor;
import ca.ulaval.glo4003.appemployee.domain.time.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.time.TimeProcessor;
import ca.ulaval.glo4003.appemployee.web.converters.ExpenseConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ExpenseViewModel;

public class ExpenseServiceTest {

	private List<ExpenseViewModel> expenseModels = new ArrayList<ExpenseViewModel>();
	private List<Expense> expenses = new ArrayList<Expense>();

	private static final String UID = "1234";
	private static final double AMOUNT = 500.50;
	private static final String DATE = "2014-11-13";
	private static final String USER_EMAIL = "test@company.com";
	private static final String COMMENT = "this is a comment";

	@Mock
	private Expense expenseMock;

	@Mock
	private PayPeriod payPeriodMock;

	@Mock
	private ExpenseViewModel expenseViewModelMock;

	@Mock
	private TimeProcessor timeProcessorMock;

	@Mock
	private ExpenseConverter expenseConverterMock;

	@Mock
	private ExpenseProcessor expenseProcessorMock;

	@InjectMocks
	private ExpenseService expenseService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		expenseService = new ExpenseService(timeProcessorMock, expenseProcessorMock, expenseConverterMock);
	}

	@Test
	public void canInstantiateService() {
		assertNotNull(expenseService);
	}

	@Test
	public void retrieveExpenseByUidReturnsValidExpense() throws Exception {
		when(expenseProcessorMock.retrieveExpenseByUid(UID)).thenReturn(expenseMock);
		Expense returnedExpense = expenseService.retrieveExpenseByUid(UID);
		assertEquals(expenseMock, returnedExpense);
	}

	@Test
	public void createExpenseCallsCorrectProcessorMethod() throws Exception {
		when(expenseViewModelMock.getAmount()).thenReturn(AMOUNT);
		when(expenseViewModelMock.getComment()).thenReturn(COMMENT);
		when(expenseViewModelMock.getUid()).thenReturn(UID);
		when(expenseViewModelMock.getUserEmail()).thenReturn(USER_EMAIL);
		when(expenseViewModelMock.getDate()).thenReturn(DATE);

		expenseService.createExpense(expenseViewModelMock);

		verify(expenseProcessorMock, times(1)).createExpense(AMOUNT, new LocalDate(DATE), USER_EMAIL, COMMENT);
	}

	@Test
	public void editExpenseCallsCorrectProcessorMethod() throws Exception {
		when(expenseViewModelMock.getAmount()).thenReturn(AMOUNT);
		when(expenseViewModelMock.getComment()).thenReturn(COMMENT);
		when(expenseViewModelMock.getUid()).thenReturn(UID);
		when(expenseViewModelMock.getUserEmail()).thenReturn(USER_EMAIL);
		when(expenseViewModelMock.getDate()).thenReturn(DATE);

		expenseService.editExpense(expenseViewModelMock);

		verify(expenseProcessorMock, times(1)).editExpense(UID, AMOUNT, new LocalDate(DATE), USER_EMAIL, COMMENT);
	}

	@Test
	public void retrieveExpenseViewModelReturnsValidExpenseViewModel() throws Exception {
		when(expenseProcessorMock.retrieveExpenseByUid(UID)).thenReturn(expenseMock);
		when(expenseConverterMock.convert(expenseMock)).thenReturn(expenseViewModelMock);

		ExpenseViewModel returnedExpenseModel = expenseService.retrieveExpenseViewModel(UID);

		assertEquals(expenseViewModelMock, returnedExpenseModel);
	}

	@Test
	public void retrieveExpenseViewModelsListForCurrentPayPeriodReturnsCollectionOfExpenseViewModels() {
		when(expenseConverterMock.convert(expenses)).thenReturn(expenseModels);
		Collection<ExpenseViewModel> returnedExpenseModels = expenseService
				.retrieveExpenseViewModelsListForCurrentPayPeriod(USER_EMAIL);
		assertEquals(expenseModels, returnedExpenseModels);
	}

	@Test
	public void retrieveUserExpenseViewModelReturnsExpenseViewModel() {
		ExpenseViewModel expenseViewModel = expenseService.retrieveUserExpenseViewModel(USER_EMAIL);
		assertEquals(USER_EMAIL, expenseViewModel.getUserEmail());
	}

}
