package ca.ulaval.glo4003.appemployee.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.appemployee.domain.project.Project;
import ca.ulaval.glo4003.appemployee.domain.project.ProjectRepository;
import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.task.TaskRepository;
import ca.ulaval.glo4003.appemployee.persistence.RepositoryException;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ProjectViewModel;

public class ProjectServiceTest {
	private static final String TASK_ID = "0001";
	private static final String PROJECT_ID = "0001";
	private static final String PROJECT_NAME = "name";
	private static final String NEW_NAME = "newName";

	private ProjectService projectService;
	private ProjectRepository projectRepositoryMock;
	private Project projectMock;
	private Project sampleProject;
	private ProjectViewModel projectViewModelMock;
	private Task taskMock;
	private TaskRepository taskRepositoryMock;

	@Before
	public void init() {
		projectRepositoryMock = mock(ProjectRepository.class);
		projectMock = mock(Project.class);
		projectViewModelMock = mock(ProjectViewModel.class);
		taskMock = mock(Task.class);
		taskRepositoryMock = mock(TaskRepository.class);
		sampleProject = new Project(PROJECT_ID, PROJECT_NAME);
		projectService = new ProjectService(projectRepositoryMock, taskRepositoryMock);
	}

	@Test
	public void getAllProjectsCallsCorrectMethodInRepository(){
		projectService.getAllProjects();
		verify(projectRepositoryMock, times(1)).findAll();
	}
	
	@Test
	public void addProjectCallsCorrectRepositoryMethod() throws Exception{
		projectService.addProject(projectMock);
		verify(projectRepositoryMock, times(1)).store(projectMock);
	}

	@Test(expected = RepositoryException.class)
	public void addProjectThrowsExceptionIfProjectWasNotAdded() throws Exception{
		doThrow(new RepositoryException()).when(projectRepositoryMock).store(projectMock);
		projectService.addProject(projectMock);
	}
	
	@Test
	public void updateProjectCallsCorrectRepositoryMethod() throws Exception{
		when(projectRepositoryMock.findById(PROJECT_ID)).thenReturn(projectMock);
		projectService.updateProject(PROJECT_ID, projectViewModelMock);
		verify(projectRepositoryMock, times(1)).store(projectMock);
	}
	
	@Test(expected = RepositoryException.class)
	public void updateProjectThrowsExceptionWhenProjectIsNotUpdated() throws Exception{
		when(projectRepositoryMock.findById(PROJECT_ID)).thenReturn(projectMock);
		doThrow(new RepositoryException()).when(projectRepositoryMock).store(projectMock);
		projectService.updateProject(PROJECT_ID, projectViewModelMock);
	}
	
	@Test
	public void updateProjectSetsProjectName() throws Exception{
		when(projectRepositoryMock.findById(PROJECT_ID)).thenReturn(sampleProject);
		when(projectViewModelMock.getName()).thenReturn(NEW_NAME);
		
		projectService.updateProject(PROJECT_ID, projectViewModelMock);

		assertEquals(projectViewModelMock.getName(), sampleProject.getName());
	}
	
	@Test(expected = RepositoryException.class)
	public void updateProjectThrowsExceptionIfProjectIsNotUpdated() throws Exception{
		when(projectRepositoryMock.findById(PROJECT_ID)).thenReturn(projectMock);
		when(projectViewModelMock.getName()).thenReturn(PROJECT_NAME);
		doThrow(new RepositoryException()).when(projectRepositoryMock).store(projectMock);
		projectService.updateProject(PROJECT_ID, projectViewModelMock);
	}
	
	@Test
	public void addTaskToProjectCorrecltyCallsRepositoryMethod() throws Exception{
		when(projectRepositoryMock.findById(PROJECT_ID)).thenReturn(projectMock);
		projectService.updateProject(PROJECT_ID, projectViewModelMock);
		verify(projectRepositoryMock, times(1)).store(projectMock);
	}
	
	@Test
	public void addTaskToProjectCorrectlyAddsATaskToTheProject(){
		when(projectRepositoryMock.findById(PROJECT_ID)).thenReturn(sampleProject);
		when(taskMock.getuId()).thenReturn(TASK_ID);
		when(taskRepositoryMock.findByUid(TASK_ID)).thenReturn(taskMock);
		projectService.addTaskToProject(PROJECT_ID, TASK_ID);
		assertTrue(sampleProject.getTaskuIds().contains(TASK_ID));
	}
	
	@Test
	public void addTaskCallsCorrectRepositoryMethod() throws Exception{
		projectService.addTask(taskMock);
		verify(taskRepositoryMock, times(1)).store(taskMock);
	}
	
	@Test(expected = RepositoryException.class)
	public void addTaskThrowsExceptionIftaskIsNotCorrectlyAdded() throws Exception{
		doThrow(new RepositoryException()).when(taskRepositoryMock).store(taskMock);
		projectService.addTask(taskMock);
	}
	
	@Test
	public void getTaskByIdCallsCorrectRepositoryMethod(){
		projectService.getTaskById(TASK_ID);
		verify(taskRepositoryMock, times(1)).findByUid(TASK_ID);
	}
	
	@Test
	public void getProjectByIdCallsCorrectRepositoryMethod(){
		projectService.getProjectById(PROJECT_ID);
		verify(projectRepositoryMock, times(1)).findById(PROJECT_ID);
	}
	
	@Test
	public void gettAllTasksByProjectIdReturnsListeOfTasks(){
		List<String> sampleList = new ArrayList<String>();
		sampleList.add(TASK_ID);
		when(projectRepositoryMock.findById(PROJECT_ID)).thenReturn(projectMock);
		when(projectMock.getTaskuIds()).thenReturn(sampleList);
		when(taskRepositoryMock.findByUid(TASK_ID)).thenReturn(taskMock);
		List<Task> sampleTaskList = projectService.getAllTasksByProjectId(PROJECT_ID);
		assertTrue(sampleTaskList.contains(taskMock));
	}
}
