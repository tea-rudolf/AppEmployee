package ca.ulaval.glo4003.appemployee.web.converter;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.appemployee.domain.User;
import ca.ulaval.glo4003.appemployee.web.dto.UserDto;

@Component
public class UserConverter {

	public UserDto convert(User user) {
		UserDto userDto = new UserDto();

		userDto.username = user.getUsername();
		userDto.password = user.getPassword();
		userDto.email = user.getEmail();
		userDto.isLoggedOn = Boolean.toString(user.isLoggedIn());

		return userDto;
	}

	public User convert(UserDto userDto) {
		User user = new User(userDto.username, userDto.password, userDto.email);

		return user;
	}

}
