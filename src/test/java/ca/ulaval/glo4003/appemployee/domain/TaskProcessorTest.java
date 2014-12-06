package ca.ulaval.glo4003.appemployee.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ca.ulaval.glo4003.appemployee.domain.exceptions.TaskNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.repository.TaskRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.UserRepository;
import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.task.TaskProcessor;
import ca.ulaval.glo4003.appemployee.domain.user.Role;
import ca.ulaval.glo4003.appemployee.domain.user.User;

public class TaskProcessorTest {
	
	private List<String> emails = new ArrayList<String>();
	private List<User> users = new ArrayList<User>();
	private Collection<User> allUsers = new ArrayList<User>();
	private List<Task> tasks = new ArrayList<Task>();
	private List<String> taskIds = new ArrayList<String>();
	
	private static final String TASK_UID = "uid";
	private static final String USER_EMAIL = "email@email.com";
	private static final String TASK_NAME = "name";
	private static final double MULTIPLICATIVE_FACTOR = 1.0;
	
	@Mock
	private Task taskMock;
	
	@Mock
	private User userMock;
	
	@Mock
	private TaskRepository taskRepositoryMock;
	
	@Mock
	private UserRepository userRepositoryMock;
	
	@InjectMocks
	private TaskProcessor taskProcessor;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		taskProcessor = new TaskProcessor(taskRepositoryMock, userRepositoryMock);
	}
	
	@Test
	public void canInstantiateTaskProcessor() {
		assertNotNull(taskProcessor);
	}

	@Test
	public void retrieveTaskByUidReturnsCorrectTask() throws TaskNotFoundException {
		when(taskRepositoryMock.findByUid(TASK_UID)).thenReturn(taskMock);
		Task returnedTask = taskProcessor.retrieveTaskByUid(TASK_UID);
		assertEquals(taskMock, returnedTask);
	}
	
	@Test(expected = TaskNotFoundException.class)
	public void retrieveTaskByUidThrowsExceptionIfTaskIsNotFound() throws TaskNotFoundException {
		when(taskRepositoryMock.findByUid(TASK_UID)).thenReturn(null);
		taskProcessor.retrieveTaskByUid(TASK_UID);
	}
	
	@Test
	public void retrieveAllEmployeesByTaskIdReturnsListOfUsers() {
		when(taskRepositoryMock.findByUid(TASK_UID)).thenReturn(taskMock);
		when(taskMock.getAuthorizedUsers()).thenReturn(emails);
		when(userRepositoryMock.findByEmails(emails)).thenReturn(users);
		
		List<User> returnedUsers = taskProcessor.retrieveAllEmployeesByTaskId(TASK_UID);
		
		assertEquals(users, returnedUsers);
	}
	
	@Test
	public void evaluateAvailableEmployeeEmailsByTaskReturnsListOfEmails() {
		when(taskRepositoryMock.findByUid(TASK_UID)).thenReturn(taskMock);
		when(userRepositoryMock.findAll()).thenReturn(allUsers);
		when(taskMock.userIsAlreadyAssignedToTask(USER_EMAIL)).thenReturn(false);
		when(userMock.getRole()).thenReturn(Role.EMPLOYEE);
		when(userMock.getEmail()).thenReturn(USER_EMAIL);
		List<String> availableUserEmails = new ArrayList<String>();
		
		List<String> returnedEmails = taskProcessor.evaluateAvailableEmployeeEmailsByTask(TASK_UID);
		
		assertEquals(availableUserEmails, returnedEmails);
	}
	
	@Test
	public void addEmployeeToEachTaskOfProjectCallsCorrectDomainMethod() {
		when(taskRepositoryMock.findByUids(taskIds)).thenReturn(tasks);
		tasks.add(taskMock);
		when(taskMock.userIsAlreadyAssignedToTask(USER_EMAIL)).thenReturn(false);
		when(taskMock.getAuthorizedUsers()).thenReturn(emails);
		
		taskProcessor.addEmployeeToEachTaskOfProject(taskIds, USER_EMAIL);
		
		verify(taskMock, times(1)).assignUserToTask(USER_EMAIL);
	}
	
	@Test
	public void editTaskCallsCorrectDomainMethodIfUserEmailParameterIsEmpty() throws Exception {
		when(taskRepositoryMock.findByUid(TASK_UID)).thenReturn(taskMock);
		taskProcessor.editTask(TASK_UID, TASK_NAME, "", MULTIPLICATIVE_FACTOR);
		verify(taskMock, times(1)).update(TASK_NAME, MULTIPLICATIVE_FACTOR);
	}
	
	@Test
	public void editTaskCallsCorrectDomainMethodIfUserEmailParameterIsGiven() throws Exception {
		when(taskRepositoryMock.findByUid(TASK_UID)).thenReturn(taskMock);
		taskProcessor.editTask(TASK_UID, TASK_NAME, USER_EMAIL, MULTIPLICATIVE_FACTOR);
		verify(taskMock, times(1)).update(TASK_NAME, USER_EMAIL, MULTIPLICATIVE_FACTOR);
	}
	
	@Test
	public void retrieveAllTasksAssignToUserIdReturnsListOfTasks() {
		List<Task> assignedToCurrentUserTasks = new ArrayList<Task>();
		assignedToCurrentUserTasks.add(taskMock);
		when(taskRepositoryMock.findAll()).thenReturn(tasks);
		tasks.add(taskMock);
		when(taskMock.userIsAlreadyAssignedToTask(USER_EMAIL)).thenReturn(true);
		
		List<Task> returnedTasks = taskProcessor.retrieveAllTasksAssignedToUserId(USER_EMAIL);
		
		assertEquals(assignedToCurrentUserTasks, returnedTasks);
	}
	
	@Test
	public void retrieveTaskByIdReturnsValidTask() {
		when(taskRepositoryMock.findByUid(TASK_UID)).thenReturn(taskMock);
		Task returnedTask = taskProcessor.retrieveTaskById(TASK_UID);
		assertEquals(taskMock, returnedTask);
	}
	
	@Test
	public void retrieveTaskNameReturnsCorrectTaskName() {
		when(taskMock.getName()).thenReturn(TASK_NAME);
		when(taskProcessor.retrieveTaskById(TASK_UID)).thenReturn(taskMock);
		String returnedTaskName = taskProcessor.retrieveTaskName(TASK_UID);
		assertEquals(taskMock.getName(), returnedTaskName);
	}
	
	@Test
	public void retrieveAllTasksByUserIdReturnsListOfTasks() {
		emails.add(USER_EMAIL);
		when(taskMock.getAuthorizedUsers()).thenReturn(emails);
		taskMock.assignUserToTask(USER_EMAIL);
		List<Task> returnedTasks = taskProcessor.retrieveAllTasksByUserId(USER_EMAIL);
		assertEquals(tasks, returnedTasks);
	}
}
