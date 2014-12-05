package ca.ulaval.glo4003.appemployee.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.exceptions.ProjectExistsException;
import ca.ulaval.glo4003.appemployee.domain.project.Project;
import ca.ulaval.glo4003.appemployee.domain.project.ProjectProcessor;
import ca.ulaval.glo4003.appemployee.domain.repository.ProjectRepository;
import ca.ulaval.glo4003.appemployee.web.converters.ProjectConverter;
import ca.ulaval.glo4003.appemployee.web.converters.TaskConverter;
import ca.ulaval.glo4003.appemployee.web.converters.UserConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ProjectViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TaskViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.UserViewModel;

@Service
public class ProjectService {

	private TaskConverter taskConverter;
	private ProjectConverter projectConverter;
	private UserConverter userConverter;
	private ProjectRepository projectRepository;
	private ProjectProcessor projectProcessor;

	@Autowired
	public ProjectService(ProjectConverter projectConverter, TaskConverter taskConverter, UserConverter userConverter,
			ProjectRepository projectRepository, ProjectProcessor projectProcessor) {
		this.taskConverter = taskConverter;
		this.userConverter = userConverter;
		this.projectConverter = projectConverter;
		this.projectRepository = projectRepository;
		this.projectProcessor = projectProcessor;
	}

	public Collection<ProjectViewModel> retrieveAllProjects() {
		return projectConverter.convert(projectRepository.findAll());
	}

	public void editProject(String projectId, ProjectViewModel projectViewModel) throws Exception {
		projectProcessor.editProject(projectId, projectViewModel.getName(), projectViewModel.getSelectedUserEmail());
	}

	public void createProject(ProjectViewModel projectViewModel) throws Exception {

		if (projectProcessor.checkIfProjectWithSameNameExists(projectViewModel.getName())) {
			throw new ProjectExistsException(
					String.format("The project %s already exists!", projectViewModel.getName()));
		}

		Project newProject = new Project(projectViewModel.getName(), projectViewModel.getTaskIds(),
				projectViewModel.getUserIds(), projectViewModel.getExpenseIds());
		projectRepository.store(newProject);

	}

	public ProjectViewModel retrieveProjectViewModelForExistingProject(String projectNumber) {
		ProjectViewModel projectViewModel = projectConverter.convert(projectRepository.findById(projectNumber));
		projectViewModel.setAvailableUsers(projectProcessor.evaluateAvailableEmployeeEmailsByProject(projectNumber));
		return projectViewModel;
	}

	public Collection<TaskViewModel> retrieveTasksByProject(String projectNumber) {
		return taskConverter.convert(projectProcessor.retrieveAllTasksByProjectId(projectNumber));
	}

	public Collection<UserViewModel> retieveEmployeesByProject(String projectNumber) {
		return userConverter.convert(projectProcessor.retrieveAllEmployeesByProjectId(projectNumber));
	}

	public void addNewTaskToProject(String projectNumber, TaskViewModel taskViewModel) throws Exception {
		projectProcessor.addTaskToProject(projectNumber, taskViewModel.getName(),
				taskViewModel.getMultiplicativeFactor());
	}
}
