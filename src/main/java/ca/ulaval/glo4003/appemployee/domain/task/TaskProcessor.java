package ca.ulaval.glo4003.appemployee.domain.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.appemployee.domain.exceptions.TaskNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.exceptions.TimeEntryNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.repository.TaskRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.UserRepository;
import ca.ulaval.glo4003.appemployee.domain.time.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.time.TimeEntry;
import ca.ulaval.glo4003.appemployee.domain.time.TimeProcessor;
import ca.ulaval.glo4003.appemployee.domain.user.Role;
import ca.ulaval.glo4003.appemployee.domain.user.User;

@Component
public class TaskProcessor {

	private TimeProcessor timeProcessor;
	private TaskRepository taskRepository;
	private UserRepository userRepository;

	@Autowired
	public TaskProcessor(TimeProcessor timeProcessor, TaskRepository taskRepository, UserRepository userRepository) {
		this.timeProcessor = timeProcessor;
		this.taskRepository = taskRepository;
		this.userRepository = userRepository;
	}

	public Task retrieveTaskByUid(String taskUid) throws TaskNotFoundException {
		Task task = taskRepository.findByUid(taskUid);

		if (task == null) {
			throw new TaskNotFoundException("Task does not exist in repository");
		}
		return task;
	}

	// est caller ou?
	public List<Task> evaluateUserTasksForAPayPeriod(PayPeriod payPeriod, String userEmail) throws TimeEntryNotFoundException, TaskNotFoundException {

		List<Task> tasks = new ArrayList<Task>();

		for (String timeEntryUid : payPeriod.getTimeEntryIds()) {
			TimeEntry entry = timeProcessor.retrieveTimeEntryByUid(timeEntryUid);
			if (entry.getUserEmail().equals(userEmail)) {
				tasks.add(retrieveTaskByUid(entry.getTaskUid()));
			}
		}
		return tasks;
	}

	public List<User> retrieveAllEmployeesByTaskId(String taskNumber) {
		List<String> userEmails = taskRepository.findByUid(taskNumber).getAuthorizedUsers();
		return userRepository.findByEmails(userEmails);
	}

	public List<String> evaluateAvailableEmployeeEmailsByTask(String taskNumber) {
		Task task = taskRepository.findByUid(taskNumber);
		Collection<User> allUsers = userRepository.findAll();
		List<String> availableUserEmails = new ArrayList<String>();

		for (User user : allUsers) {
			if (!task.userIsAssignedToTask(user.getEmail()) && !user.getRole().equals(Role.ENTERPRISE)) {
				availableUserEmails.add(user.getEmail());
			}
		}
		return availableUserEmails;
	}

}
