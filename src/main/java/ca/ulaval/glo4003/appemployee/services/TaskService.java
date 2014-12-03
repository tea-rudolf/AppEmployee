package ca.ulaval.glo4003.appemployee.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.task.TaskProcessor;

@Service
public class TaskService {

	private TaskProcessor taskProcessor;

	@Autowired
	public TaskService(TaskProcessor taskProcessor) {
		this.taskProcessor = taskProcessor;
	}

}
