package ca.ulaval.glo4003.appemployee;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.support.BindingAwareModelMap;

import ca.ulaval.glo4003.appemployee.domain.User;
import ca.ulaval.glo4003.appemployee.domain.dao.UserRepository;
import ca.ulaval.glo4003.appemployee.web.controllers.LoginController;
import ca.ulaval.glo4003.appemployee.web.converter.UserConverter;
import ca.ulaval.glo4003.appemployee.web.dto.UserDto;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {
	
	@Mock
	public UserRepository repository;
	
	@Mock
	public UserConverter converter;
	
	@InjectMocks
	public LoginController controller;
	
	private BindingAwareModelMap model;
	
	@Before
	public void setUp() {
		model = new BindingAwareModelMap();
	}
	
	@Test
	public void rendersIndex() {
		assertEquals("login", new LoginController(repository, converter).index());
	}
	
	@Test
	public void loginPostFetchUserFromRepo() {
		UserDto dto = new UserDto();
		User user = addToConverter(dto);
		
		controller.login(dto);
		
		verify(repository).fetch(user);
	}
	
	private User addToConverter(UserDto dto) {
		User user = mock(User.class);
		given(converter.convert(dto)).willReturn(user);
		return user;
	}
}
