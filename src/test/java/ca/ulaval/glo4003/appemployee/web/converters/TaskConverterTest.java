package ca.ulaval.glo4003.appemployee.web.converters;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TaskViewModel;

public class TaskConverterTest {
	private static final String FIRST_NAME = "firstName";
	private static final String FIRST_ID = "123456";
	private static final String SECOND_NAME = "secondName";
	private static final String SECOND_ID = "789103";

	private TaskConverter taskConverter;
	private TaskViewModel taskViewModelMock;
	private Task taskMock;

	@Before
	public void setUp() {
		taskViewModelMock = mock(TaskViewModel.class);
		taskMock = mock(Task.class);
		taskConverter = new TaskConverter();
	}

	@Test
	public void convertTasksListToViewModelsConvertsAllOfThem() {
		Task firstTask = createTask(FIRST_ID, FIRST_NAME);
		Task secondTask = createTask(SECOND_ID, SECOND_NAME);
		List<Task> tasks = new ArrayList<Task>();
		tasks.add(firstTask);
		tasks.add(secondTask);

		TaskViewModel[] viewModels = taskConverter.convert(tasks).toArray(new TaskViewModel[1]);

		assertEquals(FIRST_NAME, viewModels[0].getName());
		assertEquals(FIRST_ID, viewModels[0].getuId());

		assertEquals(SECOND_NAME, viewModels[1].getName());
		assertEquals(SECOND_ID, viewModels[1].getuId());
	}

	@Test
	public void convertTaskViewModelToTask() {
		when(taskViewModelMock.getuId()).thenReturn(FIRST_ID);
		when(taskViewModelMock.getName()).thenReturn(FIRST_NAME);

		taskMock = taskConverter.convert(taskViewModelMock);

		assertEquals(taskViewModelMock.getuId(), taskMock.getuId());
		assertEquals(taskViewModelMock.getName(), taskMock.getName());
	}

	@Test
	public void convertTaskToTaskViewModel() {
		when(taskMock.getuId()).thenReturn(FIRST_ID);
		when(taskMock.getName()).thenReturn(FIRST_NAME);

		taskViewModelMock = taskConverter.convert(taskMock);

		assertEquals(taskMock.getuId(), taskViewModelMock.getuId());
		assertEquals(taskMock.getName(), taskViewModelMock.getName());
	}

	private Task createTask(String number, String name) {
		Task task = mock(Task.class);
		given(task.getName()).willReturn(name);
		given(task.getuId()).willReturn(number);
		return task;
	}
}
