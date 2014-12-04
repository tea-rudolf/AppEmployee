package ca.ulaval.glo4003.appemployee.domain.project;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.appemployee.domain.repository.ProjectRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.TaskRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.UserRepository;
import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.task.TaskProcessor;
import ca.ulaval.glo4003.appemployee.domain.user.Role;
import ca.ulaval.glo4003.appemployee.domain.user.User;

@Component
public class ProjectProcessor {

	private ProjectRepository projectRepository;
	private UserRepository userRepository;
	private TaskRepository taskRepository;
	private TaskProcessor taskProcessor;

	@Autowired
	public ProjectProcessor(ProjectRepository projectRepository, UserRepository userRepository, TaskRepository taskRepository, TaskProcessor taskProcessor) {
		this.projectRepository = projectRepository;
		this.userRepository = userRepository;
		this.taskRepository = taskRepository;
		this.taskProcessor = taskProcessor;
	}

	public List<String> evaluateAvailableEmployeeEmailsByProject(String projectUid) {
		Project project = projectRepository.findById(projectUid);
		Collection<User> allUsers = userRepository.findAll();
		List<String> availableUsers = new ArrayList<String>();

		for (User user : allUsers) {
			if (!project.userIsAssignedToProject(user.getEmail()) && !user.getRole().equals(Role.ENTERPRISE)) {
				availableUsers.add(user.getEmail());
			}
		}
		return availableUsers;
	}

	public void editProject(String projectId, String projectName, String selectedUserEmail) throws Exception {
		Project project = projectRepository.findById(projectId);
		project.update(projectName, selectedUserEmail);
		taskProcessor.addEmployeeToEachTaskOfProject(project.getTaskUids(), selectedUserEmail);
		projectRepository.store(project);
	}
	
	public List<User> retrieveAllEmployeesByProjectId(String projectId) {
		Project project = projectRepository.findById(projectId);
		List<User> employees = new ArrayList<User>();

		for (String employeeEmail : project.getEmployeeUids()) {
			if (!employeeEmail.isEmpty()) {
				User employee = userRepository.findByEmail(employeeEmail);
				employees.add(employee);
			}
		}
		return employees;
	}
	
	public List<Task> retrieveAllTasksByProjectId(String projectId) {
		Project project = projectRepository.findById(projectId);
		List<Task> tasks = new ArrayList<Task>();
		
		for (String taskId : project.getTaskUids()) {
			Task task = taskRepository.findByUid(taskId);
			tasks.add(task);
		}
		return tasks;
	}
	
	public void addTaskToProject(String projectNumber, String taskName) throws Exception {
		Project project = projectRepository.findById(projectNumber);
		Task newTask = new Task(taskName, project.getEmployeeUids());
		
		project.addTaskUid(newTask.getUid());
		
		taskRepository.store(newTask);	
		projectRepository.store(project);
	}
}
