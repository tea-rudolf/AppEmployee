//package ca.ulaval.glo4003.appemployee.services;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//import static org.mockito.Mockito.doThrow;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
//import ca.ulaval.glo4003.appemployee.domain.repository.ExpenseRepository;
//import ca.ulaval.glo4003.appemployee.domain.repository.PayPeriodRepository;
//import ca.ulaval.glo4003.appemployee.domain.repository.TaskRepository;
//import ca.ulaval.glo4003.appemployee.domain.repository.TimeEntryRepository;
//import ca.ulaval.glo4003.appemployee.domain.repository.UserRepository;
//import ca.ulaval.glo4003.appemployee.domain.task.Task;
//import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntry;
//import ca.ulaval.glo4003.appemployee.domain.user.User;
//import ca.ulaval.glo4003.appemployee.persistence.RepositoryException;
//
//public class UserServiceTest {
//
//	private static final String TIME_ENTRY_ID = "id";
//	private static final String EMAIL = "employee1@employee.com";
//	private static final String EMAIL2 = "employee1@employee.com";
//
//	private UserService userService;
//	private TaskRepository taskRepositoryMock;
//	private UserRepository userRepositoryMock;
//	private PayPeriodRepository payPeriodRepositoryMock;
//	private ExpenseRepository expenseRepositoryMock;
//	private TimeEntryRepository timeEntryRepositoryMock;
//	private PayPeriod payPeriodMock;
//	private TimeEntry timeEntryMock;
//	private Task taskMock;
//	private User userMock;
//	private User userMock2;
//
//	@Before
//	public void init() {
//		userRepositoryMock = mock(UserRepository.class);
//		taskRepositoryMock = mock(TaskRepository.class);
//		payPeriodRepositoryMock = mock(PayPeriodRepository.class);
//		expenseRepositoryMock = mock(ExpenseRepository.class);
//		timeEntryRepositoryMock = mock(TimeEntryRepository.class);
//		payPeriodMock = mock(PayPeriod.class);
//		timeEntryMock = mock(TimeEntry.class);
//		taskMock = mock(Task.class);
//		userMock = mock(User.class);
//		userMock2 = mock(User.class);
//		userService = new UserService(userRepositoryMock, payPeriodRepositoryMock, taskRepositoryMock, expenseRepositoryMock, timeEntryRepositoryMock);
//	}
//
//	@Test
//	public void updateCurrentPayPeriodCallsCorrectRepositoryMethod() throws Exception {
//		userService.updateCurrentPayPeriod(payPeriodMock);
//		verify(payPeriodRepositoryMock, times(1)).update(payPeriodMock);
//	}
//
//	@Test(expected = RepositoryException.class)
//	public void updateCurrentPayPeriodThrowsExceptionIfPayPeriodIsNotUpdated() throws Exception {
//		doThrow(new RepositoryException()).when(payPeriodRepositoryMock).update(payPeriodMock);
//		userService.updateCurrentPayPeriod(payPeriodMock);
//	}
//
//	@Test
//	public void getTaskForUserForAPayPeriodReturnsListOfTasks() {
//		List<String> sampleIdList = new ArrayList<String>();
//		sampleIdList.add(TIME_ENTRY_ID);
//		when(payPeriodMock.getTimeEntryIds()).thenReturn(sampleIdList);
//		when(timeEntryRepositoryMock.findByUid(TIME_ENTRY_ID)).thenReturn(timeEntryMock);
//		when(timeEntryMock.getUserEmail()).thenReturn(EMAIL);
//		when(timeEntryMock.getuId()).thenReturn(TIME_ENTRY_ID);
//		when(taskRepositoryMock.findByUid(TIME_ENTRY_ID)).thenReturn(taskMock);
//
//		List<Task> simpleTaskList = userService.getTasksForUserForAPayPeriod(payPeriodMock, EMAIL);
//
//		assertTrue(simpleTaskList.contains(taskMock));
//	}
//
//	@Test
//	public void getTimeEntriesForUserForAPayPeriodReturnsListOfTimeEntries() {
//		List<String> sampleIdList = new ArrayList<String>();
//		sampleIdList.add(TIME_ENTRY_ID);
//		when(payPeriodMock.getTimeEntryIds()).thenReturn(sampleIdList);
//		when(timeEntryRepositoryMock.findByUid(TIME_ENTRY_ID)).thenReturn(timeEntryMock);
//		when(timeEntryMock.getUserEmail()).thenReturn(EMAIL);
//
//		List<TimeEntry> sampleTimeEntryList = userService.getTimeEntriesForUserForAPayPeriod(payPeriodMock, EMAIL);
//
//		assertTrue(sampleTimeEntryList.contains(timeEntryMock));
//	}
//
//	@Test
//	public void findByEmailReturnsUser() {
//		when(userRepositoryMock.findByEmail(EMAIL)).thenReturn(userMock);
//		when(userMock.getEmail()).thenReturn(EMAIL);
//
//		User user = userService.findByEmail(EMAIL);
//
//		assertEquals(userMock.getEmail(), user.getEmail());
//	}
//
//	@Test
//	public void findUsersByEmail() {
//		List<String> emails = new ArrayList<String>();
//		emails.add(EMAIL);
//		emails.add(EMAIL2);
//		when(userRepositoryMock.findByEmail(EMAIL)).thenReturn(userMock);
//		when(userRepositoryMock.findByEmail(EMAIL2)).thenReturn(userMock2);
//
//		List<User> users = userService.findUsersByEmail(emails);
//
//		assertEquals(2, users.size());
//	}
//}
