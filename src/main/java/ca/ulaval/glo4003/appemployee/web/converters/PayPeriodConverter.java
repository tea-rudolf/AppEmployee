package ca.ulaval.glo4003.appemployee.web.converters;

import java.util.List;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntry;
import ca.ulaval.glo4003.appemployee.web.viewmodels.PayPeriodViewModel;

@Component
public class PayPeriodConverter {

	public PayPeriodConverter() {

	}
	
	public TimeEntry convertToTimeEntry(PayPeriodViewModel payPeriodViewModel) {
		TimeEntry newTimeEntry = new TimeEntry();
        newTimeEntry.setBillableHours(payPeriodViewModel.getBillableHoursTimeEntry());
        newTimeEntry.setDate(payPeriodViewModel.getDateTimeEntry());
        newTimeEntry.setTaskId(payPeriodViewModel.getTaskIdTimeEntry());
        newTimeEntry.setUserEmail(payPeriodViewModel.getUserEmail());

		return newTimeEntry;
	}

	public PayPeriodViewModel convert(PayPeriod payPeriod, List<TimeEntry> timeEntrys, List<Task> tasks) {
		PayPeriodViewModel payPeriodViewModel = new PayPeriodViewModel();
		payPeriodViewModel.setStartDate(payPeriod.getStartDate().toString());
		payPeriodViewModel.setEndDate(payPeriod.getEndDate().toString());
		payPeriodViewModel.setAvailableTasks(tasks);

		return payPeriodViewModel;
	}
}
