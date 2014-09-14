package ca.ulaval.glo4003.appemployee.web.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController 
{
	@RequestMapping("/")
	public String index() {
		return "index";
	}
}
