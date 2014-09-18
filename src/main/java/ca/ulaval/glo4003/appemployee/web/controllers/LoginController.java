package ca.ulaval.glo4003.appemployee.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ca.ulaval.glo4003.appemployee.domain.User;
import ca.ulaval.glo4003.appemployee.domain.dao.UserRepository;
import ca.ulaval.glo4003.appemployee.web.converter.UserConverter;
import ca.ulaval.glo4003.appemployee.web.dto.UserDto;

@Controller
public class LoginController {
	
	private UserRepository repository;	
	private UserConverter converter;
	
	@Autowired
	public LoginController(UserRepository repository, UserConverter converter) {
		this.repository = repository;
		this.converter = converter;
	}
	
	@RequestMapping("/")
	public String index() {
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute("entry", new UserDto());
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(UserDto dto) {
		User user = converter.convert(dto);
		repository.fetch(user);
		return "employee";
	}

	@RequestMapping(value = "/employee", method = RequestMethod.GET)
	public void employee(Model model) {

	}
}
