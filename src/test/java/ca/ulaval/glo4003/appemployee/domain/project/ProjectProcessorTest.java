package ca.ulaval.glo4003.appemployee.domain.project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

import ca.ulaval.glo4003.appemployee.domain.exceptions.ProjectExistsException;
import ca.ulaval.glo4003.appemployee.domain.exceptions.TaskAlreadyAssignedToProjectException;
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

	private List<User> users = new ArrayList<User>();
	private List<String> uids = new ArrayList<String>();
	private List<Task> tasks = new ArrayList<Task>();
	private Collection<Project> projects = new ArrayList<Project>();

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
		projectProcessor = new ProjectProcessor(projectRepositoryMock, userRepositoryMock, taskRepositoryMock,
				taskProcessorMock);
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
	public void evaluateAvailableEmployeeEmailsByProjectDoNotReturnEmployeeWhenAlreadyAssignedToProject() {
		users.add(userMock);
		when(userMock.getEmail()).thenReturn(USER_EMAIL);
		when(userMock.getRole()).thenReturn(Role.EMPLOYEE);
		when(projectRepositoryMock.findById(PROJECT_UID)).thenReturn(projectMock);
		when(userRepositoryMock.findAll()).thenReturn(users);
		when(projectMock.userIsAssignedToProject(USER_EMAIL)).thenReturn(true);

		List<String> returnedListOfEmails = projectProcessor.evaluateAvailableEmployeeEmailsByProject(PROJECT_UID);

		assertEquals(0, returnedListOfEmails.size());
	}

	@Test
	public void evaluateAvailableEmployeeEmailsByProjectDoNotReturnEmployeeWhenEmployeeHasEnterpriseRole() {
		users.add(userMock);
		when(userMock.getEmail()).thenReturn(USER_EMAIL);
		when(userMock.getRole()).thenReturn(Role.ENTERPRISE);
		when(projectRepositoryMock.findById(PROJECT_UID)).thenReturn(projectMock);
		when(userRepositoryMock.findAll()).thenReturn(users);
		when(projectMock.userIsAssignedToProject(USER_EMAIL)).thenReturn(false);

		List<String> returnedListOfEmails = projectProcessor.evaluateAvailableEmployeeEmailsByProject(PROJECT_UID);

		assertEquals(0, returnedListOfEmails.size());
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
		setupRetrieveTasksByProjectIdForRetriveUsersByProjectTest();
		uids.add(USER_EMAIL);
		when(taskMock.getAuthorizedUsers()).thenReturn(uids);
		when(userRepositoryMock.findByEmail(USER_EMAIL)).thenReturn(userMock);
		List<User> users = new ArrayList<User>();
		users.add(userMock);

		List<User> returnedUsers = projectProcessor.retrieveAllEmployeesByProjectId(PROJECT_UID);

		assertEquals(users, returnedUsers);
	}

	@Test
	public void retrieveAllEmployeesByProjectIdDoNotReturnEmployeeWhenEmployeeNameIsEmpty() {
		when(projectRepositoryMock.findById(PROJECT_UID)).thenReturn(projectMock);
		uids.add("");
		when(projectMock.getEmployeeUids()).thenReturn(uids);
		when(userRepositoryMock.findByEmail(USER_EMAIL)).thenReturn(userMock);

		List<User> returnedUsers = projectProcessor.retrieveAllEmployeesByProjectId(PROJECT_UID);

		assertEquals(0, returnedUsers.size());
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

	@Test(expected = TaskAlreadyAssignedToProjectException.class)
	public void addTaskToProjectThrowsExceptionWhenTaskAlreadyExists() throws Exception {
		when(projectRepositoryMock.findById(PROJECT_UID)).thenReturn(projectMock);
		retrieveTaskMockInfo();

		projectProcessor.addTaskToProject(PROJECT_UID, TASK_NAME, MULTIPLICATIVE_FACTOR);
	}

	@Test
	public void createProjectCallsCorrectRepositoryMethod() throws Exception {
		ArgumentCaptor<Project> projectArgumentCaptor = ArgumentCaptor.forClass(Project.class);
		projectProcessor.createProject(PROJECT_NAME, uids, uids, uids);
		verify(projectRepositoryMock, times(1)).store(projectArgumentCaptor.capture());
	}

	@Test(expected = ProjectExistsException.class)
	public void createProjectThrowsExceptionWhenProjectAlreadyExists() throws Exception {
		projects.add(projectMock);
		when(projectRepositoryMock.findAll()).thenReturn(projects);
		when(projectMock.getName()).thenReturn(PROJECT_NAME);

		projectProcessor.createProject(PROJECT_NAME, uids, uids, uids);
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

	private void retrieveTaskMockInfo() {
		uids.add(TASK_UID);
		when(taskMock.getName()).thenReturn(TASK_NAME);
		tasks.add(taskMock);
		when(projectMock.getTaskUids()).thenReturn(uids);
		when(taskRepositoryMock.findByUids(uids)).thenReturn(tasks);
	}
	
	private void setupRetrieveTasksByProjectIdForRetriveUsersByProjectTest() {
		projectProcessor = new ProjectProcessor(projectRepositoryMock, userRepositoryMock, taskRepositoryMock,
				taskProcessorMock) {
			
			@Override
			public List<Task> retrieveAllTasksByProjectId(String PROJECT_UID) {
				tasks.add(taskMock);
				return tasks;
			}
		};
		
	}

}
