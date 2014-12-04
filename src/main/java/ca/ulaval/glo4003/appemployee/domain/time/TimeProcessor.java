package ca.ulaval.glo4003.appemployee.domain.time;

import java.util.ArrayList;
import java.util.List;

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
			throw new PayPeriodNotFoundException("Cannot find pay period containing date " + currentDate.toString());
		}
		return currentPayPeriod;
	}

	public PayPeriod retrievePreviousPayPeriod() throws PayPeriodNotFoundException {
		PayPeriod currentPayPeriod = retrieveCurrentPayPeriod();
		LocalDate date = currentPayPeriod.getStartDate().minusDays(1);
		PayPeriod previousPayPeriod = payPeriodRepository.findByDate(date);

		if (previousPayPeriod == null) {
			throw new PayPeriodNotFoundException("Cannot find pay period containing date " + date.toString());
		}
		return previousPayPeriod;
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

	public TimeEntry retrieveTimeEntryByUid(String timeEntryUid) throws TimeEntryNotFoundException {
		TimeEntry timeEntry = timeEntryRepository.findByUid(timeEntryUid);

		if (timeEntry == null) {
			throw new TimeEntryNotFoundException("TimeEntry does not exist in repository");
		}
		return timeEntry;
	}

	public List<TimeEntry> evaluateUserTimeEntriesForPayPeriod(PayPeriod payPeriod, String userEmail)
			throws TimeEntryNotFoundException {
		ArrayList<TimeEntry> timeEntries = new ArrayList<TimeEntry>();

		for (String timeEntryUid : payPeriod.getTimeEntryIds()) {
			TimeEntry entry = retrieveTimeEntryByUid(timeEntryUid);
			if (entry.getUserEmail().equals(userEmail)) {
				timeEntries.add(entry);
			}
		}
		return timeEntries;
	}

	private void updateTimeEntry(double billableHours, LocalDate date, String userEmail, String taskUid,
			String comment, TimeEntry timeEntry) throws Exception {
		timeEntry.update(billableHours, date, userEmail, taskUid, comment);
		timeEntryRepository.store(timeEntry);
	}

}
