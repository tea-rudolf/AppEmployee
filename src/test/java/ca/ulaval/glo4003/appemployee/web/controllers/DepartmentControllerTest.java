package ca.ulaval.glo4003.appemployee.web.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

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
import ca.ulaval.glo4003.appemployee.web.converters.DepartmentConverter;
import ca.ulaval.glo4003.appemployee.web.converters.UserConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.AssignationEmployeDepartmentViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.DepartmentViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.UserViewModel;

public class DepartmentControllerTest {

	private static final String EMAIL_ATTRIBUTE = "email";
	private static final String DEPARTMENT_NAME = "dummyDepartment";
	private static final String EMAIL = "test@test.com";
	private static final String DEPARTMENT_FORM = "editDepartment";
	private static final String CREATE_USER_FORM = "createUser";
	private static final String EDIT_DEPARTMENT_REDIRECT = "redirect:/departments/{departmentName}/edit";
	private static final String EDIT_EMPLOYEE_FORM = "editEmployee";
	private static final String CREATE_DEPARTMENT_FORM = "createDepartment";
	private static final String DEPARTMENTS_LIST_REDIRECT = "redirect:/departments/";
	private static final String ASSIGN_EMPLOYE_FORM = "assignEmployeToDepartment";
	private static final String DEPARTMENT_LIST_FORM = "departmentsList";

	private List<User> users = new ArrayList<User>();

	@Mock
	private DepartmentService departmentServiceMock;

	@Mock
	private UserService userServiceMock;

	@Mock
	private UserConverter userConverterMock;

