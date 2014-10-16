package ca.ulaval.glo4003.appemployee.services;

import java.util.List;
import java.util.Map.Entry;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.expense.ExpenseRepository;
import ca.ulaval.glo4003.appemployee.domain.payperiod.ConfigManager;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriodFactory;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriodNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriodRepository;
import ca.ulaval.glo4003.appemployee.domain.task.TaskRepository;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntryRepository;
import ca.ulaval.glo4003.appemployee.domain.user.UserRepository;
import ca.ulaval.glo4003.appemployee.persistence.RepositoryException;

@Service
public class PayPeriodService {

	private PayPeriodRepository payPeriodRepository;
	private UserRepository userRepository;
	private TaskRepository taskRepository;
	private TimeEntryRepository timeEntryRepository;
	private ExpenseRepository expenseRepository;
	private PayPeriodFactory payPeriodFactory;

	@Autowired
	public PayPeriodService(PayPeriodRepository payPeriodRepository, UserRepository userRepository, TaskRepository taskRepository,
			TimeEntryRepository timeEntryRepository, ExpenseRepository expenseRepository) {
		this.payPeriodRepository = payPeriodRepository;
		this.userRepository = userRepository;
		this.taskRepository = taskRepository;
		this.timeEntryRepository = timeEntryRepository;
		this.expenseRepository = expenseRepository;
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
		currentPayPeriod = payPeriodRepository.findByDate(new LocalDate());
		if (currentPayPeriod == null) {
			createCurrentPayPeriod();
		}
		return currentPayPeriod;
	}

	private PayPeriod createCurrentPayPeriod() {

		ConfigManager configManger = ConfigManager.getInstance();
		List<Entry<String, String>> payPeriodDates = configManger.getPayPeriodDates();
		PayPeriod newPayPeriod = new PayPeriod();
		for (Entry<String, String> datesEntry : payPeriodDates) {
			if (IsInCurrentPayPeriod(datesEntry.getKey(), datesEntry.getValue())) {
				newPayPeriod = payPeriodFactory.createPayPeriod(datesEntry.getKey(), datesEntry.getValue());
				try {
					payPeriodRepository.persist(newPayPeriod);
				} catch (Exception e) {
					throw new PayPeriodNotFoundException("Pay period does not exist.");
				}
			}
		}
		return newPayPeriod;
	}

	private boolean IsInCurrentPayPeriod(String startDate, String endDate) {
		LocalDate currentDate = new LocalDate();

		return currentDate.isAfter(new LocalDate(startDate)) && currentDate.isBefore(new LocalDate(endDate));
	}

	public PayPeriod getPreviousPayPeriod() throws Exception {
		return payPeriodRepository.findByDate(this.getCurrentPayPeriod().getStartDate().minusDays(1));
	}

}
