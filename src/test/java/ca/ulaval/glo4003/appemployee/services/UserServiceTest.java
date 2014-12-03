package ca.ulaval.glo4003.appemployee.services;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ca.ulaval.glo4003.appemployee.domain.exceptions.UserNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.expense.Expense;
import ca.ulaval.glo4003.appemployee.domain.repository.ExpenseRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.TaskRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.TimeEntryRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.UserRepository;
import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.time.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.time.TimeEntry;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.persistence.RepositoryException;
import ca.ulaval.glo4003.appemployee.web.converters.UserConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TimeViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.UserViewModel;

public class UserServiceTest {

	private static final String TIME_ENTRY_ID = "id";
	private static final String EMPLOYEE_ROLE = "EMPLOYEE";
	private static final String EMAIL = "employee1@employee.com";
	private static final String EMAIL2 = "employee2@employee.com";
	private static final String PASSWORD = "password";
	private static final double WAGE = 0;

	@Mock
	private TaskRepository taskRepositoryMock;

	@Mock
	private UserRepository userRepositoryMock;

	@Mock
	private ExpenseRepository expenseRepositoryMock;

	@Mock
	private TimeEntryRepository timeEntryRepositoryMock;

	@Mock
	private PayPeriod payPeriodMock;

	@Mock
	private TimeEntry timeEntryMock;

	@Mock
	private Task taskMock;

	@Mock
	private User userMock;

	@Mock
	private UserViewModel userViewModelMock;

	@Mock
	private TimeViewModel timeViewModelMock;

	@Mock
	private Expense expenseMock;

	@Mock
	private User secondUserMock;

	@Mock
	private UserConverter userConverterMock;

	@InjectMocks
	private User user;

	@InjectMocks
	private UserViewModel userViewModel;

	@InjectMocks
	private UserService userService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		userViewModel = new UserViewModel();
		userViewModel.setPassword(PASSWORD);
		user = new User(EMAIL, PASSWORD, null, WAGE);
		userService = new UserService(userRepositoryMock, taskRepositoryMock, timeEntryRepositoryMock, userConverterMock);
	}

	@Test
	public void getTaskForUserForAPayPeriodReturnsListOfTasks() {
		List<String> sampleIdList = new ArrayList<String>();
		sampleIdList.add(TIME_ENTRY_ID);
		when(payPeriodMock.getTimeEntryIds()).thenReturn(sampleIdList);
		when(timeEntryRepositoryMock.findByUid(TIME_ENTRY_ID)).thenReturn(timeEntryMock);
		when(timeEntryMock.getUserEmail()).thenReturn(EMAIL);
		when(timeEntryMock.getUid()).thenReturn(TIME_ENTRY_ID);
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
	public void getTimeEntryFindsCorrectTimeEntry() {
		when(timeEntryMock.getUid()).thenReturn(TIME_ENTRY_ID);
		when(timeEntryRepositoryMock.findByUid(TIME_ENTRY_ID)).thenReturn(timeEntryMock);
		TimeEntry returnedTimeEntry = userService.getTimeEntry(TIME_ENTRY_ID);
		assertEquals(timeEntryMock.getUid(), returnedTimeEntry.getUid());
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
	public void retrieveUsersByEmailReturnsListOfUsers() {
		List<String> emails = new ArrayList<String>();
		emails.add(EMAIL);
		emails.add(EMAIL2);

		when(userMock.getEmail()).thenReturn(EMAIL);
		when(secondUserMock.getEmail()).thenReturn(EMAIL2);

		List<User> users = new ArrayList<User>();
		users.add(userMock);
		users.add(secondUserMock);

		when(userRepositoryMock.findByEmails(emails)).thenReturn(users);

		List<User> returnedUsersList = userService.retrieveUsersByEmail(emails);

		assertTrue(returnedUsersList.contains(userMock));
		assertTrue(returnedUsersList.contains(secondUserMock));
	}

	@Test
	public void retrieveAllUserEmailsReturnsListOfEmails() {
		List<User> users = new ArrayList<User>();
		users.add(userMock);
		when(userRepositoryMock.findAll()).thenReturn(users);
		when(userMock.getEmail()).thenReturn(EMAIL);
		List<String> returnedEmailsList = userService.retrieveAllUserEmails();
		assertTrue(returnedEmailsList.contains(EMAIL));
	}

	@Test
	public void updateEmployeeInformationCallsUserRepository() throws Exception {
		when(userViewModelMock.getRole()).thenReturn(EMPLOYEE_ROLE);
		userService.updateEmployeeInformation(userViewModelMock);
		verify(userRepositoryMock, times(1)).store(any(User.class));
	}

	public void updatePasswordCallsCorrectRepositoryMethod() throws Exception {
		when(userRepositoryMock.findByEmail(EMAIL)).thenReturn(userMock);
		userService.updatePassword(EMAIL, userViewModelMock);
		verify(userRepositoryMock, times(1)).store(userMock);
	}

	@Test(expected = RepositoryException.class)
	public void updatePasswordThrowsExceptionWhenUserIsNotUpdated() throws Exception {
		when(userRepositoryMock.findByEmail(EMAIL)).thenReturn(userMock);
		doThrow(new RepositoryException()).when(userRepositoryMock).store(userMock);
		userService.updatePassword(EMAIL, userViewModelMock);
	}

	@Test
	public void updatePasswordSetsNewPassword() {
		when(userRepositoryMock.findByEmail(EMAIL)).thenReturn(user);
		userService.updatePassword(EMAIL, userViewModel);
		assertEquals(userViewModel.getPassword(), user.getPassword());
	}

	@Test(expected = RepositoryException.class)
	public void updateTimeEntryThrowsRepositoryExceptionIfCannotStore() throws Exception {
		when(timeEntryRepositoryMock.findByUid(TIME_ENTRY_ID)).thenReturn(timeEntryMock);
		doThrow(new RepositoryException()).when(timeEntryRepositoryMock).store(timeEntryMock);
		userService.updateTimeEntry(TIME_ENTRY_ID, timeViewModelMock);
	}

}
