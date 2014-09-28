package ca.ulaval.glo4003.appemployee.web.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

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
import org.springframework.web.servlet.view.RedirectView;

import ca.ulaval.glo4003.appemployee.domain.user.UserRepository;
import ca.ulaval.glo4003.appemployee.web.viewmodels.LoginFormViewModel;

@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {

	private static final String VALID_PASSWORD = "password";

	private static final String VALID_EMAIL = "email@email.com";

	@Mock
	private UserRepository repository;

	@InjectMocks
	private HomeController controller;

	private ModelMap model = new ModelMap();
	private SessionStatus status = new SimpleSessionStatus();

	@Before
	public void setUpAccounts() {
		given(repository.validateCredentials(VALID_EMAIL, VALID_PASSWORD)).willReturn(true);
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
	public void whenLoginSuccessfulLoginRedirectsToHomePage() {
		ModelAndView response = controller.login(getValidForm(), model);

		assertEquals("/", ((RedirectView) response.getView()).getUrl());
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
		form.email = VALID_EMAIL;
		form.password = VALID_PASSWORD;
		return form;
	}

	private LoginFormViewModel getInvalidForm() {
		return new LoginFormViewModel();
	}

}
