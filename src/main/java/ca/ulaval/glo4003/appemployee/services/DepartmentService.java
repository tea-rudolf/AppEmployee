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
import ca.ulaval.glo4003.appemployee.domain.exceptions.EmployeeAlreadyExistsException;
import ca.ulaval.glo4003.appemployee.domain.exceptions.SupervisorAccessException;
import ca.ulaval.glo4003.appemployee.domain.repository.DepartmentRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.UserRepository;
import ca.ulaval.glo4003.appemployee.domain.user.Role;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.web.converters.DepartmentConverter;
import ca.ulaval.glo4003.appemployee.web.converters.UserConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.DepartmentViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.EmployeeAssignationViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.UserViewModel;

@Service
public class DepartmentService {

	private DepartmentRepository departmentRepository;
	private UserRepository userRepository;
	private DepartmentProcessor departmentProcessor;
	private DepartmentConverter departmentConverter;
	private UserConverter userConverter;

	@Autowired
	public DepartmentService(DepartmentRepository departmentRepository, UserRepository userRepository, DepartmentProcessor departmentProcessor,
			DepartmentConverter departmentConverter, UserConverter userConverter) {
		this.departmentRepository = departmentRepository;
		this.userRepository = userRepository;
		this.departmentProcessor = departmentProcessor;
		this.departmentConverter = departmentConverter;
		this.userConverter = userConverter;
	}

	public void createEmployee(String supervisorID, String departmentName, UserViewModel userViewModel) throws Exception {
		if (userRepository.findByEmail(userViewModel.getEmail()) != null) {
			throw new EmployeeAlreadyExistsException("Employee you are trying to create already exists.");
		}
		if (!departmentProcessor.isSupervisorAssignedToDepartment(supervisorID, departmentName)) {
			throw new SupervisorAccessException("You do not have supervisor rights in this department.");
		}

		User user = new User(userViewModel.getEmail(), userViewModel.getPassword(), Role.valueOf(userViewModel.getRole()), userViewModel.getWage());
		userRepository.store(user);
	}

	public void assignUserToDepartment(UserViewModel userViewModel, String supervisorID, String departmentName) throws Exception {
		Department department = departmentRepository.findByName(departmentName);

		if (department == null) {
			throw new DepartmentNotFoundException("Department does not exist");
		}

		if (!departmentProcessor.isSupervisorAssignedToDepartment(supervisorID, departmentName)) {
			throw new SupervisorAccessException("You do not have supervisor rights in this department.");
		}

		department.addEmployee(userViewModel.getEmail());
		departmentRepository.store(department);
	}

	public Collection<Department> retrieveDepartmentsList() {
		return departmentRepository.findAll();
	}

	public void createDepartement(DepartmentViewModel departmentViewModel) throws Exception {
		if (departmentViewModel.getSelectedUserEmails() != null && (!departmentViewModel.getSelectedUserEmails().isEmpty())) {
			List<String> userEmails = Arrays.asList(departmentViewModel.getSelectedUserEmails().split(","));
			departmentProcessor.createDepartment(departmentViewModel.getName(), userEmails);
		}
	}

	public DepartmentViewModel retrieveAvailableEmployeesViewModel() {
		List<String> availableEmployees = retrieveUserEmailsNotAssignedToDepartment();
		DepartmentViewModel model = new DepartmentViewModel(availableEmployees);

		return model;
	}

	public DepartmentViewModel retrieveDepartmentViewModel(String departmentName) {
		Department department = departmentRepository.findByName(departmentName);
		return departmentConverter.convert(department);
	}

	public Collection<UserViewModel> retrieveEmployeesListViewModel(String departmentName) throws DepartmentNotFoundException {
		List<User> employees = departmentProcessor.retrieveEmployeesList(departmentName);
		return userConverter.convert(employees);
	}

	public EmployeeAssignationViewModel retrieveEmployeeAssignationViewModel() {
		ArrayList<String> departmentNames = retrieveDepartmentNamesList();
		List<String> unassignedEmployees = retrieveUserEmailsNotAssignedToDepartment();
		EmployeeAssignationViewModel model = new EmployeeAssignationViewModel(departmentNames, unassignedEmployees);

		return model;
	}

	// duplication, enlever
	public void assignUserToDepartment(EmployeeAssignationViewModel model) throws Exception {
		Department department = departmentRepository.findByName(model.getSelectedDepartment());
		department.addEmployee(model.getSelectedEmployee());
		departmentRepository.store(department);
	}

	private List<String> retrieveUserEmailsNotAssignedToDepartment() {
		return departmentProcessor.retrieveEmployeesNotAssignedToDepartment();
	}

	private ArrayList<String> retrieveDepartmentNamesList() {
		ArrayList<String> departmentNames = new ArrayList<String>();

		for (Department department : retrieveDepartmentsList()) {
			departmentNames.add(department.getName());
		}
		return departmentNames;
	}

}
