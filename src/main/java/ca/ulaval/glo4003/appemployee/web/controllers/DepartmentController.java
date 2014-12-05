package ca.ulaval.glo4003.appemployee.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import ca.ulaval.glo4003.appemployee.domain.exceptions.DepartmentExistsException;
import ca.ulaval.glo4003.appemployee.domain.exceptions.DepartmentNotFoundException;
import ca.ulaval.glo4003.appemployee.services.DepartmentService;
import ca.ulaval.glo4003.appemployee.services.UserService;
import ca.ulaval.glo4003.appemployee.web.viewmodels.DepartmentViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.EmployeeAssignationViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.MessageViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.UserViewModel;

@Controller
@RequestMapping(value = "/departments")
@SessionAttributes({ "email" })
public class DepartmentController {

	private static final String EMAIL_ATTRIBUTE = "email";

	private DepartmentService departmentService;
	private UserService userService;

	@Autowired
	public DepartmentController(DepartmentService departmentService, UserService userService) {
		this.departmentService = departmentService;
		this.userService = userService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String showDepartmentsList(Model model, HttpSession session) {
		model.addAttribute("departments", departmentService.retrieveDepartmentsList());
		return "departmentsList";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String showCreateDepartmentForm(Model model, HttpSession session) {
		DepartmentViewModel departmentViewModel;
		try {
			departmentViewModel = departmentService.retrieveAvailableEmployeesViewModel();
			model.addAttribute("department", departmentViewModel);
		} catch (DepartmentNotFoundException e) {
			model.addAttribute("message", new MessageViewModel(e.getClass().getSimpleName(), e.getMessage()));
			// redirect
		}
		return "createDepartment";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String createDepartment(Model model, DepartmentViewModel departmentViewModel, HttpSession session)
			throws Exception {
		
		try {
			departmentService.createDepartement(departmentViewModel);
		} catch (DepartmentExistsException e) {
			model.addAttribute("message", new MessageViewModel(e.getClass().getSimpleName(), e.getMessage()));
			return showCreateDepartmentForm(model, session);
		}
		
		return "redirect:/departments/";
	}

	@RequestMapping(value = "/{departmentName}/edit", method = RequestMethod.GET)
	public String showEmployeesList(@PathVariable String departmentName, Model model, HttpSession session)
			throws DepartmentNotFoundException {
		model.addAttribute("department", departmentService.retrieveDepartmentViewModel(departmentName));
		model.addAttribute("employees", departmentService.retrieveEmployeesListViewModel(departmentName));
		return "editDepartment";
	}

	@RequestMapping(value = "/{departmentName}/employees/createEmployee", method = RequestMethod.GET)
	public String showCreateEmployeeForm(@PathVariable String departmentName, Model model, UserViewModel userViewModel,
			HttpSession session) {
		model.addAttribute("departmentName", departmentName);
		model.addAttribute("user", userViewModel);
		return "createUser";
	}

	@RequestMapping(value = "/{departmentName}/employees/createEmployee", method = RequestMethod.POST)
	public String createEmployee(@PathVariable String departmentName, Model model, UserViewModel userViewModel,
			HttpSession session) {
		String supervisorId = session.getAttribute(EMAIL_ATTRIBUTE).toString();

		try {

			departmentService.createEmployee(supervisorId, departmentName, userViewModel);
			departmentService.assignUserToDepartment(userViewModel, supervisorId, departmentName);

			return "redirect:/departments/{departmentName}/edit";
		} catch (Exception e) {
			model.addAttribute("message", new MessageViewModel(e.getClass().getSimpleName(), e.getMessage()));
			return showCreateEmployeeForm(departmentName, model, userViewModel, session);
		}
	}

	@RequestMapping(value = "/{departmentName}/employees/{email}/edit", method = RequestMethod.GET)
	public String showEditEmployeeForm(@PathVariable String departmentName, @PathVariable String email, Model model,
			HttpSession session) {
		model.addAttribute("user", userService.retrieveUserViewModel(email));
		model.addAttribute("departmentName", departmentName);
		return "editEmployee";
	}

	@RequestMapping(value = "/{departmentName}/employees/{email}/edit", method = RequestMethod.POST)
	public String editEmployee(@PathVariable String departmentName, UserViewModel userViewModel, Model model,
			HttpSession session) {
		try {
			userService.editUser(userViewModel);
			model.addAttribute("departmentName", departmentName);

			return "redirect:/departments/{departmentName}/edit";
		} catch (Exception e) {
			model.addAttribute("message", new MessageViewModel(e.getClass().getSimpleName(), e.getMessage()));
			return "redirect:/departments/" + departmentName + "/employees/" + userViewModel.getEmail() + "/edit";
		}
	}

	@RequestMapping(value = "/assignEmployes", method = RequestMethod.GET)
	public String showAssignEmployeeToDepartmentForm(Model model, HttpSession session) {
		try {
			model.addAttribute("assignationModel", departmentService.retrieveEmployeeAssignationViewModel());
		} catch (Exception e) {
			model.addAttribute("message", new MessageViewModel(e.getClass().getSimpleName(), e.getMessage()));
			// quel redirect
		}
		return "assignEmployeToDepartment";
	}

	@RequestMapping(value = "/assignEmployes", method = RequestMethod.POST)
	public String assignEmployeeToDepartment(EmployeeAssignationViewModel model, HttpSession session) throws Exception {
		departmentService.assignOrphanEmployeeToDepartment(model);
		return "redirect:/departments";
	}

}
