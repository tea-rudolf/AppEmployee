package ca.ulaval.glo4003.appemployee.services;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.payperiod.NoCurrentPayPeriodException;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriodNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.repository.PayPeriodRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.TimeEntryRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.TravelRepository;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntry;
import ca.ulaval.glo4003.appemployee.domain.travel.Travel;
import ca.ulaval.glo4003.appemployee.persistence.RepositoryException;

@Service
public class PayPeriodService {

	private PayPeriodRepository payPeriodRepository;
	private TimeEntryRepository timeEntryRepository;
	private TravelRepository travelRepository;

	@Autowired
	public PayPeriodService(PayPeriodRepository payPeriodRepository, TimeEntryRepository timeEntryRepository, TravelRepository travelRepository) {
		this.payPeriodRepository = payPeriodRepository;
		this.timeEntryRepository = timeEntryRepository;
		this.travelRepository = travelRepository;
	}

	public void storeTimeEntry(TimeEntry entry) {
		try {
			timeEntryRepository.store(entry);
		} catch (Exception e) {
			throw new RepositoryException(e.getMessage());
		}
	}
	
	public void storeTravel(Travel travel){
		try {
			travelRepository.store(travel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void updateCurrentPayPeriodTimeEntries(PayPeriod payPeriod) {
		try {
			payPeriodRepository.update(payPeriod);
		} catch (Exception e) {
			throw new RepositoryException(e.getMessage());
		}
	}

	public PayPeriod getCurrentPayPeriod() {
		PayPeriod currentPayPeriod;

		try {
			currentPayPeriod = payPeriodRepository.findByDate(new LocalDate());
		} catch (PayPeriodNotFoundException exception) {
			throw new NoCurrentPayPeriodException(exception.getMessage());
		}

		return currentPayPeriod;
	}

	public PayPeriod getPreviousPayPeriod() {
		return payPeriodRepository.findByDate(this.getCurrentPayPeriod().getStartDate().minusDays(1));
	}

	public void updatePayPeriod(PayPeriod payPeriod) {
		try {
			payPeriodRepository.update(payPeriod);
		} catch (Exception e) {
			throw new RepositoryException(e.getMessage());
		}
	}

}
