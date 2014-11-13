package ca.ulaval.glo4003.appemployee.web.controllers;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import ca.ulaval.glo4003.appemployee.domain.department.Department;
import ca.ulaval.glo4003.appemployee.domain.exceptions.DepartmentNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.services.DepartmentService;
import ca.ulaval.glo4003.appemployee.services.UserService;
import ca.ulaval.glo4003.appemployee.web.converters.DepartmentConverter;
import ca.ulaval.glo4003.appemployee.web.converters.UserConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.MessageViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.UserViewModel;

@Controller
@RequestMapping(value = "/departments")
@SessionAttributes({ "email" })
public class DepartmentController {

	private static final String EMAIL_ATTRIBUTE = "email";
	private DepartmentService departmentService;
	private UserService userService;
	private UserConverter userConverter;
	private DepartmentConverter departmentConverter;

	@Autowired
	public DepartmentController(DepartmentService departmentService, UserService userService, UserConverter userConverter,
			DepartmentConverter departmentConverter) {
		this.departmentService = departmentService;
		this.userService = userService;
		this.userConverter = userConverter;
		this.departmentConverter = departmentConverter;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String showDepartmentsList(Model model, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return "redirect:/";
		}

		model.addAttribute("departments", departmentService.retrieveDepartmentsList());
		return "departmentsList";
	}

	@RequestMapping(value = "/{departmentName}/edit", method = RequestMethod.GET)
	public String showEmployeesList(@PathVariable String departmentName, Model model, HttpSession session) throws DepartmentNotFoundException {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return "redirect:/";
		}

		Department department = departmentService.retrieveDepartmentByName(departmentName);
		List<User> employees = departmentService.retrieveEmployeesList(departmentName);

		Collection<UserViewModel> employeesViewModel = userConverter.convert(employees);
		model.addAttribute("department", departmentConverter.convert(department));
		model.addAttribute("employees", employeesViewModel);

		return "editDepartment";
	}

	@RequestMapping(value = "/{departmentName}/employees/createEmployee", method = RequestMethod.GET)
	public String showCreateEmployeeAccountPage(@PathVariable String departmentName, Model model, UserViewModel userViewModel, HttpSession session) {
		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return "redirect:/";
		}

		model.addAttribute("departmentName", departmentName);
		model.addAttribute("user", userViewModel);
		return "createUser";
	}

	@RequestMapping(value = "/{departmentName}/employees/createEmployee", method = RequestMethod.POST)
	public String createEmployeeAccount(@PathVariable String departmentName, Model model, UserViewModel userViewModel, HttpSession session) {
		String supervisorId = session.getAttribute(EMAIL_ATTRIBUTE).toString();

		try {

			if (userViewModel.getRole().equals("NONE")) {
				model.addAttribute("message", new MessageViewModel("No Role selected", "No role was selected!"));
				return showCreateEmployeeAccountPage(departmentName, model, userViewModel, session);
			}

			departmentService.createUser(supervisorId, departmentName, userViewModel);
			departmentService.assignUserToDepartment(userViewModel, supervisorId, departmentName);

			return "redirect:/departments/{departmentName}/edit";
		} catch (Exception e) {
			model.addAttribute("message", new MessageViewModel(e.getClass().getSimpleName(), e.getMessage()));
			return showCreateEmployeeAccountPage(departmentName, model, userViewModel, session);
		}

	}

	@RequestMapping(value = "/{departmentName}/employees/{email}/edit", method = RequestMethod.GET)
	public String showUpdateEmployeeInfo(@PathVariable String departmentName, @PathVariable String email, Model model, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return "redirect:/";
		}

		User employee = userService.retrieveByEmail(email);
		UserViewModel userViewModel = userConverter.convert(employee);
		model.addAttribute("user", userViewModel);
		model.addAttribute("departmentName", departmentName);

		return "editEmployee";
	}

	@RequestMapping(value = "/{departmentName}/employees/{email}/edit", method = RequestMethod.POST)
	public String updateEmployeeInfo(@PathVariable String departmentName, UserViewModel userViewModel, Model model, HttpSession session) {
		try {

			if (userViewModel.getRole().equals("NONE")) {
				model.addAttribute("message", new MessageViewModel("No Role selected", "No role was selected!"));
				return showCreateEmployeeAccountPage(departmentName, model, userViewModel, session);
			}

			userService.updateEmployeeInformation(userViewModel);
			model.addAttribute("departmentName", departmentName);
			return "redirect:/departments/{departmentName}/edit";
		} catch (Exception e) {
			model.addAttribute("message", new MessageViewModel(e.getClass().getSimpleName(), e.getMessage()));
			return "editEmployee";
		}
	}

}
