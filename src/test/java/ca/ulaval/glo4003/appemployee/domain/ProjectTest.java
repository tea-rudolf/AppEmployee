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
		project.addTaskId(TASK_ID);
		assertTrue(project.getTaskIds().contains(TASK_ID));
	}

	@Test(expected = TaskAlreadyExistsException.class)
	public void addTaskIdThrowsTaskAlreadyExistsExceptionWhenAddingAnExistingId() {
		project.addTaskId(TASK_ID);
		project.addTaskId(TASK_ID);
	}

}
