package ca.ulaval.glo4003.appemployee.domain.task;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.appemployee.domain.exceptions.TaskNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.exceptions.TimeEntryNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.repository.TaskRepository;
import ca.ulaval.glo4003.appemployee.domain.time.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.time.TimeEntry;
import ca.ulaval.glo4003.appemployee.domain.time.TimeProcessor;

@Component
public class TaskProcessor {

	private TimeProcessor timeProcessor;
	private TaskRepository taskRepository;

	@Autowired
	public TaskProcessor(TimeProcessor timeProcessor, TaskRepository taskRepository) {
		this.timeProcessor = timeProcessor;
		this.taskRepository = taskRepository;
	}

	public Task retrieveTaskByUid(String taskUid) throws TaskNotFoundException {
		Task task = taskRepository.findByUid(taskUid);

		if (task == null) {
			throw new TaskNotFoundException("Task does not exist in repository");
		}
		return task;
	}

	// est caller ou?
	public List<Task> evaluateUserTasksForPayPeriod(PayPeriod payPeriod, String userEmail) throws TimeEntryNotFoundException, TaskNotFoundException {

		List<Task> tasks = new ArrayList<Task>();

		for (String timeEntryUid : payPeriod.getTimeEntryIds()) {
			TimeEntry entry = timeProcessor.retrieveTimeEntryByUid(timeEntryUid);
			if (entry.getUserEmail().equals(userEmail)) {
				tasks.add(retrieveTaskByUid(entry.getTaskUid()));
			}
		}
		return tasks;
	}

}
