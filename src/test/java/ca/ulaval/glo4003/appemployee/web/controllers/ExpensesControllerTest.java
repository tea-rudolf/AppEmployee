//package ca.ulaval.glo4003.appemployee.web.controllers;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//import javax.servlet.http.HttpSession;
//
//import org.joda.time.LocalDate;
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.ui.ModelMap;
//
//import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
//import ca.ulaval.glo4003.appemployee.domain.repository.ExpenseRepository;
//import ca.ulaval.glo4003.appemployee.services.ExpenseService;
//import ca.ulaval.glo4003.appemployee.services.PayPeriodService;
//import ca.ulaval.glo4003.appemployee.services.UserService;
//import ca.ulaval.glo4003.appemployee.web.converters.ExpenseConverter;
//import ca.ulaval.glo4003.appemployee.web.viewmodels.ExpenseViewModel;
//
//public class ExpensesControllerTest {
//
//	private static final String EMAIL_KEY = "email";
//	private static final String VALID_EMAIL = "employee@employee.com";
//	private static final String EXPENSES_JSP = "expenses";
//	private static final String EXPENSES_SUBMIT_JSP = "expensesSubmitted";
//	private static final LocalDate START_DATE = new LocalDate("2014-10-13");
//	private static final LocalDate END_DATE = new LocalDate("2014-10-26");
//
//	private PayPeriodService payPeriodServiceMock;
//	private ExpensesController expensesControllerMock;
//	private ModelMap modelMapMock;
//	private ExpenseViewModel expenseViewModelMock;
//	private HttpSession sessionMock;
//	private PayPeriod payPeriodMock;
//	private ExpenseConverter expenseConverterMock;
//	private ExpenseService expenseServiceMock;
//	private UserService userServiceMock;
//
//	@Before
//	public void init() {
//		payPeriodServiceMock = mock(PayPeriodService.class);
//		expensesControllerMock = mock(ExpensesController.class);
//		modelMapMock = mock(ModelMap.class);
//		expenseViewModelMock = mock(ExpenseViewModel.class);
//		sessionMock = mock(HttpSession.class);
//		payPeriodMock = mock(PayPeriod.class);
//		expenseConverterMock = mock(ExpenseConverter.class);
//		expenseServiceMock = mock(ExpenseService.class);
//		userServiceMock = mock(UserService.class);
//		expensesControllerMock = new ExpensesController(expenseServiceMock, expenseConverterMock, payPeriodServiceMock, userServiceMock);
//	}
//
//	@Test
//	public void getExpensesReturnsExpensesForm() {
//		when(payPeriodServiceMock.getCurrentPayPeriod()).thenReturn(payPeriodMock);
//		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
//		when(payPeriodMock.getStartDate()).thenReturn(START_DATE);
//		when(payPeriodMock.getEndDate()).thenReturn(END_DATE);
//
//		String returnedForm = expensesControllerMock.getExpenses(modelMapMock, sessionMock);
//
//		assertEquals(EXPENSES_JSP, returnedForm);
//	}
//
//	@Test
//	public void saveTimeReturnsSubmittedExpensesForIfSuccessfulSubmit() throws Exception {
//		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
//
//		String returnedForm = expensesControllerMock.saveExpenses(expenseViewModelMock, sessionMock);
//
//		assertEquals(EXPENSES_SUBMIT_JSP, returnedForm);
//	}
//}
