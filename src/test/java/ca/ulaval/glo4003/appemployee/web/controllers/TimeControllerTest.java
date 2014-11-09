package ca.ulaval.glo4003.appemployee.web.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.repository.TaskRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.UserRepository;
import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntry;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.services.PayPeriodService;
import ca.ulaval.glo4003.appemployee.services.ProjectService;
import ca.ulaval.glo4003.appemployee.services.UserService;
import ca.ulaval.glo4003.appemployee.web.converters.TimeConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TimeViewModel;

@RunWith(MockitoJUnitRunner.class)
public class TimeControllerTest {

	private List<Task> tasks = new ArrayList<Task>();
	
	private static final String EMAIL_KEY = "email";
	private static final String VALID_EMAIL = "employee@employee.com";
	private static final String PREVIOUS_TIME_SHEET_JSP = "previousTime";
	private static final String TIME_SHEET_JSP = "time";
	private static final String TIME_SHEET_SUBMIT_JSP = "timeSheetSubmitted";
	private static final String REDIRECT_LINK = "redirect:/";
	private static final String ERROR_REDIRECT = "redirect:/time/errorNoTaskSelected";
	private static final String TIME_REDIRECT = "redirect:/time/";
	private static final String TIME_ENTRY_UID = "0001";
	private static final String CREATE_TIME_JSP = "createTimeEntry";
	private static final String EDIT_TIME_ENTRY_JSP = "editTimeEntry";
	private static final String EDIT_PREVIOUS_TIME_ENTRY_JSP = "editPreviousTimeEntry";
	private static final String CREATE_PREVIOUS_TIME_JSP = "createPreviousTimeEntry";
	private static final String PREVIOUS_TIME_REDIRECT = "redirect:/time/previousTime/";

	private PayPeriodService payPeriodServiceMock;
	private TimeConverter timeConverterMock;
	private TimeController timeControllerMock;
	private ModelMap modelMapMock;
	private TimeViewModel payPeriodViewModelMock;
	private HttpSession sessionMock;
	private PayPeriod payPeriodMock;
	private User userMock;
	private UserRepository userRepositoryMock;
	private TaskRepository taskRepositoryMock;
	private UserService userServiceMock;
	private TimeEntry timeEntryMock;
	private ProjectService projectServiceMock;
	private Model modelMock;

