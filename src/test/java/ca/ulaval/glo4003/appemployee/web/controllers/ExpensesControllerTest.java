package ca.ulaval.glo4003.appemployee.web.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import ca.ulaval.glo4003.appemployee.domain.expense.Expense;
import ca.ulaval.glo4003.appemployee.domain.time.PayPeriod;
import ca.ulaval.glo4003.appemployee.services.ExpenseService;
import ca.ulaval.glo4003.appemployee.services.TimeService;
import ca.ulaval.glo4003.appemployee.services.UserService;
import ca.ulaval.glo4003.appemployee.web.converters.ExpenseConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ExpenseViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.PayPeriodViewModel;

public class ExpensesControllerTest {

	private static final String EMAIL_KEY = "email";
	private static final String VALID_EMAIL = "employee@employee.com";
	private static final String EXPENSE_UID = "id";
	private static final String EXPENSES_JSP = "expenses";
	private static final String CREATE_EXPENSE_JSP = "createExpense";
	private static final String REDIRECT_EXPENSE_LINK = "redirect:/expenses/";
	private static final String EDIT_EXPENSE_JSP = "editExpense";

	private Collection<ExpenseViewModel> expensesViewModels;

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

	@Mock
	private PayPeriodViewModel payPeriodViewModelMock;

	@InjectMocks
	private ExpensesController expensesControllerMock;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		expensesControllerMock = new ExpensesController(expenseServiceMock,
				payPeriodServiceMock);
	}

	@Test
	public void showExpensesListReturnsExpensesFormIfViewModelIsValid() {
		when(
				expenseServiceMock
						.retrieveExpenseViewModelsListForCurrentPayPeriod(VALID_EMAIL))
				.thenReturn(expensesViewModels);
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		String returnedForm = expensesControllerMock.showExpensesList(
				modelMapMock, sessionMock);

		assertEquals(EXPENSES_JSP, returnedForm);
	}

	@Test
	public void showCreateExpenseFormReturnsCreateExpenseFormWhenViewModelIsValid() {
		when(payPeriodServiceMock.retrieveCurrentPayPeriodViewModel())
				.thenReturn(payPeriodViewModelMock);
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);

		String returnedForm = expensesControllerMock.showCreateExpenseForm(
				modelMock, expenseViewModelMock, sessionMock);

		assertEquals(CREATE_EXPENSE_JSP, returnedForm);
	}

	@Test
	public void createExpenseReturnsSubmittedExpensesForIfSuccessfulSubmit()
			throws Exception {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);

		String returnedForm = expensesControllerMock.createExpense(modelMock,
				expenseViewModelMock, sessionMock);

		assertEquals(REDIRECT_EXPENSE_LINK, returnedForm);
	}

	@Test
	public void createExpenseCallsCorrectServiceMethod() throws Exception {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		expensesControllerMock.createExpense(modelMock, expenseViewModelMock,
				sessionMock);
		verify(expenseServiceMock, times(1))
				.createExpense(expenseViewModelMock);
	}

	@Test
	public void showEditExpenseFormReturnsEditedExpenseForm() throws Exception {
		when(payPeriodServiceMock.retrieveCurrentPayPeriodViewModel())
				.thenReturn(payPeriodViewModelMock);
		when(expenseServiceMock.retrieveExpenseViewModel(EXPENSE_UID))
				.thenReturn(expenseViewModelMock);
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);

		String returnedForm = expensesControllerMock.showEditExpenseForm(
				EXPENSE_UID, modelMock, sessionMock);

		assertEquals(EDIT_EXPENSE_JSP, returnedForm);
	}

	@Test
	public void editExpenseCallsCorrectServiceMethod() throws Exception {
		expensesControllerMock.editExpense(EXPENSE_UID, expenseViewModelMock,
				sessionMock);
		verify(expenseServiceMock, times(1)).editExpense(expenseViewModelMock);
	}

	@Test
	public void editExpenseReturnsRedirectExpenseLink() throws Exception {
		String returnedForm = expensesControllerMock.editExpense(EXPENSE_UID,
				expenseViewModelMock, sessionMock);
		assertEquals(REDIRECT_EXPENSE_LINK, returnedForm);
	}
}
