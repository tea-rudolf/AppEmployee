package ca.ulaval.glo4003.appemployee.domain.project;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.appemployee.domain.repository.ProjectRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.UserRepository;
import ca.ulaval.glo4003.appemployee.domain.user.Role;
import ca.ulaval.glo4003.appemployee.domain.user.User;

@Component
public class ProjectProcessor {

	private ProjectRepository projectRepository;
	private UserRepository userRepository;

	@Autowired
	public ProjectProcessor(ProjectRepository projectRepository, UserRepository userRepository) {
		this.projectRepository = projectRepository;
		this.userRepository = userRepository;
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

	public void addProject(Project project) throws Exception {
		projectRepository.store(project);
		
	}
}
