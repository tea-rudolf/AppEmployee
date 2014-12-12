package ca.ulaval.glo4003.appemployee.web.converters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.time.TimeEntry;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TimeEntryViewModel;

@Component
public class TimeConverter {	

	public Collection<TimeEntryViewModel> convert(List<TimeEntry> timeEntries) {
		Collection<TimeEntryViewModel> viewModels = new ArrayList<TimeEntryViewModel>();

		for (TimeEntry entry : timeEntries) {
			TimeEntryViewModel viewModel = convert(entry);
			viewModel.setUid(entry.getUid());
			viewModels.add(viewModel);
		}

		return viewModels;
	}
	
	public TimeEntryViewModel convert(TimeEntry timeEntry, String taskName, List<Task> tasks) {
		return new TimeEntryViewModel(timeEntry.getUid(), timeEntry.getUserEmail(), timeEntry.getDate().toString(), timeEntry.getBillableHours(), timeEntry.getTaskUid(), 
				taskName, timeEntry.getComment(), tasks);

	}


	public TimeEntryViewModel convert(String userEmail, List<Task> tasks) {
		TimeEntryViewModel timeViewModel = new TimeEntryViewModel();
		timeViewModel.setUserEmail(userEmail);
		timeViewModel.setAvailableTasks(tasks);
		return timeViewModel;
	}
	
	private TimeEntryViewModel convert(TimeEntry timeEntry) {
		return new TimeEntryViewModel(timeEntry.getUid(), timeEntry.getUserEmail(), timeEntry.getDate().toString(), timeEntry.getBillableHours(), timeEntry.getTaskUid(), 
				timeEntry.getComment());

	}
}
