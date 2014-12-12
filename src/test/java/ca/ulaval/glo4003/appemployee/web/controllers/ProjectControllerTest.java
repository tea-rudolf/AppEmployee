package ca.ulaval.glo4003.appemployee.web.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
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

import ca.ulaval.glo4003.appemployee.domain.exceptions.ProjectExistsException;
import ca.ulaval.glo4003.appemployee.domain.exceptions.TaskAlreadyAssignedToProjectException;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.services.ProjectService;
import ca.ulaval.glo4003.appemployee.services.UserService;
import ca.ulaval.glo4003.appemployee.web.viewmodels.MessageViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ProjectViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TaskViewModel;

public class ProjectControllerTest {
	private static final String SAMPLE_PROJECT_NUMBER = "1";
	private static final String EMAIL_KEY = "email";
	private static final String VALID_EMAIL = "employee@employee.com";
	private static final String PROJECTS_LIST_FORM = "projectList";
	private static final String CREATE_PROJECT_FORM = "createProject";
	private static final String REDIRECT_PROJECT = "redirect:/projects";
	private static final String EDIT_PROJECT_FORM = "editProject";
	private static final String EDITED_PROJECT_REDIRECT = "redirect:/projects/1/edit";
	private static final String CREATE_TASK_FORM = "createTask";
	
	@Mock
	private HttpSession sessionMock;

	@Mock
	private Model modelMock;

	@Mock
	private ProjectService projectServiceMock;

	@Mock
	private ProjectViewModel projectViewModelMock;

	@Mock
	private TaskViewModel taskViewModelMock;

	@Mock
	private UserService userServiceMock;

	@Mock
	private User currentUserMock;

	@InjectMocks
	private ProjectController projectController;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		projectController = new ProjectController(projectServiceMock, userServiceMock);
	}

	@Test
	public void showProjectsReturnProjectsListWhenSuccessful() {
		String returnedForm = projectController.showProjects(modelMock, sessionMock);
		assertEquals(returnedForm, PROJECTS_LIST_FORM);
	}
	
	@Test
	public void showProjectsCallCorrectServiceMethod() {
		projectController.showProjects(modelMock, sessionMock);
		verify(projectServiceMock, times(1)).retrieveAllProjects();
	}
	
	@Test
	public void showCreateProjectFormReturnsCreateProjectFormIfSuccessful() {
		String returnedForm = projectController.showCreateProjectForm(modelMock, projectViewModelMock, sessionMock);
		assertEquals(returnedForm, CREATE_PROJECT_FORM);
	}
	
	@Test
	public void createProjectsRedirectsToProjectsPageIfServiceMethodIsCalled() throws Exception {
		String returnedForm = projectController.createProject(modelMock, projectViewModelMock, sessionMock);
		assertEquals(returnedForm, REDIRECT_PROJECT);
	}
	
	@Test
	public void createProjectsReturnCreateProjectFormIfServiceMethodThrowsException() throws Exception {
		doThrow(new ProjectExistsException("")).when(projectServiceMock).createProject(projectViewModelMock);
		String returnedForm = projectController.createProject(modelMock, projectViewModelMock, sessionMock);
		assertEquals(returnedForm, CREATE_PROJECT_FORM);
	}
	
	@Test
	public void showEditProjectFormReturnsEditProjectFormIfValidSessionAttribute() {
		when(sessionMock.getAttribute(EMAIL_KEY)).thenReturn(VALID_EMAIL);
		when(userServiceMock.retrieveUserByEmail(VALID_EMAIL)).thenReturn(currentUserMock);
		String returnedForm = projectController.showEditProjectForm(SAMPLE_PROJECT_NUMBER, modelMock, sessionMock);
		assertEquals(returnedForm, EDIT_PROJECT_FORM);
	}
	
	@Test
	public void editProjectReturnsEditProjectRedirectionPageIfServiceMethodIsCalledCorrectly() throws Exception {
		String returnedForm = projectController.editProject(SAMPLE_PROJECT_NUMBER, modelMock, projectViewModelMock, sessionMock);
		assertEquals(returnedForm, EDITED_PROJECT_REDIRECT);
	}
	
	@Test
	public void editProjectReturnsAlertIfSomethingWentWrongOnUpdate() throws Exception {
		doThrow(new Exception()).when(projectServiceMock).editProject(SAMPLE_PROJECT_NUMBER, projectViewModelMock);
		projectController.editProject(SAMPLE_PROJECT_NUMBER, modelMock, projectViewModelMock, sessionMock);
		verify(modelMock, times(1)).addAttribute(org.mockito.Matchers.eq("message"), org.mockito.Matchers.any(MessageViewModel.class));
	}
	
	@Test
	public void showCreateTaskFormReturnstaskFormWehnSuccessful() {
		String returnedForm = projectController.showCreateTaskForm(SAMPLE_PROJECT_NUMBER, modelMock, taskViewModelMock, sessionMock);
		assertEquals(returnedForm, CREATE_TASK_FORM);
	}
	
	@Test
	public void createTaskRedirectsToEditProjectPageIfServiceMethodIsCorrectlyCalled() throws Exception {
		String returnedForm = projectController.createTask(SAMPLE_PROJECT_NUMBER, modelMock, taskViewModelMock, sessionMock);
		assertEquals(returnedForm, EDITED_PROJECT_REDIRECT);
	}
	
	@Test
	public void createTaskReturnsCreateTaskFormIfServiceMethodFails() throws Exception {
		doThrow(new TaskAlreadyAssignedToProjectException("")).when(projectServiceMock).addNewTaskToProject(
				SAMPLE_PROJECT_NUMBER, taskViewModelMock);
		String returnedForm = projectController.createTask(SAMPLE_PROJECT_NUMBER, modelMock, taskViewModelMock,
				sessionMock);
		assertEquals(returnedForm, CREATE_TASK_FORM);
	}

}