package ca.ulaval.glo4003.appemployee;

import static org.junit.Assert.*;

import org.junit.Test;

import ca.ulaval.glo4003.appemployee.web.controllers.LoginController;

public class LoginControllerTest {
	
	@Test
	public void rendersIndex() {
		assertEquals("index", new LoginController().index());
	}

}
