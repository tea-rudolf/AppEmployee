package ca.ulaval.glo4003.appemployee.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.appemployee.domain.expense.Expense;
import ca.ulaval.glo4003.appemployee.domain.expense.ExpenseRepository;
import ca.ulaval.glo4003.appemployee.domain.payperiod.CurrentDateIsInvalidException;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriodRepository;
import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.task.TaskRepository;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntry;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntryRepository;
import ca.ulaval.glo4003.appemployee.domain.user.Role;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.user.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class PayPeriodServiceTest {

	private static final String VALID_EMAIL = "test@test.com";
	private static final String INVALID_EMAIL = "crap@crap.com";
	private static final String VALID_PASSWORD = "1234";
	private static final Integer VALID_WAGE = 0;
	private static final LocalDate VALID_DATE = new LocalDate("2014-10-14");
	private static final LocalDate START_DATE = new LocalDate("2014-10-02");
	private static final LocalDate END_DATE = new LocalDate("2014-10-15");
	private static final LocalDate PREVIOUS_DATE = new LocalDate("2014-10-01");
	private static final LocalDate PREVIOUS_START_DATE = new LocalDate("2014-09-25");
	private static final Integer NUMBER_OF_ITEMS = 0;
	private static final double EPSILON = 0.001;

	private UserRepository userRepositoryMock;
	private PayPeriodRepository payPeriodRepositoryMock;
	private TaskRepository taskRepositoryMock;
	private TimeEntryRepository timeEntryRepositoryMock;
	private ExpenseRepository expenseRepositoryMock;
	private User user;
	private PayPeriod payPeriod;
	private PayPeriod previousPayPeriod;
	private PayPeriod payPeriodMock;
	private PayPeriodService payPeriodServiceMock;
	private TimeEntry timeEntryMock;


	@Before
	public void init() {
		userRepositoryMock = mock(UserRepository.class);
		payPeriodMock = mock(PayPeriod.class);
		timeEntryMock = mock(TimeEntry.class);
		payPeriodRepositoryMock = mock(PayPeriodRepository.class);
		taskRepositoryMock = mock(TaskRepository.class);
		timeEntryRepositoryMock = mock(TimeEntryRepository.class);
		expenseRepositoryMock = mock(ExpenseRepository.class);
		payPeriodServiceMock = mock(PayPeriodService.class);
		user = new User(VALID_EMAIL, VALID_PASSWORD, Role.EMPLOYEE.toString(), VALID_WAGE);
		payPeriod = new PayPeriod(START_DATE, END_DATE);
		previousPayPeriod = new PayPeriod(PREVIOUS_START_DATE, PREVIOUS_DATE);
		payPeriodServiceMock = new PayPeriodService(payPeriodRepositoryMock, userRepositoryMock, taskRepositoryMock, timeEntryRepositoryMock, expenseRepositoryMock);
	}

	@Test
	public void getUserByEmailReturnsUserIfSuccessful() {
		when(userRepositoryMock.findByEmail(VALID_EMAIL)).thenReturn(user);
		User sampleUser = payPeriodServiceMock.getUserByEmail(VALID_EMAIL);
		assertEquals(sampleUser.getEmail(), VALID_EMAIL);
	}

	@Test
	public void getUserByEmailCallsCorrectMethodInRepository() {
		payPeriodServiceMock.getUserByEmail(VALID_EMAIL);
		verify(userRepositoryMock, times(1)).findByEmail(VALID_EMAIL);
	}

	@Test(expected = UserNotFoundException.class)
	public void getUserByEmailThrowsExceptionIfUserNotFound() {
		when(userRepositoryMock.findByEmail(INVALID_EMAIL)).thenThrow(new UserNotFoundException());
		payPeriodServiceMock.getUserByEmail(INVALID_EMAIL);
	}

	@Test
	public void getCurrentPayPeriodReturnsPayPeriodIfSuccessful(){
		when(payPeriodRepositoryMock.findPayPeriod(VALID_DATE)).thenReturn(payPeriod);
		PayPeriod samplePayPeriod = payPeriodServiceMock.getCurrentPayPeriod();
		assertEquals(samplePayPeriod.getEndDate(), END_DATE);
	}
	
	@Test(expected = CurrentDateIsInvalidException.class)
	public void getCurrentPayPeriodThrowsExceptionIfPayPeriodDoesNotExist(){
		when(payPeriodRepositoryMock.findPayPeriod(VALID_DATE)).thenThrow(new CurrentDateIsInvalidException());
		payPeriodServiceMock.getCurrentPayPeriod();
	}
	
	@Test
	public void getCurrentPayPeriodCallsCorrectMethodInRepository(){
		payPeriodServiceMock.getCurrentPayPeriod();
		verify(payPeriodRepositoryMock, times(1)).findPayPeriod(VALID_DATE);
	}
	
	@Test
	public void getPreviousPayPeriodCallsCorrectMethodInRepository(){
		when(payPeriodRepositoryMock.findPayPeriod(VALID_DATE)).thenReturn(payPeriod);
		payPeriodServiceMock.getPreviousPayPeriod();
		verify(payPeriodRepositoryMock, times(1)).findPayPeriod(PREVIOUS_DATE);
	}
	
	@Test
	public void getPreviousPayPeriodFindsCorrectPayPeriod(){
		when(payPeriodServiceMock.getCurrentPayPeriod()).thenReturn(payPeriod);
		when(payPeriodRepositoryMock.findPayPeriod(PREVIOUS_DATE)).thenReturn(previousPayPeriod);
		PayPeriod samplePayPeriod = payPeriodServiceMock.getPreviousPayPeriod();
		assertEquals(samplePayPeriod.getStartDate(), PREVIOUS_START_DATE);
	}
	
	@Test
	public void getTasksForUserReturnsListOfTasks(){
		when(timeEntryMock.getUserId()).thenReturn(VALID_EMAIL);
		List<Task> sampleList = payPeriodServiceMock.getTasksForUser(payPeriod, VALID_EMAIL);
		assertEquals(sampleList.size(), NUMBER_OF_ITEMS, EPSILON);
	}
	
	@Test
	public void getTasksForUserCallsGetTimeEntryIdsMethod(){
		payPeriodServiceMock.getTasksForUser(payPeriodMock, VALID_EMAIL);
		verify(payPeriodMock, times(1)).getTimeEntryIds();
	}
	
	@Test
	public void getExpensesForUserReturnsListOfExpenses(){
		when(payPeriodMock.getStartDate()).thenReturn(START_DATE);
		when(payPeriodMock.getEndDate()).thenReturn(END_DATE);
		List<Expense> sampleList = payPeriodServiceMock.getExpensesForUser(payPeriod, VALID_EMAIL);
		assertEquals(sampleList.size(), NUMBER_OF_ITEMS, EPSILON);
	}
	
	
	
}
