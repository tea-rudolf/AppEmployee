package ca.ulaval.glo4003.appemployee.web.controllers;

import static org.junit.Assert.*;
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
import ca.ulaval.glo4003.appemployee.domain.task.TaskExistsException;
import ca.ulaval.glo4003.appemployee.services.ProjectService;
import ca.ulaval.glo4003.appemployee.web.converters.ProjectConverter;
import ca.ulaval.glo4003.appemployee.web.converters.TaskConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.MessageViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ProjectViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TaskViewModel;

public class ProjectControllerTest {
	private static final String SAMPLE_PROJECTNUMBER = "1";
	private static final String SAMPLE_TASKNUMBER = "2";
	
	HttpSession sessionMock;
	Model model = new ExtendedModelMap();
	ProjectController projectController;
	ProjectService projectServiceMock;
	ProjectConverter projectConverterMock;
	ProjectViewModel projectViewModelMock;
	Project projectMock;
	List<Project> projectList = new ArrayList<Project>();
	Collection<ProjectViewModel> projectViewModelCollection = new ArrayList<ProjectViewModel>();
	Task taskMock;
	TaskConverter taskConverterMock;
	TaskViewModel taskViewModelMock;
	List<Task> taskList = new ArrayList<Task>();
	Collection<TaskViewModel> taskViewModelCollection = new ArrayList<TaskViewModel>();
	
	@Before
	public void init() {
		createMocks();
		instantiateObjects();
	}

	private void createMocks() {
		sessionMock = mock(HttpSession.class);
		projectMock = mock(Project.class);
		projectServiceMock = mock(ProjectService.class);
		projectConverterMock = mock(ProjectConverter.class);
		projectViewModelMock = mock(ProjectViewModel.class);
		taskMock = mock(Task.class);
		taskConverterMock = mock(TaskConverter.class);
		taskViewModelMock = mock(TaskViewModel.class);
	}

	private void instantiateObjects() {
		projectController = new ProjectController(projectServiceMock, projectConverterMock, taskConverterMock);
	}
	
	@Test
	public void getProjectsUpdatesTheModelCorrectly() {
		when(projectServiceMock.getAllProjects()).thenReturn(projectList);
		when(projectConverterMock.convert(projectList)).thenReturn(projectViewModelCollection);
		
		projectController.getProjects(model, sessionMock);
		
		assertSame(model.asMap().get("projects"), projectViewModelCollection);
	}
	
	@Test
	public void projectCreationUpdatesTheModelCorrectly() {
		projectController.projectCreation(model, projectViewModelMock, sessionMock);
		assertSame(model.asMap().get("project"), projectViewModelMock);
	}
	
	@Test
	public void addProjectCallsTheCorrectServiceMethods() {
		when(projectConverterMock.convert(projectViewModelMock)).thenReturn(projectMock);
		projectController.addProject(model, projectViewModelMock, sessionMock);
		verify(projectServiceMock).addProject(projectMock);
	}
	
	@Test
	public void addProjectReturnsAnErrorMessageOnProjectExistsException() {
		when(projectConverterMock.convert(projectViewModelMock)).thenReturn(projectMock);
		doThrow(new ProjectExistsException()).when(projectServiceMock).addProject(projectMock);
		
		projectController.addProject(model, projectViewModelMock, sessionMock);
		
		assertEquals(model.asMap().get("message").getClass(), MessageViewModel.class);
	}
	
	@Test
	public void projectMoficiationUpdatesTheModelCorrectly() {
		when(projectServiceMock.getProjectByNumber(eq(SAMPLE_PROJECTNUMBER))).thenReturn(projectMock);
		when(projectConverterMock.convert(projectMock)).thenReturn(projectViewModelMock);
		when(projectMock.getTasks()).thenReturn(taskList);
		when(taskConverterMock.convert(taskList)).thenReturn(taskViewModelCollection);
		
		projectController.projectModification(SAMPLE_PROJECTNUMBER, model, sessionMock);
		
		assertSame(model.asMap().get("project"), projectViewModelMock);
	}
	
	@Test
	public void editProjectCallsTheCorrectServiceMethods() {
		projectController.editProject(SAMPLE_PROJECTNUMBER, projectViewModelMock, sessionMock);
		verify(projectServiceMock).updateProject(SAMPLE_PROJECTNUMBER, projectViewModelMock);
	}
	
	@Test
	public void taskCreationUpdatesTheModelCorrectly() {
		projectController.taskCreation(SAMPLE_PROJECTNUMBER, model, taskViewModelMock, sessionMock);
		assertSame(model.asMap().get("task"), taskViewModelMock);
		assertEquals(model.asMap().get("projectNumber"), SAMPLE_PROJECTNUMBER);
	}
	
	@Test
	public void addTaskCallsTheCorrectServiceMethods() {
		when(taskConverterMock.convert(taskViewModelMock)).thenReturn(taskMock);
		projectController.addTask(SAMPLE_PROJECTNUMBER, model, taskViewModelMock, sessionMock);
		verify(projectServiceMock).addTask(SAMPLE_PROJECTNUMBER, taskMock);
	}
	
	@Test
	public void addTaskReturnsAnErrorMessageOnTaskExistsException() {
		when(taskConverterMock.convert(taskViewModelMock)).thenReturn(taskMock);
		doThrow(new TaskExistsException()).when(projectServiceMock).addTask(SAMPLE_PROJECTNUMBER, taskMock);
		
		projectController.addTask(SAMPLE_PROJECTNUMBER, model, taskViewModelMock, sessionMock);
		
		assertEquals(model.asMap().get("message").getClass(), MessageViewModel.class);
	}
	
	@Test
	public void taskMoficiationUpdatesTheModelCorrectly() {
		when(projectServiceMock.getTaskByNumber(eq(SAMPLE_PROJECTNUMBER), eq(SAMPLE_TASKNUMBER))).thenReturn(taskMock);
		when(taskConverterMock.convert(taskMock)).thenReturn(taskViewModelMock);
		
		projectController.taskModification(SAMPLE_PROJECTNUMBER, SAMPLE_TASKNUMBER, model, sessionMock);
		
		assertSame(model.asMap().get("task"), taskViewModelMock);
		assertEquals(model.asMap().get("projectNumber"), SAMPLE_PROJECTNUMBER);
	}
	
	@Test
	public void editTaskCallsTheCorrectServiceMethods() {
		projectController.editTask(SAMPLE_PROJECTNUMBER, SAMPLE_TASKNUMBER, taskViewModelMock, sessionMock);
		verify(projectServiceMock).updateTask(SAMPLE_PROJECTNUMBER, SAMPLE_TASKNUMBER, taskViewModelMock);
	}
}
