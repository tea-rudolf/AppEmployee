package ca.ulaval.glo4003.appemployee.services;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriodRepository;
import ca.ulaval.glo4003.appemployee.persistence.RepositoryException;

@Service
public class PayPeriodService {

	private PayPeriodRepository payPeriodRepository;

	@Autowired
	public PayPeriodService(PayPeriodRepository payPeriodRepository) {
		this.payPeriodRepository = payPeriodRepository;
	}

	public void updateCurrentPayPeriodTimeEntries(PayPeriod payPeriod) {
		try {
			payPeriodRepository.update(payPeriod);
		} catch (Exception e) {
			throw new RepositoryException(e.getMessage());
		}
	}

	public PayPeriod getCurrentPayPeriod() {
		return payPeriodRepository.findByDate(new LocalDate());
	}

	public PayPeriod getPreviousPayPeriod() throws Exception {
		return payPeriodRepository.findByDate(this.getCurrentPayPeriod().getStartDate().minusDays(1));
	}

}
