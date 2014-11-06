package ca.ulaval.glo4003.appemployee.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.services.DepartmentService;
import ca.ulaval.glo4003.appemployee.web.viewmodels.UserViewModel;

@Controller
@RequestMapping(value = "/department")
@SessionAttributes({ "email" })
public class DepartmentController {

	static final String EMAIL_ATTRIBUTE = "email";
	private DepartmentService departmentService;

	@Autowired
	public DepartmentController(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@RequestMapping(value = "/createEmployee", method = RequestMethod.GET)
	public String createEmployeeAccount(Model model, UserViewModel userViewModel, HttpSession session) {
		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return "redirect:/";
		}

		model.addAttribute("user", userViewModel);
		return "createEmployee";
	}

	@RequestMapping(value = "/createEmployee", method = RequestMethod.POST)
	public String createEmployeeAccount(@PathVariable String currentUserId, @PathVariable String departmentName, Model model, UserViewModel userViewModel) throws Exception {
		 departmentService.createUser(currentUserId, departmentName, userViewModel);
		 departmentService.assignUserToDepartment(userViewModel, currentUserId, departmentName);
		return "redirect:/createEmployee";
	}

}
