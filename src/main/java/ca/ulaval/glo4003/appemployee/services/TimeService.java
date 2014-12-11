package ca.ulaval.glo4003.appemployee.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.exceptions.TimeEntryNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.task.TaskProcessor;
import ca.ulaval.glo4003.appemployee.domain.time.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.time.TimeEntry;
import ca.ulaval.glo4003.appemployee.domain.time.TimeProcessor;
import ca.ulaval.glo4003.appemployee.web.converters.TimeConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.PayPeriodViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TimeEntryViewModel;

@Service
public class TimeService {

	private TaskProcessor taskProcessor;
	private TimeProcessor timeProcessor;
	private TimeConverter timeConverter;

	@Autowired
	public TimeService(TaskProcessor taskProcessor, TimeProcessor timeProcessor, TimeConverter timeConverter) {
		this.taskProcessor = taskProcessor;
		this.timeProcessor = timeProcessor;
		this.timeConverter = timeConverter;
	}

	public PayPeriod retrieveCurrentPayPeriod() {
		return timeProcessor.retrieveCurrentPayPeriod();
	}

	public PayPeriod retrievePreviousPayPeriod() {
		return timeProcessor.retrievePreviousPayPeriod();
	}

	public void editPayPeriod(PayPeriod payPeriod) throws Exception {
		timeProcessor.editPayPeriod(payPeriod);
	}

	public void createTimeEntry(TimeEntryViewModel timeEntryViewModel, PayPeriod payPeriod) throws Exception {
		timeProcessor.createTimeEntry(payPeriod, timeEntryViewModel.getHours(), new LocalDate(
				timeEntryViewModel.getDate()), timeEntryViewModel.getUserEmail(), timeEntryViewModel.getTaskId(), timeEntryViewModel.getComment());
	}

	public void updateTimeEntry(TimeEntryViewModel timeEntryViewModel) throws Exception {
		timeProcessor.editTimeEntry(timeEntryViewModel.getUid(), timeEntryViewModel.getHours(),
			new LocalDate(timeEntryViewModel.getDate()), timeEntryViewModel.getUserEmail(), timeEntryViewModel.getTaskId(), timeEntryViewModel.getComment());
	}

	public PayPeriodViewModel retrieveCurrentPayPeriodViewModel() {
		PayPeriod payPeriod = retrieveCurrentPayPeriod();
		return new PayPeriodViewModel(payPeriod.getStartDate().toString(), payPeriod.getEndDate().toString());
	}

	public PayPeriodViewModel retrievePreviousPayPeriodViewModel() {
		PayPeriod payPeriod = retrievePreviousPayPeriod();
		return new PayPeriodViewModel(payPeriod.getStartDate().toString(), payPeriod.getEndDate().toString());
	}

	public TimeEntryViewModel retrieveTimeEntryViewModelForUser(String userEmail) {
		return timeConverter.convert(userEmail, taskProcessor.retrieveAllTasksByUserId(userEmail));
	}

	public Collection<TimeEntryViewModel> retrieveAllTimeEntriesViewModelsForCurrentPayPeriod(String userEmail) throws TimeEntryNotFoundException {
		List<TimeEntry> timeEntries = timeProcessor.evaluateUserTimeEntriesForPayPeriod(retrieveCurrentPayPeriod(), userEmail);
		return convertTimeEntriesWithTasks(timeEntries);
	}

	public Collection<TimeEntryViewModel> retrieveAllTimeEntriesViewModelsForPreviousPayPeriod(String userEmail) throws TimeEntryNotFoundException {
		return timeConverter.convert(timeProcessor.evaluateUserTimeEntriesForPayPeriod(retrievePreviousPayPeriod(), userEmail));
	}

	public TimeEntryViewModel retrieveTimeEntryViewModel(String timeEntryUid) throws TimeEntryNotFoundException {
		TimeEntry timeEntry = timeProcessor.retrieveTimeEntryByUid(timeEntryUid);
		return timeConverter.convert(timeEntry, taskProcessor.retrieveTaskName(timeEntry.getTaskUid()), taskProcessor.retrieveAllTasksByUserId(timeEntry.getUserEmail()));
	}
	
	private Collection<TimeEntryViewModel> convertTimeEntriesWithTasks(List<TimeEntry> timeEntries) {
		Collection<TimeEntryViewModel> timeEntryViewModels = new ArrayList<TimeEntryViewModel>();
		for (TimeEntry timeEntry : timeEntries) {
			timeEntryViewModels .add(timeConverter.convert(timeEntry, taskProcessor.retrieveTaskName(timeEntry.getTaskUid()), 
					taskProcessor.retrieveAllTasksByUserId(timeEntry.getUserEmail())));
		}
		return timeEntryViewModels;
	}
}
