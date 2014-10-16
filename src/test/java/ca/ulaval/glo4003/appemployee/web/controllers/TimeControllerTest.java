//package ca.ulaval.glo4003.appemployee.web.controllers;
//
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.*;
//
//import javax.servlet.http.HttpSession;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.runners.MockitoJUnitRunner;
//import org.springframework.ui.ModelMap;
//
//import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
//import ca.ulaval.glo4003.appemployee.domain.user.User;
//import ca.ulaval.glo4003.appemployee.domain.user.UserNotFoundException;
//import ca.ulaval.glo4003.appemployee.services.PayPeriodService;
//import ca.ulaval.glo4003.appemployee.web.converters.PayPeriodConverter;
//import ca.ulaval.glo4003.appemployee.web.viewmodels.PayPeriodViewModel;
//
//@RunWith(MockitoJUnitRunner.class)
//public class TimeControllerTest {
//
//	private static final String EMAIL_KEY = "email";
//	private static final String PAY_PERIOD_KEY = "payPeriodForm";
//	private static final String VALID_EMAIL = "test@test.com";
//	private static final String ERROR_MESSAGE = "message";
//	private static final String TIME_SHEET_JSP = "timeSheet";
//	private static final String TIME_SHEET_SUBMIT_JSP = "timeSheetSubmitted";
//
//	private PayPeriodService payPeriodServiceMock;
//	private PayPeriodConverter payPeriodConverterMock;
//	private TimeController timeControllerMock;
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
//		timeControllerMock = mock(TimeController.class);
//		modelMapMock = mock(ModelMap.class);
//		payPeriodViewModelMock = mock(PayPeriodViewModel.class);
//		sessionMock = mock(HttpSession.class);
//		payPeriodMock = mock(PayPeriod.class);
//		userMock = mock(User.class);
//		timeControllerMock = new TimeController(payPeriodServiceMock, payPeriodConverterMock);
//	}
//
//	@Test
//	public void getTimeReturnsTimeSheet() {
//		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
//		when(payPeriodServiceMock.getUserByEmail(VALID_EMAIL)).thenReturn(userMock);
//		when(userMock.getCurrentPayPeriod()).thenReturn(payPeriodMock);
//		when(payPeriodConverterMock.convert(payPeriodMock)).thenReturn(payPeriodViewModelMock);
//
//		String returnedForm = timeControllerMock.getTime(modelMapMock, sessionMock);
//
//		assertEquals(TIME_SHEET_JSP, returnedForm);
//	}
//
//	@Test
//	public void saveTimeReturnsSubmittedTimeSheetIfSuccessfulSubmit() {
//		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
//		when(payPeriodServiceMock.getUserByEmail(VALID_EMAIL)).thenReturn(userMock);
//
//		String returnedForm = timeControllerMock.saveTime(payPeriodViewModelMock, sessionMock);
//
//		assertEquals(TIME_SHEET_SUBMIT_JSP, returnedForm);
//	}
//
//	@Test
//	public void addsUserEmailWhenGetTime() {
//		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
//		when(payPeriodServiceMock.getUserByEmail(VALID_EMAIL)).thenReturn(userMock);
//		when(userMock.getCurrentPayPeriod()).thenReturn(payPeriodMock);
//		when(payPeriodConverterMock.convert(payPeriodMock)).thenReturn(payPeriodViewModelMock);
//		when(userMock.getEmail()).thenReturn(VALID_EMAIL);
//
//		timeControllerMock.getTime(modelMapMock, sessionMock);
//
//		verify(modelMapMock).addAttribute(PAY_PERIOD_KEY, payPeriodViewModelMock);
//		verify(modelMapMock).addAttribute(EMAIL_KEY, VALID_EMAIL);
//	}
//
//	@Test(expected = UserNotFoundException.class)
//	public void getUserByEmailThrowsExceptionWhenUserNotFound() {
//		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
//		when(payPeriodServiceMock.getUserByEmail(VALID_EMAIL)).thenThrow(new UserNotFoundException(ERROR_MESSAGE));
//		when(userMock.getCurrentPayPeriod()).thenReturn(payPeriodMock);
//		when(payPeriodConverterMock.convert(payPeriodMock)).thenReturn(payPeriodViewModelMock);
//
//		timeControllerMock.getTime(modelMapMock, sessionMock);
//	}
//}
