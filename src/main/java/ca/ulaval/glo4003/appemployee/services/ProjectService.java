package ca.ulaval.glo4003.appemployee.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.project.Project;
import ca.ulaval.glo4003.appemployee.domain.project.ProjectRepository;
import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.task.TaskRepository;
import ca.ulaval.glo4003.appemployee.persistence.RepositoryException;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ProjectViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TaskViewModel;

@Service
public class ProjectService {

	private ProjectRepository projectRepository;
	private TaskRepository taskRepository;

	@Autowired
	public ProjectService(ProjectRepository projectRepository, TaskRepository taskRepository) {
		this.projectRepository = projectRepository;
		this.taskRepository = taskRepository;
	}

	public Collection<Project> getAllProjects() {
		return projectRepository.findAll();
	}

	public void addProject(Project project) {
		try {
			projectRepository.store(project);
		} catch (Exception e) {
			throw new RepositoryException(e.getMessage());
		}
	}

	public void updateProject(String projectId, ProjectViewModel viewModel) {
		Project project = projectRepository.findById(projectId);
		project.setName(viewModel.getName());
		try {
			projectRepository.store(project);
		} catch (Exception e) {
			throw new RepositoryException(e.getMessage());
		}
	}

	public void addTaskToProject(String projectId, String taskId) {
		Project project = projectRepository.findById(projectId);
		project.addTaskuId(taskId);
		try {
			projectRepository.store(project);
		} catch (Exception e) {
			throw new RepositoryException(e.getMessage());
		}
	}

	public void addTask(Task task) {
		try {
			taskRepository.store(task);
		} catch (Exception e) {
			throw new RepositoryException(e.getMessage());
		}
	}

	public void updateTask(String projectId, String taskId, TaskViewModel viewModel) {
		Task task = taskRepository.findByUid(taskId);
		task.setName(viewModel.getName());
		try {
			taskRepository.store(task);
		} catch (Exception e) {
			throw new RepositoryException(e.getMessage());
		}
	}

	public Task getTaskById(String taskId) {
		return taskRepository.findByUid(taskId);
	}

	public Project getProjectById(String projectId) {
		return projectRepository.findById(projectId);
	}

	public List<Task> getAllTasksByProjectId(String projectId) {
		Project project = projectRepository.findById(projectId);
		List<String> projectTasksId = project.getTaskuIds();
		List<Task> tasks = new ArrayList<Task>();
		for (String taskId : projectTasksId) {

			Task task = taskRepository.findByUid(taskId);

			tasks.add(task);
		}
		return tasks;
	}
}
