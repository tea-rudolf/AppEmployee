package ca.ulaval.glo4003.appemployee.web.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import ca.ulaval.glo4003.appemployee.domain.department.Department;
import ca.ulaval.glo4003.appemployee.domain.exceptions.DepartmentNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.user.Role;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.services.DepartmentService;
import ca.ulaval.glo4003.appemployee.services.UserService;
import ca.ulaval.glo4003.appemployee.web.viewmodels.DepartmentViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.EmployeeAssignationViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.UserViewModel;

public class DepartmentControllerTest {

	private static final String EMAIL_ATTRIBUTE = "email";
	private static final String DEPARTMENT_NAME = "Research and development";
	private static final String EMAIL = "test@test.com";
	private static final String CREATE_USER_FORM = "createUser";
	private static final String DEPARTMENT_REDIRECT = "redirect:/departments/Research and development";
	private static final String EDIT_DEPARTMENT_REDIRECT = "redirect:/departments/{departmentName}/edit";
	private static final String EDIT_DEPARTMENT_EMPLOYEE = "redirect:/departments/Research and development/employees/email/edit";
	private static final String EDIT_EMPLOYEE_FORM = "editEmployee";
	private static final String CREATE_DEPARTMENT_FORM = "createDepartment";
	private static final String DEPARTMENTS_LIST_REDIRECT = "redirect:/departments/";
	private static final String ASSIGN_EMPLOYE_FORM = "assignEmployeToDepartment";
	private static final String DEPARTMENT_LIST_FORM = "departmentsList";
	private static final String DEPARTMENT_LIST_REDIRECT = "redirect:/departments";

	@Mock
	private DepartmentService departmentServiceMock;

	@Mock
	private UserService userServiceMock;

	@Mock
	private Model modelMock;

	@Mock
	private HttpSession sessionMock;

	@Mock
	private UserViewModel userViewModelMock;

	@Mock
	private Department departmentMock;

	@Mock
	private User userMock;

	@Mock
	private DepartmentViewModel departmentViewModelMock;

	@Mock
	private EmployeeAssignationViewModel assignationEmployeDepViewModelMock;

