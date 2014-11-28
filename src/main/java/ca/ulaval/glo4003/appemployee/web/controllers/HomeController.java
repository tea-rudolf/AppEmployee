package ca.ulaval.glo4003.appemployee.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.appemployee.services.UserService;
import ca.ulaval.glo4003.appemployee.web.viewmodels.LoginFormViewModel;

@Controller
@SessionAttributes({ "email", "role" })
public class HomeController {

	private UserService userService;
	static final Integer SESSION_IDLE_TRESHOLD_IN_SECONDS = 462;
	static final String EMAIL_ATTRIBUTE = "email";
	static final String ROLE_ATTRIBUTE = "role";
	static final String HOME_VIEW = "home";
	static final String LOGIN_FORM_ATTRIBUTE = "loginForm";
	static final String SIMPLE_REDIRECT = "redirect:/";

	@Autowired
	public HomeController(UserService userService) {
		this.userService = userService;
	}

	@ModelAttribute("loginForm")
	public LoginFormViewModel defaultUser() {
		return new LoginFormViewModel();
	}

	@RequestMapping("/")
	public String displayLoginForm() {
		return HOME_VIEW;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(LoginFormViewModel form, ModelMap model, HttpSession session) {

		if (userService.isUserValid(form.getEmail(), form.getPassword())) {
			model.addAttribute(EMAIL_ATTRIBUTE, form.getEmail());
			model.addAttribute(ROLE_ATTRIBUTE, userService.retrieveUserRole(form.getEmail()));
			session.setMaxInactiveInterval(SESSION_IDLE_TRESHOLD_IN_SECONDS);
			return new ModelAndView(HOME_VIEW, model);
		} else {
			model.addAttribute("alert", "Invalid username and/or password.");
			model.addAttribute(LOGIN_FORM_ATTRIBUTE, form);
			return new ModelAndView(HOME_VIEW);
		}
	}

	@RequestMapping(value = "**/logout")
	public String logout(SessionStatus sessionStatus, ModelMap model) {
		sessionStatus.setComplete();
		model.clear();
		return SIMPLE_REDIRECT;
	}
}
