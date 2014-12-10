package ca.ulaval.glo4003.appemployee.domain;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ca.ulaval.glo4003.appemployee.domain.exceptions.TaskExistsException;
import ca.ulaval.glo4003.appemployee.domain.project.Project;
import ca.ulaval.glo4003.appemployee.domain.project.ProjectProcessor;
import ca.ulaval.glo4003.appemployee.domain.repository.ProjectRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.TaskRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.UserRepository;
import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.task.TaskProcessor;
import ca.ulaval.glo4003.appemployee.domain.user.Role;
import ca.ulaval.glo4003.appemployee.domain.user.User;

public class ProjectProcessorTest {
	
	private static final String PROJECT_UID = "uid";
	private static final String USER_EMAIL = "email@email.com";
	private static final String PROJECT_NAME = "name";
	private static final String TASK_NAME = "name";
	private static final String TASK_UID = "1";
	private static final double MULTIPLICATIVE_FACTOR = 1.0;
	
	Collection<User> users = new ArrayList<User>();
	List<String> uids = new ArrayList<String>();
	List<Task> tasks = new ArrayList<Task>();
	Collection<Project> projects = new ArrayList<Project>();
	
	@Mock
	private User userMock;
	
	@Mock
	private Task taskMock;
	
	@Mock
	private Project projectMock;
	
	@Mock
	private ProjectRepository projectRepositoryMock;
	
	@Mock
	private UserRepository userRepositoryMock;
	
	@Mock
	private TaskRepository taskRepositoryMock;
	
	@Mock
	private TaskProcessor taskProcessorMock;
	
	@InjectMocks
	private ProjectProcessor projectProcessor;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		projectProcessor = new ProjectProcessor(projectRepositoryMock, userRepositoryMock, taskRepositoryMock, taskProcessorMock);
	}
	
	@Test
	public void canInstantiateProjectProcessor() {
		assertNotNull(projectProcessor);
	}
	
	@Test
	public void evaluateAvailableEmployeeEmailsByProjectReturnsListOfUserEmails() {
		users.add(userMock);
		when(userMock.getEmail()).thenReturn(USER_EMAIL);
		when(userMock.getRole()).thenReturn(Role.EMPLOYEE);
		when(projectRepositoryMock.findById(PROJECT_UID)).thenReturn(projectMock);
		when(userRepositoryMock.findAll()).thenReturn(users);
		when(projectMock.userIsAssignedToProject(USER_EMAIL)).thenReturn(false);
		List<String> userEmails = new ArrayList<String>();
		userEmails.add(USER_EMAIL);
		
		List<String> returnedListOfEmails = projectProcessor.evaluateAvailableEmployeeEmailsByProject(PROJECT_UID);
		
		assertEquals(userEmails, returnedListOfEmails);
	}
	
	@Test
	public void editProjectCallsCorrectRepositoryMethod() throws Exception {
		when(projectRepositoryMock.findById(PROJECT_UID)).thenReturn(projectMock);
		when(projectMock.getTaskUids()).thenReturn(uids);
		projectProcessor.editProject(PROJECT_UID, PROJECT_NAME, USER_EMAIL);
		verify(projectRepositoryMock, times(1)).store(projectMock);
	}
	
	@Test
	public void retrieveAllEmployeesByProjectIdReturnsListOfUsers() {
		when(projectRepositoryMock.findById(PROJECT_UID)).thenReturn(projectMock);
		uids.add(USER_EMAIL);
		when(userMock.getEmail()).thenReturn(USER_EMAIL);
		when(projectMock.getEmployeeUids()).thenReturn(uids);
		when(userRepositoryMock.findByEmail(USER_EMAIL)).thenReturn(userMock);
		List<User> users = new ArrayList<User>();
		users.add(userMock);
		
		List<User> returnedUsers = projectProcessor.retrieveAllEmployeesByProjectId(PROJECT_UID);
		
		assertEquals(users, returnedUsers);
	}
	
	@Test
	public void retrieveAllTasksByProjectIdReturnsListOfTasks() {
		when(projectRepositoryMock.findById(PROJECT_UID)).thenReturn(projectMock);
		when(taskMock.getUid()).thenReturn(TASK_UID);
		uids.add(TASK_UID);
		when(projectMock.getTaskUids()).thenReturn(uids);
		when(taskRepositoryMock.findByUid(TASK_UID)).thenReturn(taskMock);
		List<Task> tasks = new ArrayList<Task>();
		tasks.add(taskMock);
		
		List<Task> returnedTasks = projectProcessor.retrieveAllTasksByProjectId(PROJECT_UID);
		
		assertEquals(tasks, returnedTasks);
	}
	
	@Test
	public void addTaskToProjectCallsCorrectRepositoryMethod() throws Exception {
		ArgumentCaptor<Task> taskArgumentCaptor = ArgumentCaptor.forClass(Task.class);
		when(projectRepositoryMock.findById(PROJECT_UID)).thenReturn(projectMock);
		
		projectProcessor.addTaskToProject(PROJECT_UID, TASK_NAME, MULTIPLICATIVE_FACTOR);
		
		verify(taskRepositoryMock, times(1)).store(taskArgumentCaptor.capture());
		verify(projectRepositoryMock, times(1)).store(projectMock);
	}
	
	@Test
	public void checkIfTaskAlreadyExistsInProjectReturnsTrueIfTaskExistsInProject() {
		uids.add(TASK_UID);
		when(taskMock.getUid()).thenReturn(TASK_UID);
		when(taskMock.getName()).thenReturn(TASK_NAME);
		tasks.add(taskMock);
		when(projectMock.getTaskUids()).thenReturn(uids);
		when(taskRepositoryMock.findByUids(uids)).thenReturn(tasks);
		
		Boolean returnedValue = projectProcessor.checkIfTaskAlreadyExistsInProject(projectMock, TASK_NAME);
		
		assertTrue(returnedValue);
	}
	
	@Test
	public void checkIfTaskAlreadyExistsInProjectReturnsTrueIfTaskDoesNotExist() {
		uids.add(TASK_UID);
		when(taskMock.getUid()).thenReturn(TASK_UID);
		tasks.add(taskMock);
		when(taskRepositoryMock.findByUids(uids)).thenReturn(tasks);
		
		Boolean returnedValue = projectProcessor.checkIfTaskAlreadyExistsInProject(projectMock, TASK_NAME);
		
		assertFalse(returnedValue);
	}
	
	@Test
	public void createProjectCallsCorrectRepositoryMethod() throws Exception {
		ArgumentCaptor<Project> projectArgumentCaptor = ArgumentCaptor.forClass(Project.class);
		projectProcessor.createProject(PROJECT_NAME, uids, uids, uids);
		verify(projectRepositoryMock, times(1)).store(projectArgumentCaptor.capture());
	}
	
	@Test
	public void retrieveAllProjectsReturnsCollectionOfAllProjects() {
		projects.add(projectMock);
		when(projectRepositoryMock.findAll()).thenReturn(projects);
		
		Collection<Project> returnedProjects = projectProcessor.retrieveAllProjects();
		
		assertEquals(projects, returnedProjects);
	}
	
	@Test
	public void retrieveProjectByIdReturnsProjectWithCorrespondingId() {
		when(projectRepositoryMock.findById(PROJECT_UID)).thenReturn(projectMock);
		Project returnedProject = projectProcessor.retrieveProjectById(PROJECT_UID);
		assertEquals(projectMock, returnedProject);
	}

}
