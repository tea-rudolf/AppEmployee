package ca.ulaval.glo4003.appemployee.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping(value = "/employee")
public class EmployeeBoardController {

	@RequestMapping(method = RequestMethod.GET)
	public String get() {
		return "employee";
	}

	@RequestMapping(value = "/logout")
	public String logout(SessionStatus sessionStatus, ModelMap model) {
		sessionStatus.setComplete();
		model.clear();
		return "redirect:/";
	}
}
