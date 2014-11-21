package ca.ulaval.glo4003.appemployee.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import ca.ulaval.glo4003.appemployee.domain.exceptions.DepartmentNotFoundException;
import ca.ulaval.glo4003.appemployee.services.DepartmentService;
import ca.ulaval.glo4003.appemployee.services.UserService;
import ca.ulaval.glo4003.appemployee.web.viewmodels.AssignationEmployeDepartmentViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.DepartmentViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.MessageViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.UserViewModel;

@Controller
@RequestMapping(value = "/departments")
@SessionAttributes({ "email" })
public class DepartmentController {

	private static final String EMAIL_ATTRIBUTE = "email";
	private static final String SIMPLE_REDIRECT = "redirect:/";
	private static final String CREATE_DEPARTMENT_FORM = "createDepartment";
	private static final String DEPARTMENT_LIST_FORM = "departmentsList";
	private static final String EDIT_DEPARTMENT_FORM = "editDepartment";
	private static final String CREATE_USER_FORM = "createUser";
	private static final String ASSIGN_EMPLOYE_FORM = "assignEmployeToDepartment";
	private static final String EDIT_DEPARTMENT_REDIRECT = "redirect:/departments/{departmentName}/edit";
	private static final String EDIT_EMPLOYEE_FORM = "editEmployee";
	private static final String DEPARTMENTS_LIST_REDIRECT = "redirect:/departments/";

	private DepartmentService departmentService;
	private UserService userService;

	@Autowired
	public DepartmentController(DepartmentService departmentService, UserService userService) {
		this.departmentService = departmentService;
		this.userService = userService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String showDepartmentsList(Model model, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return SIMPLE_REDIRECT;
		}

		model.addAttribute("departments", departmentService.retrieveDepartmentsList());

		return DEPARTMENT_LIST_FORM;
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String createDepartment(Model model, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return SIMPLE_REDIRECT;
		}

		DepartmentViewModel departmentViewModel = departmentService.getViewModelForCreation();

		model.addAttribute("department", departmentViewModel);

		return CREATE_DEPARTMENT_FORM;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String saveDepartment(Model model, DepartmentViewModel departmentViewModel, HttpSession session) throws Exception {

		departmentService.createDepartement(departmentViewModel);

		return DEPARTMENTS_LIST_REDIRECT;

	}

	@RequestMapping(value = "/{departmentName}/edit", method = RequestMethod.GET)
	public String showEmployeesList(@PathVariable String departmentName, Model model, HttpSession session) throws DepartmentNotFoundException {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return SIMPLE_REDIRECT;
		}

		model.addAttribute("department", departmentService.getViewModelForEdition(departmentName));
		model.addAttribute("employees", departmentService.getEmployesViewModelsForEdition(departmentName));

		return EDIT_DEPARTMENT_FORM;
	}

	@RequestMapping(value = "/{departmentName}/employees/createEmployee", method = RequestMethod.GET)
	public String showCreateEmployeeAccountPage(@PathVariable String departmentName, Model model, UserViewModel userViewModel, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return SIMPLE_REDIRECT;
		}

		model.addAttribute("departmentName", departmentName);
		model.addAttribute("user", userViewModel);

		return CREATE_USER_FORM;
	}

	@RequestMapping(value = "/{departmentName}/employees/createEmployee", method = RequestMethod.POST)
	public String createEmployeeAccount(@PathVariable String departmentName, Model model, UserViewModel userViewModel, HttpSession session) {
		String supervisorId = session.getAttribute(EMAIL_ATTRIBUTE).toString();

		try {

			departmentService.createUser(supervisorId, departmentName, userViewModel);
			departmentService.assignUserToDepartment(userViewModel, supervisorId, departmentName);

			return EDIT_DEPARTMENT_REDIRECT;
		} catch (Exception e) {
			model.addAttribute("message", new MessageViewModel(e.getClass().getSimpleName(), e.getMessage()));
			return showCreateEmployeeAccountPage(departmentName, model, userViewModel, session);
		}

	}

	@RequestMapping(value = "/{departmentName}/employees/{email}/edit", method = RequestMethod.GET)
	public String showUpdateEmployeeInfo(@PathVariable String departmentName, @PathVariable String email, Model model, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return SIMPLE_REDIRECT;
		}

		model.addAttribute("user", userService.getUserViewModelForEdition(email));
		model.addAttribute("departmentName", departmentName);

		return EDIT_EMPLOYEE_FORM;
	}

	@RequestMapping(value = "/{departmentName}/employees/{email}/edit", method = RequestMethod.POST)
	public String updateEmployeeInfo(@PathVariable String departmentName, UserViewModel userViewModel, Model model, HttpSession session) {
		try {

			userService.updateEmployeeInformation(userViewModel);

			model.addAttribute("departmentName", departmentName);

			return EDIT_DEPARTMENT_REDIRECT;

		} catch (Exception e) {
			model.addAttribute("message", new MessageViewModel(e.getClass().getSimpleName(), e.getMessage()));
			return EDIT_EMPLOYEE_FORM;
		}
	}

	@RequestMapping(value = "/assignEmployes", method = RequestMethod.GET)
	public String selectEmployeToAssignToDepartment(Model model, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return SIMPLE_REDIRECT;
		}

		model.addAttribute("assignationModel", departmentService.getViewModelToAssignEmployeToDepartment());

		return ASSIGN_EMPLOYE_FORM;
	}

	@RequestMapping(value = "/assignEmployes", method = RequestMethod.POST)
	public String saveEditedDepartment(AssignationEmployeDepartmentViewModel model, HttpSession session) throws Exception {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return SIMPLE_REDIRECT;
		}

		departmentService.assignUserToDepartment(model);

		return DEPARTMENT_LIST_FORM;
	}

}
