package ca.ulaval.glo4003.appemployee.web.converters;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TaskViewModel;

public class TaskConverterTest {
	private static final String FIRST_NAME = "firstName";
	private static final String FIRST_ID = "123456";
	private static final String SECOND_NAME = "secondName";
	private static final String SECOND_ID = "789103";
	private ArrayList<String> authorizedUsers;

	@Mock
	private TaskViewModel taskViewModelMock;
	
	@Mock
	private Task taskMock;
	
	@InjectMocks
	private TaskConverter taskConverter;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		taskConverter = new TaskConverter();
		authorizedUsers = new ArrayList<String>(Arrays.asList("FIRST_ID", "SECOND_ID"));
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
		assertEquals(authorizedUsers, viewModels[0].getAuthorizedUsers());

		assertEquals(SECOND_NAME, viewModels[1].getName());
		assertEquals(SECOND_ID, viewModels[1].getuId());
		assertEquals(authorizedUsers, viewModels[1].getAuthorizedUsers());
	}

	@Test
	public void convertTaskToTaskViewModel() {
		when(taskMock.getUid()).thenReturn(FIRST_ID);
		when(taskMock.getName()).thenReturn(FIRST_NAME);
		when(taskMock.getAuthorizedUsers()).thenReturn(authorizedUsers);

		taskViewModelMock = taskConverter.convert(taskMock);

		assertEquals(taskMock.getUid(), taskViewModelMock.getuId());
		assertEquals(taskMock.getName(), taskViewModelMock.getName());
		assertEquals(taskMock.getAuthorizedUsers(), taskViewModelMock.getAuthorizedUsers());
	}

	private Task createTask(String number, String name) {
		Task task = mock(Task.class);
		given(task.getName()).willReturn(name);
		given(task.getUid()).willReturn(number);
		given(task.getAuthorizedUsers()).willReturn(authorizedUsers);
		return task;
	}
}
