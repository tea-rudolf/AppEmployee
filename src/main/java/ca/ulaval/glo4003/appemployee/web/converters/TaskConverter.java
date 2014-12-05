package ca.ulaval.glo4003.appemployee.web.converters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TaskViewModel;

@Component
public class TaskConverter {

	public Collection<TaskViewModel> convert(List<Task> tasks) {
		Collection<TaskViewModel> viewModels = new ArrayList<TaskViewModel>();

		for (Task task : tasks) {
			TaskViewModel viewModel = convert(task);
			viewModels.add(viewModel);
		}
		return viewModels;
	}

	public TaskViewModel convert(Task task) {
		TaskViewModel taskViewModel = new TaskViewModel(task.getUid(), task.getName(), task.getAuthorizedUsers(),
				task.getMultiplicativeFactor());
		return taskViewModel;
	}
}
