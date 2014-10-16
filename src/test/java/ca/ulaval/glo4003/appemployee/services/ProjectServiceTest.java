package ca.ulaval.glo4003.appemployee.services;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.appemployee.domain.project.Project;
import ca.ulaval.glo4003.appemployee.domain.project.ProjectExistsException;
import ca.ulaval.glo4003.appemployee.domain.project.ProjectNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.project.ProjectRepository;
import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ProjectViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TaskViewModel;

public class ProjectServiceTest {
	private static final String SAMPLE_PROJECTNUMBER = "1";
	private static final String SAMPLE_TASKNUMBER = "2";
	private static final String SAMPLE_TASKNAME = "SampleTask";

	private ProjectService projectService;
	private ProjectRepository projectRepositoryMock;
	private Project projectMock;
	private ProjectViewModel projectViewModelMock;
	private Task taskMock;
	private TaskViewModel taskViewModelMock;

	@Before
	public void init() {
		createMocks();
//		stubMethods();
//		instantiateObjects();
	}

	private void createMocks() {
		projectRepositoryMock = mock(ProjectRepository.class);
		projectMock = mock(Project.class);
		projectViewModelMock = mock(ProjectViewModel.class);
		taskMock = mock(Task.class);
		taskViewModelMock = mock(TaskViewModel.class);
	}

//	private void stubMethods() {
//		when(projectRepositoryMock.getByNumber(eq(SAMPLE_PROJECTNUMBER))).thenReturn(projectMock);
//		when(projectMock.getTaskByNumber(eq(SAMPLE_TASKNUMBER))).thenReturn(taskMock);
//	}
//
//	private void instantiateObjects() {
//		projectService = new ProjectService(projectRepositoryMock);
//	}
//
//	@Test
//	public void getProjectByNumberReturnsCorrectProject() {
//		assertSame(projectMock, projectService.getProjectByNumber(SAMPLE_PROJECTNUMBER));
//	}
//
//	@Test(expected = ProjectNotFoundException.class)
//	public void getProjectByNumberThrowsProjectNotFoundExceptionWhenSpecifyingAnUnexistingProject() {
//		when(projectRepositoryMock.getByNumber(eq(SAMPLE_PROJECTNUMBER))).thenThrow(new ProjectNotFoundException());
//		projectService.getProjectByNumber(SAMPLE_PROJECTNUMBER);
//	}
//
//	@Test
//	public void getProjectByNumberCallsCorrectRepositoryMethods() {
//		projectService.getProjectByNumber(SAMPLE_PROJECTNUMBER);
//		verify(projectRepositoryMock).getByNumber(eq(SAMPLE_PROJECTNUMBER));
//	}
//
//	@Test
//	public void getAllProjectsCallsCorrectRepositoryMethods() {
//		projectService.getAllProjects();
//		verify(projectRepositoryMock).findAll();
//	}
//
//	@Test(expected = ProjectExistsException.class)
//	public void addProjectThrowsProjectExistsExceptionWhenSpecifyingAnExistingProject() {
//		doThrow(new ProjectExistsException()).when(projectRepositoryMock).persist(eq(projectMock));
//		projectService.addProject(projectMock);
//	}
//
//	@Test
//	public void addProjectCallsCorrectRepositoryMethods() {
//		projectService.addProject(projectMock);
//		verify(projectRepositoryMock).persist(eq(projectMock));
//	}
//
//	@Test
//	public void updateProjectCallsCorrectRepositoryMethods() {
//		projectService.updateProject(SAMPLE_PROJECTNUMBER, projectViewModelMock);
//		verify(projectRepositoryMock).update(eq(projectMock));
//	}
//
//	@Test
//	public void addTaskCallsCorrectDomainMethods() {
//		projectService.addTask(SAMPLE_PROJECTNUMBER, taskMock);
//		verify(projectMock).addTask(eq(taskMock));
//	}
//
//	@Test
//	public void addTaskCallsCorrectRepositoryMethods() {
//		projectService.addTask(SAMPLE_PROJECTNUMBER, taskMock);
//		verify(projectRepositoryMock).update(eq(projectMock));
//	}
//
//	@Test
//	public void updateTaskCallsCorrectDomainMethods() {
//		when(taskViewModelMock.getName()).thenReturn(SAMPLE_TASKNAME);
//		projectService.updateTask(SAMPLE_PROJECTNUMBER, SAMPLE_TASKNUMBER, taskViewModelMock);
//		verify(taskMock).setName(eq(SAMPLE_TASKNAME));
//	}
//
//	@Test
//	public void updateTaskCallsCorrectRepositoryMethods() {
//		projectService.updateTask(SAMPLE_PROJECTNUMBER, SAMPLE_TASKNUMBER, taskViewModelMock);
//		verify(projectRepositoryMock).update(eq(projectMock));
//	}
//
//	@Test
//	public void getTaskByNumberReturnsTheCorrectTask() {
//		Task actualTask = projectService.getTaskByNumber(SAMPLE_PROJECTNUMBER, SAMPLE_TASKNUMBER);
//		assertSame(taskMock, actualTask);
//	}
}
