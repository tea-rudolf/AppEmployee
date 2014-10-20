package ca.ulaval.glo4003.appemployee.web.converters;

import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntry;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TimeViewModel;

@Component
public class TimeConverter {

	public TimeConverter() {

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

	public TimeViewModel convert(PayPeriod payPeriod, List<TimeEntry> timeEntrys, List<Task> tasks) {
		TimeViewModel payPeriodViewModel = new TimeViewModel();

		payPeriodViewModel.setStartDate(payPeriod.getStartDate().toString());
		payPeriodViewModel.setEndDate(payPeriod.getEndDate().toString());
		payPeriodViewModel.setAvailableTasks(tasks);

		return payPeriodViewModel;
	}
}
