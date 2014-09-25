package ca.ulaval.glo4003.appemployee;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ca.ulaval.glo4003.appemployee.web.controllers.LoginController;

public class LoginControllerTest {

	@Test
	public void rendersIndex() {
		assertEquals("login", new LoginController().index());
	}

}
