package ca.ulaval.glo4003.appemployee.domain;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.appemployee.domain.exceptions.EmployeeAlreadyExistsException;
import ca.ulaval.glo4003.appemployee.domain.exceptions.TaskAlreadyAssignedToProjectException;
import ca.ulaval.glo4003.appemployee.domain.project.Project;

@RunWith(MockitoJUnitRunner.class)
public class ProjectTest {
	private static final String TASK_ID = "0001";
	private static final String PROJECT_UID = TASK_ID;
	private static final String PROJECTNAME = "SampleName";
	private static final String USER_UID = "1234";

	private Project project;

	@Before
	public void setUp() {
		project = new Project(PROJECT_UID, PROJECTNAME);
	}

	@Test
	public void canInstantiateProject() {
		assertNotNull(project);
	}

	@Test
	public void addTaskIdAddsTaskCorrectlyToTheList() {
		project.addTaskUid(TASK_ID);

		assertTrue(project.getTaskUids().contains(TASK_ID));
	}

	@Test(expected = TaskAlreadyAssignedToProjectException.class)
	public void addTaskIdThrowsTaskAlreadyExistsExceptionWhenAddingAnExistingId() {
		project.addTaskUid(TASK_ID);
		project.addTaskUid(TASK_ID);
	}

//	@Test
//	public void whenUserIsAddedToProjectUserShouldBeInUsersList() {
//		project.addEmployeeToProject(USER_UID);
//
//		assertTrue(project.getEmployeeUids().contains(USER_UID));
//	}
//
//	@Test(expected = EmployeeAlreadyExistsException.class)
//	public void addEmployeeThrowsEmployeeAlreadyExistsExceptionWhenAddingAnExistingId() {
//		project.addEmployeeToProject(USER_UID);
//		project.addEmployeeToProject(USER_UID);
//	}
//
//	@Test
//	public void whenUserIsAlreadyAssignedToProjectReturnsTrue() {
//		project.addEmployeeToProject(USER_UID);
//
//		assertTrue(project.userIsAssignedToProject(USER_UID));
//
//	}
}
