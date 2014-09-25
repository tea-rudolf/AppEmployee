package ca.ulaval.glo4003.appemployee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.project.Project;
import ca.ulaval.glo4003.appemployee.domain.project.ProjectRepository;
import ca.ulaval.glo4003.appemployee.domain.project.Task;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ProjectViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TaskViewModel;

@Service
public class ProjectService {
	
	private ProjectRepository projectRepository;
	
	@Autowired
	public ProjectService(ProjectRepository projectRepository) {
		this.projectRepository = projectRepository;
	}
	
	public Project getProjectByNumber(String number) {
		return projectRepository.getByNumber(number);
	}
	
	public List<Project> getAllProjects() {
		return projectRepository.getAll();
	}
	
	public void addProject(Project project) {
		projectRepository.persist(project);
	}
	
	public void updateProject(String number, ProjectViewModel viewModel) {
		Project project = getProjectByNumber(number);
		project.setName(viewModel.getName());
		projectRepository.update(project);
	}
	
	public void addTask(String number, Task task) {
		Project project = getProjectByNumber(number);
		project.addTask(task);
		projectRepository.update(project);
	}
	
	public void updateTask(String projectNumber, String taskNumber, TaskViewModel viewModel) {
		Project project = getProjectByNumber(projectNumber);
		Task task = project.getTaskByNumber(taskNumber);
		task.setName(viewModel.getName());
		projectRepository.update(project);
	}
	
	public Task getTaskByNumber(String projectNumber, String taskNumber) {
		Project project = getProjectByNumber(projectNumber);
		return project.getTaskByNumber(taskNumber);
	}
}
