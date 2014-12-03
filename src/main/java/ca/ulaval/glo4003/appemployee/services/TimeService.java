package ca.ulaval.glo4003.appemployee.services;

import java.util.Collection;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.exceptions.PayPeriodNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.time.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.time.TimeEntry;
import ca.ulaval.glo4003.appemployee.domain.time.TimeProcessor;
import ca.ulaval.glo4003.appemployee.web.converters.TimeConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.PayPeriodViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TimeViewModel;

@Service
public class TimeService {

	private TimeProcessor timeProcessor;
	private TimeConverter timeConverter;
	private UserService userService;

	@Autowired
	public TimeService(TimeProcessor timeProcessor, TimeConverter timeConverter, UserService userService) {
		this.timeProcessor = timeProcessor;
		this.timeConverter = timeConverter;
		this.userService = userService;
	}

	public PayPeriod retrieveCurrentPayPeriod() throws PayPeriodNotFoundException {
		return timeProcessor.retrieveCurrentPayPeriod();
	}

	public PayPeriod retrievePreviousPayPeriod() {
		return timeProcessor.retrievePreviousPayPeriod();
	}

	public void editPayPeriod(PayPeriod payPeriod) throws Exception {
		timeProcessor.editPayPeriod(payPeriod);
	}

	public void createTimeEntry(TimeViewModel timeEntryViewModel, PayPeriod payPeriod) throws Exception {
		timeProcessor.createTimeEntry(payPeriod, timeEntryViewModel.getHoursTimeEntry(), new LocalDate(timeEntryViewModel.getDateTimeEntry()),
				timeEntryViewModel.getUserEmail(), timeEntryViewModel.getTaskIdTimeEntry(), timeEntryViewModel.getCommentTimeEntry());
	}

	public void updateTimeEntry(TimeViewModel timeEntryViewModel) throws Exception {
		timeProcessor.editTimeEntry(timeEntryViewModel.getTimeEntryUid(), timeEntryViewModel.getHoursTimeEntry(),
				new LocalDate(timeEntryViewModel.getDateTimeEntry()), timeEntryViewModel.getUserEmail(), timeEntryViewModel.getTaskIdTimeEntry(),
				timeEntryViewModel.getCommentTimeEntry());
	}

	public PayPeriodViewModel retrieveCurrentPayPeriodViewModel() {
		PayPeriod payPeriod = retrieveCurrentPayPeriod();
		return new PayPeriodViewModel(payPeriod.getStartDate().toString(), payPeriod.getEndDate().toString());
	}

	public PayPeriodViewModel retrievePreviousPayPeriodViewModel() {
		PayPeriod payPeriod = retrievePreviousPayPeriod();
		return new PayPeriodViewModel(payPeriod.getStartDate().toString(), payPeriod.getEndDate().toString());
	}
	
	public TimeViewModel retrieveTimeEntryViewModelForUser(String userEmail) {
		return timeConverter.convert(userEmail);
	}

	public Collection<TimeViewModel> retrieveAllTimeEntriesViewModelsForCurrentPayPeriod(String userEmail) {
		List<TimeEntry> timeEntries = userService.getTimeEntriesForUserForAPayPeriod(retrieveCurrentPayPeriod(), userEmail);
		return timeConverter.convert(timeEntries);
	}

	public Collection<TimeViewModel> retrieveAllTimeEntriesViewModelsForPreviousPayPeriod(String userEmail) {
		return timeConverter.convert(userService.getTimeEntriesForUserForAPayPeriod(retrievePreviousPayPeriod(), userEmail));
	}

	public TimeViewModel retrieveTimeEntryViewModel(String timeEntryUid) {
		TimeEntry timeEntry = userService.getTimeEntry(timeEntryUid);
		return timeConverter.convert(timeEntry);
	}

}
