package ca.ulaval.glo4003.appemployee.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import ca.ulaval.glo4003.appemployee.domain.exceptions.UserNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.services.UserService;
import ca.ulaval.glo4003.appemployee.web.converters.UserConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.UserViewModel;

@Controller
@RequestMapping(value = "/editProfile")
@SessionAttributes({ "email" })
public class UserController {

	static final String EMAIL_ATTRIBUTE = "email";
	static final String SIMPLE_REDIRECT = "redirect:/";
	static final String EDIT_PROFILE_REDIRECT = "redirect:/editProfile/";
	static final String EDIT_USER_NOT_FOUND_REDIRECT = "redirect:/editProfile/userNotFoundError";
	static final String USER_NOT_FOUND_LINK = "userNotFoundError";
	static final String EDIT_PROFILE_FORM = "editProfile";
	

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
			return SIMPLE_REDIRECT;
		}

		model.addAttribute("user", userConverter.convert(userService.retrieveByEmail(session.getAttribute(EMAIL_ATTRIBUTE).toString())));
		return EDIT_PROFILE_FORM;
	}

	@RequestMapping(value = "/editProfile", method = RequestMethod.GET)
	public String userModification(@PathVariable Model model, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return SIMPLE_REDIRECT;
		}

		User user = userService.retrieveByEmail(session.getAttribute(EMAIL_ATTRIBUTE).toString());
		model.addAttribute("user", userConverter.convert(user));
		return EDIT_PROFILE_FORM;
	}

	@RequestMapping(value = "/editProfile", method = RequestMethod.POST)
	public String updateUser(@PathVariable UserViewModel viewModel, HttpSession session) throws Exception {

		if (!viewModel.getEmail().equals("")) {
			try {
				userService.retrieveByEmail(viewModel.getEmail());
			} catch (UserNotFoundException userNotFound) {
				return EDIT_USER_NOT_FOUND_REDIRECT;
			}
		}

		userService.updatePassword(session.getAttribute(EMAIL_ATTRIBUTE).toString(), viewModel);
		return EDIT_PROFILE_REDIRECT;
	}

	@RequestMapping(value = "/userNotFoundError", method = RequestMethod.GET)
	public String getErrorNoTaskSelected(ModelMap model, HttpSession session) {
		return USER_NOT_FOUND_LINK;
	}

}
