package ca.ulaval.glo4003.appemployee.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

public class DepartmentServiceTest {

	private static final double WAGE = 45000.00;
	private static final String PASSWORD = "1234";
	private static final String EMAIL = "test@company.com";
	private static final String DEPARTMENT_NAME = "dummyDepartment";
	private static final String SUPERVISOR_ID = "0001";
	private static final String SELECTED_EMAILS = "test@company.com,test2@company.com";

	@Mock
	private UserProcessor userProcessorMock;

	@Mock
	private DepartmentProcessor departmentProcessorMock;

	@Mock
	private DepartmentConverter departmentConverterMock;

	@Mock
	private UserConverter userConverterMock;

	@Mock
	private UserViewModel userViewModelMock;

	@Mock
	private User userMock;

	@Mock
	private Department departmentMock;

	@Mock
	private DepartmentViewModel departmentViewModelMock;

	@Mock
	private EmployeeAssignationViewModel employeeAssignationViewModelMock;

	@InjectMocks
	private DepartmentService departmentService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		departmentService = new DepartmentService(userProcessorMock, departmentProcessorMock, departmentConverterMock,
				userConverterMock);
	}

	@Test
	public void canInstantiateDepartmentService() {
		assertNotNull(departmentService);
	}

	@Test(expected = SupervisorAccessException.class)
	public void createEmployeeThrowsExceptionWhenSupervisorDoNotHaveRights() throws Exception {
		when(departmentProcessorMock.isSupervisorAssignedToDepartment(SUPERVISOR_ID, DEPARTMENT_NAME))
				.thenReturn(false);
		departmentService.createEmployee(SUPERVISOR_ID, DEPARTMENT_NAME, userViewModelMock);
	}

	@Test
	public void createEmployeeCallsUserProcessor() throws Exception {
		when(departmentProcessorMock.isSupervisorAssignedToDepartment(SUPERVISOR_ID, DEPARTMENT_NAME)).thenReturn(true);
		retrieveUserViewModelMockInfo();

		departmentService.createEmployee(SUPERVISOR_ID, DEPARTMENT_NAME, userViewModelMock);

		verify(userProcessorMock, times(1)).createUser(EMAIL, PASSWORD, Role.EMPLOYEE, WAGE);
	}

	@Test(expected = SupervisorAccessException.class)
	public void assignUserToDepartmentThrowsExceptionWhenSupervisorDoNotHaveRights() throws Exception {
		when(departmentProcessorMock.isSupervisorAssignedToDepartment(SUPERVISOR_ID, DEPARTMENT_NAME))
				.thenReturn(false);
		departmentService.assignUserToDepartment(userViewModelMock, SUPERVISOR_ID, DEPARTMENT_NAME);
	}

	@Test
	public void assignUserToDepartmentCallsDepartmentProcessor() throws Exception {
		when(departmentProcessorMock.isSupervisorAssignedToDepartment(SUPERVISOR_ID, DEPARTMENT_NAME)).thenReturn(true);
		retrieveUserViewModelMockInfo();

		departmentService.assignUserToDepartment(userViewModelMock, SUPERVISOR_ID, DEPARTMENT_NAME);

		verify(departmentProcessorMock, times(1)).assignEmployeeToDepartment(EMAIL, DEPARTMENT_NAME);
	}

	@Test
	public void createDepartmentCallsDepartmentProcessorWithUserEmailsListWhenSelectedUsersNotEmpty() throws Exception {
		List<String> userEmails = Arrays.asList(SELECTED_EMAILS.split(","));
		when(departmentViewModelMock.getSelectedUserEmails()).thenReturn(SELECTED_EMAILS);
		when(departmentViewModelMock.getName()).thenReturn(DEPARTMENT_NAME);

		departmentService.createDepartement(departmentViewModelMock);

		verify(departmentProcessorMock, times(1)).createDepartment(DEPARTMENT_NAME, userEmails);
	}

	@Test
	public void createDepartmentCallsDepartmentProcessorWithEmptyUserEmailsListWhenSelectedUsersIsNull()
			throws Exception {
		when(departmentViewModelMock.getSelectedUserEmails()).thenReturn(null);
		when(departmentViewModelMock.getName()).thenReturn(DEPARTMENT_NAME);

		departmentService.createDepartement(departmentViewModelMock);

		verify(departmentProcessorMock, times(1)).createDepartment(DEPARTMENT_NAME, new ArrayList<String>());
	}

	@Test
	public void createDepartmentCallsDepartmentProcessorWithEmptyUserEmailsListWhenSelectedUsersIsEmpty()
			throws Exception {
		when(departmentViewModelMock.getSelectedUserEmails()).thenReturn("");
		when(departmentViewModelMock.getName()).thenReturn(DEPARTMENT_NAME);

		departmentService.createDepartement(departmentViewModelMock);

		verify(departmentProcessorMock, times(1)).createDepartment(DEPARTMENT_NAME, new ArrayList<String>());
	}

	@Test
	public void retrieveDepartmentsListReturnsAllDepartment() {
		Collection<Department> departments = new ArrayList<Department>();
		departments.add(new Department(DEPARTMENT_NAME));
		when(departmentProcessorMock.retrieveAllDepartments()).thenReturn(departments);

		Collection<Department> actualDepartments = departmentService.retrieveDepartmentsList();

		assertEquals(departments, actualDepartments);
	}

	@Test
	public void assignOrphanEmployeeToDepartmentCallsDEpartmentProcessor() throws Exception {
		when(employeeAssignationViewModelMock.getSelectedDepartment()).thenReturn(DEPARTMENT_NAME);
		when(employeeAssignationViewModelMock.getSelectedEmployee()).thenReturn(EMAIL);

		departmentService.assignOrphanEmployeeToDepartment(employeeAssignationViewModelMock);
	}

	@Test
	public void retrieveAvailableEmployeesViewModelThrowsExceptionWhenDepartmentNotFound() throws Exception {
		List<String> employees = new ArrayList<String>();
		employees.add(EMAIL);
		when(departmentProcessorMock.evaluateEmployeesNotAssignedToDepartment()).thenReturn(employees);

		DepartmentViewModel availableEmployees = departmentService.retrieveAvailableEmployeesViewModel();

		assertEquals(new DepartmentViewModel(employees).getAvailableUsers(), availableEmployees.getAvailableUsers());
	}

	@Test(expected = DepartmentNotFoundException.class)
	public void retrieveDepartmentViewModelThrowsExceptionWhenDepartmentNotFound() throws Exception {
		when(departmentProcessorMock.retrieveDepartmentByName(DEPARTMENT_NAME)).thenThrow(
				new DepartmentNotFoundException(""));
		departmentService.retrieveDepartmentViewModel(DEPARTMENT_NAME);
	}

	@Test
	public void retrieveDepartmentViewModelReturnsDepartmentViewModel() throws Exception {
		when(departmentProcessorMock.retrieveDepartmentByName(DEPARTMENT_NAME)).thenReturn(departmentMock);
		when(departmentConverterMock.convert(departmentMock)).thenReturn(departmentViewModelMock);

		DepartmentViewModel actualDepartmentViewModel = departmentService.retrieveDepartmentViewModel(DEPARTMENT_NAME);

		assertEquals(departmentViewModelMock, actualDepartmentViewModel);
	}

	@Test(expected = DepartmentNotFoundException.class)
	public void retrieveEmployeesListViewModelThrowsExceptionWhenDepartmentNotFound() throws Exception {
		when(departmentProcessorMock.evaluateEmployeesList(DEPARTMENT_NAME)).thenThrow(
				new DepartmentNotFoundException(""));
		departmentService.retrieveEmployeesListViewModel(DEPARTMENT_NAME);
	}

	@Test
	public void retrieveEmployeesListViewModelReturnsEmployeesList() throws Exception {
		List<User> employees = new ArrayList<User>();
		employees.add(userMock);
		List<UserViewModel> employeesViewModel = new ArrayList<UserViewModel>();
		employeesViewModel.add(userViewModelMock);
		when(departmentProcessorMock.evaluateEmployeesList(DEPARTMENT_NAME)).thenReturn(employees);
		when(userConverterMock.convert(employees)).thenReturn(employeesViewModel);

		Collection<UserViewModel> actualEmployees = departmentService.retrieveEmployeesListViewModel(DEPARTMENT_NAME);

		assertEquals(employeesViewModel, actualEmployees);
	}

	@Test(expected = DepartmentNotFoundException.class)
	public void retrieveEmployeeAssignationViewModelThrowsExceptionWhenDepartmentNotFound() throws Exception {
		when(departmentProcessorMock.evaluateEmployeesNotAssignedToDepartment()).thenThrow(
				new DepartmentNotFoundException(""));
		departmentService.retrieveEmployeeAssignationViewModel();
	}

	@Test
	public void retrieveEmployeeAssignationViewModel() throws Exception {
		List<String> unassignedEmployees = new ArrayList<String>();
		ArrayList<String> departmentNames = new ArrayList<String>();
		Collection<Department> departments = new ArrayList<Department>();
		departments.add(new Department(DEPARTMENT_NAME));
		unassignedEmployees.add(EMAIL);
		departmentNames.add(DEPARTMENT_NAME);
		when(departmentProcessorMock.evaluateEmployeesNotAssignedToDepartment()).thenReturn(unassignedEmployees);
		when(departmentProcessorMock.retrieveAllDepartments()).thenReturn(departments);

		EmployeeAssignationViewModel viewModel = departmentService.retrieveEmployeeAssignationViewModel();

		assertEquals(departmentNames, viewModel.getDepartmentsList());
		assertEquals(unassignedEmployees, viewModel.getEmployeesWithNoDepartment());
	}

	private void retrieveUserViewModelMockInfo() {
		when(userViewModelMock.getEmail()).thenReturn(EMAIL);
		when(userViewModelMock.getPassword()).thenReturn(PASSWORD);
		when(userViewModelMock.getRole()).thenReturn(Role.EMPLOYEE.toString());
		when(userViewModelMock.getWage()).thenReturn(WAGE);
	}

}
