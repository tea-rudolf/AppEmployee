package ca.ulaval.glo4003.appemployee.UT;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import ca.ulaval.glo4003.appemployee.web.controllers.LoginController;
import ca.ulaval.glo4003.appemployee.web.dto.UserDto;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {


	@Mock
	private BindingResult result;

	@Mock
	private Model model;

	@Mock
	private UserDto userCredentialsDto;

	@InjectMocks
	public LoginController controller;

	//@Test
//	public void rendersIndex() {
//		assertEquals("login", new LoginController().index(model));
//	}

	//@Test
	public void loginPostCallsUserService() throws Exception {
//		controller.login(userCredentialsDto, result);
//
//		verify(service).login(userCredentialsDto);
	}

	@Test
	public void givenValidCredentialsWhenPostThenReturnUserView() {
//		String viewPage = controller.login(userCredentialsDto, result);
//		
//		assertEquals("redirect:/employee", viewPage);
	}
	
	@Test
	public void givenInvalidCredentialsWhenPostThenRedirectIndexPage() {
//		serviceHasInvalidCredentials();
//		
//		controller.login(userCredentialsDto, result);
//		
//		assertEquals(true, result.hasErrors());
	}
	
	private void serviceHasInvalidCredentials() {
//		userCredentialsDto.username = "wrongUsername";
//		userCredentialsDto.password = "wrongPassword";
//		given(result.hasErrors()).willReturn(true);
	}
}
