package ca.ulaval.glo4003.appemployee.web.controllers;

import static org.junit.Assert.*;

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
	
	//private static final String VALID_PASSWORD = "password";
	//private static final String VALID_EMAIL = "email@email.com";
	
	@Mock
	private PayPeriodService payPeriodService;
	
	@Mock
	private PayPeriodConverter payPeriodConverter;
	
	@InjectMocks
	private TimeController timeController;
	
	//private User user;
	//private PayPeriod payPeriod;
	private ModelMap model;
	private PayPeriodViewModel payPeriodViewModel;
	
	@Before
	public void init(){
		model = new ModelMap();
		payPeriodViewModel = new PayPeriodViewModel();
		//user = new User(VALID_EMAIL, VALID_PASSWORD);
	}

	@Test
	public void getTimeReturnsTimeSheet() {
		String returnedForm = timeController.getTime(model, null);
		assertEquals("time", returnedForm);
	}
	
	@Test
	public void saveTimeReturnsSubmittedTimeSheetIfSuccessfulSubmit(){
		String returnedForm = timeController.saveTime(payPeriodViewModel, null);
		assertEquals("timeSheetSubmitted", returnedForm);
	}
	
	//@Test
	//public void get()

}
