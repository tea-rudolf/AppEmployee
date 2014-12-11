package ca.ulaval.glo4003.appemployee.domain.department;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ca.ulaval.glo4003.appemployee.domain.exceptions.DepartmentAlreadyExistsException;
import ca.ulaval.glo4003.appemployee.domain.exceptions.DepartmentNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.repository.DepartmentRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.UserRepository;
import ca.ulaval.glo4003.appemployee.domain.user.User;

public class DepartmentProcessorTest {

	@Mock
	private UserRepository userRepositoryMock;

	@Mock
	private DepartmentRepository departmentRepositoryMock;

	@Mock
	private Department departmentMock;

	@InjectMocks
	private DepartmentProcessor departmentProcessor;

	private static final String DEPARTMENT_NAME = "dummy_departmentName";
	private static final String UNASSIGNED_EMPLOYEES_DEPARTMENT = "Department of unassigned employees";
	private static final String EMAIL = "test@test.com";
	private static final String SUPERVISOR = "supervisor@test.com";
	private List<String> employeesId = new ArrayList<String>();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		departmentProcessor = spy(new DepartmentProcessor(userRepositoryMock, departmentRepositoryMock));
	}

	@Test
	public void canInstantiateDepartmentProcessor() {
		assertNotNull(departmentProcessor);
	}

	@Test(expected = DepartmentNotFoundException.class)
	public void evaluateEmployeesListThrowsExceptionWhenDepartmentNotFound() throws Exception {
		when(departmentRepositoryMock.findByName(DEPARTMENT_NAME)).thenReturn(null);
		departmentProcessor.evaluateEmployeesList(DEPARTMENT_NAME);
	}

	@Test
	public void evaluateEmployeesListReturnsList() throws Exception {
		List<User> employees = new ArrayList<User>();
		employeesId.add(EMAIL);
		when(departmentRepositoryMock.findByName(DEPARTMENT_NAME)).thenReturn(departmentMock);
		when(departmentMock.getEmployeeIds()).thenReturn(employeesId);
		when(userRepositoryMock.findByEmails(employeesId)).thenReturn(employees);

		List<User> actualEmployees = departmentProcessor.evaluateEmployeesList(DEPARTMENT_NAME);

		assertEquals(employees, actualEmployees);
	}

	@Test(expected = DepartmentNotFoundException.class)
	public void isSupervisorAssignedToDepartmentThrowsExceptionWhenDepartmentNotFound()
			throws DepartmentNotFoundException {
		when(departmentRepositoryMock.findByName(DEPARTMENT_NAME)).thenReturn(null);
		departmentProcessor.isSupervisorAssignedToDepartment(SUPERVISOR, DEPARTMENT_NAME);
	}

	@Test
	public void isSupervisorAssignedToDepartmentReturnsFalseWhenSupervisorNotAssigned()
			throws DepartmentNotFoundException {
		when(departmentRepositoryMock.findByName(DEPARTMENT_NAME)).thenReturn(departmentMock);
		when(departmentMock.containsSupervisor(SUPERVISOR)).thenReturn(false);

		boolean isSupervisorAssign = departmentProcessor.isSupervisorAssignedToDepartment(SUPERVISOR, DEPARTMENT_NAME);

		assertEquals(false, isSupervisorAssign);
	}

	@Test
	public void isSupervisorAssignedToDepartmentReturnsTrueWhenSupervisorAssigned() throws Exception {
		when(departmentRepositoryMock.findByName(DEPARTMENT_NAME)).thenReturn(departmentMock);
		when(departmentMock.containsSupervisor(SUPERVISOR)).thenReturn(true);

		boolean isSupervisorAssign = departmentProcessor.isSupervisorAssignedToDepartment(SUPERVISOR, DEPARTMENT_NAME);

		assertEquals(true, isSupervisorAssign);
	}

	@Test(expected = DepartmentAlreadyExistsException.class)
	public void createDepartmentsThrowsExceptionWhenDepartmentAlreadyExists() throws Exception {
		List<String> userEmails = new ArrayList<String>();
		userEmails.add(EMAIL);
		when(departmentRepositoryMock.findByName(DEPARTMENT_NAME)).thenReturn(departmentMock);

		departmentProcessor.createDepartment(DEPARTMENT_NAME, userEmails);
	}

	@Test
	public void createDepartmentStoresObjectInRepository() throws Exception {
		ArgumentCaptor<Department> departmentArgumentCaptor = ArgumentCaptor.forClass(Department.class);
		List<String> userEmails = new ArrayList<String>();
		userEmails.add(EMAIL);
		when(departmentRepositoryMock.findByName(DEPARTMENT_NAME)).thenReturn(null);

		departmentProcessor.createDepartment(DEPARTMENT_NAME, userEmails);

		verify(departmentRepositoryMock, times(1)).store(departmentArgumentCaptor.capture());
	}

	@Test(expected = DepartmentNotFoundException.class)
	public void evaluateEmployeesNotAssignedToDepartmentThrowsExceptionWhenDepartmentNotFound() throws Exception {
		when(departmentRepositoryMock.findByName(DEPARTMENT_NAME)).thenReturn(null);
		departmentProcessor.evaluateEmployeesNotAssignedToDepartment();
	}

	@Test
	public void evaluateEmployeesNotAssignedToDepartmentReturnsEmployeeIds() throws Exception {
		employeesId.add(EMAIL);
		when(departmentRepositoryMock.findByName(UNASSIGNED_EMPLOYEES_DEPARTMENT)).thenReturn(departmentMock);
		when(departmentMock.getEmployeeIds()).thenReturn(employeesId);

		List<String> actualEmployeesId = departmentProcessor.evaluateEmployeesNotAssignedToDepartment();

		assertEquals(employeesId, actualEmployeesId);
	}

	@Test(expected = DepartmentNotFoundException.class)
	public void assignEmployeeToDepartmentThrowsExceptionwhenDepartmentNotFound() throws Exception {
		when(departmentRepositoryMock.findByName(DEPARTMENT_NAME)).thenReturn(null);
		departmentProcessor.assignEmployeeToDepartment(EMAIL, DEPARTMENT_NAME);
	}

	@Test
	public void assignEmployeeToDepartmentAddsEmployeeToDepartment() throws Exception {
		when(departmentRepositoryMock.findByName(DEPARTMENT_NAME)).thenReturn(departmentMock);
		departmentProcessor.assignEmployeeToDepartment(EMAIL, DEPARTMENT_NAME);
		verify(departmentMock, times(1)).addEmployee(EMAIL);
	}

	@Test(expected = DepartmentNotFoundException.class)
	public void unassignEmployeeToDepartmentThrowsExceptionwhenDepartmentNotFound() throws Exception {
		when(departmentRepositoryMock.findByName(DEPARTMENT_NAME)).thenReturn(null);
		departmentProcessor.unassignEmployeeToDepartment(EMAIL, DEPARTMENT_NAME);
	}

	@Test
	public void unassignEmployeeToDepartmentRemoveEmployeeFromDepartment() throws Exception {
		when(departmentRepositoryMock.findByName(DEPARTMENT_NAME)).thenReturn(departmentMock);
		departmentProcessor.unassignEmployeeToDepartment(EMAIL, DEPARTMENT_NAME);
		verify(departmentMock, times(1)).removeEmployee(EMAIL);
	}

	@Test
	public void retrieveAllDepartmentsReturns() {
		Collection<Department> departments = new ArrayList<Department>();
		departments.add(departmentMock);
		when(departmentRepositoryMock.findAll()).thenReturn(departments);

		Collection<Department> actualDepartments = departmentProcessor.retrieveAllDepartments();

		assertEquals(departments, actualDepartments);
	}

	@Test
	public void removeEmployeesListFromUnassignedDepartmentCallsUnassignEmployeeToDepartment() throws Exception {
		List<String> employees = new ArrayList<String>();
		employees.add(EMAIL);
		when(departmentRepositoryMock.findByName(UNASSIGNED_EMPLOYEES_DEPARTMENT)).thenReturn(departmentMock);

		departmentProcessor.removeEmployeesListFromUnassignedDepartment(employees);

		verify(departmentMock, times(1)).removeEmployee(EMAIL);
	}
}
