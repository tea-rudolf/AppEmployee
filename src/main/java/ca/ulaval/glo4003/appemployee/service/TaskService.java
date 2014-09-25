package ca.ulaval.glo4003.appemployee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.Project;
import ca.ulaval.glo4003.appemployee.domain.Task;
import ca.ulaval.glo4003.appemployee.domain.dao.ProjectRepository;
import ca.ulaval.glo4003.appemployee.domain.dao.TaskRepository;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ProjectViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TaskViewModel;

@Service
public class TaskService {
	
	private TaskRepository taskRepository;
	
	@Autowired
	public TaskService(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}
	
	public Task getTaskByNumber(int number) {
		return taskRepository.findByNumber(number);
	}
	
	public List<Task> getAllTasks() {
		return taskRepository.findAll();
	}
	
	public void addTask(Task task) {
		taskRepository.persist(task);
	}
	
	public void editTask(int number, TaskViewModel viewModel) {
		Task task = getTaskByNumber(number);
		task.setName(viewModel.getName());
		taskRepository.update(task);
	}
}
