package ca.ulaval.glo4003.appemployee.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.task.TaskProcessor;
import ca.ulaval.glo4003.appemployee.web.converters.TaskConverter;
import ca.ulaval.glo4003.appemployee.web.converters.UserConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TaskViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.UserViewModel;

@Service
public class TaskService {

	private TaskProcessor taskProcessor;
	private TaskConverter taskConverter;
	private UserConverter userConverter;

	@Autowired
	public TaskService(TaskProcessor taskProcessor, TaskConverter taskConverter, UserConverter userConverter) {
		this.taskProcessor = taskProcessor;
		this.taskConverter = taskConverter;
		this.userConverter = userConverter;
	}

	public TaskViewModel retrieveTaskViewModelForExistingTask(String taskNumber) {
		TaskViewModel taskViewModel = taskConverter.convert(taskProcessor.retrieveTaskById(taskNumber));
		taskViewModel.setAvailableUsers(taskProcessor.evaluateAvailableEmployeeEmailsByTask(taskNumber));
		return taskViewModel;
	}

	public List<UserViewModel> retrieveEmployeesByTask(String taskNumber) {
		return userConverter.convert(taskProcessor.retrieveAllEmployeesByTaskId(taskNumber));
	}
	
	public void editTask(String projectId, String taskId, TaskViewModel taskViewModel) throws Exception {
		taskProcessor.editTask(taskId, taskViewModel.getName(), taskViewModel.getSelectedUserEmail(), taskViewModel.getMultiplicativeFactor());
	}
}
