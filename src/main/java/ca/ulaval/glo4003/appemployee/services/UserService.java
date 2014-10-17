package ca.ulaval.glo4003.appemployee.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.expense.ExpenseRepository;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriodRepository;
import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.task.TaskRepository;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntry;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntryRepository;

@Service
public class UserService {

	private PayPeriodRepository payPeriodRepository;
	private TaskRepository taskRepository;
	private ExpenseRepository expenseRepository;
	private TimeEntryRepository timeEntryRepository;

	@Autowired
	public UserService(PayPeriodRepository payPeriodRepository, TaskRepository taskRepository, 
			ExpenseRepository expenseRepository, TimeEntryRepository timeEntryRepository) {
		this.payPeriodRepository = payPeriodRepository;
		this.taskRepository = taskRepository;
		this.expenseRepository = expenseRepository;
		this.timeEntryRepository = timeEntryRepository;
	}


	public void updateCurrentPayPeriod(PayPeriod payPeriod) throws Exception {
		payPeriodRepository.update(payPeriod);
		
	}

	public TaskRepository getTaskRepository() {
		return taskRepository;
	}

	public void setTaskRepository(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	public ExpenseRepository getExpenseRepository() {
		return expenseRepository;
	}

	public void setExpenseRepository(ExpenseRepository expenseRepository) {
		this.expenseRepository = expenseRepository;
	}
	
 	public List<Task> getTasksForUserForAPayPerod(PayPeriod payPeriod, String userId) {
 		
 		List<Task> tasks = new ArrayList<Task>();
 		
 		for (String timeEntryId : payPeriod.getTimeEntryIds()) {
 			TimeEntry entry = timeEntryRepository.findByUid(timeEntryId);
 			if (entry.getUserEmail() == userId) {
 				tasks.add(taskRepository.findByUid(entry.getuId()));
 			}
 		}
 		
 		return tasks;
 		
 	}
 	
 	public List<TimeEntry> getTimeEntriesForUser(PayPeriod payPeriod, String userEmail) {
 		
 		List<TimeEntry> timeEntries = new ArrayList<TimeEntry>();
 		
 		for (String timeEntryId : payPeriod.getTimeEntryIds()) {
 			TimeEntry entry = timeEntryRepository.findByUid(timeEntryId);
 			if (entry.getUserEmail() == userEmail) {
 				timeEntries.add(entry);
 			}
 		}
 		
 		return timeEntries;
 		
 	}
 	

}
