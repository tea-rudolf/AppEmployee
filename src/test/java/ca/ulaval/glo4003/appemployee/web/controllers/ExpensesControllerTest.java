package ca.ulaval.glo4003.appemployee.web.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpSession;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import ca.ulaval.glo4003.appemployee.domain.expense.Expense;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.services.ExpenseService;
import ca.ulaval.glo4003.appemployee.services.TimeService;
import ca.ulaval.glo4003.appemployee.services.UserService;
import ca.ulaval.glo4003.appemployee.web.converters.ExpenseConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ExpenseViewModel;

public class ExpensesControllerTest {

	private static final String EMAIL_KEY = "email";
	private static final String VALID_EMAIL = "employee@employee.com";
	private static final String EXPENSE_UID = "id";
	private static final String EXPENSES_JSP = "expenses";
	private static final String CREATE_EXPENSE_JSP = "createExpense";
	private static final String REDIRECT_LINK = "redirect:/";
	private static final String REDIRECT_EXPENSE_LINK = "redirect:/expenses/";
	private static final String EXPENSES_SUBMIT_JSP = "expensesSubmitted";
	private static final String EDITED_EXPENSE_JSP = "editExpense";
	private static final LocalDate START_DATE = new LocalDate("2014-10-13");
	private static final LocalDate END_DATE = new LocalDate("2014-10-26");

	// private List<Expense> expenses = new ArrayList<Expense>();

	@Mock
	private TimeService payPeriodServiceMock;

	@Mock
	private ModelMap modelMapMock;

	@Mock
	private Model modelMock;

	@Mock
	private ExpenseViewModel expenseViewModelMock;

	@Mock
	private HttpSession sessionMock;

	@Mock
	private PayPeriod payPeriodMock;

	@Mock
	private ExpenseConverter expenseConverterMock;

	@Mock
	private ExpenseService expenseServiceMock;

	@Mock
	private UserService userServiceMock;

	@Mock
	private Expense expenseMock;

	@InjectMocks
	private ExpensesController expensesControllerMock;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		expensesControllerMock = new ExpensesController(expenseServiceMock, payPeriodServiceMock);
	}

	@Test
	public void getExpensesReturnsExpensesForm() {
		when(payPeriodServiceMock.retrieveCurrentPayPeriod()).thenReturn(payPeriodMock);
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(payPeriodMock.getStartDate()).thenReturn(START_DATE);
		when(payPeriodMock.getEndDate()).thenReturn(END_DATE);

		String returnedForm = expensesControllerMock.getExpenses(modelMapMock, sessionMock);

		assertEquals(EXPENSES_JSP, returnedForm);
	}

	@Test
	public void getExpensesReturnsRedirectIfMissingAttribute() {
		String returnedForm = expensesControllerMock.getExpenses(modelMapMock, sessionMock);
		assertEquals(REDIRECT_LINK, returnedForm);
	}

	// @Test
	// public void getExpensesCallConvertMethod() {
	// when(payPeriodServiceMock.retrieveCurrentPayPeriod()).thenReturn(payPeriodMock);
	// when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
	// when(payPeriodMock.getStartDate()).thenReturn(START_DATE);
	// when(payPeriodMock.getEndDate()).thenReturn(END_DATE);
	// when(userServiceMock.getExpensesForUserForAPayPeriod(payPeriodMock,
	// VALID_EMAIL)).thenReturn(expenses);
	//
	// expensesControllerMock.getExpenses(modelMapMock, sessionMock);
	//
	// verify(expenseConverterMock, times(1)).convert(expenses);
	// }

	@Test
	public void createExpenseReturnsRedirectLinkIfMissingAttribute() {
		String returnedForm = expensesControllerMock.createExpense(modelMock, expenseViewModelMock, sessionMock);
		assertEquals(REDIRECT_LINK, returnedForm);
	}

	@Test
	public void createExpenseReturnsCreateExpenseForm() {
		when(payPeriodServiceMock.retrieveCurrentPayPeriod()).thenReturn(payPeriodMock);
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(payPeriodMock.getStartDate()).thenReturn(START_DATE);
		when(payPeriodMock.getEndDate()).thenReturn(END_DATE);

		String returnedForm = expensesControllerMock.createExpense(modelMock, expenseViewModelMock, sessionMock);

		assertEquals(CREATE_EXPENSE_JSP, returnedForm);
	}

	@Test
	public void saveExpenseReturnsSubmittedExpensesForIfSuccessfulSubmit() throws Exception {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);

		String returnedForm = expensesControllerMock.saveExpense(modelMock, expenseViewModelMock, sessionMock);

		assertEquals(EXPENSES_SUBMIT_JSP, returnedForm);
	}

	// @Test
	// public void saveExpenseCallsConvertMethod() throws Exception {
	// when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
	// expensesControllerMock.saveExpense(modelMock, expenseViewModelMock,
	// sessionMock);
	// verify(expenseConverterMock, times(1)).convert(expenseViewModelMock);
	// }
	//
	// @Test
	// public void saveExpenseCallsStoreMethod() throws Exception {
	// when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
	// when(expenseConverterMock.convert(expenseViewModelMock)).thenReturn(expenseMock);
	// expensesControllerMock.saveExpense(modelMock, expenseViewModelMock,
	// sessionMock);
	// verify(expenseServiceMock, times(1)).saveExpense(expenseMock);
	// }

	@Test
	public void editExpenseReturnsRedirectLinkIfMissingAttribute() throws Exception {
		String returnedForm = expensesControllerMock.editExpense(EXPENSE_UID, modelMock, sessionMock);
		assertEquals(REDIRECT_LINK, returnedForm);
	}

	@Test
	public void editExpenseReturnsEditedExpenseForm() throws Exception {
		when(payPeriodServiceMock.retrieveCurrentPayPeriod()).thenReturn(payPeriodMock);
		when(expenseServiceMock.retrieveExpenseByUid(EXPENSE_UID)).thenReturn(expenseMock);
		when(expenseConverterMock.convert(expenseMock)).thenReturn(expenseViewModelMock);
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(payPeriodMock.getStartDate()).thenReturn(START_DATE);
		when(payPeriodMock.getEndDate()).thenReturn(END_DATE);

		String returnedForm = expensesControllerMock.editExpense(EXPENSE_UID, modelMock, sessionMock);

		assertEquals(EDITED_EXPENSE_JSP, returnedForm);
	}

	@Test
	public void saveEditedExpenseCallsUpdateMethod() throws Exception {
		expensesControllerMock.saveEditedExpense(EXPENSE_UID, expenseViewModelMock, sessionMock);
		verify(expenseServiceMock, times(1)).updateExpense(expenseViewModelMock);
	}

	@Test
	public void saveEditedExpenseReturnsRedirectExpenseLink() throws Exception {
		String returnedForm = expensesControllerMock.saveEditedExpense(EXPENSE_UID, expenseViewModelMock, sessionMock);
		assertEquals(REDIRECT_EXPENSE_LINK, returnedForm);
	}
}
