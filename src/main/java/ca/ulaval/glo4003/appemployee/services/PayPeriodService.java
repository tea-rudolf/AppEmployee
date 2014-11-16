package ca.ulaval.glo4003.appemployee.services;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.exceptions.PayPeriodNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.repository.PayPeriodRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.TimeEntryRepository;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntry;
import ca.ulaval.glo4003.appemployee.web.converters.TimeConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TimeViewModel;

@Service
public class PayPeriodService {

	private PayPeriodRepository payPeriodRepository;
	private TimeEntryRepository timeEntryRepository;
	private TimeConverter timeConverter;

	@Autowired
	public PayPeriodService(PayPeriodRepository payPeriodRepository, TimeEntryRepository timeEntryRepository, TimeConverter timeConverter) {
		this.payPeriodRepository = payPeriodRepository;
		this.timeEntryRepository = timeEntryRepository;
		this.timeConverter = timeConverter;
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

	public void saveTimeEntry(TimeViewModel timeEntryViewModel) throws Exception {
		TimeEntry newTimeEntry = timeConverter.convert(timeEntryViewModel);
		PayPeriod currentPayPeriod = retrieveCurrentPayPeriod();
		currentPayPeriod.addTimeEntry(newTimeEntry.getUid());
		updatePayPeriod(currentPayPeriod);
		timeEntryRepository.store(newTimeEntry);
	}
}
