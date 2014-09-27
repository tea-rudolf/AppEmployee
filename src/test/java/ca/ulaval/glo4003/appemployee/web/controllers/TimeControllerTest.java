package ca.ulaval.glo4003.appemployee.web.controllers;

import static org.junit.Assert.*;

import javax.servlet.http.HttpSession;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;

import ca.ulaval.glo4003.appemployee.domain.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.appemployee.services.PayPeriodService;
import ca.ulaval.glo4003.appemployee.web.converters.PayPeriodConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.PayPeriodViewModel;

@RunWith(MockitoJUnitRunner.class)
public class TimeControllerTest {
	
<<<<<<< HEAD
	private static final String EMAIL_KEY = "email";
	private static final String PAY_PERIOD_KEY = "payPeriodForm";
	private static final String VALID_EMAIL = "test@test.com";
	
=======
>>>>>>> d0a7fa8848a6556988797b96396e79ad3188b262
	@Mock
	private PayPeriodService payPeriodService;
	
	@Mock
	private PayPeriodConverter payPeriodConverter;
	
	@InjectMocks
	private TimeController timeController;
<<<<<<< HEAD
	
=======

>>>>>>> d0a7fa8848a6556988797b96396e79ad3188b262
	private ModelMap model;
	private PayPeriodViewModel payPeriodViewModel;
	private HttpSession session;
	
	@Before
	public void init(){
		model = new ModelMap();
		session.setAttribute(EMAIL_KEY, VALID_EMAIL);
		payPeriodViewModel = new PayPeriodViewModel();
	}

	@Test
	public void getTimeReturnsTimeSheet() {
		String returnedForm = timeController.getTime(model, session);
		assertEquals("time", returnedForm);
	}
	
	@Test
	public void saveTimeReturnsSubmittedTimeSheetIfSuccessfulSubmit(){
		String returnedForm = timeController.saveTime(payPeriodViewModel, session);
		assertEquals("timeSheetSubmitted", returnedForm);
	}
	
<<<<<<< HEAD
	@Test
	public void addsUserEmailWhenGetTime(){
		timeController.getTime(model, session);
		assertTrue(model.containsKey(EMAIL_KEY));
	}
	
	@Test
	public void addsPayPeriodFormWhenGetTime(){
		timeController.getTime(model, session);
		assertTrue(model.containsKey(PAY_PERIOD_KEY));
	}

=======
	
>>>>>>> d0a7fa8848a6556988797b96396e79ad3188b262
}
