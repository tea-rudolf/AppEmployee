package ca.ulaval.glo4003.appemployee.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import ca.ulaval.glo4003.appemployee.services.UserService;
import ca.ulaval.glo4003.appemployee.web.viewmodels.MessageViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.UserViewModel;

@Controller
@RequestMapping(value = "/editProfile")
@SessionAttributes({ "email" })
public class UserController {

	static final String EMAIL_ATTRIBUTE = "email";

	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String showUserProfileForm(Model model, HttpSession session) {
		model.addAttribute("user", userService.retrieveUserViewModel(session.getAttribute(EMAIL_ATTRIBUTE).toString()));
		return "editProfile";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String updatePassword(@ModelAttribute("user") UserViewModel viewModel, Model model) throws Exception {
		try {
			userService.editUser(viewModel);
		} catch (Exception e) {
			model.addAttribute("message", new MessageViewModel(e.getClass().getSimpleName(), e.getMessage()));	
		}
		return "redirect:/";
	}

}
