package ca.ulaval.glo4003.appemployee.web.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
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
	private static final String EMAIL_KEY = "email";
	private static final String VALID_EMAIL = "employee@employee.com";
	private static final String REDIRECT_LINK = "redirect:/";

	private HttpSession sessionMock;
	private Model model = new ExtendedModelMap();
	private ProjectController projectController;
	private ProjectService projectServiceMock;
	private ProjectConverter projectConverterMock;
	private ProjectViewModel projectViewModelMock;
	private ProjectViewModel projectViewModel;
	private Project projectMock;
	private List<Project> projectList = new ArrayList<Project>();
	private Collection<ProjectViewModel> projectViewModelCollection = new ArrayList<ProjectViewModel>();
	private Task taskMock;
	private TaskConverter taskConverterMock;
	private TaskViewModel taskViewModelMock;
	private TaskViewModel taskViewModel;
	private List<Task> taskList = new ArrayList<Task>();
	private List<User> employeeList = new ArrayList<User>();
	private Collection<TaskViewModel> taskViewModelCollection = new ArrayList<TaskViewModel>();
	private UserConverter userConverterMock;
	private UserService userServiceMock;
	private User currentUserMock;
	private List<UserViewModel> userViewModelCollection = new ArrayList<UserViewModel>();

	@Before
	public void init() {
		sessionMock = mock(HttpSession.class);
		projectMock = mock(Project.class);
		projectServiceMock = mock(ProjectService.class);
		projectConverterMock = mock(ProjectConverter.class);
		projectViewModelMock = mock(ProjectViewModel.class);
		taskMock = mock(Task.class);
		taskConverterMock = mock(TaskConverter.class);
		taskViewModelMock = mock(TaskViewModel.class);
		userConverterMock = mock(UserConverter.class);
		userServiceMock = mock(UserService.class);
		currentUserMock = mock(User.class);
		projectViewModel = new ProjectViewModel();
		projectViewModel.setUserEmail("");
		taskViewModel = new TaskViewModel();
		taskViewModel.setUserEmail("");
		projectController = new ProjectController(projectServiceMock, userServiceMock, projectConverterMock, taskConverterMock, userConverterMock);
	}

	@Test
	public void getProjectsUpdatesTheModelCorrectly() {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(projectServiceMock.getAllProjects()).thenReturn(projectList);
		when(projectConverterMock.convert(projectList)).thenReturn(projectViewModelCollection);

		projectController.getProjects(model, sessionMock);

		assertSame(model.asMap().get("projects"), projectViewModelCollection);
	}

	@Test
	public void getProjectsReturnRedirectIfSessionAttributeIsNull() {
		String returnedForm = projectController.getProjects(model, sessionMock);
		assertEquals(REDIRECT_LINK, returnedForm);
	}

	@Test
	public void projectCreationUpdatesTheModelCorrectly() {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		projectController.createProject(model, projectViewModelMock, sessionMock);
		assertSame(model.asMap().get("project"), projectViewModelMock);
	}

	@Test
	public void projectCreationReturnsRedirectIfSessionAttributeIsNull() {
		String returnedForm = projectController.createProject(model, projectViewModelMock, sessionMock);
		assertEquals(REDIRECT_LINK, returnedForm);
	}

	@Test
	public void addProjectCallsTheCorrectServiceMethods() throws Exception {
		when(projectConverterMock.convert(projectViewModelMock)).thenReturn(projectMock);
		projectController.saveProject(model, projectViewModelMock, sessionMock);
		verify(projectServiceMock).addProject(projectMock);
	}

	@Test
	public void addProjectReturnsAnErrorMessageOnProjectExistsException() throws Exception {
		when(projectConverterMock.convert(projectViewModelMock)).thenReturn(projectMock);
		doThrow(new ProjectExistsException()).when(projectServiceMock).addProject(projectMock);

		projectController.saveProject(model, projectViewModelMock, sessionMock);

		assertEquals(model.asMap().get("message").getClass(), MessageViewModel.class);
	}

	@Test
	public void projectModificationUpdatesTheModelCorrectly() {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(projectServiceMock.getProjectById(eq(SAMPLE_PROJECTNUMBER))).thenReturn(projectMock);
		when(projectConverterMock.convert(projectMock)).thenReturn(projectViewModelMock);
		when(projectServiceMock.getAllTasksByProjectId(SAMPLE_PROJECTNUMBER)).thenReturn(taskList);
		when(projectServiceMock.getAllEmployeesByProjectId(eq(SAMPLE_PROJECTNUMBER))).thenReturn(employeeList);
		when(taskConverterMock.convert(taskList)).thenReturn(taskViewModelCollection);
		when(userServiceMock.retrieveByEmail(sessionMock.getAttribute(EMAIL_KEY).toString())).thenReturn(currentUserMock);
		when(userConverterMock.convert(employeeList)).thenReturn(userViewModelCollection);

		projectController.editProject(SAMPLE_PROJECTNUMBER, model, sessionMock);

		assertSame(model.asMap().get("project"), projectViewModelMock);
	}

	@Test
	public void projectModificationReturnsRedirectIfSessionAttributeIsNull() {
		String returnedForm = projectController.editProject(SAMPLE_PROJECTNUMBER, model, sessionMock);
		assertEquals(REDIRECT_LINK, returnedForm);
	}

	@Test
	public void editProjectCallsTheCorrectServiceMethods() throws Exception {
		projectController.saveEditedProject(SAMPLE_PROJECTNUMBER, model, projectViewModel, sessionMock);
		verify(projectServiceMock).updateProject(SAMPLE_PROJECTNUMBER, projectViewModel);
	}

	@Test
	public void taskCreationUpdatesTheModelCorrectly() {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);

		projectController.createTask(SAMPLE_PROJECTNUMBER, model, taskViewModelMock, sessionMock);

		assertSame(model.asMap().get("task"), taskViewModelMock);
		assertEquals(model.asMap().get("projectNumber"), SAMPLE_PROJECTNUMBER);
	}

	@Test
	public void taskCreationReturnsRedirectIfSessionAttributeIsNull() {
		String returnedForm = projectController.createTask(SAMPLE_PROJECTNUMBER, model, taskViewModelMock, sessionMock);
		assertEquals(REDIRECT_LINK, returnedForm);
	}

	@Test
	public void saveTaskCallsTheCorrectServiceMethods() throws Exception {
		when(taskConverterMock.convert(taskViewModelMock)).thenReturn(taskMock);
		projectController.saveTask(SAMPLE_PROJECTNUMBER, model, taskViewModelMock, sessionMock);
		verify(projectServiceMock).addNewTaskToProject(taskViewModelMock, SAMPLE_PROJECTNUMBER);
	}

	@Test
	public void taskModificationUpdatesTheModelCorrectly() {
		List<String> authorizedUsers = new ArrayList<String>();
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(projectServiceMock.getTaskById(eq(SAMPLE_TASKNUMBER))).thenReturn(taskMock);
		when(taskConverterMock.convert(taskMock)).thenReturn(taskViewModelMock);
		when(taskMock.getAuthorizedUsers()).thenReturn(authorizedUsers);
		when(userServiceMock.retrieveUsersByEmail(authorizedUsers)).thenReturn(employeeList);
		when(userServiceMock.retrieveByEmail(sessionMock.getAttribute(EMAIL_KEY).toString())).thenReturn(currentUserMock);
		when(userConverterMock.convert(employeeList)).thenReturn(userViewModelCollection);

		projectController.editTask(SAMPLE_PROJECTNUMBER, SAMPLE_TASKNUMBER, model, sessionMock);

		assertSame(model.asMap().get("task"), taskViewModelMock);
		assertEquals(model.asMap().get("projectNumber"), SAMPLE_PROJECTNUMBER);
	}

	@Test
	public void taskModificationReturnsRedirectIfSessionAttributeIsNull() {
		String returnedForm = projectController.editTask(SAMPLE_PROJECTNUMBER, SAMPLE_TASKNUMBER, model, sessionMock);
		assertEquals(REDIRECT_LINK, returnedForm);
	}

	@Test
	public void editTaskCallsTheCorrectServiceMethods() throws Exception {
		projectController.saveEditedTask(SAMPLE_PROJECTNUMBER, SAMPLE_TASKNUMBER, model, taskViewModel, sessionMock);
		verify(projectServiceMock).updateTask(SAMPLE_PROJECTNUMBER, SAMPLE_TASKNUMBER, taskViewModel);
	}

}
