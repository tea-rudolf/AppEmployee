package ca.ulaval.glo4003.appemployee.web.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.web.viewmodels.UserViewModel;

@Component
public class UserConverter {

	public User convert(UserViewModel userViewModel) {
		User user = new User();
		user.setEmail(userViewModel.getEmail());
		user.setPassword(userViewModel.getPassword());
		user.setWage(userViewModel.getWage());
		user.setRole(userViewModel.getRole());

		return user;
	}

	public List<UserViewModel> convert(List<User> users) {

		List<UserViewModel> userViewModels = new ArrayList<UserViewModel>();

		for (User user : users) {
			UserViewModel userViewModel = convert(user);
			userViewModels.add(userViewModel);
		}
		return userViewModels;
	}

	public UserViewModel convert(User user) {
		UserViewModel userViewModel = new UserViewModel();
		userViewModel.setEmail(user.getEmail());
		userViewModel.setPassword(user.getPassword());
		userViewModel.setWage(user.getWage());
		userViewModel.setRole(user.getRole());

		return userViewModel;
	}

}