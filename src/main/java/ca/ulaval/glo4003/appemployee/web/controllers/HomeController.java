package ca.ulaval.glo4003.appemployee.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.domain.user.UserRepository;
import ca.ulaval.glo4003.appemployee.web.viewmodels.LoginFormViewModel;

@Controller
@SessionAttributes({ "email" })
public class HomeController {

	private UserRepository userRepository;

	@Autowired
	public HomeController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@ModelAttribute("loginForm")
	public LoginFormViewModel defaultUser() {
		return new LoginFormViewModel();
	}

	@RequestMapping("/")
	public String displayLoginForm() {
		return "home";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(LoginFormViewModel form, ModelMap model) {
		if (userRepository.validateCredentials(form.getEmail(), form.getPassword())) {
			User user = userRepository.findByEmail(form.getEmail());
			model.addAttribute("email", form.getEmail());
			model.addAttribute("role", user.getRole());// ne rajoute pas
														// l'attribut
			return redirectHome();
		}
		model.addAttribute("alert", "Courriel et/ou mot de passe invalide");
		model.addAttribute("loginForm", form);

		return new ModelAndView("home");
	}

	private ModelAndView redirectHome() {
		RedirectView redirect = new RedirectView("/");
		redirect.setExposeModelAttributes(false);
		return new ModelAndView(redirect);
	}

	@RequestMapping(value = "/logout")
	public String logout(SessionStatus sessionStatus, ModelMap model) {
		sessionStatus.setComplete();
		model.clear();
		return "redirect:/";
	}
}
