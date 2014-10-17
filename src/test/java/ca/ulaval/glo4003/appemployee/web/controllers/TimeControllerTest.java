package ca.ulaval.glo4003.appemployee.web.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;

import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.task.TaskRepository;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntry;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntryRepository;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.domain.user.UserRepository;
import ca.ulaval.glo4003.appemployee.services.PayPeriodService;
import ca.ulaval.glo4003.appemployee.services.UserService;
import ca.ulaval.glo4003.appemployee.web.converters.PayPeriodConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.PayPeriodViewModel;

@RunWith(MockitoJUnitRunner.class)
public class TimeControllerTest {

	private static final String EMAIL_KEY = "email";
	private static final String VALID_EMAIL = "employee@employee.com";
	private static final String TIME_SHEET_JSP = "timeSheet";
	private static final String TIME_SHEET_SUBMIT_JSP = "timeSheetSubmitted";
	private static final String TIME_ENTRY_UID = "0001";

	private PayPeriodService payPeriodServiceMock;
	private PayPeriodConverter payPeriodConverterMock;
	private TimeController timeControllerMock;
	private ModelMap modelMapMock;
	private PayPeriodViewModel payPeriodViewModelMock;
	private HttpSession sessionMock;
	private PayPeriod payPeriodMock;
	private User userMock;
	private UserRepository userRepositoryMock;
	private TimeEntryRepository timeEntryRepositoryMock;
	private TaskRepository taskRepositoryMock;
	private UserService userServiceMock;
	private TimeEntry timeEntryMock;

	@Before
	public void init() {
		payPeriodServiceMock = mock(PayPeriodService.class);
		payPeriodConverterMock = mock(PayPeriodConverter.class);
		timeControllerMock = mock(TimeController.class);
		modelMapMock = mock(ModelMap.class);
		payPeriodViewModelMock = mock(PayPeriodViewModel.class);
		sessionMock = mock(HttpSession.class);
		payPeriodMock = mock(PayPeriod.class);
		userMock = mock(User.class);
		userRepositoryMock = mock(UserRepository.class);
		timeEntryRepositoryMock = mock(TimeEntryRepository.class);
		taskRepositoryMock = mock(TaskRepository.class);
		userServiceMock = mock(UserService.class);
		timeEntryMock = mock(TimeEntry.class);
		timeControllerMock = new TimeController(payPeriodServiceMock, payPeriodConverterMock, userRepositoryMock, timeEntryRepositoryMock, taskRepositoryMock, userServiceMock);
	}

	@Test
	public void getTimeReturnsTimeSheet() {
		when(userRepositoryMock.findByEmail(VALID_EMAIL)).thenReturn(userMock);
		when(payPeriodServiceMock.getCurrentPayPeriod()).thenReturn(payPeriodMock);
		when(userMock.getEmail()).thenReturn(VALID_EMAIL);
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);

		String returnedForm = timeControllerMock.getTime(modelMapMock, sessionMock);

		assertEquals(TIME_SHEET_JSP, returnedForm);
	}

	@Test
	public void saveTimeReturnsSubmittedTimeSheetIfSuccessfulSubmit() throws Exception {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(payPeriodServiceMock.getCurrentPayPeriod()).thenReturn(payPeriodMock);
		when(payPeriodConverterMock.convertToTimeEntry(payPeriodViewModelMock)).thenReturn(timeEntryMock);
		when(timeEntryMock.getuId()).thenReturn(TIME_ENTRY_UID);

		String returnedForm = timeControllerMock.saveTime(payPeriodViewModelMock, sessionMock);

		assertEquals(TIME_SHEET_SUBMIT_JSP, returnedForm);
	}

	@Test
	public void getTimeAddsUserEmailAsAttribute() {
		when(userRepositoryMock.findByEmail(VALID_EMAIL)).thenReturn(userMock);
		when(payPeriodServiceMock.getCurrentPayPeriod()).thenReturn(payPeriodMock);
		when(userMock.getEmail()).thenReturn(VALID_EMAIL);
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);

		timeControllerMock.getTime(modelMapMock, sessionMock);

		verify(modelMapMock).addAttribute(EMAIL_KEY, VALID_EMAIL);
	}
}
