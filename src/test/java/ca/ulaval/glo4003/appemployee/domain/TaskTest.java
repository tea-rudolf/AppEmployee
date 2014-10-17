package ca.ulaval.glo4003.appemployee.domain;

import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.appemployee.domain.task.Task;

@RunWith(MockitoJUnitRunner.class)
public class TaskTest {

	private Task task;

	@Test
	public void canInstantiateTask() {
		task = new Task(UUID.randomUUID().toString());
		assertNotNull(task);
	}

}
