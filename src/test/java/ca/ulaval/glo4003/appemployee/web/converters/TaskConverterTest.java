package ca.ulaval.glo4003.appemployee.web.converters;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TaskViewModel;

public class TaskConverterTest {
	private final static String FIRST_NAME = "firstName";
	private final static String FIRST_ID = "123456";
	private final static String SECOND_NAME = "secondName";
	private final static String SECOND_ID = "789103";
	private final static String TASK_COMMENT = "comment";
	
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
		assertEquals(FIRST_ID, viewModels[0].getComment());

		assertEquals(SECOND_NAME, viewModels[1].getName());
		assertEquals(SECOND_ID, viewModels[1].getComment());
	}

	@Test
	public void convertTaskViewModelToTask() {
		when(taskViewModelMock.getuId()).thenReturn(FIRST_ID);
		when(taskViewModelMock.getName()).thenReturn(FIRST_NAME);
		when(taskViewModelMock.getComment()).thenReturn(TASK_COMMENT);
		
		taskMock = taskConverter.convert(taskViewModelMock);
		
		assertEquals(taskViewModelMock.getuId(), taskMock.getuId());
		assertEquals(taskViewModelMock.getName(), taskMock.getName());
		assertEquals(taskViewModelMock.getComment(), taskMock.getComment());
	}

	@Test
	public void convertTaskToTaskViewModel() {
		when(taskMock.getuId()).thenReturn(FIRST_ID);
		when(taskMock.getName()).thenReturn(FIRST_NAME);
		when(taskMock.getComment()).thenReturn(TASK_COMMENT);
		
		taskViewModelMock = taskConverter.convert(taskMock);
		
		assertEquals(taskMock.getuId(), taskViewModelMock.getuId());
		assertEquals(taskMock.getName(), taskViewModelMock.getName());
		assertEquals(taskMock.getComment(), taskViewModelMock.getComment());
	}

	private Task createTask(String number, String name) {
		Task task = mock(Task.class);
		given(task.getName()).willReturn(name);
		given(task.getComment()).willReturn(number);
		return task;
	}
}
