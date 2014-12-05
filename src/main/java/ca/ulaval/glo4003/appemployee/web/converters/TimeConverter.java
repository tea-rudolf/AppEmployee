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
		return new TimeEntryViewModel(timeEntry.getUid(), timeEntry.getUserEmail(), timeEntry.getDate().toString(), timeEntry.getBillableHours(), timeEntry.getTaskUid(), 
				taskService.retrieveTaskName(timeEntry.getTaskUid()), timeEntry.getComment(), taskService.retrieveAllTasksByUserId(timeEntry.getUserEmail()));

	}

	public TimeEntryViewModel convert(String userEmail) {
		TimeEntryViewModel timeViewModel = new TimeEntryViewModel();
		timeViewModel.setUserEmail(userEmail);
		timeViewModel.setAvailableTasks(taskService.retrieveAllTasksByUserId(userEmail));
		return timeViewModel;
	}
}
