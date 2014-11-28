package ca.ulaval.glo4003.appemployee.services;

import java.util.Collection;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.exceptions.PayPeriodNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.repository.PayPeriodRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.TimeEntryRepository;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntry;
import ca.ulaval.glo4003.appemployee.web.converters.TimeConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.PayPeriodViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TimeViewModel;

@Service
public class TimeService {

	private PayPeriodRepository payPeriodRepository;
	private TimeEntryRepository timeEntryRepository;
	private TimeConverter timeConverter;
	private UserService userService;

	@Autowired
	public TimeService(PayPeriodRepository payPeriodRepository, TimeEntryRepository timeEntryRepository, TimeConverter timeConverter, UserService userService) {
		this.payPeriodRepository = payPeriodRepository;
		this.timeEntryRepository = timeEntryRepository;
		this.timeConverter = timeConverter;
		this.userService = userService;
	}

	public PayPeriod retrieveCurrentPayPeriod() throws PayPeriodNotFoundException {
		LocalDate currentDate = new LocalDate();
		PayPeriod currentPayPeriod = payPeriodRepository.findByDate(currentDate);

		if (currentPayPeriod == null) {
			throw new PayPeriodNotFoundException("Cannot find project pay period containing date " + currentDate.toString());
		}
		return currentPayPeriod;
	}

	public PayPeriod retrievePreviousPayPeriod() {
		return payPeriodRepository.findByDate(retrieveCurrentPayPeriod().getStartDate().minusDays(1));
	}

	public void updatePayPeriod(PayPeriod payPeriod) throws Exception {
		payPeriodRepository.update(payPeriod);
	}

	public void createTimeEntry(TimeViewModel timeEntryViewModel, PayPeriod payPeriod) throws Exception {
		TimeEntry newTimeEntry = timeConverter.convert(timeEntryViewModel);
		payPeriod.addTimeEntry(newTimeEntry.getUid());
		updatePayPeriod(payPeriod);
		timeEntryRepository.store(newTimeEntry);
	}

	public void updateTimeEntry(TimeViewModel timeViewModel) throws Exception {
		TimeEntry timeEntry = timeEntryRepository.findByUid(timeViewModel.getTimeEntryUid());
		timeEntry.setBillableHours(timeViewModel.getHoursTimeEntry());
		timeEntry.setDate(new LocalDate(timeViewModel.getDateTimeEntry()));
		timeEntry.setTaskUid(timeViewModel.getTaskIdTimeEntry());
		timeEntry.setUserEmail(timeViewModel.getUserEmail());
		timeEntry.setComment(timeViewModel.getCommentTimeEntry());
		timeEntryRepository.store(timeEntry);
	}

	public TimeViewModel retrieveTimeEntryViewModelForCurrentPayPeriod(String userEmail) {
		return timeConverter.convert(retrieveCurrentPayPeriod(), userEmail);
	}

	public Collection<TimeViewModel> retrieveTimeEntriesViewModelsForCurrentPayPeriod(String userEmail) {
		return timeConverter.convert(userService.getTimeEntriesForUserForAPayPeriod(retrieveCurrentPayPeriod(), userEmail));
	}

	public TimeViewModel retrieveViewModelForTimeEntry(String timeEntryUid) {
		return timeConverter.convert(userService.getTimeEntry(timeEntryUid));
	}

	public TimeViewModel retrieveViewModelForPreviousPayPeriod(String userEmail) {
		return timeConverter.convert(retrievePreviousPayPeriod(), userEmail);
	}

	public Collection<TimeViewModel> retrieveTimeEntriesViewModelsForPreviousPayPeriod(String userEmail) {
		return timeConverter.convert(userService.getTimeEntriesForUserForAPayPeriod(retrievePreviousPayPeriod(), userEmail));
	}

	public PayPeriodViewModel retrievePayPeriodViewModel() {
		PayPeriod payPeriod = retrieveCurrentPayPeriod();
		return new PayPeriodViewModel(payPeriod.getStartDate().toString(), payPeriod.getEndDate().toString());
	}
}
