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
import ca.ulaval.glo4003.appemployee.web.viewmodels.UserViewModel;

@Controller
@RequestMapping(value = "/editProfile")
@SessionAttributes({ "email" })
public class UserController {

	static final String EMAIL_ATTRIBUTE = "email";
	static final String EDIT_PROFILE_JSP = "editProfile";
	static final String REDIRECT = "redirect:/";
	static final String EDIT_PROFILE_ERROR_REDIRECT = "redirect:/editProfile/userNotFoundError";
	static final String EMPLOYEE_REDIRECT = "redirect:/employee";
	static final String USER_NOT_FOUND = "userNotFoundError";

	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String displayUserProfile(Model model, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return REDIRECT;
		}

		model.addAttribute("user", userService.retrieveViewModelForCurrentUser(session.getAttribute(EMAIL_ATTRIBUTE).toString()));
		return EDIT_PROFILE_JSP;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String updatePassword(@ModelAttribute("user") UserViewModel viewModel, HttpSession session) throws Exception {

		userService.updatePassword(session.getAttribute(EMAIL_ATTRIBUTE).toString(), viewModel);
		return EMPLOYEE_REDIRECT;
	}

}
