package ca.ulaval.glo4003.appemployee.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/employee")
public class EmployeeBoardController {

	@RequestMapping(method = RequestMethod.GET)
	public String get() {
		return "employee";
	}
}
