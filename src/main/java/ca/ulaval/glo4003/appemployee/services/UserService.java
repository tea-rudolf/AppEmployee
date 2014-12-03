package ca.ulaval.glo4003.appemployee.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.exceptions.UserNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.user.Role;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.domain.user.UserProcessor;
import ca.ulaval.glo4003.appemployee.web.converters.UserConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.UserViewModel;

@Service
public class UserService {

	private UserProcessor userProcessor;
	private UserConverter userConverter;

	@Autowired
	public UserService(UserProcessor userProcessor, UserConverter userConverter) {
		this.userProcessor = userProcessor;
		this.userConverter = userConverter;
	}

	public User retrieveUserByEmail(String email) throws UserNotFoundException {
		return userProcessor.retrieveUserByEmail(email);
	}

	public List<User> retrieveUsersByEmail(List<String> emails) {
		List<User> users = new ArrayList<User>();

		for (String email : emails) {
			users.add(retrieveUserByEmail(email));
		}
		return users;
	}

	public List<String> evaluateAllUserEmails() throws UserNotFoundException {
		return userProcessor.evaluateAllUserEmails();
	}

	public void editUser(UserViewModel userViewModel) throws Exception {
		userProcessor.updateUser(userViewModel.getEmail(), userViewModel.getPassword(), Role.valueOf(userViewModel.getRole()), userViewModel.getWage());
	}

	public boolean isUserValid(String userEmail, String password) {
		return userProcessor.validateUserCredentials(userEmail, password);
	}

	public String retrieveUserRole(String userEmail) {
		return userProcessor.retrieveUserRole(userEmail).toString();
	}

	public UserViewModel retrieveUserViewModel(String email) {
		User user = userProcessor.retrieveUserByEmail(email);
		return userConverter.convert(user);
	}

}
