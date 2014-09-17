package ca.ulaval.glo4003.appemployee.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
	
	@RequestMapping("/")
	public String index() {
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(Model model) {
		// TODO Auto-generated method stub
		return "employee";
	}
	
	@RequestMapping(value = "/employee", method = RequestMethod.GET)
	public void employee(Model model) {

	}

}
