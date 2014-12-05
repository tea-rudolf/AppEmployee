package ca.ulaval.glo4003.appemployee.domain.department;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.appemployee.domain.exceptions.DepartmentExistsException;
import ca.ulaval.glo4003.appemployee.domain.exceptions.DepartmentNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.repository.DepartmentRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.UserRepository;
import ca.ulaval.glo4003.appemployee.domain.user.User;

@Component
public class DepartmentProcessor {

	private static final String DEPARTMENT_OF_UNASSIGNED_EMPLOYEES = "Department of unassigned employees";

	private UserRepository userRepository;
	private DepartmentRepository departmentRepository;

	@Autowired
	public DepartmentProcessor(UserRepository userRepository, DepartmentRepository departmentRepository) {
		this.userRepository = userRepository;
		this.departmentRepository = departmentRepository;
	}

	public List<User> evaluateEmployeesList(String departmentName) throws DepartmentNotFoundException {
		Department department = retrieveDepartmentByName(departmentName);
		List<String> employeeIds = department.getEmployeeIds();
		List<User> employees = userRepository.findByEmails(employeeIds);

		return employees;
	}

	public boolean isSupervisorAssignedToDepartment(String supervisorID, String departmentName)
			throws DepartmentNotFoundException {
		boolean isAssigned = false;
		Department department = retrieveDepartmentByName(departmentName);

		if (department.containsSupervisor(supervisorID)) {
			isAssigned = true;
		}
		return isAssigned;
	}

	public void createDepartment(String departmentName, List<String> userEmails) throws Exception {
		
		if (departmentRepository.findByName(departmentName) != null){
			throw new DepartmentExistsException(String.format("The department %s already exists!", departmentName));
		}
		
		Department department = new Department(departmentName);

		if (userEmails != null) {
			for (String email : userEmails) {
				department.addEmployee(email);
			}
		}
		departmentRepository.store(department);
	}

	public List<String> evaluateEmployeesNotAssignedToDepartment() throws DepartmentNotFoundException {
		Department unassignedEmployeesDepartment = retrieveDepartmentByName(DEPARTMENT_OF_UNASSIGNED_EMPLOYEES);
		return unassignedEmployeesDepartment.getEmployeeIds();
	}

	public void assignEmployeeToDepartment(String employeeUid, String departmentName) throws Exception {
		Department department = retrieveDepartmentByName(departmentName);
		department.addEmployee(employeeUid);
		departmentRepository.store(department);
	}

	public void unassignEmployeeToDepartment(String employeeUid, String departmentName) throws Exception {
		Department department = retrieveDepartmentByName(departmentName);
		department.removeEmployee(employeeUid);
		departmentRepository.store(department);
	}

	public Department retrieveDepartmentByName(String departmentName) throws DepartmentNotFoundException {
		Department department = departmentRepository.findByName(departmentName);

		if (department == null) {
			throw new DepartmentNotFoundException("Department not found with following name : " + departmentName);
		}
		return department;
	}

	public Collection<Department> retrieveAllDepartments() {
		return departmentRepository.findAll();
	}

}
