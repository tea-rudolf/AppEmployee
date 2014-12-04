package ca.ulaval.glo4003.appemployee.services;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.repository.TaskRepository;
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

	public Collection<UserViewModel> retrieveEmployeesByTask(String taskNumber) {		
		return userConverter.convert(taskProcessor.retrieveAllEmployeesByTaskId(taskNumber));
	}

}
