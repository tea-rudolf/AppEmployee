package ca.ulaval.glo4003.appemployee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.User;
import ca.ulaval.glo4003.appemployee.domain.UserNotFound;
import ca.ulaval.glo4003.appemployee.domain.UserRepository;
import ca.ulaval.glo4003.appemployee.domain.WrongPassword;
import ca.ulaval.glo4003.appemployee.web.converter.UserConverter;
import ca.ulaval.glo4003.appemployee.web.dto.UserCredentialsDto;
import ca.ulaval.glo4003.appemployee.web.dto.UserDto;

@Service
public class UserService {

	private UserRepository userRepository;
	private UserConverter userConverter;

	@Autowired
	public UserService(UserRepository userRepository, UserConverter userConverter) {
		this.userRepository = userRepository;
		this.userConverter = userConverter;
	}

	public void login(UserCredentialsDto userCredentialsDto) throws UserNotFound, WrongPassword {
		User user = verifyCredentials(userCredentialsDto);
		
		
	}

	private User verifyCredentials(UserCredentialsDto userCredentialsDto)
			throws UserNotFound, WrongPassword {
		User user = userRepository.findByUsername(userCredentialsDto.username);
		user.verifyPassword(userCredentialsDto.password);
		
		return user;
	}
}
