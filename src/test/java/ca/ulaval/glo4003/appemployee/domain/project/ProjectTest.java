package ca.ulaval.glo4003.appemployee.domain.project;

import static org.junit.Assert.assertEquals;
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
	private static final String PROJECT_NAME = "SampleName";
	private static final String NEW_PROJECT_NAME = "SecondSampleName";
	private static final String NEW_EMPLOYEE_UID = "dummy@dummy.com";

	private Project project;

	@Before
	public void setUp() {
		project = new Project(PROJECT_UID, PROJECT_NAME);
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
	
	@Test
	public void updateProjectUpdatesAllProjectInformation() {
		project.update(NEW_PROJECT_NAME, NEW_EMPLOYEE_UID);
		assertEquals(project.getName(), NEW_PROJECT_NAME);
		assertTrue(project.userIsAssignedToProject(NEW_EMPLOYEE_UID));
	}

	@Test(expected = EmployeeAlreadyExistsException.class)
	public void addEmployeeThrowsEmployeeAlreadyExistsExceptionWhenAddingAnExistingId() {
		project.update(NEW_PROJECT_NAME, NEW_EMPLOYEE_UID);
		project.update(NEW_PROJECT_NAME, NEW_EMPLOYEE_UID);
	}
}
