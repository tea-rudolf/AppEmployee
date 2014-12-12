package ca.ulaval.glo4003.appemployee.web.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;

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

import ca.ulaval.glo4003.appemployee.domain.exceptions.PayPeriodNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.exceptions.TimeEntryNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.time.PayPeriod;
import ca.ulaval.glo4003.appemployee.services.TimeService;
import ca.ulaval.glo4003.appemployee.web.viewmodels.MessageViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.PayPeriodViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TimeEntryViewModel;

@RunWith(MockitoJUnitRunner.class)
public class TimeControllerTest {

	private static final String EMAIL_KEY = "email";
	private static final String VALID_EMAIL = "employee@employee.com";
	private static final String PREVIOUS_TIME_SHEET_JSP = "previousTime";
	private static final String TIME_SHEET_JSP = "time";
	private static final String TIME_REDIRECT = "redirect:/time";
	private static final String TIME_ENTRY_UID = "0001";
	private static final String CREATE_TIME_JSP = "createTimeEntry";
	private static final String EDIT_TIME_ENTRY_JSP = "editTimeEntry";
	private static final String EDIT_PREVIOUS_TIME_ENTRY_JSP = "editPreviousTimeEntry";
	private static final String CREATE_PREVIOUS_TIME_JSP = "createPreviousTimeEntry";
	private static final String PREVIOUS_TIME_REDIRECT = "redirect:/time/previousTime";

	private Collection<TimeEntryViewModel> timeEntriesViewModels;

	@Mock
	private TimeService timeServiceMock;

	@Mock
	private ModelMap modelMapMock;
	
	@Mock
	private PayPeriodViewModel payPeriodViewModelMock;

	@Mock
	private TimeEntryViewModel timeViewModelMock;

	@Mock
	private HttpSession sessionMock;

	@Mock
	private PayPeriod payPeriodMock;

	@Mock
	private Model modelMock;

