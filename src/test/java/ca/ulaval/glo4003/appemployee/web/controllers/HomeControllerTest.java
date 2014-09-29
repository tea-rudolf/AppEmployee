package ca.ulaval.glo4003.appemployee.web.controllers;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.support.SimpleSessionStatus;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.domain.user.UserRepository;
import ca.ulaval.glo4003.appemployee.web.viewmodels.LoginFormViewModel;

@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {

	private static final String VALID_PASSWORD = "password";

	private static final String VALID_EMAIL = "email@email.com";

	private static final String ROLE = "EMPLOYEE";

	@Mock
	private UserRepository repository;

	@Mock
	private User user;

	@InjectMocks
	private HomeController controller;

	private ModelMap model = new ModelMap();
	private SessionStatus status = new SimpleSessionStatus();

	@Before
	public void setUpAccounts() {
		given(repository.validateCredentials(VALID_EMAIL, VALID_PASSWORD)).willReturn(true);
		given(repository.findByEmail(VALID_EMAIL)).willReturn(user);
		given(user.getRole()).willReturn("EMPLOYEE");
	}

	@After
	public void clearModel() {
		model.clear();
	}

	@Test
	public void displayLoginFormReturnsLoginForm() {
		String response = controller.displayLoginForm();

		assertEquals("home", response);
	}

	@Test
	public void whenCannotLoginReturnsLoginForm() {
		ModelAndView response = controller.login(getInvalidForm(), model);

		assertEquals("home", response.getViewName());
	}

	@Test
	public void whenCannotLoginReturnsAlert() {
		controller.login(getInvalidForm(), model);

		assertEquals("Courriel et/ou mot de passe invalide", model.get("alert"));
	}

	@Test
	public void whenSuccessfulLoginAddsEmailsToSession() {
		controller.login(getValidForm(), model);

		assertEquals(VALID_EMAIL, model.get("email"));
	}

	@Test
	public void whenSuccessfulLoginAddsRoleToSession() {
		controller.login(getValidForm(), model);

		assertEquals(ROLE, model.get("role"));
	}

	@Test
	public void logoutClearsTheModelMap() {
		model.addAttribute("junk", "junk");
		controller.logout(status, model);

		assertTrue(model.isEmpty());
	}

	@Test
	public void logoutTerminatesSession() {
		controller.logout(status, model);

		assertTrue(status.isComplete());
	}

	private LoginFormViewModel getValidForm() {
		LoginFormViewModel form = new LoginFormViewModel();
		form.setEmail(VALID_EMAIL);
		form.setPassword(VALID_PASSWORD);
		return form;
	}

	private LoginFormViewModel getInvalidForm() {
		return new LoginFormViewModel();
	}
}
