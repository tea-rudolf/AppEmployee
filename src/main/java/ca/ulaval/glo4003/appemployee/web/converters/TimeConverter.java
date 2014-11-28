package ca.ulaval.glo4003.appemployee.web.converters;

import java.util.ArrayList;
import java.util.Collection;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntry;
import ca.ulaval.glo4003.appemployee.services.ProjectService;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TimeViewModel;

@Component
public class TimeConverter {

	private ProjectService projectService;

	@Autowired
	public TimeConverter(ProjectService projectService) {
		this.projectService = projectService;
	}

	public Collection<TimeViewModel> convert(ArrayList<TimeEntry> timeEntries) {
		Collection<TimeViewModel> viewModels = new ArrayList<TimeViewModel>();

		for (TimeEntry entry : timeEntries) {
			TimeViewModel viewModel = convert(entry);
			viewModel.setTimeEntryUid(entry.getUid());
			viewModels.add(viewModel);
		}
		
		return viewModels;
	}

	public TimeEntry convert(TimeViewModel timeViewModel) {
		TimeEntry timeEntry = new TimeEntry();
		timeEntry.setBillableHours(timeViewModel.getHoursTimeEntry());
		timeEntry.setDate(new LocalDate(timeViewModel.getDateTimeEntry()));
		timeEntry.setTaskUid(timeViewModel.getTaskIdTimeEntry());
		timeEntry.setUserEmail(timeViewModel.getUserEmail());
		timeEntry.setComment(timeViewModel.getCommentTimeEntry());
		return timeEntry;
	}

	public TimeViewModel convert(TimeEntry timeEntry) {
		TimeViewModel model = new TimeViewModel();
		model.setTimeEntryUid(timeEntry.getUid());
		model.setDateTimeEntry(timeEntry.getDate().toString());
		model.setHoursTimeEntry(timeEntry.getBillableHours());
		model.setTaskIdTimeEntry(timeEntry.getTaskUid());
		model.setTaskNameTimeEntry(projectService.getTaskName(timeEntry.getTaskUid()));
		model.setCommentTimeEntry(timeEntry.getComment());
		return model;
	}

	public TimeViewModel convert(PayPeriod payPeriod, String userEmail) {
		TimeViewModel timeViewModel = new TimeViewModel();
		timeViewModel.setStartDate(payPeriod.getStartDate().toString());
		timeViewModel.setEndDate(payPeriod.getEndDate().toString());
		timeViewModel.setUserEmail(userEmail);
		timeViewModel.setAvailableTasks(projectService.getAllTasksByCurrentUserId(userEmail));
		return timeViewModel;
	}
}
