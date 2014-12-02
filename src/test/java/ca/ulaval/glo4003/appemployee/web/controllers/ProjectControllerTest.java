package ca.ulaval.glo4003.appemployee.web.controllers;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import ca.ulaval.glo4003.appemployee.domain.exceptions.ProjectExistsException;
import ca.ulaval.glo4003.appemployee.domain.project.Project;
import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.services.ProjectService;
import ca.ulaval.glo4003.appemployee.services.UserService;
import ca.ulaval.glo4003.appemployee.web.converters.ProjectConverter;
import ca.ulaval.glo4003.appemployee.web.converters.TaskConverter;
import ca.ulaval.glo4003.appemployee.web.converters.UserConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.MessageViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ProjectViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TaskViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.UserViewModel;

public class ProjectControllerTest {
	private static final String SAMPLE_PROJECTNUMBER = "1";
	private static final String SAMPLE_TASKNUMBER = "2";
	private static final String PROJECT_NAME = "Project1";
	private static final String EMAIL_KEY = "email";
	private static final String VALID_EMAIL = "employee@employee.com";
	private static final String PROJECTS_LIST_FORM = "projectList";
	private static final String CREATE_PROJECT_FORM = "createProject";
	private static final String REDIRECT_EDIT_PROJECT = "redirect:/projects/1/edit";

	private List<String> taskIds = new ArrayList<String>();
	private List<String> expenseIds = new ArrayList<String>();
	private List<String> userIds = new ArrayList<String>();
	
	@Mock
	private HttpSession sessionMock;

	@Mock
	private Model modelMock;

	@Mock
	private ProjectService projectServiceMock;

	@Mock
	private ProjectConverter projectConverterMock;

	@Mock
	private ProjectViewModel projectViewModelMock;

	@Mock
	private ProjectViewModel projectViewModel;

	@Mock
	private Project projectMock;

	@Mock
	private Task taskMock;

	@Mock
	private TaskConverter taskConverterMock;

	@Mock
	private TaskViewModel taskViewModelMock;

	@Mock
	private TaskViewModel taskViewModel;

	@Mock
	private UserConverter userConverterMock;

	@Mock
	private UserService userServiceMock;

	@Mock
	private User currentUserMock;

	@InjectMocks
	private ProjectController projectController;

	@InjectMocks
	private List<UserViewModel> userViewModelCollection = new ArrayList<UserViewModel>();

	@InjectMocks
	private List<Task> taskList = new ArrayList<Task>();

	@InjectMocks
	private List<User> employeeList = new ArrayList<User>();

	@InjectMocks
	private Collection<TaskViewModel> taskViewModelCollection = new ArrayList<TaskViewModel>();

	@InjectMocks
	private List<Project> projectList = new ArrayList<Project>();

	@InjectMocks
	private Collection<ProjectViewModel> projectViewModelCollection = new ArrayList<ProjectViewModel>();

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		projectViewModel = new ProjectViewModel();
		projectViewModel.setUserEmail("");
		taskViewModel = new TaskViewModel();
		taskViewModel.setUserEmail("");
		projectController = new ProjectController(projectServiceMock, userServiceMock, projectConverterMock, taskConverterMock, userConverterMock);
	}

	
	@Test
	public void getProjectsReturnsProjectsListFormIfViewModelIdValid() {
		when(projectServiceMock.getAllProjects()).thenReturn(projectList);
		when(projectConverterMock.convert(projectList)).thenReturn(projectViewModelCollection);
		
		String returnedForm = projectController.getProjects(modelMock, sessionMock);
		
		assertEquals(PROJECTS_LIST_FORM, returnedForm);
	}

	@Test
	public void createProjectReturnsCreateProjectFormIfSuccessful() {
		String returnedForm = projectController.createProject(modelMock, projectViewModelMock, sessionMock);
		assertEquals(CREATE_PROJECT_FORM, returnedForm);
	}

