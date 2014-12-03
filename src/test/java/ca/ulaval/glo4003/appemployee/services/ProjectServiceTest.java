//package ca.ulaval.glo4003.appemployee.services;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.doThrow;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import org.joda.time.LocalDate;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//
//import ca.ulaval.glo4003.appemployee.domain.project.Project;
//import ca.ulaval.glo4003.appemployee.domain.repository.ProjectRepository;
//import ca.ulaval.glo4003.appemployee.domain.repository.TaskRepository;
//import ca.ulaval.glo4003.appemployee.domain.repository.UserRepository;
//import ca.ulaval.glo4003.appemployee.domain.task.Task;
//import ca.ulaval.glo4003.appemployee.domain.user.User;
//import ca.ulaval.glo4003.appemployee.persistence.RepositoryException;
//import ca.ulaval.glo4003.appemployee.web.viewmodels.ProjectViewModel;
//import ca.ulaval.glo4003.appemployee.web.viewmodels.TaskViewModel;
//
//public class ProjectServiceTest {
//	private static final String TASK_ID = "0001";
//	private static final String PROJECT_ID = "0001";
//	private static final String TASK_NAME = "PaperFilling";
//	private static final String PROJECT_NAME = "ProjectShanna";
//	private static final String DUMMY_USER_ID = "emp@company.com";
//	private static final String EMPTY_USER_ID = "";
//	private static final List<String> PROJECT_USERS = Arrays.asList("bla@bla.com", "hello@hello.com", "dummy@dummy.com");
//
//	@Mock
//	private ProjectRepository projectRepositoryMock;
//	
//	@Mock
//	private Project projectMock;
//	
//	@Mock
//	private Task taskMock;
//	
//	@Mock
//	private TaskRepository taskRepositoryMock;
//	
//	@Mock
//	private UserRepository userRepositoryMock;
//	
//	@Mock
//	private TaskViewModel taskViewModelMock;
//	
//	@Mock
//	private User userMock;
//	
//	@InjectMocks
//	private ProjectViewModel projectViewModel;
//	
//	@InjectMocks
//	private ProjectService projectService;
//
//	@Before
//	public void init() {
//		MockitoAnnotations.initMocks(this);
//		projectViewModel = new ProjectViewModel();
//		projectViewModel.setName(PROJECT_NAME);
//		projectService = Mockito.spy(new ProjectService(projectRepositoryMock, taskRepositoryMock, userRepositoryMock));
//	}
//
//	@Test
//	public void getTaskNameReturnTaskName() {
//
//		when(taskRepositoryMock.findByUid(TASK_ID)).thenReturn(taskMock);
//		when(taskMock.getName()).thenReturn(TASK_NAME);
//
//		assertEquals(projectService.getTaskName(TASK_ID), TASK_NAME);
//	}
//
//	@Test
//	public void getAllProjectsCallsCorrectMethodInRepository() {
//		projectService.getAllProjects();
//		verify(projectRepositoryMock, times(1)).findAll();
//	}
//
//	@Test
//	public void addProjectCallsCorrectRepositoryMethod() throws Exception {
//		projectService.addProject(projectMock);
//		verify(projectRepositoryMock, times(1)).store(projectMock);
//	}
//
//	@Test(expected = RepositoryException.class)
//	public void addProjectThrowsExceptionIfProjectWasNotAdded() throws Exception {
//		doThrow(new RepositoryException()).when(projectRepositoryMock).store(projectMock);
//		projectService.addProject(projectMock);
//	}
//
//	@Test
//	public void updateProjectCallsCorrectRepositoryMethod() throws Exception {
//		when(projectRepositoryMock.findById(PROJECT_ID)).thenReturn(projectMock);
//		projectService.updateProject(PROJECT_ID, projectViewModel);
//		verify(projectRepositoryMock, times(1)).store(projectMock);
//	}
//
//	@Test
//	public void updateProjectWhenUserEmailEmptyShouldNotCallAddEmployeeToProject() throws Exception {
//		when(projectRepositoryMock.findById(PROJECT_ID)).thenReturn(projectMock);
//		projectViewModel.setUserEmail(EMPTY_USER_ID);
//		projectService.updateProject(PROJECT_ID, projectViewModel);
//		verify(projectMock, times(0)).addEmployeeToProject(EMPTY_USER_ID);
//	}
//
//	@Test
//	public void updateProjectWhenUserDoesNotExistShouldNotCallAddEmployeeToProject() throws Exception {
//		when(projectRepositoryMock.findById(PROJECT_ID)).thenReturn(projectMock);
//		when(userRepositoryMock.findByEmail(DUMMY_USER_ID)).thenReturn(null);
//		verify(projectMock, times(0)).addEmployeeToProject(DUMMY_USER_ID);
//	}
//
//	@Test(expected = RepositoryException.class)
//	public void updateProjectThrowsExceptionIfProjectIsNotUpdated() throws Exception {
//		when(projectRepositoryMock.findById(PROJECT_ID)).thenReturn(projectMock);
//
//		doThrow(new RepositoryException()).when(projectRepositoryMock).store(projectMock);
//
//		projectService.updateProject(PROJECT_ID, projectViewModel);
//	}
//
//	@Test
//	public void addTaskToProjectCorrecltyCallsRepositoryMethod() throws Exception {
//		when(projectRepositoryMock.findById(PROJECT_ID)).thenReturn(projectMock);
//		projectService.updateProject(PROJECT_ID, projectViewModel);
//		verify(projectRepositoryMock, times(1)).store(projectMock);
//	}
//
//	@Test(expected = RepositoryException.class)
//	public void addTaskToProjectThrowsExceptionWhenUnableToStore() throws Exception {
//		when(projectRepositoryMock.findById(PROJECT_ID)).thenReturn(projectMock);
//		doThrow(new RepositoryException()).when(projectRepositoryMock).store(projectMock);
//		projectService.saveTaskToProject(PROJECT_ID, TASK_ID);
//	}
//
//	@Test
//	public void addTaskCallsCorrectRepositoryMethod() throws Exception {
//		projectService.saveTask(taskMock);
//		verify(taskRepositoryMock, times(1)).store(taskMock);
//	}
//
//	@Test(expected = RepositoryException.class)
//	public void addTaskThrowsExceptionIftaskIsNotCorrectlyAdded() throws Exception {
//		doThrow(new RepositoryException()).when(taskRepositoryMock).store(taskMock);
//		projectService.saveTask(taskMock);
//	}
//
//	@Test(expected = RepositoryException.class)
//	public void updateTaskThrowsRepositoryExceptionIfUnableToStore() throws Exception {
//		when(taskRepositoryMock.findByUid(TASK_ID)).thenReturn(taskMock);
//		when(taskViewModelMock.getName()).thenReturn(TASK_NAME);
//		when(taskViewModelMock.getUserEmail()).thenReturn(DUMMY_USER_ID);
//		doThrow(new RepositoryException()).when(taskRepositoryMock).store(taskMock);
//		projectService.updateTask(PROJECT_ID, TASK_ID, taskViewModelMock);
//	}
//
//	@Test
//	public void updateTaskAssignsUserEmailToTask() {
//		when(taskRepositoryMock.findByUid(TASK_ID)).thenReturn(taskMock);
//		when(projectRepositoryMock.findById(PROJECT_ID)).thenReturn(projectMock);
//		when(taskViewModelMock.getName()).thenReturn(TASK_NAME);
//		when(taskViewModelMock.getUserEmail()).thenReturn(DUMMY_USER_ID);
//		when(userRepositoryMock.findByEmail(DUMMY_USER_ID)).thenReturn(userMock);
//		when(userMock.getEmail()).thenReturn(DUMMY_USER_ID);
//
//		projectService.updateTask(PROJECT_ID, TASK_ID, taskViewModelMock);
//
//		verify(projectService, times(1)).assignUserToTask(DUMMY_USER_ID, PROJECT_ID, PROJECT_ID);
//	}
//
//	@Test
//	public void getTaskByIdCallsCorrectRepositoryMethod() {
//		projectService.getTaskById(TASK_ID);
//		verify(taskRepositoryMock, times(1)).findByUid(TASK_ID);
//	}
//
//	@Test
//	public void getProjectByIdCallsCorrectRepositoryMethod() {
//		projectService.getProjectById(PROJECT_ID);
//		verify(projectRepositoryMock, times(1)).findById(PROJECT_ID);
//	}
//
//	@Test
//	public void getAllTasksByProjectIdReturnsListOfTasks() {
//		List<String> sampleList = new ArrayList<String>();
//		sampleList.add(TASK_ID);
//		when(projectRepositoryMock.findById(PROJECT_ID)).thenReturn(projectMock);
//		when(projectMock.getTaskUids()).thenReturn(sampleList);
//		when(taskRepositoryMock.findByUid(TASK_ID)).thenReturn(taskMock);
//
//		List<Task> sampleTaskList = projectService.getAllTasksByProjectId(PROJECT_ID);
//
//		assertTrue(sampleTaskList.contains(taskMock));
//	}
//
//	@Test
//	public void getAllEmployeesByProjectIdReturnsListOfUsers() {
//		List<String> sampleUsersList = new ArrayList<String>();
//		sampleUsersList.add(DUMMY_USER_ID);
//		when(projectRepositoryMock.findById(PROJECT_ID)).thenReturn(projectMock);
//		when(projectMock.getEmployeeUids()).thenReturn(sampleUsersList);
//		when(userRepositoryMock.findByEmail(DUMMY_USER_ID)).thenReturn(userMock);
//
//		List<User> returnedUsersList = projectService.getAllEmployeesByProjectId(PROJECT_ID);
//
//		assertTrue(returnedUsersList.contains(userMock));
//	}
//
//	@Test
//	public void getAllTasksByUserIdCallsCorrectRepositoryMethod() {
//		projectService.getAllTasksByCurrentUserId(DUMMY_USER_ID);
//		verify(projectRepositoryMock, times(1)).findAll();
//	}
//
//	@Test
//	public void givenUnassignedUserWhenAssigningUserToTaskThenAddsUserToProjectAndTask() {
//		when(projectRepositoryMock.findById(PROJECT_ID)).thenReturn(projectMock);
//		when(taskRepositoryMock.findByUid(TASK_ID)).thenReturn(taskMock);
//		when(projectMock.userIsAssignedToProject(DUMMY_USER_ID)).thenReturn(false);
//
//		projectService.assignUserToTask(DUMMY_USER_ID, PROJECT_ID, PROJECT_ID);
//
//		verify(projectMock, times(1)).addEmployeeToProject(DUMMY_USER_ID);
//		verify(taskMock, times(1)).assignUserToTask(DUMMY_USER_ID);
//	}
//
//	@Test
//	public void givenAssignedUserWhenAssigningToTaskThenAddsUserToTask() {
//		when(projectRepositoryMock.findById(PROJECT_ID)).thenReturn(projectMock);
//		when(taskRepositoryMock.findByUid(TASK_ID)).thenReturn(taskMock);
//		when(projectMock.userIsAssignedToProject(DUMMY_USER_ID)).thenReturn(true);
//
//		projectService.assignUserToTask(DUMMY_USER_ID, PROJECT_ID, PROJECT_ID);
//		verify(taskMock, times(1)).assignUserToTask(DUMMY_USER_ID);
//	}
//
//	@Test
//	public void givenProjectWithUsersWhenAddingTaskToProjectTaskWillHaveSameUsersAsProject() throws Exception {
//		given(projectRepositoryMock.findById(PROJECT_ID)).willReturn(projectMock);
//		given(projectMock.getEmployeeUids()).willReturn(PROJECT_USERS);
//		ArgumentCaptor<Task> taskArgumentCaptor = ArgumentCaptor.forClass(Task.class);
//
//		projectService.addNewTaskToProject(taskViewModelMock, PROJECT_ID);
//		verify(taskRepositoryMock, times(1)).store(taskArgumentCaptor.capture());
//		Task createdTask = taskArgumentCaptor.getValue();
//		assertEquals(PROJECT_USERS, createdTask.getAuthorizedUsers());
//	}
// }
