package ca.ulaval.glo4003.appemployee.web.controllers;

import static org.junit.Assert.*;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;

import static org.mockito.Mockito.*;
import ca.ulaval.glo4003.appemployee.domain.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.appemployee.services.PayPeriodService;
import ca.ulaval.glo4003.appemployee.web.converters.PayPeriodConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.PayPeriodViewModel;

@RunWith(MockitoJUnitRunner.class)
public class TimeControllerTest {
	

	private static final String EMAIL_KEY = "email";
	private static final String PAY_PERIOD_KEY = "payPeriodForm";
	private static final String VALID_EMAIL = "test@test.com";
	private static final String VALID_PASSWORD = "1234";
	private static final String ERROR_MESSAGE = "message";
	//private static final LocalDate START_DATE = new LocalDate(2014,9,22);
	//private static final LocalDate END_DATE = new LocalDate(2014,10,3);
	
	PayPeriodService payPeriodServiceMock;
	PayPeriodConverter payPeriodConverterMock;
	TimeController timeControllerMock;
	ModelMap modelMapMock;
	PayPeriodViewModel payPeriodViewModelMock;
	HttpSession sessionMock;
	PayPeriod payPeriodMock;
	User userMock;
	
	@Before
	public void init(){
		payPeriodServiceMock = mock(PayPeriodService.class);
		payPeriodConverterMock = mock(PayPeriodConverter.class);
		timeControllerMock = mock(TimeController.class);
		modelMapMock = mock(ModelMap.class);
		payPeriodViewModelMock = mock(PayPeriodViewModel.class);
		sessionMock = mock(HttpSession.class);
		payPeriodMock = mock(PayPeriod.class);
		userMock = mock(User.class);
		timeControllerMock = new TimeController(payPeriodServiceMock, payPeriodConverterMock);
		userMock = new User(VALID_EMAIL, VALID_PASSWORD);
	}

	@Test
	public void getTimeReturnsTimeSheet() {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(userMock.getCurrentPayPeriod()).thenReturn(payPeriodMock);
		when(payPeriodConverterMock.convert(payPeriodMock)).thenReturn(payPeriodViewModelMock);
		
		String returnedForm = timeControllerMock.getTime(modelMapMock, sessionMock);
		
		assertEquals("time", returnedForm);
	}
	
	@Test
	public void saveTimeReturnsSubmittedTimeSheetIfSuccessfulSubmit(){
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		
		String returnedForm = timeControllerMock.saveTime(payPeriodViewModelMock, sessionMock);
		
		assertEquals("timeSheetSubmitted", returnedForm);
	}
	
	@Test
	public void addsUserEmailWhenGetTime(){
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(userMock.getCurrentPayPeriod()).thenReturn(payPeriodMock);
		when(payPeriodConverterMock.convert(payPeriodMock)).thenReturn(payPeriodViewModelMock);
		
		timeControllerMock.getTime(modelMapMock, sessionMock);
		
		assertTrue(modelMapMock.containsKey(EMAIL_KEY));
	}
	
	@Test
	public void addsPayPeriodFormWhenGetTime(){
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(userMock.getCurrentPayPeriod()).thenReturn(payPeriodMock);
		when(payPeriodConverterMock.convert(payPeriodMock)).thenReturn(payPeriodViewModelMock);
		
		timeControllerMock.getTime(modelMapMock, sessionMock);
		
		assertTrue(modelMapMock.containsKey(PAY_PERIOD_KEY));
	}
	
	@Test(expected = UserNotFoundException.class)
	public void getUserByEmailThrowsExceptionWhenUserNotFound(){
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(payPeriodServiceMock.getUserByEmail(VALID_EMAIL)).thenThrow(new UserNotFoundException(ERROR_MESSAGE));
		when(userMock.getCurrentPayPeriod()).thenReturn(payPeriodMock);
		when(payPeriodConverterMock.convert(payPeriodMock)).thenReturn(payPeriodViewModelMock);
		
		timeControllerMock.getTime(modelMapMock, sessionMock);
	}
}
