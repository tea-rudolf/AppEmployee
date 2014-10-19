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
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import ca.ulaval.glo4003.appemployee.domain.project.Project;
import ca.ulaval.glo4003.appemployee.domain.project.ProjectExistsException;
import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.task.TaskAlreadyExistsException;
import ca.ulaval.glo4003.appemployee.services.ProjectService;
import ca.ulaval.glo4003.appemployee.web.converters.ProjectConverter;
import ca.ulaval.glo4003.appemployee.web.converters.TaskConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.MessageViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ProjectViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TaskViewModel;

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
	private Project projectMock;
	private List<Project> projectList = new ArrayList<Project>();
	private Collection<ProjectViewModel> projectViewModelCollection = new ArrayList<ProjectViewModel>();
	private Task taskMock;
	private TaskConverter taskConverterMock;
	private TaskViewModel taskViewModelMock;
	private List<Task> taskList = new ArrayList<Task>();
	private Collection<TaskViewModel> taskViewModelCollection = new ArrayList<TaskViewModel>();

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
		projectController = new ProjectController(projectServiceMock, projectConverterMock, taskConverterMock);
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
		projectController.projectCreation(model, projectViewModelMock, sessionMock);
		assertSame(model.asMap().get("project"), projectViewModelMock);
	}

	@Test
	public void projectCreationReturnsRedirectIfSessionAttributeIsNull() {
		String returnedForm = projectController.projectCreation(model, projectViewModelMock, sessionMock);
		assertEquals(REDIRECT_LINK, returnedForm);
	}

	@Test
	public void addProjectCallsTheCorrectServiceMethods() throws Exception {
		when(projectConverterMock.convert(projectViewModelMock)).thenReturn(projectMock);
		projectController.addProject(model, projectViewModelMock, sessionMock);
		verify(projectServiceMock).addProject(projectMock);
	}

	@Test
	public void addProjectReturnsAnErrorMessageOnProjectExistsException() throws Exception {
		when(projectConverterMock.convert(projectViewModelMock)).thenReturn(projectMock);
		doThrow(new ProjectExistsException()).when(projectServiceMock).addProject(projectMock);

		projectController.addProject(model, projectViewModelMock, sessionMock);

		assertEquals(model.asMap().get("message").getClass(), MessageViewModel.class);
	}

	@Test
	public void projectModificationUpdatesTheModelCorrectly() {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(projectServiceMock.getProjectById(eq(SAMPLE_PROJECTNUMBER))).thenReturn(projectMock);
		when(projectConverterMock.convert(projectMock)).thenReturn(projectViewModelMock);
		when(projectServiceMock.getAllTasksByProjectId(SAMPLE_PROJECTNUMBER)).thenReturn(taskList);
		when(taskConverterMock.convert(taskList)).thenReturn(taskViewModelCollection);

		projectController.projectModification(SAMPLE_PROJECTNUMBER, model, sessionMock);

		assertSame(model.asMap().get("project"), projectViewModelMock);
	}

	@Test
	public void projectModificationReturnsRedirectIfSessionAttributeIsNull() {
		String returnedForm = projectController.projectModification(SAMPLE_PROJECTNUMBER, model, sessionMock);
		assertEquals(REDIRECT_LINK, returnedForm);
	}

	@Test
	public void editProjectCallsTheCorrectServiceMethods() throws Exception {
		projectController.editProject(SAMPLE_PROJECTNUMBER, projectViewModelMock, sessionMock);
		verify(projectServiceMock).updateProject(SAMPLE_PROJECTNUMBER, projectViewModelMock);
	}

	@Test
	public void taskCreationUpdatesTheModelCorrectly() {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		projectController.taskCreation(SAMPLE_PROJECTNUMBER, model, taskViewModelMock, sessionMock);
		assertSame(model.asMap().get("task"), taskViewModelMock);
		assertEquals(model.asMap().get("projectNumber"), SAMPLE_PROJECTNUMBER);
	}

	@Test
	public void taskCreationReturnsRedirectIfSessionAttributeIsNull() {
		String returnedForm = projectController.taskCreation(SAMPLE_PROJECTNUMBER, model, taskViewModelMock, sessionMock);
		assertEquals(REDIRECT_LINK, returnedForm);
	}

	@Test
	public void addTaskCallsTheCorrectServiceMethods() throws Exception {
		when(taskConverterMock.convert(taskViewModelMock)).thenReturn(taskMock);
		projectController.addTask(SAMPLE_PROJECTNUMBER, model, taskViewModelMock, sessionMock);
		verify(projectServiceMock).addTask(taskMock);
	}

	@Test
	public void addTaskReturnsAnErrorMessageOnTaskExistsException() throws Exception {
		when(taskConverterMock.convert(taskViewModelMock)).thenReturn(taskMock);
		doThrow(new TaskAlreadyExistsException()).when(projectServiceMock).addTask(taskMock);

		projectController.addTask(SAMPLE_PROJECTNUMBER, model, taskViewModelMock, sessionMock);

		assertEquals(model.asMap().get("message").getClass(), MessageViewModel.class);
	}

	@Test
	public void taskModificationUpdatesTheModelCorrectly() {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(projectServiceMock.getTaskById(eq(SAMPLE_TASKNUMBER))).thenReturn(taskMock);
		when(taskConverterMock.convert(taskMock)).thenReturn(taskViewModelMock);

		projectController.taskModification(SAMPLE_PROJECTNUMBER, SAMPLE_TASKNUMBER, model, sessionMock);

		assertSame(model.asMap().get("task"), taskViewModelMock);
		assertEquals(model.asMap().get("projectNumber"), SAMPLE_PROJECTNUMBER);
	}

	@Test
	public void taskModificationReturnsRedirectIfSessionAttributeIsNull() {
		String returnedForm = projectController.taskModification(SAMPLE_PROJECTNUMBER, SAMPLE_TASKNUMBER, model, sessionMock);
		assertEquals(REDIRECT_LINK, returnedForm);
	}

	@Test
	public void editTaskCallsTheCorrectServiceMethods() throws Exception {
		projectController.editTask(SAMPLE_PROJECTNUMBER, SAMPLE_TASKNUMBER, taskViewModelMock, sessionMock);
		verify(projectServiceMock).updateTask(SAMPLE_PROJECTNUMBER, SAMPLE_TASKNUMBER, taskViewModelMock);
	}
}
