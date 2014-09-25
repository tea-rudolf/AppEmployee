package ca.ulaval.glo4003.appemployee.web.converters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.appemployee.domain.Project;
import ca.ulaval.glo4003.appemployee.domain.Task;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ProjectViewModel;
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
	
	public Task convert(TaskViewModel taskViewModel) {
		Task task = new Task(taskViewModel.getNumber(), taskViewModel.getName(), taskViewModel.getEmployee());
		return task;
	}
	
	public TaskViewModel convert(Task task) {
		TaskViewModel viewModel = new TaskViewModel();
		viewModel.setNumber(task.getNumber());
		viewModel.setName(task.getName());
		viewModel.setEmployee(task.getEmployee(null)); //null � changer
		return viewModel;
	}

}
