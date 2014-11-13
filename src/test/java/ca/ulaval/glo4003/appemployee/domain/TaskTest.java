package ca.ulaval.glo4003.appemployee.domain;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.appemployee.domain.exceptions.EmployeeAlreadyExistsException;
import ca.ulaval.glo4003.appemployee.domain.task.Task;

@RunWith(MockitoJUnitRunner.class)
public class TaskTest {

	private Task task;

	@Test
	public void canInstantiateTask() {
		task = new Task(UUID.randomUUID().toString());
		assertNotNull(task);
	}

	@Test
	public void whenAssigningUserToTaskUserShouldBeInAuthorizedUsersList() {
		task = new Task(UUID.randomUUID().toString());
		String dummyUserId = "1234";

		task.assignUserToTask(dummyUserId);

		assertTrue(task.getAuthorizedUsers().contains(dummyUserId));
	}

	@Test(expected = EmployeeAlreadyExistsException.class)
	public void whenAssigningUserToTaskIfUserAlreadyExistShouldThrowEmployeeAlreadyExistsException() {
		task = new Task(UUID.randomUUID().toString());
		String dummyUserId = "1234";

		task.assignUserToTask(dummyUserId);
		task.assignUserToTask(dummyUserId);

	}

}
