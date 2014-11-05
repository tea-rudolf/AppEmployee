package ca.ulaval.glo4003.appemployee.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.department.Department;
import ca.ulaval.glo4003.appemployee.domain.exceptions.EmployeeAlreadyExistsException;
import ca.ulaval.glo4003.appemployee.domain.exceptions.SupervisorAccessException;
import ca.ulaval.glo4003.appemployee.domain.repository.DepartmentRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.UserRepository;
import ca.ulaval.glo4003.appemployee.web.viewmodels.UserViewModel;

@Service
public class DepartmentService {

	private DepartmentRepository departmentRepository;
	private UserRepository userRepository;

	// private UserFactory userFactory;

	@Autowired
	public DepartmentService(DepartmentRepository departmentRepository, UserRepository userRepository) {
		this.departmentRepository = departmentRepository;
		this.userRepository = userRepository;
	}

	public void createUser(String supervisorId, String departmentName, UserViewModel userViewModel) throws Exception {
		if (userRepository.findByEmail(userViewModel.getEmail()) != null) {
			throw new EmployeeAlreadyExistsException("Employee you are trying to create already exists.");
		}

		Department department = departmentRepository.findByName(departmentName);
		if (!department.containsSupervisor(supervisorId)) {
			throw new SupervisorAccessException("You do not have supervisor access to create employee.");
		}

		// User user = userFactory.create();
	}
}
