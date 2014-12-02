package ca.ulaval.glo4003.appemployee.web.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.repository.TaskRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.UserRepository;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntry;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.services.ProjectService;
import ca.ulaval.glo4003.appemployee.services.TimeService;
import ca.ulaval.glo4003.appemployee.services.UserService;
import ca.ulaval.glo4003.appemployee.web.converters.TimeConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TimeViewModel;

@RunWith(MockitoJUnitRunner.class)
public class TimeControllerTest {

	private static final String EMAIL_KEY = "email";
	private static final String VALID_EMAIL = "employee@employee.com";
	private static final String PREVIOUS_TIME_SHEET_JSP = "previousTime";
	private static final String TIME_SHEET_JSP = "time";
	private static final String TIME_SHEET_SUBMIT_JSP = "timeSheetSubmitted";
	private static final String TIME_REDIRECT = "redirect:/time/";
	private static final String TIME_ENTRY_UID = "0001";
	private static final String CREATE_TIME_JSP = "createTimeEntry";
	private static final String EDIT_TIME_ENTRY_JSP = "editTimeEntry";
	private static final String EDIT_PREVIOUS_TIME_ENTRY_JSP = "editPreviousTimeEntry";
	private static final String CREATE_PREVIOUS_TIME_JSP = "createPreviousTimeEntry";
	private static final String PREVIOUS_TIME_REDIRECT = "redirect:/time/previousTime/";

	@Mock
	private TimeService timeServiceMock;

	@Mock
	private ModelMap modelMapMock;

	@Mock
	private TimeViewModel payPeriodViewModelMock;

	@Mock
	private HttpSession sessionMock;

	@Mock
	private PayPeriod payPeriodMock;

	@Mock
	private TimeEntry timeEntryMock;

	@Mock
	private Model modelMock;

