package ca.ulaval.glo4003.appemployee.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.repository.ExpenseRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.PayPeriodRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.TaskRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.TimeEntryRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.UserRepository;
import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntry;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.appemployee.persistence.RepositoryException;

@Service
public class UserService {

	private UserRepository userRepository;
	private PayPeriodRepository payPeriodRepository;
	private TaskRepository taskRepository;
	private TimeEntryRepository timeEntryRepository;

	@Autowired
	public UserService(UserRepository userRepository, PayPeriodRepository payPeriodRepository, TaskRepository taskRepository,
			ExpenseRepository expenseRepository, TimeEntryRepository timeEntryRepository) {
		this.userRepository = userRepository;
		this.payPeriodRepository = payPeriodRepository;
		this.taskRepository = taskRepository;
		this.timeEntryRepository = timeEntryRepository;
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

	public List<TimeEntry> getTimeEntriesForUserForAPayPeriod(PayPeriod payPeriod, String userEmail) {

		List<TimeEntry> timeEntries = new ArrayList<TimeEntry>();

		for (String timeEntryId : payPeriod.getTimeEntryIds()) {
			TimeEntry entry = timeEntryRepository.findByUid(timeEntryId);
			if (entry != null && entry.getUserEmail().equals(userEmail)) {
				timeEntries.add(entry);
			}
		}
		return timeEntries;
	}

	public User findByEmail(String email) throws UserNotFoundException {
		User user = userRepository.findByEmail(email);

		if (user == null) {
			throw new UserNotFoundException("User not found with following email : " + email);
		}
		return user;
	}

	public List<User> findUsersByEmail(List<String> emails) {
		List<User> users = new ArrayList<User>();

		for (String email : emails) {
			User user = userRepository.findByEmail(email);
			users.add(user);
		}
		return users;
	}

}
