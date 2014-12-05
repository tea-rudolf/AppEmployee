package ca.ulaval.glo4003.appemployee.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.repository.TaskRepository;
import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.task.TaskProcessor;
import ca.ulaval.glo4003.appemployee.web.converters.TaskConverter;
import ca.ulaval.glo4003.appemployee.web.converters.UserConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TaskViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.UserViewModel;

@Service
public class TaskService {

	private TaskProcessor taskProcessor;
	private TaskRepository taskRepository;
	
	private TaskConverter taskConverter;
	private UserConverter userConverter;

	@Autowired
	public TaskService(TaskProcessor taskProcessor, TaskRepository taskRepositry, TaskConverter taskConverter, UserConverter userConverter) {
		this.taskProcessor = taskProcessor;
		this.taskRepository = taskRepositry;
		this.taskConverter = taskConverter;
		this.userConverter = userConverter;
	}

	public TaskViewModel retrieveTaskViewModelForExistingTask(String taskNumber) {
		TaskViewModel taskViewModel = taskConverter.convert(taskRepository.findByUid(taskNumber));
		taskViewModel.setAvailableUsers(taskProcessor.evaluateAvailableEmployeeEmailsByTask(taskNumber));
		return taskViewModel;
	}

	public List<UserViewModel> retrieveEmployeesByTask(String taskNumber) {
		return userConverter.convert(taskProcessor.retrieveAllEmployeesByTaskId(taskNumber));
	}
	
	public void editTask(String projectId, String taskId, TaskViewModel taskViewModel) throws Exception {
		taskProcessor.editTask(taskId, taskViewModel.getName(), taskViewModel.getSelectedUserEmail(), taskViewModel.getMultiplicativeFactor());
	}

	public String retrieveTaskName(String taskUid) {
		return taskRepository.findByUid(taskUid).getName();
	}

	public List<Task> retrieveAllTasksByUserId(String userEmail) {
		List<Task> tasks = taskRepository.findAll();
		List<Task> assignedToCurrentUserTasks = new ArrayList<Task>();
		for (Task task: tasks) {
			if (task.userIsAlreadyAssignedToTask(userEmail)) {
				assignedToCurrentUserTasks.add(task);
			}
		}
		return assignedToCurrentUserTasks;
	}
}
