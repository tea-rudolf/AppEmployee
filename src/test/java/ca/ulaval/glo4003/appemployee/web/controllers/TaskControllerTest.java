package ca.ulaval.glo4003.appemployee.web.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.services.TaskService;
import ca.ulaval.glo4003.appemployee.services.UserService;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TaskViewModel;

public class TaskControllerTest {
	
	private static final String SAMPLE_PROJECT_NUMBER = "1";
	private static final String SAMPLE_TASK_NUMBER = "1";
	private static final String EMAIL_KEY = "email";
	private static final String VALID_EMAIL = "employee@employee.com";
	private static final String EDIT_TASK_FORM = "editTask";
	private static final String EDIT_TASK_REDIRECT = "redirect:/projects/1/tasks/1/edit";
	
	@Mock
	private Model modelMock;
	
	@Mock
	private TaskViewModel taskViewModelMock;
	
	@Mock
	private User userMock;
	
	@Mock
	private HttpSession sessionMock;
	
	@Mock
	private TaskService taskServiceMock;
	
	@Mock
	private UserService userServiceMock;
	
	@InjectMocks
	private TaskController taskController;
	
	@Before 
	public void init() {
		MockitoAnnotations.initMocks(this);
		taskController = new TaskController(taskServiceMock, userServiceMock);
	}
	
	@Test
	public void showEditTaskFormReturnsEditTaskFormIfSessionAttributeIsNotNull() {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(userServiceMock.retrieveUserByEmail(VALID_EMAIL)).thenReturn(userMock);
		String returnedForm = taskController.showEditTaskForm(SAMPLE_PROJECT_NUMBER, SAMPLE_TASK_NUMBER, modelMock, sessionMock);
		assertEquals(returnedForm, EDIT_TASK_FORM);
	}
	
	@Test
	public void editTaskRedirectsToEditTaskPageIfServiceMethodIsCorrectlyCalled() throws Exception {
		String returnedForm = taskController.editTask(SAMPLE_PROJECT_NUMBER, SAMPLE_TASK_NUMBER, modelMock, taskViewModelMock, sessionMock);
		assertEquals(returnedForm, EDIT_TASK_REDIRECT);
	}
	
	@Test
	public void editTaskCallsServiceMethod() throws Exception {
		taskController.editTask(SAMPLE_PROJECT_NUMBER, SAMPLE_TASK_NUMBER, modelMock, taskViewModelMock, sessionMock);
		verify(taskServiceMock, times(1)).editTask(SAMPLE_PROJECT_NUMBER, SAMPLE_TASK_NUMBER, taskViewModelMock);
	}

}
