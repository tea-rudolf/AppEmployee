package ca.ulaval.glo4003.appemployee.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.department.Department;
import ca.ulaval.glo4003.appemployee.domain.exceptions.DepartmentNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.exceptions.EmployeeAlreadyExistsException;
import ca.ulaval.glo4003.appemployee.domain.exceptions.SupervisorAccessException;
import ca.ulaval.glo4003.appemployee.domain.repository.DepartmentRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.UserRepository;
import ca.ulaval.glo4003.appemployee.domain.user.Role;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.web.converters.DepartmentConverter;
import ca.ulaval.glo4003.appemployee.web.converters.UserConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.AssignationEmployeDepartmentViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.DepartmentViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.UserViewModel;

@Service
public class DepartmentService {

	private DepartmentRepository departmentRepository;
	private UserRepository userRepository;
	private DepartmentConverter departmentConverter;
	private UserConverter userConverter;

	@Autowired
	public DepartmentService(DepartmentRepository departmentRepository, UserRepository userRepository, DepartmentConverter departmentConverter,
			UserConverter userConverter) {
		this.departmentRepository = departmentRepository;
		this.userRepository = userRepository;
		this.departmentConverter = departmentConverter;
		this.userConverter = userConverter;
	}

	public void createUser(String supervisorID, String departmentName, UserViewModel userViewModel) throws Exception {
		if (userRepository.findByEmail(userViewModel.getEmail()) != null) {
			throw new EmployeeAlreadyExistsException("Employee you are trying to create already exists.");
		}
		if (!isSupervisorAssignedToDepartment(supervisorID, departmentName)) {
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

		if (!isSupervisorAssignedToDepartment(supervisorID, departmentName)) {
			throw new SupervisorAccessException("You do not have supervisor rights in this department.");
		}

		department.addEmployee(userViewModel.getEmail());
		departmentRepository.store(department);
	}

	public Collection<Department> retrieveDepartmentsList() {
		return departmentRepository.findAll();
	}

	public Department retrieveDepartmentByName(String departmentName) throws DepartmentNotFoundException {
		Department department = departmentRepository.findByName(departmentName);

		if (department == null) {
			throw new DepartmentNotFoundException("Department not found with following name : " + departmentName);
		}
		return department;
	}

	public List<User> retrieveEmployeesList(String departmentName) throws DepartmentNotFoundException {
		Department department = retrieveDepartmentByName(departmentName);
		List<String> employeeIds = department.getEmployeeIds();
		List<User> employees = userRepository.findByEmails(employeeIds);
		return employees;
	}

	private boolean isSupervisorAssignedToDepartment(String supervisorID, String departmentName) {
		boolean isAssigned = false;
		Department department = departmentRepository.findByName(departmentName);

		if (department != null && department.containsSupervisor(supervisorID)) {
			isAssigned = true;
		}
		return isAssigned;
	}

	public void createDepartement(DepartmentViewModel departmentViewModel) throws Exception {
		Department department = new Department();
		department.setName(departmentViewModel.getName());
		
		if(departmentViewModel.getUserEmailsSelected() != null){
			List<String> userEmail = Arrays.asList(departmentViewModel.getUserEmailsSelected().split(","));
			for (String email : userEmail) {
				department.addEmployee(email);
			}
		}
		


		departmentRepository.store(department);
	}

	public DepartmentViewModel getViewModelForCreation() {
		DepartmentViewModel model = new DepartmentViewModel();
		model.setAvailableUsers(getUserEmailsNotAssignedToAnyDepartment());
		return model;
	}

	public List<String> getUserEmailsNotAssignedToAnyDepartment() {

		// TO FIX : A REFACTOR
		List<String> emails = new ArrayList<String>();

		for (User user : userRepository.findAll()) {
			if (user.getRole().equals(Role.EMPLOYEE)) {
				emails.add(user.getEmail());
			}

		}

		for (Department dep : departmentRepository.findAll()) {
			for (String email : dep.getEmployeeIds()) {
				emails.remove(email);
			}
		}

		return emails;
	}

	public DepartmentViewModel getViewModelForEdition(String departmentName) {
		Department d = departmentRepository.findByName(departmentName);
		DepartmentViewModel departmentViewModel = departmentConverter.convert(d);
		return departmentViewModel;
	}

	public Collection<UserViewModel> getEmployesViewModelsForEdition(String departmentName) throws DepartmentNotFoundException {
		List<User> employees = retrieveEmployeesList(departmentName);
		Collection<UserViewModel> employeesViewModel = userConverter.convert(employees);
		return employeesViewModel;
	}

	public AssignationEmployeDepartmentViewModel getViewModelToAssignEmployeToDepartment() {
		AssignationEmployeDepartmentViewModel model = new AssignationEmployeDepartmentViewModel();

		ArrayList<String> usersWithNoDepartment = new ArrayList<String>();

		for (Department dep : retrieveDepartmentsList()) {
			usersWithNoDepartment.add(dep.getName());
		}

		model.setDepartmentsList(usersWithNoDepartment);
		model.setUsersWithNoDepartment(getUserEmailsNotAssignedToAnyDepartment());

		return model;
	}

	public void assignUserToDepartment(AssignationEmployeDepartmentViewModel model) throws Exception {
		
		Department department = departmentRepository.findByName(model.getDepartmentSelected());
		department.addEmployee(model.getUserSelected());
		departmentRepository.store(department);
		
	}

}
