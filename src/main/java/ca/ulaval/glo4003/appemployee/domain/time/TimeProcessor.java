package ca.ulaval.glo4003.appemployee.domain.time;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.appemployee.domain.exceptions.PayPeriodNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.exceptions.TimeEntryNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.repository.PayPeriodRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.TimeEntryRepository;

@Component
public class TimeProcessor {

	private PayPeriodRepository payPeriodRepository;
	private TimeEntryRepository timeEntryRepository;

	@Autowired
	public TimeProcessor(PayPeriodRepository payPeriodRepository, TimeEntryRepository timeEntryRepository) {
		this.payPeriodRepository = payPeriodRepository;
		this.timeEntryRepository = timeEntryRepository;
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
		PayPeriod currentPayPeriod = retrieveCurrentPayPeriod();
		return payPeriodRepository.findByDate(currentPayPeriod.getStartDate().minusDays(1));
	}

	public void editPayPeriod(PayPeriod payPeriod) throws Exception {
		payPeriodRepository.update(payPeriod);
	}

	public void createTimeEntry(PayPeriod payPeriod, double billableHours, LocalDate date, String userEmail, String taskUid, String comment) throws Exception {
		TimeEntry timeEntry = new TimeEntry(billableHours, date, userEmail, taskUid, comment);
		payPeriod.addTimeEntry(timeEntry.getUid());
		editPayPeriod(payPeriod);
		timeEntryRepository.store(timeEntry);
	}

	public void editTimeEntry(String timeEntryUid, double billableHours, LocalDate date, String userEmail, String taskUid, String comment) throws Exception {
		TimeEntry timeEntry = retrieveTimeEntryByUid(timeEntryUid);
		updateTimeEntry(billableHours, date, userEmail, taskUid, comment, timeEntry);
	}

	private void updateTimeEntry(double billableHours, LocalDate date, String userEmail, String taskUid, String comment, TimeEntry timeEntry) throws Exception {
		timeEntry.update(billableHours, date, userEmail, taskUid, comment);
		timeEntryRepository.store(timeEntry);
	}

	private TimeEntry retrieveTimeEntryByUid(String timeEntryUid) throws TimeEntryNotFoundException {
		TimeEntry timeEntry = timeEntryRepository.findByUid(timeEntryUid);

		if (timeEntry == null) {
			throw new TimeEntryNotFoundException("TimeEntry does not exist in repository");
		}
		return timeEntry;
	}

}
