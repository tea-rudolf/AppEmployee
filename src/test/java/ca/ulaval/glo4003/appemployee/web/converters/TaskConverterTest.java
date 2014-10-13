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

	private TaskConverter converter;

	@Before
	public void setUp() {
		converter = new TaskConverter();
	}

	@Test
	public void convertTasksListToViewModelsConvertsAllOfThem() {
		String firstName = "firstName";
		String firstNumber = "123456";
		String secondName = "secondName";
		String secondNumber = "789103";
		Task firstTask = createTask(firstNumber, firstName);
		Task secondTask = createTask(secondNumber, secondName);
		List<Task> tasks = new ArrayList<Task>();
		tasks.add(firstTask);
		tasks.add(secondTask);

		TaskViewModel[] viewModels = converter.convert(tasks).toArray(new TaskViewModel[1]);

		assertEquals(firstName, viewModels[0].getName());
		assertEquals(firstNumber, viewModels[0].getComment());

		assertEquals(secondName, viewModels[1].getName());
		assertEquals(secondNumber, viewModels[1].getComment());
	}

	@Test
	public void convertTaskToTaskViewModelSetsNumberAndName() {
		String number = "123456";
		String name = "abcdefg";
		Task task = createTask(number, name);

		TaskViewModel viewModel = converter.convert(task);

		assertEquals(number, viewModel.getComment());
		assertEquals(name, viewModel.getName());
	}

	@Test
	public void convertTaskViewModelToTaskSetsNumberAndName() {
		TaskViewModel viewModel = new TaskViewModel();
		viewModel.setName("task");
		viewModel.setComment("123456");

		Task task = converter.convert(viewModel);

		assertEquals(viewModel.getName(), task.getName());
		assertEquals(viewModel.getComment(), task.getComment());
	}

	private Task createTask(String number, String name) {
		Task task = mock(Task.class);
		given(task.getName()).willReturn(name);
		given(task.getComment()).willReturn(number);
		return task;
	}
}