	@InjectMocks
	private DepartmentController departmentController;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		departmentController = new DepartmentController(departmentServiceMock, userServiceMock);
	}

	@Test
	public void showDepartmentsListReturnsDepartmentsListFormWhenSessionAttributeNotNull() {
		when(sessionMock.getAttribute(EMAIL_ATTRIBUTE)).thenReturn(EMAIL);
		String returnedForm = departmentController.showDepartmentsList(modelMock, sessionMock);
		assertEquals(DEPARTMENT_LIST_FORM, returnedForm);
	}

	@Test
	public void showCreateDepartmentFormReturnsCreateDepartmentFormWhenDepartmentViewModelIdValid() throws Exception {
		when(departmentServiceMock.retrieveAvailableEmployeesViewModel()).thenReturn(departmentViewModelMock);
		String returnedForm = departmentController.showCreateDepartmentForm(modelMock, sessionMock);
		assertEquals(CREATE_DEPARTMENT_FORM, returnedForm);
	}

	@Test
	public void createDepartmentRedirectsToDepartmentsListWhenSuccessful() throws Exception {
		String returnedForm = departmentController.createDepartment(modelMock, departmentViewModelMock, sessionMock);
		assertEquals(DEPARTMENTS_LIST_REDIRECT, returnedForm);
	}

	@Test
	public void createDepartmentCallsCorrectServiceMethodIfSuccessful() throws Exception {
		departmentController.createDepartment(modelMock, departmentViewModelMock, sessionMock);
		verify(departmentServiceMock, times(1)).createDepartement(departmentViewModelMock);
	}

	@Test
	public void showEmployeesListReturnDepartmentFormWhenSuccessful() throws DepartmentNotFoundException {
		String returnedForm = departmentController.showEmployeesList(DEPARTMENT_NAME, modelMock, sessionMock);
		assertEquals(DEPARTMENT_REDIRECT, returnedForm);
	}

	@Test
	public void showCreateEmployeeAccountPageReturnsUserCreationFormWhenSuccessful() {
		when(sessionMock.getAttribute(EMAIL_ATTRIBUTE)).thenReturn(EMAIL);
		String returnedForm = departmentController.showCreateEmployeeForm(DEPARTMENT_NAME, modelMock,
				userViewModelMock, sessionMock);
		assertEquals(CREATE_USER_FORM, returnedForm);
	}

	@Test
	public void createEmployeeAccountRedirectsToEditionPageWhenRoleIsNotMissing() {
		when(sessionMock.getAttribute(EMAIL_ATTRIBUTE)).thenReturn(EMAIL);
		when(userViewModelMock.getRole()).thenReturn(Role.EMPLOYEE.toString());
		String returnedForm = departmentController.createEmployee(DEPARTMENT_NAME, modelMock, userViewModelMock,
				sessionMock);
		assertEquals(EDIT_DEPARTMENT_REDIRECT, returnedForm);
	}

	@Test
	public void createEmployeeAccountRedirectsToEditionPageWhenExceptionIsThrown() throws Exception {
		when(sessionMock.getAttribute(EMAIL_ATTRIBUTE)).thenReturn(EMAIL);
		when(userViewModelMock.getRole()).thenReturn(Role.EMPLOYEE.toString());
		doThrow(new Exception()).when(departmentServiceMock).createEmployee(EMAIL, DEPARTMENT_NAME, userViewModelMock);
		String returnedForm = departmentController.createEmployee(DEPARTMENT_NAME, modelMock, userViewModelMock,
				sessionMock);
		assertEquals(CREATE_USER_FORM, returnedForm);
	}

	@Test
	public void showEditEmployeeFormReturnsEditEmployeeFormWhenSuccessful() {
		when(sessionMock.getAttribute(EMAIL_ATTRIBUTE)).thenReturn(EMAIL);
		when(userServiceMock.retrieveUserByEmail(EMAIL)).thenReturn(userMock);
		String returnedForm = departmentController.showEditEmployeeForm(DEPARTMENT_NAME, EMAIL, modelMock, sessionMock);
		assertEquals(EDIT_EMPLOYEE_FORM, returnedForm);
	}

	@Test
	public void editEmployeeReturnsEditedDepartmentFormIfSuccessful() {
		when(sessionMock.getAttribute(EMAIL_ATTRIBUTE)).thenReturn(EMAIL);
		when(userViewModelMock.getRole()).thenReturn(Role.EMPLOYEE.toString());
		String returnedForm = departmentController.editEmployee(DEPARTMENT_NAME, userViewModelMock, modelMock,
				sessionMock);
		assertEquals(EDIT_DEPARTMENT_REDIRECT, returnedForm);
	}

	@Test
	public void editEmployeeReturnsEditEmployeeFormWhenExceptionIsThrown() throws Exception {
		when(sessionMock.getAttribute(EMAIL_ATTRIBUTE)).thenReturn(EMAIL);
		when(userViewModelMock.getRole()).thenReturn(Role.EMPLOYEE.toString());
		when(userViewModelMock.getEmail()).thenReturn(EMAIL_ATTRIBUTE);
		doThrow(new Exception()).when(userServiceMock).editUser(userViewModelMock);
		String returnedForm = departmentController.editEmployee(DEPARTMENT_NAME, userViewModelMock, modelMock,
				sessionMock);
		assertEquals(EDIT_DEPARTMENT_EMPLOYEE, returnedForm);
	}

	@Test
	public void showAssignEmployeeToDepartmentFormReturnsAssignEmployeeFormIfSuccessful() {
		String returnedForm = departmentController.showAssignEmployeeToDepartmentForm(modelMock, sessionMock);
		assertEquals(ASSIGN_EMPLOYE_FORM, returnedForm);
	}

	@Test
	public void assignEmployeeToDepartmentReturnsDepartmentsListFormIfSaveIsSuccessful() throws Exception {
		String returnedForm = departmentController.assignEmployeeToDepartment(assignationEmployeDepViewModelMock,
				sessionMock);
		assertEquals(DEPARTMENT_LIST_REDIRECT, returnedForm);
	}

	@Test
	public void assignEmployeeToDepartmentCallsCorrectServiceMethod() throws Exception {
		departmentController.assignEmployeeToDepartment(assignationEmployeDepViewModelMock, sessionMock);
		verify(departmentServiceMock, times(1)).assignOrphanEmployeeToDepartment(assignationEmployeDepViewModelMock);
	}

}
