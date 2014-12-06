package ca.ulaval.glo4003.appemployee.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.appemployee.domain.exceptions.TimeEntryNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.task.TaskProcessor;
import ca.ulaval.glo4003.appemployee.domain.time.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.time.TimeEntry;
import ca.ulaval.glo4003.appemployee.domain.time.TimeProcessor;
import ca.ulaval.glo4003.appemployee.web.converters.TimeConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.PayPeriodViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TimeEntryViewModel;

@RunWith(MockitoJUnitRunner.class)
public class TimeServiceTest {
	
	private List<Task> tasks = new ArrayList<Task>();
	private List<TimeEntry> timeEntries = new ArrayList<TimeEntry>();
	private Collection<TimeEntryViewModel> timeEntryModels = new ArrayList<TimeEntryViewModel>();

	private static final String DATE = "2014-11-11";
	private static final String END_DATE = "2014-11-23";
	private static final String USER_EMAIL = "email@email.com";
	private static final String COMMENT = "comment";
	private static final String A_UID = "0001";
	private static final double HOURS = 8.0;
	private static final String TASK_NAME = "name";

	@Mock
	private PayPeriod payPeriodMock;

	@Mock
	private PayPeriodViewModel timeViewModelMock;
	
	@Mock
	private TimeEntryViewModel timeEntryViewModelMock;

	@Mock
	private TimeConverter timeConverterMock;

	@Mock
	private TimeEntry timeEntryMock;

	@Mock
	private UserService userServiceMock;

	@Mock
	private TimeProcessor timeProcessorMock;
	
	@Mock
	private TaskProcessor taskProcessorMock;

