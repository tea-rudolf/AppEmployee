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
	
	@Mock
	private PayPeriodService payPeriodService;
	
	@Mock
	private PayPeriodConverter payPeriodConverter;
	
	@InjectMocks
	private TimeController timeController;

	private ModelMap model;
	private PayPeriodViewModel payPeriodViewModel;
	
	@Before
	public void init(){
		model = new ModelMap();
		payPeriodViewModel = new PayPeriodViewModel();
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
	
	
}
