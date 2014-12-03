package ca.ulaval.glo4003.appemployee.web.converters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.appemployee.domain.time.TimeEntry;
import ca.ulaval.glo4003.appemployee.services.ProjectService;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TimeViewModel;

@Component
public class TimeConverter {

	private ProjectService projectService;

	@Autowired
	public TimeConverter(ProjectService projectService) {
		this.projectService = projectService;
	}

	public Collection<TimeViewModel> convert(List<TimeEntry> timeEntries) {
		Collection<TimeViewModel> viewModels = new ArrayList<TimeViewModel>();

		for (TimeEntry entry : timeEntries) {
			TimeViewModel viewModel = convert(entry);
			viewModel.setTimeEntryUid(entry.getUid());
			viewModels.add(viewModel);
		}

		return viewModels;
	}

	public TimeViewModel convert(TimeEntry timeEntry) {
		TimeViewModel model = new TimeViewModel();
		model.setTimeEntryUid(timeEntry.getUid());
		model.setUserEmail(timeEntry.getUserEmail());
		model.setDateTimeEntry(timeEntry.getDate().toString());
		model.setHoursTimeEntry(timeEntry.getBillableHours());
		model.setTaskIdTimeEntry(timeEntry.getTaskUid());
		model.setTaskNameTimeEntry(projectService.getTaskName(timeEntry
				.getTaskUid()));
		model.setCommentTimeEntry(timeEntry.getComment());
		model.setAvailableTasks(projectService
				.getAllTasksByCurrentUserId(timeEntry.getUserEmail()));
		return model;
	}

	public TimeViewModel convert(String userEmail) {
		TimeViewModel timeViewModel = new TimeViewModel();
		timeViewModel.setUserEmail(userEmail);
		timeViewModel.setAvailableTasks(projectService
				.getAllTasksByCurrentUserId(userEmail));
		return timeViewModel;
	}
}
