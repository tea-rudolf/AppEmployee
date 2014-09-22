package ca.ulaval.glo4003.appemployee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.Project;
import ca.ulaval.glo4003.appemployee.domain.dao.ProjectRepository;

@Service
public class ProjectService {
	
	private ProjectRepository projectRepository;
	
	@Autowired
	public ProjectService(ProjectRepository projectRepository) {
		this.projectRepository = projectRepository;
	}
	
	public List<Project> getAllProjects() {
		return projectRepository.findAll();
	}
	
	public void addProject(Project project) {
		projectRepository.persist(project);
	}
}
