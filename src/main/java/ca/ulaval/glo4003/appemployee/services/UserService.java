package ca.ulaval.glo4003.appemployee.services;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.expense.Expense;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.repository.ExpenseRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.PayPeriodRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.TaskRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.TimeEntryRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.UserRepository;
import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntry;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.persistence.RepositoryException;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TimeViewModel;

@Service
public class UserService {

	private UserRepository userRepository;
	private PayPeriodRepository payPeriodRepository;
	private TaskRepository taskRepository;
	private TimeEntryRepository timeEntryRepository;
	private ExpenseRepository expenseRepository;

	@Autowired
	public UserService(UserRepository userRepository, PayPeriodRepository payPeriodRepository, TaskRepository taskRepository,
			ExpenseRepository expenseRepository, TimeEntryRepository timeEntryRepository) {
		this.userRepository = userRepository;
		this.payPeriodRepository = payPeriodRepository;
		this.taskRepository = taskRepository;
		this.timeEntryRepository = timeEntryRepository;
		this.expenseRepository = expenseRepository;
	}

	public void updateCurrentPayPeriod(PayPeriod payPeriod) {
		try {
			payPeriodRepository.update(payPeriod);
		} catch (Exception e) {
			throw new RepositoryException(e.getMessage());
		}

	}

	public List<Task> getTasksForUserForAPayPeriod(PayPeriod payPeriod, String userId) {

		List<Task> tasks = new ArrayList<Task>();

		for (String timeEntryId : payPeriod.getTimeEntryIds()) {
			TimeEntry entry = timeEntryRepository.findByUid(timeEntryId);
			if (entry != null && entry.getUserEmail().equals(userId)) {
				tasks.add(taskRepository.findByUid(entry.getuId()));
			}
		}

		return tasks;

	}

	public ArrayList<TimeEntry> getTimeEntriesForUserForAPayPeriod(PayPeriod payPeriod, String userEmail) {

		ArrayList<TimeEntry> timeEntries = new ArrayList<TimeEntry>();

		for (String timeEntryId : payPeriod.getTimeEntryIds()) {
			TimeEntry entry = timeEntryRepository.findByUid(timeEntryId);

			if (entry.getUserEmail().equals(userEmail)) {
				timeEntries.add(entry);
			}
		}

		return timeEntries;

	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public List<User> findUsersByEmail(List<String> emails) {
		List<User> users = new ArrayList<User>();

		for (String email : emails) {
			User user = userRepository.findByEmail(email);
			users.add(user);
		}

		return users;
	}

	public TimeEntry getTimeEntry(String id) {

		return timeEntryRepository.findByUid(id);
	}

	public void updateTimeEntry(String projectNumber, TimeViewModel viewModel) {
		TimeEntry entry = timeEntryRepository.findByUid(projectNumber);
		entry.setBillableHours(viewModel.getHoursTimeEntry());
		entry.setComment(viewModel.getCommentTimeEntry());
		entry.setDate(new LocalDate(viewModel.getDateTimeEntry()));
		entry.setTaskuId(viewModel.getTaskIdTimeEntry());

		try {
			timeEntryRepository.store(entry);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<Expense> getExpensesForUserForAPayPeriod(PayPeriod payPeriod, String userEmail) {

		ArrayList<Expense> expenses = new ArrayList<Expense>();
		System.out.println("1 = " + payPeriod.getStartDate());
		System.out.println("2 = " + payPeriod.getEndDate());
		System.out.println("3 = " + userEmail);
		for (Expense expense : expenseRepository.findAll()) {
			System.out.println("4");
			if (expense.getUserEmail().equals(userEmail) && expense.getDate().isBefore(payPeriod.getEndDate())
					&& expense.getDate().isAfter(payPeriod.getStartDate())) {
				System.out.println("5");
				expenses.add(expense);
			}
		}
		System.out.println("6");
		return expenses;
	}

}
