package ca.ulaval.glo4003.appemployee.domain.project;

import java.util.ArrayList;
import java.util.Collection;

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
	public ProjectProcessor(ProjectRepository projectRepository,
			UserRepository userRepository) {
		this.projectRepository = projectRepository;
		this.userRepository = userRepository;
	}

	public Collection<User> evaluateAvailableEmployeesByProject(
			String projectUid) {
		Project project = projectRepository.findById(projectUid);
		Collection<User> allUsers = userRepository.findAll();
		Collection<User> availableUsers = new ArrayList<User>();

		for (User user : allUsers) {
			if (!project.userIsAssignedToProject(user.getEmail())
					&& !user.getRole().equals(Role.ENTERPRISE)) {
				availableUsers.add(user);
			}
		}
		return availableUsers;
	}
}