	@InjectMocks
	private TimeService timeService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		timeService = new TimeService(taskProcessorMock, timeProcessorMock,
				timeConverterMock);
	}

	@Test
	public void canInstantiateService() {
		assertNotNull(timeService);
	}
	
	@Test
	public void retrieveCurrentPayPeriodReturnsValidPayPeriod() {
		when(timeProcessorMock.retrieveCurrentPayPeriod()).thenReturn(payPeriodMock);
		PayPeriod returnedPayPeriod = timeService.retrieveCurrentPayPeriod();
		assertEquals(payPeriodMock, returnedPayPeriod);
	}
	
	@Test
	public void retrievePreviousPayPeriodReturnsValidPayPeriod() {
		when(timeProcessorMock.retrievePreviousPayPeriod()).thenReturn(payPeriodMock);
		PayPeriod returnedPayPeriod = timeService.retrievePreviousPayPeriod();
		assertEquals(payPeriodMock, returnedPayPeriod);
	}
	
	@Test
	public void editPayPeriodCallsCorrectProcessorMethod() throws Exception {
		timeService.editPayPeriod(payPeriodMock);
		verify(timeProcessorMock, times(1)).editPayPeriod(payPeriodMock);
	}
	
	@Test
	public void createTimeEntryCallsCorrectProcessorMethod() throws Exception {
		when(timeEntryViewModelMock.getHours()).thenReturn(HOURS);
		when(timeEntryViewModelMock.getDate()).thenReturn(DATE);
		when(timeEntryViewModelMock.getTaskId()).thenReturn(A_UID);
		when(timeEntryViewModelMock.getUserEmail()).thenReturn(USER_EMAIL);
		when(timeEntryViewModelMock.getComment()).thenReturn(COMMENT);
		
		timeService.createTimeEntry(timeEntryViewModelMock, payPeriodMock);
		
		verify(timeProcessorMock, times(1)).createTimeEntry(payPeriodMock, HOURS, new LocalDate(DATE), USER_EMAIL, A_UID, COMMENT);
	}
	
	@Test
	public void updateTimeEntryCallsCorrectProcessorMethod() throws Exception {
		when(timeEntryViewModelMock.getHours()).thenReturn(HOURS);
		when(timeEntryViewModelMock.getDate()).thenReturn(DATE);
		when(timeEntryViewModelMock.getTaskId()).thenReturn(A_UID);
		when(timeEntryViewModelMock.getUserEmail()).thenReturn(USER_EMAIL);
		when(timeEntryViewModelMock.getComment()).thenReturn(COMMENT);
		when(timeEntryViewModelMock.getUid()).thenReturn(A_UID);
		
		timeService.updateTimeEntry(timeEntryViewModelMock);
		
		verify(timeProcessorMock, times(1)).editTimeEntry(A_UID, HOURS, new LocalDate(DATE), USER_EMAIL, A_UID, COMMENT);
	}
	
	@Test
	public void retrieveCurrentPayPeriodViewModelReturnsPayPeriodViewModel() {
		when(timeService.retrieveCurrentPayPeriod()).thenReturn(payPeriodMock);
		when(payPeriodMock.getStartDate()).thenReturn(new LocalDate(DATE));
		when(payPeriodMock.getEndDate()).thenReturn(new LocalDate(END_DATE));
		
		PayPeriodViewModel returnedPayPeriodModel = timeService.retrieveCurrentPayPeriodViewModel();
		
		assertNotNull(returnedPayPeriodModel);
	}
	
	@Test
	public void retrievePreviousPayPeriodViewModelReturnsPayPeriodViewModel() {
		when(timeService.retrievePreviousPayPeriod()).thenReturn(payPeriodMock);
		when(payPeriodMock.getStartDate()).thenReturn(new LocalDate(DATE));
		when(payPeriodMock.getEndDate()).thenReturn(new LocalDate(END_DATE));
		
		PayPeriodViewModel returnedPayPeriodModel = timeService.retrievePreviousPayPeriodViewModel();
		
		assertNotNull(returnedPayPeriodModel);
	}
	
	@Test
	public void retrieveTimeEntryViewModelForUserReturnsValidTimeEntryViewModel() {
		when(taskProcessorMock.retrieveAllTasksByUserId(USER_EMAIL)).thenReturn(tasks);
		when(timeConverterMock.convert(USER_EMAIL, tasks)).thenReturn(timeEntryViewModelMock);
		
		TimeEntryViewModel returnedModel = timeService.retrieveTimeEntryViewModelForUser(USER_EMAIL);
		
		assertEquals(timeEntryViewModelMock, returnedModel);
	}
	
	@Test
	public void retrieveAllTimeEntriesViewModelsForCurrentPayPeriodReturnsCollectionOfTimeEntryViewModels() throws TimeEntryNotFoundException {
		when(timeService.retrieveCurrentPayPeriod()).thenReturn(payPeriodMock);
		when(timeProcessorMock.evaluateUserTimeEntriesForPayPeriod(payPeriodMock, USER_EMAIL)).thenReturn(timeEntries);
		when(timeConverterMock.convert(timeEntries)).thenReturn(timeEntryModels);
		
		Collection<TimeEntryViewModel> returnedModels = timeService.retrieveAllTimeEntriesViewModelsForCurrentPayPeriod(USER_EMAIL);
		
		assertEquals(timeEntryModels, returnedModels);
	}
	
	@Test
	public void retrieveAllTimeEntriesViewModelsForPreviousPayPeriodReturnsCollectionOfTimeEntryViewModels() throws TimeEntryNotFoundException {
		when(timeService.retrievePreviousPayPeriod()).thenReturn(payPeriodMock);
		when(timeProcessorMock.evaluateUserTimeEntriesForPayPeriod(payPeriodMock, USER_EMAIL)).thenReturn(timeEntries);
		when(timeConverterMock.convert(timeEntries)).thenReturn(timeEntryModels);
		
		Collection<TimeEntryViewModel> returnedModels = timeService.retrieveAllTimeEntriesViewModelsForPreviousPayPeriod(USER_EMAIL);
		
		assertEquals(timeEntryModels, returnedModels);
	}
	
	@Test
	public void retrieveTimeEntryViewModelReturnsValidViewModel() throws TimeEntryNotFoundException {
		when(timeProcessorMock.retrieveTimeEntryByUid(A_UID)).thenReturn(timeEntryMock);
		when(timeEntryMock.getUserEmail()).thenReturn(USER_EMAIL);
		when(taskProcessorMock.retrieveAllTasksByUserId(USER_EMAIL)).thenReturn(tasks);
		when(timeEntryMock.getTaskUid()).thenReturn(A_UID);
		when(taskProcessorMock.retrieveTaskName(A_UID)).thenReturn(TASK_NAME);
		when(timeConverterMock.convert(timeEntryMock, TASK_NAME, tasks)).thenReturn(timeEntryViewModelMock);
		
		TimeEntryViewModel returnedModel = timeService.retrieveTimeEntryViewModel(A_UID);
		
		assertEquals(timeEntryViewModelMock, returnedModel);
	}


}
