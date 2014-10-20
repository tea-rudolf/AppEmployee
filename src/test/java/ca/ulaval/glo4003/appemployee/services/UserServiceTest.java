package ca.ulaval.glo4003.appemployee.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.repository.ExpenseRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.PayPeriodRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.TaskRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.TimeEntryRepository;
import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntry;
import ca.ulaval.glo4003.appemployee.persistence.RepositoryException;

public class UserServiceTest {

	private static final String TIME_ENTRY_ID = "id";
	private static final String USER_ID = "id";

	private UserService userService;
	private TaskRepository taskRepositoryMock;
	private PayPeriodRepository payPeriodRepositoryMock;
	private ExpenseRepository expenseRepositoryMock;
	private TimeEntryRepository timeEntryRepositoryMock;
	private PayPeriod payPeriodMock;
	private TimeEntry timeEntryMock;
	private Task taskMock;

	@Before
	public void init() {
		taskRepositoryMock = mock(TaskRepository.class);
		payPeriodRepositoryMock = mock(PayPeriodRepository.class);
		expenseRepositoryMock = mock(ExpenseRepository.class);
		timeEntryRepositoryMock = mock(TimeEntryRepository.class);
		payPeriodMock = mock(PayPeriod.class);
		timeEntryMock = mock(TimeEntry.class);
		taskMock = mock(Task.class);
		userService = new UserService(payPeriodRepositoryMock, taskRepositoryMock, expenseRepositoryMock, timeEntryRepositoryMock);
	}

	@Test
	public void updateCurrentPayPeriodCallsCorrectRepositoryMethod() throws Exception {
		userService.updateCurrentPayPeriod(payPeriodMock);
		verify(payPeriodRepositoryMock, times(1)).update(payPeriodMock);
	}

	@Test(expected = RepositoryException.class)
	public void updateCurrentPayPeriodThrowsExceptionIfPayPeriodIsNotUpdated() throws Exception {
		doThrow(new RepositoryException()).when(payPeriodRepositoryMock).update(payPeriodMock);
		userService.updateCurrentPayPeriod(payPeriodMock);
	}

	@Test
	public void getTaskForUserForAPayPeriodReturnsListOfTasks() {
		List<String> sampleIdList = new ArrayList<String>();
		sampleIdList.add(TIME_ENTRY_ID);
		when(payPeriodMock.getTimeEntryIds()).thenReturn(sampleIdList);
		when(timeEntryRepositoryMock.findByUid(TIME_ENTRY_ID)).thenReturn(timeEntryMock);
		when(timeEntryMock.getUserEmail()).thenReturn(USER_ID);
		when(timeEntryMock.getuId()).thenReturn(TIME_ENTRY_ID);
		when(taskRepositoryMock.findByUid(TIME_ENTRY_ID)).thenReturn(taskMock);
		List<Task> simpleTaskList = userService.getTasksForUserForAPayPeriod(payPeriodMock, USER_ID);
		assertTrue(simpleTaskList.contains(taskMock));
	}

	@Test
	public void getTimeEntriesForUserForAPayPeriodReturnsListOfTimeEntries() {
		List<String> sampleIdList = new ArrayList<String>();
		sampleIdList.add(TIME_ENTRY_ID);
		when(payPeriodMock.getTimeEntryIds()).thenReturn(sampleIdList);
		when(timeEntryRepositoryMock.findByUid(TIME_ENTRY_ID)).thenReturn(timeEntryMock);
		when(timeEntryMock.getUserEmail()).thenReturn(USER_ID);
		List<TimeEntry> sampleTimeEntryList = userService.getTimeEntriesForUserForAPayPeriod(payPeriodMock, USER_ID);
		assertTrue(sampleTimeEntryList.contains(timeEntryMock));
	}

}
