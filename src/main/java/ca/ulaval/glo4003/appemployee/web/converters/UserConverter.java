package ca.ulaval.glo4003.appemployee.web.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.web.viewmodels.UserViewModel;

@Component
public class UserConverter {

	public List<UserViewModel> convert(List<User> users) {
		System.out.println("a");
		List<UserViewModel> userViewModels = new ArrayList<UserViewModel>();
		System.out.println("b");
		for (User user : users) {
			UserViewModel userViewModel = convert(user);
			userViewModels.add(userViewModel);
		}
		System.out.println("c");
		return userViewModels;
	}

	public UserViewModel convert(User user) {
		System.out.println("-a");
		UserViewModel userViewModel = new UserViewModel();
		userViewModel.setEmail(user.getEmail());
		userViewModel.setPassword(user.getPassword());
		userViewModel.setWage(user.getWage());
		System.out.println("-b");
		userViewModel.setRole(user.getRole().toString());
		System.out.println("-c");
		return userViewModel;
	}

}
