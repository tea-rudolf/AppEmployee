package ca.ulaval.glo4003.appemployee.domain.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.appemployee.domain.exceptions.TaskNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.repository.TaskRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.UserRepository;
import ca.ulaval.glo4003.appemployee.domain.user.Role;
import ca.ulaval.glo4003.appemployee.domain.user.User;

@Component
public class TaskProcessor {

	private TaskRepository taskRepository;
	private UserRepository userRepository;

	@Autowired
	public TaskProcessor(TaskRepository taskRepository, UserRepository userRepository) {
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
	
	public List<User> retrieveAllEmployeesByTaskId(String taskNumber) {
		List<String> userEmails = taskRepository.findByUid(taskNumber).getAuthorizedUsers();
		return userRepository.findByEmails(userEmails);
	}

	public List<String> evaluateAvailableEmployeeEmailsByTask(String taskNumber) {
		Task task = taskRepository.findByUid(taskNumber);
		Collection<User> allUsers = userRepository.findAll();
		List<String> availableUserEmails = new ArrayList<String>();

		for (User user : allUsers) {
			if (!task.userIsAlreadyAssignedToTask(user.getEmail()) && !user.getRole().equals(Role.ENTERPRISE)) {
				availableUserEmails.add(user.getEmail());
			}
		}
		return availableUserEmails;
	}
	
	public void addEmployeeToEachTaskOfProject(List<String> taskIds, String newUserEmail) {
		List<Task> tasks = taskRepository.findByUids(taskIds);
		for (Task task : tasks) {
			if (!task.userIsAlreadyAssignedToTask(newUserEmail)) {
				task.assignUserToTask(newUserEmail);
			}
		}
	}

	public void editTask(String taskId, String taskName, String newUserEmail) throws Exception {
		Task task = taskRepository.findByUid(taskId);
		task.update(taskName, newUserEmail);
		taskRepository.store(task);	
	}

}
