package ca.ulaval.glo4003.appemployee.UT;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.BDDMockito.given;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.appemployee.domain.User;
import ca.ulaval.glo4003.appemployee.domain.UserNotFound;
import ca.ulaval.glo4003.appemployee.domain.UserRepository;
import ca.ulaval.glo4003.appemployee.domain.WrongPassword;
import ca.ulaval.glo4003.appemployee.service.UserService;
import ca.ulaval.glo4003.appemployee.web.converter.UserConverter;
import ca.ulaval.glo4003.appemployee.web.dto.UserCredentialsDto;
import ca.ulaval.glo4003.appemployee.web.dto.UserDto;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private UserConverter userConverter;
	
	@Mock
	private User user;
	
	@InjectMocks
	public UserService userService;
	
	@Before
	public void setUp() {
		userService = new UserService(userRepository, userConverter);
		
	}
	
	@Test
	public void givenValidCredentialsWhenLoginThenReturnUserDto() throws Exception {
/*		UserCredentialsDto userCredentialsDto = new UserCredentialsDto();
		setValidCredentials(userCredentialsDto);
		given(userRepository.findByUsername(userCredentialsDto.username)).willReturn(user);
		
		userService.login(userCredentialsDto);
		
		verify(userService).*/

		
	}

	private void setValidCredentials(UserCredentialsDto userCredentialsDto) {
		userCredentialsDto.username = "GracyMb";
		userCredentialsDto.password = "toto1";
		
	}


}

