package ca.ulaval.glo4003.appemployee.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BasicController {

	@RequestMapping(value = "/")
	public String home() {
		return "login"; // a changer pour une page home
	}

}