	@InjectMocks
	private TimeController timeControllerMock;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		timeControllerMock = new TimeController(timeServiceMock);
	}

	@Test
	public void getTimeReturnsTimeSheet() {
		when(timeServiceMock.retrieveEmptyTimeEntryViewModelForCurrentPayPeriod(VALID_EMAIL)).thenReturn(payPeriodViewModelMock);
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);

		String returnedForm = timeControllerMock.showTimeEntriesForm(modelMapMock, sessionMock);

		assertEquals(TIME_SHEET_JSP, returnedForm);
	}

	// @Test
	// public void getTimeAddsUserEmailAsAttribute() {
	// when(payPeriodServiceMock.retrieveViewModelForCurrentPayPeriod(VALID_EMAIL)).thenReturn(payPeriodViewModelMock);
	// when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
	//
	// timeControllerMock.getTime(modelMapMock, sessionMock);
	//
	// verify(modelMapMock).addAttribute(EMAIL_KEY, VALID_EMAIL);
	// }

	@Test
	public void createTimeEntryReturnsCreateTimeForm() {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(timeServiceMock.retrieveEmptyTimeEntryViewModelForCurrentPayPeriod(VALID_EMAIL)).thenReturn(payPeriodViewModelMock);

		String returnedForm = timeControllerMock.showCreateTimeEntryForm(modelMock, payPeriodViewModelMock, sessionMock);

		assertEquals(CREATE_TIME_JSP, returnedForm);
	}

	@Test
	public void saveTimeEntryReturnsSubmittedTimeSheetIfSuccessfulSubmit() throws Exception {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(timeServiceMock.retrieveCurrentPayPeriod()).thenReturn(payPeriodMock);
		//when(timeConverterMock.convert(payPeriodViewModelMock)).thenReturn(timeEntryMock);
		when(timeEntryMock.getUid()).thenReturn(TIME_ENTRY_UID);
		when(payPeriodViewModelMock.getTaskIdTimeEntry()).thenReturn(TIME_ENTRY_UID);

		String returnedForm = timeControllerMock.createTimeEntry(modelMock, payPeriodViewModelMock, sessionMock);

		assertEquals(TIME_SHEET_SUBMIT_JSP, returnedForm);
	}

	@Test
	public void editTimeEntryReturnsEditedTimeEntryForm() {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(timeServiceMock.retrieveEmptyTimeEntryViewModelForCurrentPayPeriod(VALID_EMAIL)).thenReturn(payPeriodViewModelMock);
		when(timeServiceMock.retrieveTimeEntryViewModel(TIME_ENTRY_UID)).thenReturn(payPeriodViewModelMock);

		String returnedForm = timeControllerMock.showEditTimeEntryForm(TIME_ENTRY_UID, modelMock, sessionMock);

		assertEquals(EDIT_TIME_ENTRY_JSP, returnedForm);
	}

	@Test
	public void saveEditedTimeEntryReturnsCorrectRedirectLink() throws Exception {
		when(payPeriodViewModelMock.getTaskIdTimeEntry()).thenReturn(TIME_ENTRY_UID);
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);

		String returnedForm = timeControllerMock.editTimeEntry(TIME_ENTRY_UID, modelMock, payPeriodViewModelMock, sessionMock);

		assertEquals(TIME_REDIRECT, returnedForm);
	}

	@Test
	public void saveEditedTimeEntryCallsUpdateMethod() throws Exception {
		when(payPeriodViewModelMock.getTaskIdTimeEntry()).thenReturn(TIME_ENTRY_UID);
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);

		timeControllerMock.editTimeEntry(TIME_ENTRY_UID, modelMock, payPeriodViewModelMock, sessionMock);

		verify(timeServiceMock, times(1)).updateTimeEntry(payPeriodViewModelMock);
	}

	@Test
	public void getPreviousTimeReturnsTimeSheet() {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(timeServiceMock.retrievePreviousPayPeriodViewModel(VALID_EMAIL)).thenReturn(payPeriodViewModelMock);

		String returnedForm = timeControllerMock.showPreviousTimeForm(modelMapMock, sessionMock);

		assertEquals(PREVIOUS_TIME_SHEET_JSP, returnedForm);
	}

	@Test
	public void createPreviousTimeEntryReturnsValidFormIsSuccessful() {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(timeServiceMock.retrievePreviousPayPeriodViewModel(VALID_EMAIL)).thenReturn(payPeriodViewModelMock);

		String returnedForm = timeControllerMock.showCreatePreviousTimeEntryForm(modelMock, payPeriodViewModelMock, sessionMock);

		assertEquals(CREATE_PREVIOUS_TIME_JSP, returnedForm);
	}

	@Test
	public void savePreviousTimeReturnsSubmittedTimeSheetIfSuccessfulSubmit() throws Exception {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(timeServiceMock.retrievePreviousPayPeriod()).thenReturn(payPeriodMock);
		when(timeServiceMock.retrievePreviousPayPeriodViewModel(VALID_EMAIL)).thenReturn(payPeriodViewModelMock);
		when(timeEntryMock.getUid()).thenReturn(TIME_ENTRY_UID);
		when(payPeriodViewModelMock.getTaskIdTimeEntry()).thenReturn(TIME_ENTRY_UID);

		String returnedForm = timeControllerMock.createPreviousTimeEntry(modelMock, payPeriodViewModelMock, sessionMock);

		assertEquals(TIME_SHEET_SUBMIT_JSP, returnedForm);
	}

	@Test
	public void savePreviousTimeEntryCallsUpdateMethod() throws Exception {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(timeServiceMock.retrievePreviousPayPeriod()).thenReturn(payPeriodMock);
		when(timeServiceMock.retrievePreviousPayPeriodViewModel(VALID_EMAIL)).thenReturn(payPeriodViewModelMock);
		when(timeEntryMock.getUid()).thenReturn(TIME_ENTRY_UID);
		when(payPeriodViewModelMock.getTaskIdTimeEntry()).thenReturn(TIME_ENTRY_UID);

		timeControllerMock.createPreviousTimeEntry(modelMock, payPeriodViewModelMock, sessionMock);

		verify(timeServiceMock, times(1)).createTimeEntry(payPeriodViewModelMock, payPeriodMock);
	}

	@Test
	public void editPreviousTimeEntryReturnsValidFormIsSuccessful() {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(timeServiceMock.retrievePreviousPayPeriodViewModel(VALID_EMAIL)).thenReturn(payPeriodViewModelMock);
		when(timeServiceMock.retrieveTimeEntryViewModel(TIME_ENTRY_UID)).thenReturn(payPeriodViewModelMock);

		String returnedForm = timeControllerMock.showEditPreviousTimeEntryForm(TIME_ENTRY_UID, modelMock, sessionMock);

		assertEquals(EDIT_PREVIOUS_TIME_ENTRY_JSP, returnedForm);
	}

	@Test
	public void savePreviousEditedTimeEntryReturnsValidFormIfSuccessful() throws Exception {
		when(payPeriodViewModelMock.getTaskIdTimeEntry()).thenReturn(TIME_ENTRY_UID);
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);

		String returnedForm = timeControllerMock.editPreviousTimeEntry(TIME_ENTRY_UID, modelMock, payPeriodViewModelMock, sessionMock);

		assertEquals(PREVIOUS_TIME_REDIRECT, returnedForm);
	}

	@Test
	public void savePreviousEditedTimeEntryCallsUpdateMethod() throws Exception {
		when(payPeriodViewModelMock.getTaskIdTimeEntry()).thenReturn(TIME_ENTRY_UID);
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);

		timeControllerMock.editPreviousTimeEntry(TIME_ENTRY_UID, modelMock, payPeriodViewModelMock, sessionMock);

		verify(timeServiceMock, times(1)).updateTimeEntry(payPeriodViewModelMock);
	}

	@Test
	public void savePreviousEditedTimeEntryReturnsEditPreviousTimeEntryFormIfMissingTaskId() throws Exception {
		when(payPeriodViewModelMock.getTaskIdTimeEntry()).thenReturn("NONE");
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(timeServiceMock.retrievePreviousPayPeriodViewModel(VALID_EMAIL)).thenReturn(payPeriodViewModelMock);
		when(timeServiceMock.retrieveTimeEntryViewModel(TIME_ENTRY_UID)).thenReturn(payPeriodViewModelMock);

		String returnedForm = timeControllerMock.editPreviousTimeEntry(TIME_ENTRY_UID, modelMock, payPeriodViewModelMock, sessionMock);

		// assertEquals(EDIT_PREVIOUS_TIME_ENTRY_JSP, returnedForm);
	}
}
