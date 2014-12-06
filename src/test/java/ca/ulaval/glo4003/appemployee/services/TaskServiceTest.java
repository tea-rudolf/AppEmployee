package ca.ulaval.glo4003.appemployee.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.task.TaskProcessor;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.web.converters.TaskConverter;
import ca.ulaval.glo4003.appemployee.web.converters.UserConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TaskViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.UserViewModel;

public class TaskServiceTest {
	
	private List<String> emails = new ArrayList<String>();
	private List<UserViewModel> userModels = new ArrayList<UserViewModel>();
	private List<User> users = new ArrayList<User>();
	
	private static final String TASK_ID = "1";
	private static final String TASK_NAME = "name";
	private static final String USER_EMAIL = "email@email.com";
	private static final double MULTIPLICATIVE_FACTOR = 1.0;
	private static final String PROJECT_ID = "1";
	
	@Mock
	private Task taskMock;
	
	@Mock
	private TaskViewModel taskViewModelMock;
	
	@Mock
	private TaskProcessor taskProcessorMock;
	
	@Mock
	private TaskConverter taskConverterMock;
	
	@Mock
	private UserConverter userConverterMock;
	
	@InjectMocks
	private TaskService taskService;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		taskService = new TaskService(taskProcessorMock, taskConverterMock, userConverterMock);
	}
	
	@Test
	public void retrieveTaskViewModelForExistingTaskReturnsCorrectViewModel() {
		when(taskProcessorMock.retrieveTaskById(TASK_ID)).thenReturn(taskMock);
		when(taskConverterMock.convert(taskMock)).thenReturn(taskViewModelMock);
		when(taskProcessorMock.evaluateAvailableEmployeeEmailsByTask(TASK_ID)).thenReturn(emails);
		
		TaskViewModel returnedModel = taskService.retrieveTaskViewModelForExistingTask(TASK_ID);
		
		assertEquals(taskViewModelMock, returnedModel);
	}
	
	@Test
	public void retrieveEmployeesByTaskReturnsValidListOfUserViewModels() {
		when(taskProcessorMock.retrieveAllEmployeesByTaskId(TASK_ID)).thenReturn(users);
		when(userConverterMock.convert(users)).thenReturn(userModels);
		
		List<UserViewModel> returnedUserModels = taskService.retrieveEmployeesByTask(TASK_ID);
		
		assertEquals(userModels, returnedUserModels);
	}
	
	@Test
	public void editTaskCallsCorrectProcessorMethod() throws Exception {
		when(taskViewModelMock.getName()).thenReturn(TASK_NAME);
		when(taskViewModelMock.getSelectedUserEmail()).thenReturn(USER_EMAIL);
		when(taskViewModelMock.getMultiplicativeFactor()).thenReturn(MULTIPLICATIVE_FACTOR);
		
		taskService.editTask(PROJECT_ID, TASK_ID, taskViewModelMock);
		
		verify(taskProcessorMock, times(1)).editTask(TASK_ID, TASK_NAME, USER_EMAIL, MULTIPLICATIVE_FACTOR);
	}

}