	@Mock
	private DepartmentConverter departmentConverterMock;

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
	private AssignationEmployeDepartmentViewModel assignationEmployeDepViewModelMock;

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
		String form = departmentController.showDepartmentsList(modelMock, sessionMock);
		assertEquals("departmentsList", form);
	}

	@Test
	public void showEmployeesListReturnEditedDepartmentForm() throws DepartmentNotFoundException {
		when(sessionMock.getAttribute(EMAIL_ATTRIBUTE)).thenReturn(EMAIL);
		when(departmentServiceMock.retrieveDepartmentByName(DEPARTMENT_NAME)).thenReturn(departmentMock);
		when(departmentServiceMock.retrieveEmployeesList(DEPARTMENT_NAME)).thenReturn(users);
		String returnedForm = departmentController.showEmployeesList(DEPARTMENT_NAME, modelMock, sessionMock);
		assertEquals(DEPARTMENT_FORM, returnedForm);
	}

	@Test
	public void showCreateEmployeeAccountPageReturnsUserCreationFormWhenSuccessful() {
		when(sessionMock.getAttribute(EMAIL_ATTRIBUTE)).thenReturn(EMAIL);
		String returnedForm = departmentController.showCreateEmployeeForm(DEPARTMENT_NAME, modelMock, userViewModelMock, sessionMock);
		assertEquals(CREATE_USER_FORM, returnedForm);
	}

	@Test
	public void createEmployeeAccountRedirectsToEditionPageWhenRoleIsNotMissing() {
		when(sessionMock.getAttribute(EMAIL_ATTRIBUTE)).thenReturn(EMAIL);
		when(userViewModelMock.getRole()).thenReturn(Role.EMPLOYEE.toString());
		String returnedForm = departmentController.createEmployee(DEPARTMENT_NAME, modelMock, userViewModelMock, sessionMock);
		assertEquals(EDIT_DEPARTMENT_REDIRECT, returnedForm);
	}

	@Test
	public void createEmployeeAccountRedirectsToEditionPageWhenExceptionIsThrown() throws Exception {
		when(sessionMock.getAttribute(EMAIL_ATTRIBUTE)).thenReturn(EMAIL);
		when(userViewModelMock.getRole()).thenReturn(Role.EMPLOYEE.toString());
		doThrow(new Exception()).when(departmentServiceMock).createUser(EMAIL, DEPARTMENT_NAME, userViewModelMock);
		String returnedForm = departmentController.createEmployee(DEPARTMENT_NAME, modelMock, userViewModelMock, sessionMock);
		assertEquals(CREATE_USER_FORM, returnedForm);
	}

	@Test
	public void showUpdateEmployeeInfoReturnsEditEmployeeFormWhenSuccessful() {
		when(sessionMock.getAttribute(EMAIL_ATTRIBUTE)).thenReturn(EMAIL);
		when(userServiceMock.retrieveByEmail(EMAIL)).thenReturn(userMock);
		String returnedForm = departmentController.showEditEmployeeForm(DEPARTMENT_NAME, EMAIL, modelMock, sessionMock);
		assertEquals(EDIT_EMPLOYEE_FORM, returnedForm);
	}

	@Test
	public void updateEmployeeInfoReturnsEditedDepartmentFormIfSuccessful() {
		when(sessionMock.getAttribute(EMAIL_ATTRIBUTE)).thenReturn(EMAIL);
		when(userViewModelMock.getRole()).thenReturn(Role.EMPLOYEE.toString());
		String returnedForm = departmentController.editEmployee(DEPARTMENT_NAME, userViewModelMock, modelMock, sessionMock);
		assertEquals(EDIT_DEPARTMENT_REDIRECT, returnedForm);
	}

	@Test
	public void updateEmployeeInfoReturnsEditEmployeeFormWhenExceptionIsThrown() throws Exception {
		when(sessionMock.getAttribute(EMAIL_ATTRIBUTE)).thenReturn(EMAIL);
		when(userViewModelMock.getRole()).thenReturn(Role.EMPLOYEE.toString());
		doThrow(new Exception()).when(userServiceMock).updateEmployeeInformation(userViewModelMock);
		String returnedForm = departmentController.editEmployee(DEPARTMENT_NAME, userViewModelMock, modelMock, sessionMock);
		assertEquals(EDIT_EMPLOYEE_FORM, returnedForm);
	}

	@Test
	public void createDepartmentReturnsCreateDepartmentForm() {
		when(sessionMock.getAttribute(EMAIL_ATTRIBUTE)).thenReturn(EMAIL);
		when(departmentServiceMock.getViewModelForCreation()).thenReturn(departmentViewModelMock);

		String returnedForm = departmentController.showCreateDepartmentForm(modelMock, sessionMock);

		assertEquals(CREATE_DEPARTMENT_FORM, returnedForm);
	}

	@Test
	public void saveDepartmentRedirectsToDepartmentsListIfSaveIsSuccessful() throws Exception {
		when(sessionMock.getAttribute(EMAIL_ATTRIBUTE)).thenReturn(EMAIL);
		String returnedForm = departmentController.createDepartment(modelMock, departmentViewModelMock, sessionMock);
		assertEquals(DEPARTMENTS_LIST_REDIRECT, returnedForm);
	}

	@Test
	public void selectEmployeToAssignToDepartmentReturnsAssignEmployeeFormIfSuccessful() {
		when(sessionMock.getAttribute(EMAIL_ATTRIBUTE)).thenReturn(EMAIL);
		when(departmentServiceMock.getViewModelToAssignEmployeToDepartment()).thenReturn(assignationEmployeDepViewModelMock);

		String returnedForm = departmentController.showAssignEmployeeToDepartmentForm(modelMock, sessionMock);

		assertEquals(ASSIGN_EMPLOYE_FORM, returnedForm);
	}

	@Test
	public void saveEditedDepartmentReturnsDepartmentsListFormIfSaveIsSuccessful() throws Exception {
		when(sessionMock.getAttribute(EMAIL_ATTRIBUTE)).thenReturn(EMAIL);
		String returnedForm = departmentController.saveEditedDepartment(assignationEmployeDepViewModelMock, sessionMock);
		assertEquals(DEPARTMENT_LIST_FORM, returnedForm);
	}

}
