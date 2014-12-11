package ca.ulaval.glo4003.appemployee.domain.task;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.appemployee.domain.exceptions.EmployeeAlreadyExistsException;

@RunWith(MockitoJUnitRunner.class)
public class TaskTest {
	private static final String DUMMY_USER_ID = "dummy@dummy.com";
	private static final String DUMMY_TASK_NAME = "DUMMY";
	private static final double DUMMY_MULTIPLICATIVE_FACTOR = 1.2;

	private Task task;

	@Before
	public void init() {
		task = new Task(UUID.randomUUID().toString());
	}

	@Test
	public void canInstantiateTask() {
		assertNotNull(task);
	}

	@Test
	public void whenAssigningUserToTaskUserShouldBeInAuthorizedUsersList() {
		task.assignUserToTask(DUMMY_USER_ID);
		assertTrue(task.getAuthorizedUsers().contains(DUMMY_USER_ID));
	}

	@Test(expected = EmployeeAlreadyExistsException.class)
	public void whenAssigningUserToTaskIfUserAlreadyExistShouldThrowEmployeeAlreadyExistsException() {
		task.assignUserToTask(DUMMY_USER_ID);
		task.assignUserToTask(DUMMY_USER_ID);

	}

	@Test
	public void updateTaskWithoutNewUserUpdatesTaskCorrectly() {
		task.update(DUMMY_TASK_NAME, DUMMY_MULTIPLICATIVE_FACTOR);
		assertEquals(task.getName(), DUMMY_TASK_NAME);
		assertTrue(DUMMY_MULTIPLICATIVE_FACTOR == task.getMultiplicativeFactor());
	}

	@Test
	public void updateTaskWithNewUserUpdatesTaskCorrectly() {
		task.update(DUMMY_TASK_NAME, DUMMY_USER_ID, DUMMY_MULTIPLICATIVE_FACTOR);
		assertEquals(task.getName(), DUMMY_TASK_NAME);
		assertTrue(task.userIsAlreadyAssignedToTask(DUMMY_USER_ID));
		assertTrue(DUMMY_MULTIPLICATIVE_FACTOR == task.getMultiplicativeFactor());
	}

}
