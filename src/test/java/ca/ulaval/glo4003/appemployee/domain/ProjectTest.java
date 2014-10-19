package ca.ulaval.glo4003.appemployee.domain;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.appemployee.domain.project.Project;
import ca.ulaval.glo4003.appemployee.domain.task.TaskAlreadyExistsException;

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
	public void canInstatiateProject() {
		assertNotNull(project);
	}

	@Test
	public void addTaskIdAddsTaskCorrectlyToTheList() {
		project.addTaskuId(TASK_ID);
		assertTrue(project.getTaskuIds().contains(TASK_ID));
	}

	@Test(expected = TaskAlreadyExistsException.class)
	public void addTaskIdThrowsTaskAlreadyExistsExceptionWhenAddingAnExistingId() {
		project.addTaskuId(TASK_ID);
		project.addTaskuId(TASK_ID);
	}

	@Test
	public void whenUserIsAddedToProjectUserShouldBeInUsersList() {
		project.addUserToProject(USER_UID);

		assertTrue(project.getUseruIds().contains(USER_UID));
	}

	@Test
	public void whenUserIsAlreadyAssignedToProjectReturnsTrue() {
		project.addUserToProject(USER_UID);

		assertTrue(project.userIsAlreadyAssigned(USER_UID));

	}
}
