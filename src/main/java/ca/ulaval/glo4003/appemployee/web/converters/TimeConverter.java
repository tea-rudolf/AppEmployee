package ca.ulaval.glo4003.appemployee.web.converters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.task.Task;
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
			TimeViewModel viewModel = convertToViewModel(entry);
			viewModels.add(viewModel);
		}
		return viewModels;
	}

	public TimeEntry convertToTimeEntry(TimeViewModel payPeriodViewModel) {
		TimeEntry timeEntry = new TimeEntry();
		timeEntry.setBillableHours(payPeriodViewModel.getHoursTimeEntry());
		timeEntry.setDate(new LocalDate(payPeriodViewModel.getDateTimeEntry()));
		timeEntry.setTaskuId(payPeriodViewModel.getTaskIdTimeEntry());
		timeEntry.setUserEmail(payPeriodViewModel.getUserEmail());
		timeEntry.setComment(payPeriodViewModel.getCommentTimeEntry());
		return timeEntry;
	}

	public TimeViewModel convertToViewModel(TimeEntry timeEntry) {
		TimeViewModel model = new TimeViewModel();
		model.setTimeEntryuId(timeEntry.getuId());
		model.setDateTimeEntry(timeEntry.getDate().toString());
		model.setHoursTimeEntry(timeEntry.getBillableHours());
		model.setTaskNameTimeEntry(projectService.getTaskName(timeEntry.getTaskuId()));
		model.setCommentTimeEntry(timeEntry.getComment());
		return model;
	}

	public TimeViewModel convert(PayPeriod payPeriod, List<Task> tasks) {
		TimeViewModel payPeriodViewModel = new TimeViewModel();
		payPeriodViewModel.setStartDate(payPeriod.getStartDate().toString());
		payPeriodViewModel.setEndDate(payPeriod.getEndDate().toString());
		payPeriodViewModel.setAvailableTasks(tasks);
		return payPeriodViewModel;
	}
}
