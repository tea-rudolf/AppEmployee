package ca.ulaval.glo4003.appemployee.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.expense.Expense;
import ca.ulaval.glo4003.appemployee.domain.expense.ExpenseRepository;
import ca.ulaval.glo4003.appemployee.domain.payperiod.ConfigManager;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriodFactory;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriodNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriodRepository;
import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.task.TaskRepository;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntry;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntryRepository;
import ca.ulaval.glo4003.appemployee.domain.user.User;
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
	
	public void updateCurrentPayPeriodTimeEntrys(PayPeriod payPeriod){
		List<String> timeEntryIds = new ArrayList<String>();
		
		for (String timeEntryId : payPeriod.getTimeEntryIds()) {
			timeEntryIds.add(timeEntryId);
		}
		payPeriod.setTimeEntryIds(timeEntryIds);
		
		try {
			payPeriodRepository.update(payPeriod);
		} catch (Exception e) {
			throw new RepositoryException(e.getMessage());
		}
	}
	

	public PayPeriod getCurrentPayPeriod(){
		PayPeriod foundPayPeriod;

		try {
			foundPayPeriod = payPeriodRepository.findByDate(new LocalDate());
		} catch (PayPeriodNotFoundException e) {
			createCurrentPayPeriod();
			foundPayPeriod = payPeriodRepository.findByDate(new LocalDate());
		}
		return foundPayPeriod;
	}

	public void createCurrentPayPeriod(){

		ConfigManager configManger = ConfigManager.getInstance();
		List<Entry<String, String>> payPeriodDates = configManger.getPayPeriodDates();

		for (Entry<String, String> datesEntry : payPeriodDates) {
			if (checkIfCurrentDateIsInPayPeriod(datesEntry.getKey(), datesEntry.getValue())) {
				PayPeriod newPayPeriod = payPeriodFactory.createPayPeriod(datesEntry.getKey(), datesEntry.getValue());
				try {
					payPeriodRepository.persist(newPayPeriod);
				} catch (Exception e) {
					throw new PayPeriodNotFoundException("Pay period does not exist.");
				}
			}
		}
	}

	public boolean checkIfCurrentDateIsInPayPeriod(String startDate, String endDate) {
		LocalDate currentDate = new LocalDate();
		return currentDate.isAfter(new LocalDate(startDate)) && currentDate.isBefore(new LocalDate(endDate));
	}

	public PayPeriod getPreviousPayPeriod() throws Exception {
		return payPeriodRepository.findByDate(this.getCurrentPayPeriod().getStartDate().minusDays(1));
	}

	public List<Task> getTasksForUser(PayPeriod payPeriod, String userId) {

		List<Task> tasks = new ArrayList<Task>();

		for (String timeEntryId : payPeriod.getTimeEntryIds()) {
			TimeEntry entry = timeEntryRepository.findByUid(timeEntryId);
			if (entry.getUserId() == userId) {
				tasks.add(taskRepository.findByUid(entry.getuId()));
			}
		}
		return tasks;

	}

	public List<Expense> getExpensesForUser(PayPeriod payPeriod, String userId) {
		List<Expense> expenses = new ArrayList<Expense>();
		for (Expense expense : expenseRepository.findAllExpensesByUser(userId)) {
			if (expense.getDate().isBefore(payPeriod.getEndDate()) && expense.getDate().isAfter(payPeriod.getStartDate())) {
				expenses.add(expense);
			}
		}
		return expenses;
	}
	
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

}