//	@Test
//	public void projectModificationUpdatesTheModelCorrectly() {
//		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
//		when(projectServiceMock.getProjectById(eq(SAMPLE_PROJECTNUMBER))).thenReturn(projectMock);
//		when(projectConverterMock.convert(projectMock)).thenReturn(projectViewModelMock);
//		when(projectServiceMock.getAllTasksByProjectId(SAMPLE_PROJECTNUMBER)).thenReturn(taskList);
//		when(projectServiceMock.getAllEmployeesByProjectId(eq(SAMPLE_PROJECTNUMBER))).thenReturn(employeeList);
//		when(taskConverterMock.convert(taskList)).thenReturn(taskViewModelCollection);
//		when(userServiceMock.retrieveByEmail(sessionMock.getAttribute(EMAIL_KEY).toString())).thenReturn(currentUserMock);
//		when(userConverterMock.convert(employeeList)).thenReturn(userViewModelCollection);
//
//		projectController.editProject(SAMPLE_PROJECTNUMBER, modelMock, sessionMock);
//
//		assertSame(modelMock.asMap().get("project"), projectViewModelMock);
//	}

	@Test
	public void editProjectCallsTheCorrectServiceMethods() throws Exception {
		projectController.saveEditedProject(SAMPLE_PROJECTNUMBER, modelMock, projectViewModel, sessionMock);
		verify(projectServiceMock).updateProject(SAMPLE_PROJECTNUMBER, projectViewModel);
	}

//	@Test
//	public void taskCreationUpdatesTheModelCorrectly() {
//		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
//
//		projectController.createTask(SAMPLE_PROJECTNUMBER, modelMock, taskViewModelMock, sessionMock);
//
//		assertSame(modelMock.asMap().get("task"), taskViewModelMock);
//		assertEquals(modelMock.asMap().get("projectNumber"), SAMPLE_PROJECTNUMBER);
//	}

//	@Test
//	public void saveTaskCallsTheCorrectServiceMethods() throws Exception {
//		when(taskConverterMock.convert(taskViewModelMock)).thenReturn(taskMock);
//		projectController.saveTask(SAMPLE_PROJECTNUMBER, model, taskViewModelMock, sessionMock);
//		verify(projectServiceMock).addNewTaskToProject(taskViewModelMock, SAMPLE_PROJECTNUMBER);
//	}

//	@Test
//	public void taskModificationUpdatesTheModelCorrectly() {
//		List<String> authorizedUsers = new ArrayList<String>();
//		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
//		when(projectServiceMock.getTaskById(eq(SAMPLE_TASKNUMBER))).thenReturn(taskMock);
//		when(taskConverterMock.convert(taskMock)).thenReturn(taskViewModelMock);
//		when(taskMock.getAuthorizedUsers()).thenReturn(authorizedUsers);
//		when(userServiceMock.retrieveUsersByEmail(authorizedUsers)).thenReturn(employeeList);
//		when(userServiceMock.retrieveByEmail(sessionMock.getAttribute(EMAIL_KEY).toString())).thenReturn(currentUserMock);
//		when(userConverterMock.convert(employeeList)).thenReturn(userViewModelCollection);
//
//		projectController.editTask(SAMPLE_PROJECTNUMBER, SAMPLE_TASKNUMBER, modelMock, sessionMock);
//
//		assertSame(modelMock.asMap().get("task"), taskViewModelMock);
//		assertEquals(modelMock.asMap().get("projectNumber"), SAMPLE_PROJECTNUMBER);
//	}

	@Test
	public void editTaskCallsTheCorrectServiceMethods() throws Exception {
		projectController.saveEditedTask(SAMPLE_PROJECTNUMBER, SAMPLE_TASKNUMBER, modelMock, taskViewModel, sessionMock);
		verify(projectServiceMock).updateTask(SAMPLE_PROJECTNUMBER, SAMPLE_TASKNUMBER, taskViewModel);
	}

}
