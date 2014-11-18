package ca.ulaval.glo4003.appemployee.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.exceptions.UserNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.expense.Expense;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.repository.ExpenseRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.TaskRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.TimeEntryRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.TravelRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.UserRepository;
import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntry;
import ca.ulaval.glo4003.appemployee.domain.travel.Travel;
import ca.ulaval.glo4003.appemployee.domain.user.Role;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.persistence.RepositoryException;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TimeViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.UserViewModel;

@Service
public class UserService {

	private UserRepository userRepository;
	private TaskRepository taskRepository;
	private TimeEntryRepository timeEntryRepository;
	private ExpenseRepository expenseRepository;
	private TravelRepository travelRepository;

	@Autowired
	public UserService(UserRepository userRepository, TaskRepository taskRepository, ExpenseRepository expenseRepository,
			TimeEntryRepository timeEntryRepository, TravelRepository travelRepository) {
		this.userRepository = userRepository;
		this.taskRepository = taskRepository;
		this.timeEntryRepository = timeEntryRepository;
		this.expenseRepository = expenseRepository;
		this.travelRepository = travelRepository;
	}

	public User retrieveByEmail(String email) throws UserNotFoundException {
		User user = userRepository.findByEmail(email);

		if (user == null) {
			throw new UserNotFoundException("User not found with following email : " + email);
		}
		return user;
	}

	public List<User> retrieveUsersByEmail(List<String> emails) {
		return userRepository.findByEmails(emails);
	}

	public List<String> retrieveAllUserEmails() throws UserNotFoundException {
		List<String> usersEmail = new ArrayList<String>();
		Collection<User> users = userRepository.findAll();
		for (User user : users) {
			usersEmail.add(user.getEmail());
		}
		return usersEmail;
	}

	public TimeEntry getTimeEntry(String id) {
		return timeEntryRepository.findByUid(id);
	}

	public List<Task> getTasksForUserForAPayPeriod(PayPeriod payPeriod, String currentUserId) {

		List<Task> tasks = new ArrayList<Task>();

		for (String timeEntryId : payPeriod.getTimeEntryIds()) {
			TimeEntry entry = timeEntryRepository.findByUid(timeEntryId);
			if (entry != null && entry.getUserEmail().equals(currentUserId)) {
				tasks.add(taskRepository.findByUid(entry.getUid()));
			}
		}
		return tasks;
	}

	public ArrayList<TimeEntry> getTimeEntriesForUserForAPayPeriod(PayPeriod payPeriod, String userEmail) {

		ArrayList<TimeEntry> timeEntries = new ArrayList<TimeEntry>();

		for (String timeEntryId : payPeriod.getTimeEntryIds()) {
			TimeEntry entry = timeEntryRepository.findByUid(timeEntryId);
			if (entry != null && entry.getUserEmail().equals(userEmail)) {
				timeEntries.add(entry);
			}
		}
		return timeEntries;
	}

	public List<Expense> getExpensesForUserForAPayPeriod(PayPeriod payPeriod, String userEmail) {

		ArrayList<Expense> expenses = new ArrayList<Expense>();
		for (Expense expense : expenseRepository.findAll()) {
			if (expense.getUserEmail().equals(userEmail) && expense.getDate().isBefore(payPeriod.getEndDate().plusDays(1))
					&& expense.getDate().isAfter(payPeriod.getStartDate().minusDays(1))) {
				expenses.add(expense);
			}
		}

		return expenses;
	}

	public List<Travel> getTravelEntriesForUserForAPayPeriod(PayPeriod payPeriod, String userEmail) {
		ArrayList<Travel> travels = new ArrayList<Travel>();

		for (Travel travel : travelRepository.findAllTravelsByUser(userEmail)) {
			if (travel.getDate().isBefore(payPeriod.getEndDate().plusDays(1)) && travel.getDate().isAfter(payPeriod.getStartDate().minusDays(1))) {
				travels.add(travel);
			}
		}
		return travels;
	}

	public void updateTimeEntry(String projectNumber, TimeViewModel viewModel) {
		TimeEntry entry = timeEntryRepository.findByUid(projectNumber);
		entry.setBillableHours(viewModel.getHoursTimeEntry());
		entry.setComment(viewModel.getCommentTimeEntry());
		entry.setDate(new LocalDate(viewModel.getDateTimeEntry()));
		entry.setTaskUid(viewModel.getTaskIdTimeEntry());

		try {
			timeEntryRepository.store(entry);
		} catch (Exception e) {
			throw new RepositoryException(e.getMessage());
		}

	}

	public void updateEmployeeInformation(UserViewModel userViewModel) throws Exception {

		User employee = new User();
		employee.setEmail(userViewModel.getEmail());
		employee.setPassword(userViewModel.getPassword());
		employee.setWage(userViewModel.getWage());

		if (userViewModel.getRole().equals("EMPLOYEE")) {
			employee.setRole(Role.EMPLOYEE);
		} else if (userViewModel.getRole().equals("ENTERPRISE")) {
			employee.setRole(Role.ENTERPRISE);
		} else if (userViewModel.getRole().equals("SUPERVISOR")) {
			employee.setRole(Role.SUPERVISOR);
		}

		userRepository.store(employee);
	}

	public void updatePassword(String email, UserViewModel viewModel) {
		User user = userRepository.findByEmail(email);
		user.setPassword(viewModel.getPassword());

		try {
			userRepository.store(user);
		} catch (Exception e) {
			throw new RepositoryException(e.getMessage());
		}
	}

}
