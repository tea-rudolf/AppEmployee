package ca.ulaval.glo4003.appemployee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.Project;
import ca.ulaval.glo4003.appemployee.domain.Task;
import ca.ulaval.glo4003.appemployee.domain.dao.ProjectRepository;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ProjectViewModel;

@Service
public class ProjectService {
	
	private ProjectRepository projectRepository;
	
	@Autowired
	public ProjectService(ProjectRepository projectRepository) {
		this.projectRepository = projectRepository;
	}
	
	public Project getProjectByNumber(String number) {
		return projectRepository.findByNumber(number);
	}
	
	public List<Project> getAllProjects() {
		return projectRepository.findAll();
	}
	
	public void addProject(Project project) {
		projectRepository.persist(project);
	}
	
	public void editProject(String number, ProjectViewModel viewModel) {
		Project project = getProjectByNumber(number);
		project.setName(viewModel.getName());
		projectRepository.update(project);
	}
	
	public void addTask(String number, Task task) {
		Project project = getProjectByNumber(number);
		project.addTask(task);
		projectRepository.update(project);
	}
}
