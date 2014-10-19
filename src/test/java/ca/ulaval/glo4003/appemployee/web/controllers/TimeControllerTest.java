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
import ca.ulaval.glo4003.appemployee.domain.repository.TaskRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.TimeEntryRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.UserRepository;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntry;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.services.TimeService;
import ca.ulaval.glo4003.appemployee.services.ProjectService;
import ca.ulaval.glo4003.appemployee.services.UserService;
import ca.ulaval.glo4003.appemployee.web.converters.TimeConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TimeViewModel;

@RunWith(MockitoJUnitRunner.class)
public class TimeControllerTest {

	private static final String EMAIL_KEY = "email";
	private static final String VALID_EMAIL = "employee@employee.com";
	private static final String TIME_SHEET_JSP = "time";
	private static final String PREVIOUS_TIME_SHEET_JSP = "previousTime";
	private static final String TIME_SHEET_SUBMIT_JSP = "timeSheetSubmitted";
	private static final String PREVIOUS_TIME_SHEET_SUBMIT_JSP = "previousTimeSheetSubmitted";
	private static final String REDIRECT_LINK = "redirect:/";
	private static final String ERROR_REDIRECT = "redirect:/time/errorNoTaskSelected";
	private static final String TIME_ENTRY_UID = "0001";

	private TimeService payPeriodServiceMock;
	private TimeConverter timeConverter;
	private TimeController timeControllerMock;
	private ModelMap modelMapMock;
	private TimeViewModel payPeriodViewModelMock;
	private HttpSession sessionMock;
	private PayPeriod payPeriodMock;
	private User userMock;
	private UserRepository userRepositoryMock;
	private TimeEntryRepository timeEntryRepositoryMock;
	private TaskRepository taskRepositoryMock;
	private UserService userServiceMock;
	private TimeEntry timeEntryMock;
	private ProjectService projectServiceMock;

	@Before
	public void init() {
		payPeriodServiceMock = mock(TimeService.class);
		timeConverter = mock(TimeConverter.class);
		timeControllerMock = mock(TimeController.class);
		modelMapMock = mock(ModelMap.class);
		payPeriodViewModelMock = mock(TimeViewModel.class);
		sessionMock = mock(HttpSession.class);
		payPeriodMock = mock(PayPeriod.class);
		userMock = mock(User.class);
		userRepositoryMock = mock(UserRepository.class);
		timeEntryRepositoryMock = mock(TimeEntryRepository.class);
		taskRepositoryMock = mock(TaskRepository.class);
		userServiceMock = mock(UserService.class);
		timeEntryMock = mock(TimeEntry.class);
		projectServiceMock = mock(ProjectService.class);
		timeControllerMock = new TimeController(payPeriodServiceMock, timeConverter, timeEntryRepositoryMock, taskRepositoryMock, userServiceMock,
				projectServiceMock);
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
	public void getPreviousTimeReturnsTimeSheet() {
		when(userRepositoryMock.findByEmail(VALID_EMAIL)).thenReturn(userMock);
		when(payPeriodServiceMock.getPreviousPayPeriod()).thenReturn(payPeriodMock);
		when(userMock.getEmail()).thenReturn(VALID_EMAIL);
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);

		String returnedForm = timeControllerMock.getPreviousTime(modelMapMock, sessionMock);

		assertEquals(PREVIOUS_TIME_SHEET_JSP, returnedForm);
	}


	@Test
	public void saveTimeReturnsSubmittedTimeSheetIfSuccessfulSubmit() throws Exception {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(payPeriodServiceMock.getCurrentPayPeriod()).thenReturn(payPeriodMock);
		when(timeConverter.convertToTimeEntry(payPeriodViewModelMock)).thenReturn(timeEntryMock);
		when(timeEntryMock.getuId()).thenReturn(TIME_ENTRY_UID);
		when(payPeriodViewModelMock.getTaskIdTimeEntry()).thenReturn(TIME_ENTRY_UID);

		String returnedForm = timeControllerMock.saveTime(payPeriodViewModelMock, sessionMock);

		assertEquals(TIME_SHEET_SUBMIT_JSP, returnedForm);
	}
	
	@Test
	public void savePreviousTimeReturnsSubmittedTimeSheetIfSuccessfulSubmit() throws Exception {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(payPeriodServiceMock.getPreviousPayPeriod()).thenReturn(payPeriodMock);
		when(timeConverter.convertToTimeEntry(payPeriodViewModelMock)).thenReturn(timeEntryMock);
		when(timeEntryMock.getuId()).thenReturn(TIME_ENTRY_UID);
		when(payPeriodViewModelMock.getTaskIdTimeEntry()).thenReturn(TIME_ENTRY_UID);

		String returnedForm = timeControllerMock.saveTime(payPeriodViewModelMock, sessionMock);

		assertEquals(PREVIOUS_TIME_SHEET_SUBMIT_JSP, returnedForm);
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

	@Test
	public void getTimeReturnRedirectsIfEmailAttributeIsNull() {
		String returnedForm = timeControllerMock.getTime(modelMapMock, sessionMock);
		assertEquals(REDIRECT_LINK, returnedForm);
	}

	@Test
	public void saveTimeReturnsErrorIfTaskIdIsNull() throws Exception {
		String returnedForm = timeControllerMock.saveTime(payPeriodViewModelMock, sessionMock);
		assertEquals(ERROR_REDIRECT, returnedForm);
	}
	
	@Test
	public void getPreviousTimeReturnRedirectsIfEmailAttributeIsNull() {
		String returnedForm = timeControllerMock.getPreviousTime(modelMapMock, sessionMock);
		assertEquals(REDIRECT_LINK, returnedForm);
	}
	
	@Test
	public void savePreviousTimeReturnsErrorIfTaskIdIsNull() throws Exception {
		String returnedForm = timeControllerMock.savePreviousTime(payPeriodViewModelMock, sessionMock);
		assertEquals(ERROR_REDIRECT, returnedForm);
	}
}