	@InjectMocks
	private TimeController timeController;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		timeController = new TimeController(timeServiceMock);
	}
	
	@Test
	public void currentPayPeriodDatesCallsCorrectServiceMethod() {
		payPeriodViewModelMock = timeController.currentPayPeriodDates(sessionMock);
		verify(timeServiceMock, times(1)).retrieveCurrentPayPeriodViewModel();
	}
	
	@Test
	public void previousPayPeriodDatesCallsCorrectServiceMethod() {
		payPeriodViewModelMock = timeController.previousPayPeriodDates(sessionMock);
		verify(timeServiceMock, times(1)).retrievePreviousPayPeriodViewModel();
	}

	@Test
	public void showTimeEntriesFormReturnsTimeSheetWhenViewModelIsValid() throws Exception {
		when(timeServiceMock.retrieveAllTimeEntriesViewModelsForCurrentPayPeriod(VALID_EMAIL)).thenReturn(timeEntriesViewModels);
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);

		String returnedForm = timeController.showTimeEntriesForm(modelMapMock, sessionMock);

		assertEquals(TIME_SHEET_JSP, returnedForm);
	}

	@Test
	public void showCreateTimeEntryFormReturnsCreateTimeForm() {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(timeServiceMock.retrieveTimeEntryViewModelForUser(VALID_EMAIL)).thenReturn(timeViewModelMock);
		String returnedForm = timeController.showCreateTimeEntryForm(modelMock, timeViewModelMock, sessionMock);
		assertEquals(CREATE_TIME_JSP, returnedForm);
	}

	@Test
	public void createTimeEntryReturnsCreateTimeForm() throws Exception {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(timeServiceMock.retrieveCurrentPayPeriod()).thenReturn(payPeriodMock);

		String returnedForm = timeController.createTimeEntry(modelMock, timeViewModelMock, sessionMock);

		assertEquals(TIME_REDIRECT, returnedForm);
	}

	@Test
	public void showEditTimeEntryFormReturnsEditedTimeEntryForm() throws Exception {
		when(timeServiceMock.retrieveTimeEntryViewModel(TIME_ENTRY_UID)).thenReturn(timeViewModelMock);
		String returnedForm = timeController.showEditTimeEntryForm(TIME_ENTRY_UID, modelMock, sessionMock);
		assertEquals(EDIT_TIME_ENTRY_JSP, returnedForm);
	}

	@Test
	public void editTimeEntryReturnsCorrectRedirectLink() throws Exception {
		when(timeViewModelMock.getTaskId()).thenReturn(TIME_ENTRY_UID);
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);

		String returnedForm = timeController.editTimeEntry(TIME_ENTRY_UID, modelMock, timeViewModelMock, sessionMock);

		assertEquals(TIME_REDIRECT, returnedForm);
	}

	@Test
	public void editTimeEntryCallsUpdateMethod() throws Exception {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		timeController.editTimeEntry(TIME_ENTRY_UID, modelMock, timeViewModelMock, sessionMock);
		verify(timeServiceMock, times(1)).updateTimeEntry(timeViewModelMock);
	}

	@Test
	public void showPreviousTimeFormReturnsPreviousTimeSheet() throws Exception {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(timeServiceMock.retrieveTimeEntryViewModelForUser(VALID_EMAIL)).thenReturn(timeViewModelMock);

		String returnedForm = timeController.showPreviousTimeForm(modelMapMock, sessionMock);

		assertEquals(PREVIOUS_TIME_SHEET_JSP, returnedForm);
	}

	@Test
	public void showCreatePreviousTimeEntryFormReturnsValidFormIsSuccessful() {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(timeServiceMock.retrieveTimeEntryViewModelForUser(VALID_EMAIL)).thenReturn(timeViewModelMock);
		String returnedForm = timeController.showCreatePreviousTimeEntryForm(modelMock, timeViewModelMock, sessionMock);
		assertEquals(CREATE_PREVIOUS_TIME_JSP, returnedForm);
	}

	@Test
	public void createPreviousTimeEntryReturnsSubmittedTimeSheetIfSuccessfulSubmit() throws Exception {
		when(timeServiceMock.retrievePreviousPayPeriod()).thenReturn(payPeriodMock);
		String returnedForm = timeController.createPreviousTimeEntry(modelMock, timeViewModelMock, sessionMock);
		assertEquals(PREVIOUS_TIME_REDIRECT, returnedForm);
	}

	@Test
	public void createTimeEntryCallsUpdateMethod() throws Exception {
		when(timeServiceMock.retrievePreviousPayPeriod()).thenReturn(payPeriodMock);
		timeController.createPreviousTimeEntry(modelMock, timeViewModelMock, sessionMock);
		verify(timeServiceMock, times(1)).createTimeEntry(timeViewModelMock, payPeriodMock);
	}

	@Test
	public void showEditPreviousTimeEntryFormReturnsValidFormIsSuccessful() throws Exception {
		when(timeServiceMock.retrieveTimeEntryViewModel(TIME_ENTRY_UID)).thenReturn(timeViewModelMock);
		String returnedForm = timeController.showEditPreviousTimeEntryForm(TIME_ENTRY_UID, modelMock, sessionMock);
		assertEquals(EDIT_PREVIOUS_TIME_ENTRY_JSP, returnedForm);
	}

	@Test
	public void editPreviousTimeEntryReturnsValidFormIfSuccessful() throws Exception {
		String returnedForm = timeController.editPreviousTimeEntry(TIME_ENTRY_UID, modelMock, timeViewModelMock, sessionMock);
		assertEquals(PREVIOUS_TIME_REDIRECT, returnedForm);
	}

	@Test
	public void editPreviousTimeEntryCallsUpdateMethod() throws Exception {
		timeController.editPreviousTimeEntry(TIME_ENTRY_UID, modelMock, timeViewModelMock, sessionMock);
		verify(timeServiceMock, times(1)).updateTimeEntry(timeViewModelMock);
	}
	
	@Test
	public void showTimeEntriesFormReturnsAlertWhenSomethingWentWrongOnUpdate() throws Exception {
		when(sessionMock.getAttribute("email")).thenReturn(VALID_EMAIL);
		doThrow(new PayPeriodNotFoundException("alert message")).when(timeServiceMock).retrieveAllTimeEntriesViewModelsForCurrentPayPeriod(VALID_EMAIL);
		timeController.showTimeEntriesForm(modelMapMock, sessionMock);
		verify(modelMapMock, times(1)).addAttribute(org.mockito.Matchers.eq("message"), org.mockito.Matchers.any(MessageViewModel.class));
	}
	
	@Test
	public void createTimeEntryReturnsAlertWhenSomethingWentWrong() throws Exception {
		when(timeServiceMock.retrieveCurrentPayPeriod()).thenReturn(payPeriodMock);
		doThrow(new Exception()).when(timeServiceMock).createTimeEntry(timeViewModelMock, payPeriodMock);
		timeController.createTimeEntry(modelMock, timeViewModelMock, sessionMock);
		verify(modelMock, times(1)).addAttribute(org.mockito.Matchers.eq("message"), org.mockito.Matchers.any(MessageViewModel.class));
	}
	
	@Test
	public void showEditTimeEntryReturnsAlertWhenSomethingWentWrong() throws Exception {
		doThrow(new TimeEntryNotFoundException("alert message")).when(timeServiceMock).retrieveTimeEntryViewModel(TIME_ENTRY_UID);
		timeController.showEditTimeEntryForm(TIME_ENTRY_UID, modelMock, sessionMock);
		verify(modelMock, times(1)).addAttribute(org.mockito.Matchers.eq("message"), org.mockito.Matchers.any(MessageViewModel.class));
	}
	
	@Test
	public void editTimeEntryReturnsAlertWhenSomethingWentWrong() throws Exception {
		doThrow(new Exception()).when(timeServiceMock).updateTimeEntry(timeViewModelMock);
		timeController.editTimeEntry(TIME_ENTRY_UID, modelMock, timeViewModelMock, sessionMock);
		verify(modelMock, times(1)).addAttribute(org.mockito.Matchers.eq("message"), org.mockito.Matchers.any(MessageViewModel.class));
	}
	
	@Test
	public void showPreviousTimeFormReturnsAlertWhenSomethingWentWrong() throws Exception {
		doThrow(new TimeEntryNotFoundException("alert message")).when(timeServiceMock).retrieveAllTimeEntriesViewModelsForPreviousPayPeriod(VALID_EMAIL);
		timeController.showPreviousTimeForm(modelMapMock, sessionMock);
		verify(modelMapMock, times(1)).addAttribute(org.mockito.Matchers.eq("message"), org.mockito.Matchers.any(MessageViewModel.class));
	}
	
	@Test
	public void createPreviousTimeEntryReturnsAlertWhenSomethingWentWrong() throws Exception {
		when(timeServiceMock.retrievePreviousPayPeriod()).thenReturn(payPeriodMock);
		doThrow(new Exception()).when(timeServiceMock).createTimeEntry(timeViewModelMock, payPeriodMock);
		timeController.createPreviousTimeEntry(modelMock, timeViewModelMock, sessionMock);
		verify(modelMock, times(1)).addAttribute(org.mockito.Matchers.eq("message"), org.mockito.Matchers.any(MessageViewModel.class));
	}
	
	@Test
	public void showEditPreviousTimeEntryFormReturnsAlertWhenSomethingWentWrong() throws Exception {
		doThrow(new TimeEntryNotFoundException("alert message")).when(timeServiceMock).retrieveTimeEntryViewModel(TIME_ENTRY_UID);
		timeController.showEditPreviousTimeEntryForm(TIME_ENTRY_UID, modelMock, sessionMock);
		verify(modelMock, times(1)).addAttribute(org.mockito.Matchers.eq("message"), org.mockito.Matchers.any(MessageViewModel.class));
	}
	
	@Test
	public void editPreviousTimeEntryFormReturnsAlertWhenSomethingWentWrong() throws Exception {
		doThrow(new Exception()).when(timeServiceMock).updateTimeEntry(timeViewModelMock);
		timeController.editPreviousTimeEntry(TIME_ENTRY_UID, modelMock, timeViewModelMock, sessionMock);
		verify(modelMock, times(1)).addAttribute(org.mockito.Matchers.eq("message"), org.mockito.Matchers.any(MessageViewModel.class));
	}
}
