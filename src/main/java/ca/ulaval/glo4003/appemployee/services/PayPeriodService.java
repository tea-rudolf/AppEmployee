package ca.ulaval.glo4003.appemployee.services;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.expense.Expense;
import ca.ulaval.glo4003.appemployee.domain.expense.ExpenseRepository;
import ca.ulaval.glo4003.appemployee.domain.payperiod.NoCurrentPayPeriodException;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriodNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriodRepository;
import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.task.TaskRepository;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntry;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntryRepository;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.domain.user.UserRepository;

@Service
public class PayPeriodService {

	private PayPeriodRepository payPeriodRepository;
	private UserRepository userRepository;
	private TaskRepository taskRepository;
	private TimeEntryRepository timeEntryRepository;
	private ExpenseRepository expenseRepository;

	@Autowired
	public PayPeriodService(PayPeriodRepository payPeriodRepository, 
			UserRepository userRepository, TaskRepository taskRepository,  		
			TimeEntryRepository timeEntryRepository, ExpenseRepository expenseRepository){
		this.payPeriodRepository = payPeriodRepository;
		this.userRepository = userRepository;
		this.taskRepository = taskRepository;
		this.timeEntryRepository = timeEntryRepository;
		this.setExpenseRepository(expenseRepository);
	}

	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public TaskRepository getTaskRepository() {
		return taskRepository;
	}

	public void setTaskRepository(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	public TimeEntryRepository getTimeEntryRepository() {
		return timeEntryRepository;
	}

	public void setTimeEntryRepository(TimeEntryRepository timeEntryRepository) {
		this.timeEntryRepository = timeEntryRepository;
	}
	
	public ExpenseRepository getExpenseRepository() {
		return expenseRepository;
	}

	public void setExpenseRepository(ExpenseRepository expenseRepository) {
		this.expenseRepository = expenseRepository;
	}
	
	public PayPeriod getCurrentPayPeriod(){
		PayPeriod payPeriodFound;

		try {
			payPeriodFound = payPeriodRepository.findPayPeriod(new LocalDate());
		} catch (PayPeriodNotFoundException e) {
			throw new NoCurrentPayPeriodException("Cannot find a current pay period.");
		}
		
		return payPeriodFound;
		
	}
	
	public PayPeriod getPreviousPayPeriod(){
		return payPeriodRepository.findPayPeriod(this.getCurrentPayPeriod().getStartDate().minusDays(1));
	}
	
	public List<Task> getTasksForUser(PayPeriod payPeriod, String userId){
		
		List<Task> tasks = new ArrayList<Task>();
		
		for (Integer timeEntryId : payPeriod.getTimeEntryIds()) {
			TimeEntry entry = timeEntryRepository.getByUid(timeEntryId);
			if (entry.getUserId() == userId){
				tasks.add(taskRepository.getByUid(entry.getuId()));
			}
		}
		
		return tasks;
		
	}
	
	public List<Expense> getExpensesForUser(PayPeriod payPeriod, String userId){
		
		List<Expense> expenses = new ArrayList<Expense>();
		
		for (Expense expense : expenseRepository.findAllExpensesForUser(userId)) {
			if (expense.getDate().isBefore(payPeriod.getEndDate()) && 
					expense.getDate().isAfter(payPeriod.getStartDate())){
				expenses.add(expense);
			}
		}
		
		return expenses;
		
	}

	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

}


