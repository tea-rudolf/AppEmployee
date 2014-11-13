package ca.ulaval.glo4003.appemployee.services;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ca.ulaval.glo4003.appemployee.domain.department.Department;
import ca.ulaval.glo4003.appemployee.domain.exceptions.DepartmentNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.exceptions.EmployeeAlreadyExistsException;
import ca.ulaval.glo4003.appemployee.domain.exceptions.SupervisorAccessException;
import ca.ulaval.glo4003.appemployee.domain.repository.DepartmentRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.UserRepository;
import ca.ulaval.glo4003.appemployee.domain.user.Role;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.web.viewmodels.UserViewModel;

public class DepartmentServiceTest {

	@Mock
	private UserRepository userRepositoryMock;

	@Mock
	private DepartmentRepository departmentRepositoryMock;

	@Mock
	private UserViewModel userViewModelMock;

	@Mock
	private User userMock;

	@Mock
	private Department departmentMock;

	@InjectMocks
	private DepartmentService departmentService;

	private static final double WAGE = 45000.00;
	private static final String PASSWORD = "1234";
	private static final String EMAIL = "test@test.com";
	private static final String DEPARTMENT_NAME = "dummyDepartment";
	private static final String SUPERVISOR_ID = "0001";

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		departmentService = new DepartmentService(departmentRepositoryMock, userRepositoryMock);
		when(userViewModelMock.getEmail()).thenReturn(EMAIL);
		when(userViewModelMock.getPassword()).thenReturn(PASSWORD);
		when(userViewModelMock.getRole()).thenReturn(Role.EMPLOYEE.toString());
		when(userViewModelMock.getWage()).thenReturn(WAGE);
	}

	@Test
	public void canInstantiateService() {
		assertNotNull(departmentService);
	}

	@Test
	public void createUserAddsUserToRepository() throws Exception {
		when(userRepositoryMock.findByEmail(EMAIL)).thenReturn(null);
		when(departmentRepositoryMock.findByName(DEPARTMENT_NAME)).thenReturn(departmentMock);
		when(departmentMock.containsSupervisor(SUPERVISOR_ID)).thenReturn(true);

		departmentService.createUser(SUPERVISOR_ID, DEPARTMENT_NAME, userViewModelMock);

		verify(userRepositoryMock, times(1)).store(any(User.class));
	}

	@Test(expected = EmployeeAlreadyExistsException.class)
	public void createUserThrowsExceptionWhenEmployeeAlreadyExists() throws Exception {
		when(userRepositoryMock.findByEmail(EMAIL)).thenReturn(userMock);
		departmentService.createUser(SUPERVISOR_ID, DEPARTMENT_NAME, userViewModelMock);
	}

	@Test(expected = SupervisorAccessException.class)
	public void createUserThrowsExceptionWhenSupervisorDoNotHaveRightAccess() throws Exception {
		when(userRepositoryMock.findByEmail(EMAIL)).thenReturn(null);
		when(departmentRepositoryMock.findByName(DEPARTMENT_NAME)).thenReturn(departmentMock);
		when(departmentMock.containsSupervisor(SUPERVISOR_ID)).thenReturn(false);

		departmentService.createUser(SUPERVISOR_ID, DEPARTMENT_NAME, userViewModelMock);
	}

	@Test
	public void assignUserToDepartmentAddsUser() throws Exception {
		when(departmentRepositoryMock.findByName(DEPARTMENT_NAME)).thenReturn(departmentMock);
		when(departmentMock.containsSupervisor(SUPERVISOR_ID)).thenReturn(true);

		departmentService.assignUserToDepartment(userViewModelMock, SUPERVISOR_ID, DEPARTMENT_NAME);

		verify(departmentMock, times(1)).addEmployee(EMAIL);
	}

	@Test
	public void assignUserToDepartmentUpdateRepository() throws Exception {
		when(departmentRepositoryMock.findByName(DEPARTMENT_NAME)).thenReturn(departmentMock);
		when(departmentMock.containsSupervisor(SUPERVISOR_ID)).thenReturn(true);

		departmentService.assignUserToDepartment(userViewModelMock, SUPERVISOR_ID, DEPARTMENT_NAME);

		verify(departmentRepositoryMock, times(1)).store(departmentMock);
	}

	@Test(expected = DepartmentNotFoundException.class)
	public void assignUserToDepartmentThrowsExceptionWhenDepartmentNotFound() throws Exception {
		when(departmentRepositoryMock.findByName(DEPARTMENT_NAME)).thenReturn(null);
		departmentService.assignUserToDepartment(userViewModelMock, SUPERVISOR_ID, DEPARTMENT_NAME);
	}

	@Test(expected = SupervisorAccessException.class)
	public void assignUserToDepartmentThrowsExceptionWhenSupervisorDoNotHaveRightAccess() throws Exception {
		when(departmentRepositoryMock.findByName(DEPARTMENT_NAME)).thenReturn(departmentMock);
		when(departmentMock.containsSupervisor(SUPERVISOR_ID)).thenReturn(false);

		departmentService.assignUserToDepartment(userViewModelMock, SUPERVISOR_ID, DEPARTMENT_NAME);
	}

	@Test
	public void retrieveDepartmentsListReturnsAllDepartments() {
		Collection<Department> expectedDepartments = new ArrayList<Department>();
		expectedDepartments.add(departmentMock);
		when(departmentRepositoryMock.findAll()).thenReturn(expectedDepartments);

		Collection<Department> returnedDepartments = departmentService.retrieveDepartmentsList();

		assertEquals(expectedDepartments.size(), returnedDepartments.size());
	}

	@Test(expected = DepartmentNotFoundException.class)
	public void retrieveDepartmentByNameThrowsExceptionWhenDepartmentDoesNotExist() throws Exception {
		when(departmentRepositoryMock.findByName(DEPARTMENT_NAME)).thenReturn(null);
		departmentService.retrieveDepartmentByName(DEPARTMENT_NAME);
	}

	@Test
	public void retrieveDepartmentByNameFindsDepartmentWhenExists() throws Exception {
		when(departmentRepositoryMock.findByName(DEPARTMENT_NAME)).thenReturn(departmentMock);
		when(departmentMock.getName()).thenReturn(DEPARTMENT_NAME);

		Department department = departmentService.retrieveDepartmentByName(DEPARTMENT_NAME);

		assertEquals(departmentMock.getName(), department.getName());
	}

	@Test(expected = DepartmentNotFoundException.class)
	public void retrieveEmployeesListThrowsExceptionWhenDepartmentDoesNotExist() throws Exception {
		when(departmentRepositoryMock.findByName(DEPARTMENT_NAME)).thenReturn(null);
		departmentService.retrieveEmployeesList(DEPARTMENT_NAME);
	}

	@Test
	public void retrieveEmployeesListReturnsListWhenDepartmentExists() throws Exception {
		List<String> expectedEmployeeIds = new ArrayList<String>();
		expectedEmployeeIds.add(EMAIL);
		List<User> expectedEmployees = new ArrayList<User>();
		expectedEmployees.add(userMock);
		when(departmentRepositoryMock.findByName(DEPARTMENT_NAME)).thenReturn(departmentMock);
		when(departmentMock.getEmployeeIds()).thenReturn(expectedEmployeeIds);
		when(userRepositoryMock.findByEmails(expectedEmployeeIds)).thenReturn(expectedEmployees);
		when(userMock.getEmail()).thenReturn(EMAIL);

		List<User> returnedEmployees = departmentService.retrieveEmployeesList(DEPARTMENT_NAME);

		assertEquals(expectedEmployees.size(), returnedEmployees.size());
	}
}
