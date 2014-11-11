package ca.ulaval.glo4003.appemployee.services;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.appemployee.domain.exceptions.UserNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.repository.ExpenseRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.TaskRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.TimeEntryRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.TravelRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.UserRepository;
import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntry;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.web.viewmodels.UserViewModel;

public class UserServiceTest {

	private static final String TIME_ENTRY_ID = "id";
	private static final String EMAIL = "employee1@employee.com";
	private static final String EMAIL2 = "employee2@employee.com";

	private UserService userService;
	private TaskRepository taskRepositoryMock;
	private UserRepository userRepositoryMock;
	private ExpenseRepository expenseRepositoryMock;
	private TimeEntryRepository timeEntryRepositoryMock;
	private TravelRepository travelRepositoryMock;
	private PayPeriod payPeriodMock;
	private TimeEntry timeEntryMock;
	private Task taskMock;
	private User userMock;
	private UserViewModel userViewModelMock;

	@Before
	public void init() {
		userRepositoryMock = mock(UserRepository.class);
		taskRepositoryMock = mock(TaskRepository.class);
		expenseRepositoryMock = mock(ExpenseRepository.class);
		timeEntryRepositoryMock = mock(TimeEntryRepository.class);
		payPeriodMock = mock(PayPeriod.class);
		timeEntryMock = mock(TimeEntry.class);
		taskMock = mock(Task.class);
		userMock = mock(User.class);
		userViewModelMock = mock(UserViewModel.class);
		userService = new UserService(userRepositoryMock, taskRepositoryMock, expenseRepositoryMock, timeEntryRepositoryMock, travelRepositoryMock);
	}

	@Test
	public void getTaskForUserForAPayPeriodReturnsListOfTasks() {
		List<String> sampleIdList = new ArrayList<String>();
		sampleIdList.add(TIME_ENTRY_ID);
		when(payPeriodMock.getTimeEntryIds()).thenReturn(sampleIdList);
		when(timeEntryRepositoryMock.findByUid(TIME_ENTRY_ID)).thenReturn(timeEntryMock);
		when(timeEntryMock.getUserEmail()).thenReturn(EMAIL);
		when(timeEntryMock.getuId()).thenReturn(TIME_ENTRY_ID);
		when(taskRepositoryMock.findByUid(TIME_ENTRY_ID)).thenReturn(taskMock);

		List<Task> simpleTaskList = userService.getTasksForUserForAPayPeriod(payPeriodMock, EMAIL);

		assertTrue(simpleTaskList.contains(taskMock));
	}

	@Test
	public void getTimeEntriesForUserForAPayPeriodReturnsListOfTimeEntries() {
		List<String> sampleIdList = new ArrayList<String>();
		sampleIdList.add(TIME_ENTRY_ID);
		when(payPeriodMock.getTimeEntryIds()).thenReturn(sampleIdList);
		when(timeEntryRepositoryMock.findByUid(TIME_ENTRY_ID)).thenReturn(timeEntryMock);
		when(timeEntryMock.getUserEmail()).thenReturn(EMAIL);

		List<TimeEntry> sampleTimeEntryList = userService.getTimeEntriesForUserForAPayPeriod(payPeriodMock, EMAIL);

		assertTrue(sampleTimeEntryList.contains(timeEntryMock));
	}

	@Test
	public void retrieveByEmailReturnsUser() {
		when(userRepositoryMock.findByEmail(EMAIL)).thenReturn(userMock);
		when(userMock.getEmail()).thenReturn(EMAIL);

		User user = userService.retrieveByEmail(EMAIL);

		assertEquals(userMock.getEmail(), user.getEmail());
	}

	@Test(expected = UserNotFoundException.class)
	public void retrieveByEmailThrowsExceptionWhenUserNotFound() {
		when(userRepositoryMock.findByEmail(EMAIL)).thenReturn(null);
		when(userMock.getEmail()).thenReturn(EMAIL);

		userService.retrieveByEmail(EMAIL);
	}

	@Test
	public void retrieveUsersByEmailCallsUserRepository() {
		List<String> emails = new ArrayList<String>();
		emails.add(EMAIL);
		emails.add(EMAIL2);

		userService.retrieveUsersByEmail(emails);

		verify(userRepositoryMock, times(1)).findByEmails(emails);
	}

	@Test
	public void updateEmployeeInformationCallsUserRepository() throws Exception {
		userService.updateEmployeeInformation(userViewModelMock);
		verify(userRepositoryMock, times(1)).store(any(User.class));
	}
}
