package ca.ulaval.glo4003.appemployee.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ca.ulaval.glo4003.appemployee.domain.UserNotFound;
import ca.ulaval.glo4003.appemployee.domain.WrongPassword;
import ca.ulaval.glo4003.appemployee.service.UserService;
import ca.ulaval.glo4003.appemployee.web.dto.LoginEntryDto;

@Controller
public class LoginController {

	private UserService service;

	@Autowired
	public LoginController(UserService service) {
		this.service = service;
	}

	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("entry", new LoginEntryDto());
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute("entry", new LoginEntryDto());
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(LoginEntryDto loginEntryDto, BindingResult result) {

		try {
			service.login(loginEntryDto);

		} catch (UserNotFound ex) {
			ex.printStackTrace();
			result.addError(new FieldError("login", "username", "User does not exist"));

		} catch (WrongPassword ex) {
			ex.printStackTrace();
			result.addError(new FieldError("login", "password", "Incorrect password"));
		}

		if (result.hasErrors()) {
			return "redirect:/";
		}

		return "redirect:/employee";
	}

	@RequestMapping(value = "/employee", method = RequestMethod.GET)
	public String employee(Model model) {
		return "employee";
	}
}