	@Before
	public void init() {
		payPeriodServiceMock = mock(PayPeriodService.class);
		timeConverterMock = mock(TimeConverter.class);
		timeControllerMock = mock(TimeController.class);
		modelMapMock = mock(ModelMap.class);
		modelMock = mock(Model.class);
		payPeriodViewModelMock = mock(TimeViewModel.class);
		sessionMock = mock(HttpSession.class);
		payPeriodMock = mock(PayPeriod.class);
		userMock = mock(User.class);
		userRepositoryMock = mock(UserRepository.class);
		taskRepositoryMock = mock(TaskRepository.class);
		userServiceMock = mock(UserService.class);
		timeEntryMock = mock(TimeEntry.class);
		projectServiceMock = mock(ProjectService.class);
		timeControllerMock = new TimeController(payPeriodServiceMock, timeConverterMock, taskRepositoryMock, userServiceMock,
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
	public void createTimeEntryReturnsRedirectLinkIfMissingAttribute(){
		String returnedForm = timeControllerMock.createPreviousTimeEntry(modelMock, payPeriodViewModelMock, sessionMock);
		assertEquals(REDIRECT_LINK, returnedForm);
	}
	
	@Test
	public void createTimeEntryReturnsCreateTimeForm(){
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(payPeriodServiceMock.getCurrentPayPeriod()).thenReturn(payPeriodMock);
		when(projectServiceMock.getAllTasksByUserId(VALID_EMAIL)).thenReturn(tasks);
		when(timeConverterMock.convert(payPeriodMock, tasks)).thenReturn(payPeriodViewModelMock);
		
		String returnedForm = timeControllerMock.createTimeEntry(modelMock, payPeriodViewModelMock, sessionMock);
		
		assertEquals(CREATE_TIME_JSP, returnedForm);
	}
	
	@Test
	public void saveTimeReturnsSubmittedTimeSheetIfSuccessfulSubmit() throws Exception {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(payPeriodServiceMock.getCurrentPayPeriod()).thenReturn(payPeriodMock);
		when(timeConverterMock.convertToTimeEntry(payPeriodViewModelMock)).thenReturn(timeEntryMock);
		when(timeEntryMock.getuId()).thenReturn(TIME_ENTRY_UID);
		when(payPeriodViewModelMock.getTaskIdTimeEntry()).thenReturn(TIME_ENTRY_UID);

		String returnedForm = timeControllerMock.saveTimeEntry(modelMock, payPeriodViewModelMock, sessionMock);

		assertEquals(TIME_SHEET_SUBMIT_JSP, returnedForm);
	}
	
	@Test
	public void editTimeEntryReturnsRedirectLinkIfMissingAttribute(){
		String returnedForm = timeControllerMock.editTimeEntry(TIME_ENTRY_UID, modelMock, sessionMock);
		assertEquals(REDIRECT_LINK, returnedForm);
	}
	
	@Test
	public void editTimeEntryReturnsEditedTimeEntryForm(){
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(payPeriodServiceMock.getCurrentPayPeriod()).thenReturn(payPeriodMock);
		when(projectServiceMock.getAllTasksByUserId(VALID_EMAIL)).thenReturn(tasks);
		when(timeConverterMock.convert(payPeriodMock, tasks)).thenReturn(payPeriodViewModelMock);
		when(userServiceMock.getTimeEntry(TIME_ENTRY_UID)).thenReturn(timeEntryMock);
		when(timeConverterMock.convertToViewModel(timeEntryMock)).thenReturn(payPeriodViewModelMock);
		
		String returnedForm = timeControllerMock.editTimeEntry(TIME_ENTRY_UID, modelMock, sessionMock);
		assertEquals(EDIT_TIME_ENTRY_JSP, returnedForm);
	}
	
	@Test
	public void saveEditedTimeEntryReturnsErrorRedirectIfMissingTasks() throws Exception{
		when(payPeriodViewModelMock.getTaskIdTimeEntry()).thenReturn("NONE");
		String returnedForm = timeControllerMock.saveEditedTimeEntry(TIME_ENTRY_UID, payPeriodViewModelMock, sessionMock);
		assertEquals(ERROR_REDIRECT, returnedForm);
	}
	
	@Test
	public void saveEditedTimeEntryReturnsCorrectRedirectLink() throws Exception{
		when(payPeriodViewModelMock.getTaskIdTimeEntry()).thenReturn(TIME_ENTRY_UID);
		String returnedForm = timeControllerMock.saveEditedTimeEntry(TIME_ENTRY_UID, payPeriodViewModelMock, sessionMock);
		assertEquals(TIME_REDIRECT, returnedForm);
	}
	
	@Test
	public void saveEditedTimeEntryCallsUpdateMethod() throws Exception{
		when(payPeriodViewModelMock.getTaskIdTimeEntry()).thenReturn(TIME_ENTRY_UID);
		timeControllerMock.saveEditedTimeEntry(TIME_ENTRY_UID, payPeriodViewModelMock, sessionMock);
		verify(userServiceMock, times(1)).updateTimeEntry(TIME_ENTRY_UID, payPeriodViewModelMock);
	}
	
	
	@Test
	public void getPreviousTimeReturnsTimeSheet() {
		when(userRepositoryMock.findByEmail(VALID_EMAIL)).thenReturn(userMock);
		when(payPeriodServiceMock.getPreviousPayPeriod()).thenReturn(payPeriodMock);
		when(projectServiceMock.getAllTasksByUserId(VALID_EMAIL)).thenReturn(tasks);
		when(userMock.getEmail()).thenReturn(VALID_EMAIL);
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);

		String returnedForm = timeControllerMock.getPreviousTime(modelMapMock, sessionMock);

		assertEquals(PREVIOUS_TIME_SHEET_JSP, returnedForm);
	}
	
	@Test
	public void getPreviousTimeReturnRedirectsIfEmailAttributeIsNull() {
		String returnedForm = timeControllerMock.getPreviousTime(modelMapMock, sessionMock);
		assertEquals(REDIRECT_LINK, returnedForm);
	}
	
	@Test
	public void createPreviousTimeEntryReturnsRedirectLinkIfMissingAttribute(){
		String returnedForm = timeControllerMock.createPreviousTimeEntry(modelMock, payPeriodViewModelMock, sessionMock);
		assertEquals(REDIRECT_LINK, returnedForm);
	}
	
	@Test
	public void createPreviousTimeEntryReturnsValidFormIsSuccessful(){
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(payPeriodServiceMock.getPreviousPayPeriod()).thenReturn(payPeriodMock);
		when(projectServiceMock.getAllTasksByUserId(VALID_EMAIL)).thenReturn(tasks);
		when(timeConverterMock.convert(payPeriodMock, tasks)).thenReturn(payPeriodViewModelMock);
		String returnedForm = timeControllerMock.createPreviousTimeEntry(modelMock, payPeriodViewModelMock, sessionMock);
		assertEquals(CREATE_PREVIOUS_TIME_JSP, returnedForm);
	}
	
	@Test
	public void savePreviousTimeReturnsSubmittedTimeSheetIfSuccessfulSubmit() throws Exception {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(payPeriodServiceMock.getPreviousPayPeriod()).thenReturn(payPeriodMock);
		when(timeConverterMock.convertToTimeEntry(payPeriodViewModelMock)).thenReturn(timeEntryMock);
		when(timeEntryMock.getuId()).thenReturn(TIME_ENTRY_UID);
		when(payPeriodViewModelMock.getTaskIdTimeEntry()).thenReturn(TIME_ENTRY_UID);

		String returnedForm = timeControllerMock.savePreviousTimeEntry(modelMock, payPeriodViewModelMock, sessionMock);

		assertEquals(TIME_SHEET_SUBMIT_JSP, returnedForm);
	}
	
	@Test
	public void savePreviousTimeEntryCallsUpdateMethod() throws Exception{
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(payPeriodServiceMock.getPreviousPayPeriod()).thenReturn(payPeriodMock);
		when(timeConverterMock.convertToTimeEntry(payPeriodViewModelMock)).thenReturn(timeEntryMock);
		when(timeEntryMock.getuId()).thenReturn(TIME_ENTRY_UID);
		when(payPeriodViewModelMock.getTaskIdTimeEntry()).thenReturn(TIME_ENTRY_UID);
		
		timeControllerMock.savePreviousTimeEntry(modelMock, payPeriodViewModelMock, sessionMock);
		
		verify(payPeriodServiceMock, times(1)).updatePayPeriod(payPeriodMock);
	}
	
	@Test
	public void editPreviousTimeEntryReturnsRedirectLinkIfMissingAttribute(){
		String returnedForm = timeControllerMock.editPreviousTimeEntry(TIME_ENTRY_UID, modelMock, sessionMock);
		assertEquals(REDIRECT_LINK, returnedForm);
	}
	
	@Test
	public void editPreviousTimeEntryReturnsValidFormIsSuccessful(){
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(payPeriodServiceMock.getPreviousPayPeriod()).thenReturn(payPeriodMock);
		when(projectServiceMock.getAllTasksByUserId(VALID_EMAIL)).thenReturn(tasks);
		when(timeConverterMock.convert(payPeriodMock, tasks)).thenReturn(payPeriodViewModelMock);
		when(userServiceMock.getTimeEntry(TIME_ENTRY_UID)).thenReturn(timeEntryMock);
		when(timeConverterMock.convertToViewModel(timeEntryMock)).thenReturn(payPeriodViewModelMock);
		
		String returnedForm = timeControllerMock.editPreviousTimeEntry(TIME_ENTRY_UID, modelMock, sessionMock);
		
		assertEquals(EDIT_PREVIOUS_TIME_ENTRY_JSP, returnedForm);
	}
	
	@Test
	public void savePreviousEditedTimeEntryReturnsErrorRedirectIfMissingTasks() throws Exception{
		when(payPeriodViewModelMock.getTaskIdTimeEntry()).thenReturn("NONE");
		String returnedForm = timeControllerMock.savePreviousEditedTimeEntry(TIME_ENTRY_UID, payPeriodViewModelMock, sessionMock);
		assertEquals(ERROR_REDIRECT, returnedForm);
	}
	
	@Test 
	public void savePreviousEditedTimeEntryReturnsValidFormIfSuccessful() throws Exception{
		when(payPeriodViewModelMock.getTaskIdTimeEntry()).thenReturn(TIME_ENTRY_UID);
		String returnedForm = timeControllerMock.savePreviousEditedTimeEntry(TIME_ENTRY_UID, payPeriodViewModelMock, sessionMock);
		assertEquals(PREVIOUS_TIME_REDIRECT, returnedForm);
	}

	@Test
	public void savePreviousEditedTimeEntryCallsUpdateMethod() throws Exception{
		when(payPeriodViewModelMock.getTaskIdTimeEntry()).thenReturn(TIME_ENTRY_UID);
		timeControllerMock.savePreviousEditedTimeEntry(TIME_ENTRY_UID, payPeriodViewModelMock, sessionMock);
		verify(userServiceMock, times(1)).updateTimeEntry(TIME_ENTRY_UID, payPeriodViewModelMock);
	}
}
