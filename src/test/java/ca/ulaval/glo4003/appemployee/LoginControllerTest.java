package ca.ulaval.glo4003.appemployee;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import ca.ulaval.glo4003.appemployee.service.UserService;
import ca.ulaval.glo4003.appemployee.web.controllers.LoginController;
import ca.ulaval.glo4003.appemployee.web.dto.LoginEntryDto;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {

	@Mock
	public UserService service;

	@Mock
	private BindingResult result;

	@Mock
	private Model model;

	@Mock
	private LoginEntryDto loginEntryDto;

	@InjectMocks
	public LoginController controller;

	@Test
	public void rendersIndex() {
		assertEquals("login", new LoginController(service).index(model));
	}

	@Test
	public void loginPostCallsUserService() throws Exception {
		controller.login(loginEntryDto, result);

		verify(service).login(loginEntryDto);
	}

}
