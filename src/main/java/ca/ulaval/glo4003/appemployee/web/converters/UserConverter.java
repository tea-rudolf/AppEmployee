package ca.ulaval.glo4003.appemployee.web.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.web.viewmodels.UserViewModel;

@Component
public class UserConverter {

	public List<UserViewModel> convert(List<User> users) {
		List<UserViewModel> userViewModels = new ArrayList<UserViewModel>();
		if (users != null) {
			for (User user : users) {
				UserViewModel userViewModel = convert(user);
				userViewModels.add(userViewModel);
			}
		}
		return userViewModels;
	}

	public UserViewModel convert(User user) {
		return new UserViewModel(user.getEmail(), user.getPassword(), user.getWage(), user.getRole().toString());
	}

}
