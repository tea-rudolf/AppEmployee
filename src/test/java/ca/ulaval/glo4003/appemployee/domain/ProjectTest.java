//package ca.ulaval.glo4003.appemployee.domain;
//
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.*;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import ca.ulaval.glo4003.appemployee.domain.project.Project;
//import ca.ulaval.glo4003.appemployee.domain.task.Task;
//import ca.ulaval.glo4003.appemployee.domain.task.TaskAlreadyExistsException;
//import ca.ulaval.glo4003.appemployee.domain.task.TaskNotFoundException;
//
//public class ProjectTest {
//	private static final String SAMPLE_PROJECTNUMBER = "1";
//	private static final String SAMPLE_PROJECTNAME = "SampleName";
//	private static final String SAMPLE_TASKNUMBER = "2";
//	private static final String UNEXISTING_TASKNUMBER = "3";
//
//	private Project project;
//	private Task taskMock;
//
//	@Before
//	public void init() {
//		project = new Project(SAMPLE_PROJECTNUMBER, SAMPLE_PROJECTNAME);
//		taskMock = mock(Task.class);
//		when(taskMock.getComment()).thenReturn(SAMPLE_TASKNUMBER);
//	}
//
//	@Test
//	public void addTaskAddsTaskCorrectlyToTheList() {
//		project.addTask(taskMock);
//		assertTrue(project.getTasks().contains(taskMock));
//	}
//
//	@Test(expected = TaskAlreadyExistsException.class)
//	public void addTaskThrowsTaskExistsExceptionWhenSpecifyingAnExistingTask() {
//		project.addTask(taskMock);
//		project.addTask(taskMock);
//	}
//
//	@Test
//	public void getTaskByNumberReturnsTheCorrectTask() {
//		project.addTask(taskMock);
//		assertSame(taskMock, project.getTaskByNumber(SAMPLE_TASKNUMBER));
//	}
//
//	@Test(expected = TaskNotFoundException.class)
//	public void getProjectByNumberThrowsTaskNotFoundExceptionWhenSpecifyingAnUnexistingTask() {
//		project.addTask(taskMock);
//		project.getTaskByNumber(UNEXISTING_TASKNUMBER);
//	}
//}
