package ca.ulaval.glo4003.appemployee.web.controllers;

import javax.servlet.http.HttpSession;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import ca.ulaval.glo4003.appemployee.domain.exceptions.UserNotFoundException;
import ca.ulaval.glo4003.appemployee.services.UserService;
import ca.ulaval.glo4003.appemployee.web.converters.UserConverter;
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
	private UserConverter userConverter;

	@Autowired
	public UserController(UserService userService, UserConverter userConverter) {
		this.userService = userService;
		this.userConverter = userConverter;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getUser(Model model, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return REDIRECT;
		}

		model.addAttribute("user", userConverter.convert(userService.retrieveByEmail(session.getAttribute(EMAIL_ATTRIBUTE).toString())));
		return EDIT_PROFILE_JSP;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String updatePassword(@ModelAttribute("user") UserViewModel viewModel, HttpSession session) throws Exception {

		if (!viewModel.getEmail().equals("")) {
			try {
				userService.retrieveByEmail(viewModel.getEmail());
			} catch (UserNotFoundException userNotFound) {
				return EDIT_PROFILE_ERROR_REDIRECT;
			}
		}

		userService.updatePassword(session.getAttribute(EMAIL_ATTRIBUTE).toString(), viewModel);
		return EMPLOYEE_REDIRECT;
	}

	@RequestMapping(value = "/userNotFoundError", method = RequestMethod.GET)
	public String getErrorUserNotFound(ModelMap model, HttpSession session) {
		return USER_NOT_FOUND;
	}

}
