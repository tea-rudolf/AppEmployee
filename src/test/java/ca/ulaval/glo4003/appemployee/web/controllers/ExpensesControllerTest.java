//package ca.ulaval.glo4003.appemployee.web.controllers;
//
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.*;
//
//import javax.servlet.http.HttpSession;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.ui.ModelMap;
//
//import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
//import ca.ulaval.glo4003.appemployee.domain.user.User;
//import ca.ulaval.glo4003.appemployee.domain.user.UserNotFoundException;
//import ca.ulaval.glo4003.appemployee.services.PayPeriodService;
//import ca.ulaval.glo4003.appemployee.web.converters.PayPeriodConverter;
//import ca.ulaval.glo4003.appemployee.web.viewmodels.PayPeriodViewModel;
//
//public class ExpensesControllerTest {
//
//	private static final String EMAIL_KEY = "email";
//	private static final String PAY_PERIOD_KEY = "payPeriodForm";
//	private static final String VALID_EMAIL = "test@test.com";
//	private static final String ERROR_MESSAGE = "message";
//	private static final String EXPENSES_JSP = "expenses";
//	private static final String EXPENSES_SUBMIT_JSP = "expensesSubmitted";
//
//	private PayPeriodService payPeriodServiceMock;
//	private PayPeriodConverter payPeriodConverterMock;
//	private ExpensesController expensesControllerMock;
//	private ModelMap modelMapMock;
//	private PayPeriodViewModel payPeriodViewModelMock;
//	private HttpSession sessionMock;
//	private PayPeriod payPeriodMock;
//	private User userMock;
//
//	@Before
//	public void init() {
//		payPeriodServiceMock = mock(PayPeriodService.class);
//		payPeriodConverterMock = mock(PayPeriodConverter.class);
//		expensesControllerMock = mock(ExpensesController.class);
//		modelMapMock = mock(ModelMap.class);
//		payPeriodViewModelMock = mock(PayPeriodViewModel.class);
//		sessionMock = mock(HttpSession.class);
//		payPeriodMock = mock(PayPeriod.class);
//		userMock = mock(User.class);
//		expensesControllerMock = new ExpensesController(payPeriodServiceMock, payPeriodConverterMock);
//	}
//
//	@Test
//	public void getExpensesReturnsExpensesForm() {
//		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
//		when(payPeriodServiceMock.getUserByEmail(VALID_EMAIL)).thenReturn(userMock);
//		when(userMock.getCurrentPayPeriod()).thenReturn(payPeriodMock);
//		when(payPeriodConverterMock.convert(payPeriodMock)).thenReturn(payPeriodViewModelMock);
//
//		String returnedForm = expensesControllerMock.getExpenses(modelMapMock, sessionMock);
//
//		assertEquals(EXPENSES_JSP, returnedForm);
//	}
//
//	@Test
//	public void saveTimeReturnsSubmittedExpensesForIfSuccessfulSubmit() {
//		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
//		when(payPeriodServiceMock.getUserByEmail(VALID_EMAIL)).thenReturn(userMock);
//
//		String returnedForm = expensesControllerMock.saveExpenses(payPeriodViewModelMock, sessionMock);
//
//		assertEquals(EXPENSES_SUBMIT_JSP, returnedForm);
//	}
//
//	@Test
//	public void addsUserEmailWhenGetExpenses() {
//		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
//		when(payPeriodServiceMock.getUserByEmail(VALID_EMAIL)).thenReturn(userMock);
//		when(userMock.getCurrentPayPeriod()).thenReturn(payPeriodMock);
//		when(payPeriodConverterMock.convert(payPeriodMock)).thenReturn(payPeriodViewModelMock);
//		when(userMock.getEmail()).thenReturn(VALID_EMAIL);
//
//		expensesControllerMock.getExpenses(modelMapMock, sessionMock);
//
//		verify(modelMapMock).addAttribute(PAY_PERIOD_KEY, payPeriodViewModelMock);
//	}
//
//	@Test(expected = UserNotFoundException.class)
//	public void getUserByEmailThrowsExceptionWhenUserNotFound() {
//		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
//		when(payPeriodServiceMock.getUserByEmail(VALID_EMAIL)).thenThrow(new UserNotFoundException(ERROR_MESSAGE));
//		when(userMock.getCurrentPayPeriod()).thenReturn(payPeriodMock);
//		when(payPeriodConverterMock.convert(payPeriodMock)).thenReturn(payPeriodViewModelMock);
//
//		expensesControllerMock.getExpenses(modelMapMock, sessionMock);
//	}
//}
