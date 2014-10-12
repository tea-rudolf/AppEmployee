package ca.ulaval.glo4003.appemployee.web.converters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.appemployee.domain.task.BillableTask;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TaskViewModel;

@Component
public class TaskConverter {

	public Collection<TaskViewModel> convert(List<BillableTask> tasks) {
		Collection<TaskViewModel> viewModels = new ArrayList<TaskViewModel>();
		for (BillableTask task : tasks) {
			TaskViewModel viewModel = convert(task);
			viewModels.add(viewModel);
		}
		return viewModels;
	}

	public BillableTask convert(TaskViewModel taskViewModel) {
		BillableTask task = new BillableTask(taskViewModel.getName());
		return task;
	}

	public TaskViewModel convert(BillableTask task) {
		TaskViewModel viewModel = new TaskViewModel();
		viewModel.setName(task.getName());
		return viewModel;
	}
}
