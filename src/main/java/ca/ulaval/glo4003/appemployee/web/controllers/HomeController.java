package ca.ulaval.glo4003.appemployee.web.controllers;

import javax.servlet.http.HttpServletRequest;
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

	@Autowired
	public HomeController(UserService userService) {
		this.userService = userService;
	}

	@ModelAttribute("loginForm")
	public LoginFormViewModel defaultUser() {
		return new LoginFormViewModel();
	}

	@RequestMapping("/")
	public String showLoginForm() {
		return "home";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(LoginFormViewModel form, ModelMap model, HttpSession session, HttpServletRequest request) {
		if (userService.validateCredentials(form.getEmail(), form.getPassword())) {
			model.addAttribute("email", form.getEmail());
			model.addAttribute("role", userService.retrieveUserRole(form.getEmail()));
			session.setMaxInactiveInterval(SESSION_IDLE_TRESHOLD_IN_SECONDS);
			request.getSession().setAttribute("LOGGEDIN_USER", form);
			return new ModelAndView("home", model);
		} else {
			model.addAttribute("alert", "Invalid username and/or password.");
			model.addAttribute("loginForm", form);
			return new ModelAndView("home");
		}
	}

	@RequestMapping(value = "**/logout")
	public String logout(SessionStatus sessionStatus, ModelMap model) {
		sessionStatus.setComplete();
		model.clear();
		return "redirect:/";
	}
}
