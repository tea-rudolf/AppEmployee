package ca.ulaval.glo4003.appemployee.web.converters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.appemployee.domain.time.TimeEntry;
import ca.ulaval.glo4003.appemployee.services.TaskService;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TimeEntryViewModel;

@Component
public class TimeConverter {

	private TaskService taskService;;

	@Autowired
	public TimeConverter(TaskService taskService) {
		this.taskService = taskService;
	}

	public Collection<TimeEntryViewModel> convert(List<TimeEntry> timeEntries) {
		Collection<TimeEntryViewModel> viewModels = new ArrayList<TimeEntryViewModel>();

		for (TimeEntry entry : timeEntries) {
			TimeEntryViewModel viewModel = convert(entry);
			viewModel.setUid(entry.getUid());
			viewModels.add(viewModel);
		}

		return viewModels;
	}

	public TimeEntryViewModel convert(TimeEntry timeEntry) {
		TimeEntryViewModel model = new TimeEntryViewModel();
		model.setUid(timeEntry.getUid());
		model.setUserEmail(timeEntry.getUserEmail());
		model.setDate(timeEntry.getDate().toString());
		model.setHours(timeEntry.getBillableHours());
		model.setTaskId(timeEntry.getTaskUid());
		model.setTaskName(taskService.retrieveTaskName(timeEntry.getTaskUid()));
		model.setComment(timeEntry.getComment());
		model.setAvailableTasks(taskService.retrieveAllTasksByUserId(timeEntry.getUserEmail()));
		return model;
	}

	public TimeEntryViewModel convert(String userEmail) {
		TimeEntryViewModel timeViewModel = new TimeEntryViewModel();
		timeViewModel.setUserEmail(userEmail);
		timeViewModel.setAvailableTasks(taskService.retrieveAllTasksByUserId(userEmail));
		return timeViewModel;
	}
}
