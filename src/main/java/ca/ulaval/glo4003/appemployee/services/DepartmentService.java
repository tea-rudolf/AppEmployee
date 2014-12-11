package ca.ulaval.glo4003.appemployee.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.department.Department;
import ca.ulaval.glo4003.appemployee.domain.department.DepartmentProcessor;
import ca.ulaval.glo4003.appemployee.domain.exceptions.DepartmentNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.exceptions.SupervisorAccessException;
import ca.ulaval.glo4003.appemployee.domain.user.Role;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.domain.user.UserProcessor;
import ca.ulaval.glo4003.appemployee.web.converters.DepartmentConverter;
import ca.ulaval.glo4003.appemployee.web.converters.UserConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.DepartmentViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.EmployeeAssignationViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.UserViewModel;

@Service
public class DepartmentService {

	private static final String DEPARTMENT_OF_UNASSIGNED_EMPLOYEES = "Department of unassigned employees";

	private UserProcessor userProcessor;
	private DepartmentProcessor departmentProcessor;
	private DepartmentConverter departmentConverter;
	private UserConverter userConverter;

	@Autowired
	public DepartmentService(UserProcessor userProcessor, DepartmentProcessor departmentProcessor,
			DepartmentConverter departmentConverter, UserConverter userConverter) {
		this.userProcessor = userProcessor;
		this.departmentProcessor = departmentProcessor;
		this.departmentConverter = departmentConverter;
		this.userConverter = userConverter;
	}

	public void createEmployee(String supervisorID, String departmentName, UserViewModel userViewModel)
			throws Exception {
		if (!departmentProcessor.isSupervisorAssignedToDepartment(supervisorID, departmentName)) {
			throw new SupervisorAccessException("You do not have supervisor rights in this department.");
		}

		userProcessor.createUser(userViewModel.getEmail(), userViewModel.getPassword(),
				Role.valueOf(userViewModel.getRole()), userViewModel.getWage());
	}

	public void assignUserToDepartment(UserViewModel userViewModel, String supervisorID, String departmentName)
			throws Exception {
		if (!departmentProcessor.isSupervisorAssignedToDepartment(supervisorID, departmentName)) {
			throw new SupervisorAccessException("You do not have supervisor rights in this department.");
		}

		departmentProcessor.assignEmployeeToDepartment(userViewModel.getEmail(), departmentName);
	}

	public void createDepartement(DepartmentViewModel departmentViewModel) throws Exception {
		List<String> userEmails = new ArrayList<String>();

		if (departmentViewModel.getSelectedUserEmails() != null
				&& !departmentViewModel.getSelectedUserEmails().isEmpty()) {
			userEmails = Arrays.asList(departmentViewModel.getSelectedUserEmails().split(","));
		}

		departmentProcessor.createDepartment(departmentViewModel.getName(), userEmails);
		departmentProcessor.removeEmployeesFromUnassignedDepartment(userEmails);
		
	}

	public Collection<Department> retrieveDepartmentsList() {
		return departmentProcessor.retrieveAllDepartments();
	}

	public void assignOrphanEmployeeToDepartment(EmployeeAssignationViewModel model) throws Exception {
		departmentProcessor.assignEmployeeToDepartment(model.getSelectedEmployee(), model.getSelectedDepartment());
		departmentProcessor.unassignEmployeeToDepartment(model.getSelectedEmployee(),
				DEPARTMENT_OF_UNASSIGNED_EMPLOYEES);
	}

	public DepartmentViewModel retrieveAvailableEmployeesViewModel() throws DepartmentNotFoundException {
		List<String> availableEmployees = retrieveUserEmailsNotAssignedToDepartment();
		return new DepartmentViewModel(availableEmployees);
	}

	public DepartmentViewModel retrieveDepartmentViewModel(String departmentName) throws DepartmentNotFoundException {
		Department department = departmentProcessor.retrieveDepartmentByName(departmentName);
		return departmentConverter.convert(department);
	}

	public Collection<UserViewModel> retrieveEmployeesListViewModel(String departmentName)
			throws DepartmentNotFoundException {
		List<User> employees = departmentProcessor.evaluateEmployeesList(departmentName);
		return userConverter.convert(employees);
	}

	public EmployeeAssignationViewModel retrieveEmployeeAssignationViewModel() throws DepartmentNotFoundException {
		ArrayList<String> departmentNames = retrieveDepartmentNamesList();
		List<String> unassignedEmployees = retrieveUserEmailsNotAssignedToDepartment();
		return new EmployeeAssignationViewModel(departmentNames, unassignedEmployees);
	}

	private List<String> retrieveUserEmailsNotAssignedToDepartment() throws DepartmentNotFoundException {
		return departmentProcessor.evaluateEmployeesNotAssignedToDepartment();
	}

	private ArrayList<String> retrieveDepartmentNamesList() {
		ArrayList<String> departmentNames = new ArrayList<String>();

		for (Department department : retrieveDepartmentsList()) {
			departmentNames.add(department.getName());
		}
		return departmentNames;
	}

}
