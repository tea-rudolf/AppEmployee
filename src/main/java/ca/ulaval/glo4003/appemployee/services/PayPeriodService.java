package ca.ulaval.glo4003.appemployee.services;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.expense.ExpenseRepository;
import ca.ulaval.glo4003.appemployee.domain.payperiod.NoCurrentPayPeriodException;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriodNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriodRepository;
import ca.ulaval.glo4003.appemployee.domain.task.TaskRepository;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntryRepository;
import ca.ulaval.glo4003.appemployee.domain.user.UserRepository;
import ca.ulaval.glo4003.appemployee.persistence.RepositoryException;

@Service
public class PayPeriodService {

	private PayPeriodRepository payPeriodRepository;

	@Autowired
	public PayPeriodService(PayPeriodRepository payPeriodRepository, UserRepository userRepository, TaskRepository taskRepository,
			TimeEntryRepository timeEntryRepository, ExpenseRepository expenseRepository) {
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
