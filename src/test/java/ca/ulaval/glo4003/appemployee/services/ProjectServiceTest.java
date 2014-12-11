package ca.ulaval.glo4003.appemployee.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ca.ulaval.glo4003.appemployee.domain.project.Project;
import ca.ulaval.glo4003.appemployee.domain.project.ProjectProcessor;
import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.web.converters.ProjectConverter;
import ca.ulaval.glo4003.appemployee.web.converters.TaskConverter;
import ca.ulaval.glo4003.appemployee.web.converters.UserConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ProjectViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TaskViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.UserViewModel;

public class ProjectServiceTest {

	private Collection<Project> projects = new ArrayList<Project>();
	private Collection<ProjectViewModel> projectModels = new ArrayList<ProjectViewModel>();
	private List<String> expenseIds = new ArrayList<String>();
	private List<String> taskIds = new ArrayList<String>();
	private List<String> userIds = new ArrayList<String>();
	private Collection<TaskViewModel> taskModels = new ArrayList<TaskViewModel>();
	private List<Task> tasks = new ArrayList<Task>();
	private List<UserViewModel> userModels = new ArrayList<UserViewModel>();
	private List<User> users = new ArrayList<User>();

	private static final String PROJECT_NAME = "project";
	private static final String USER_EMAIL = "email@email.com";
	private static final String PROJECT_ID = "1";
	private static final double MULTIPLICATIVE_FACTOR = 1.0;

	@Mock
	private Project projectMock;

	@Mock
	private ProjectViewModel projectViewModelMock;

	@Mock
	private TaskConverter taskConverterMock;

	@Mock
	private UserConverter userConverterMock;

	@Mock
	private ProjectConverter projectConverterMock;

	@Mock
	private ProjectProcessor projectProcessorMock;

	@Mock
	private Task taskMock;

	@Mock
	private TaskViewModel taskViewModelMock;

	@Mock
	private User userMock;

	@InjectMocks
	private ProjectService projectService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		projectService = new ProjectService(projectConverterMock, taskConverterMock, userConverterMock,
				projectProcessorMock);
	}

	@Test
	public void retrieveAllProjectsReturnsCollectionOfProjectViewModels() {
		when(projectProcessorMock.retrieveAllProjects()).thenReturn(projects);
		when(projectConverterMock.convert(projects)).thenReturn(projectModels);

		Collection<ProjectViewModel> returnedProjectModels = projectService.retrieveAllProjects();

		assertEquals(projectModels, returnedProjectModels);
	}

	@Test
	public void editProjectCallsCorrectProcessorMethod() throws Exception {
		when(projectViewModelMock.getName()).thenReturn(PROJECT_NAME);
		when(projectViewModelMock.getSelectedUserEmail()).thenReturn(USER_EMAIL);

		projectService.editProject(PROJECT_ID, projectViewModelMock);

		verify(projectProcessorMock, times(1)).editProject(PROJECT_ID, PROJECT_NAME, USER_EMAIL);
	}

	@Test
	public void createProjectCallsCorrectProcessorMethod() throws Exception {
		when(projectViewModelMock.getName()).thenReturn(PROJECT_NAME);
		when(projectViewModelMock.getExpenseIds()).thenReturn(expenseIds);
		when(projectViewModelMock.getTaskIds()).thenReturn(taskIds);
		when(projectViewModelMock.getUserIds()).thenReturn(userIds);

		projectService.createProject(projectViewModelMock);

		verify(projectProcessorMock, times(1)).createProject(PROJECT_NAME, taskIds, userIds, expenseIds);
	}

	@Test
	public void retrieveProjectViewModelForExistingProjectReturnsProjectViewModel() {
		when(projectProcessorMock.retrieveProjectById(PROJECT_ID)).thenReturn(projectMock);
		when(projectConverterMock.convert(projectMock)).thenReturn(projectViewModelMock);
		when(projectProcessorMock.evaluateAvailableEmployeeEmailsByProject(PROJECT_ID)).thenReturn(userIds);

		ProjectViewModel returnedProjectModel = projectService.retrieveProjectViewModelForExistingProject(PROJECT_ID);

		assertEquals(projectViewModelMock, returnedProjectModel);
	}

	@Test
	public void retrieveTasksByProjectReturnsTaskViewModels() {
		when(projectProcessorMock.retrieveAllTasksByProjectId(PROJECT_ID)).thenReturn(tasks);
		when(taskConverterMock.convert(tasks)).thenReturn(taskModels);

		Collection<TaskViewModel> returnedTaskModels = projectService.retrieveTasksByProject(PROJECT_ID);

		assertEquals(taskModels, returnedTaskModels);
	}

	@Test
	public void retrieveEmployeesByProjectReturnsUserViewModels() {
		when(projectProcessorMock.retrieveAllEmployeesByProjectId(PROJECT_ID)).thenReturn(users);
		when(userConverterMock.convert(users)).thenReturn(userModels);

		Collection<UserViewModel> returnedUserModels = projectService.retieveEmployeesByProject(PROJECT_ID);

		assertEquals(userModels, returnedUserModels);
	}

	@Test
	public void addNewTaskToProjectCallsCorrectProcessorMethod() throws Exception {
		when(taskViewModelMock.getMultiplicativeFactor()).thenReturn(MULTIPLICATIVE_FACTOR);
		when(taskViewModelMock.getName()).thenReturn(PROJECT_NAME);

		projectService.addNewTaskToProject(PROJECT_ID, taskViewModelMock);

		verify(projectProcessorMock, times(1)).addTaskToProject(PROJECT_ID, PROJECT_NAME, MULTIPLICATIVE_FACTOR);
	}

}
