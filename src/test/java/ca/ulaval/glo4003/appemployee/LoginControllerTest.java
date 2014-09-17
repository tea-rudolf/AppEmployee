package ca.ulaval.glo4003.appemployee;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.support.BindingAwareModelMap;

import ca.ulaval.glo4003.appemployee.domain.dao.UserRepository;
import ca.ulaval.glo4003.appemployee.web.controllers.LoginController;
import ca.ulaval.glo4003.appemployee.web.converter.UserConverter;

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
		assertEquals("login", new LoginController().index());
	}
	
	@Test
	public void loginReturnsUserView() {
		String view = controller.login(model);
		
		assertEquals("redirect:/employee", view);
		
	}
}
