package ca.ulaval.glo4003.appemployee.web.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ca.ulaval.glo4003.appemployee.domain.User;
import ca.ulaval.glo4003.appemployee.domain.UserNotFound;
import ca.ulaval.glo4003.appemployee.domain.UserRepository;
import ca.ulaval.glo4003.appemployee.service.UserService;
import ca.ulaval.glo4003.appemployee.web.dto.UserCredentialsDto;

@Controller
public class LoginController {

	private UserService service;
	private UserRepository userRepo;

	@Autowired
	public LoginController(UserService service) {
		this.service = service;
	}

	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("entry", new UserCredentialsDto());
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute("entry", new UserCredentialsDto());
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(UserCredentialsDto userCredentialsDto, Principal principal, HttpServletRequest request) throws UserNotFound {
		
		User user = userRepo.findByUsername(userCredentialsDto.username);	
		request.getSession().setAttribute("user", user);
		
		return "redirect:/employee";
	}

	@RequestMapping(value = "/employee", method = RequestMethod.GET)
	public String employee(Model model) {
		return "employee";
	}
}
