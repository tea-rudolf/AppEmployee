package ca.ulaval.glo4003.appemployee.web.converter;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.appemployee.domain.User;
import ca.ulaval.glo4003.appemployee.web.dto.UserDto;

@Component
public class UserConverter {

	public User convert(UserDto dto) {
		User user = new User(dto.username);
		user.setPassword(dto.password);
		return user;
	}

}
