package ca.ulaval.glo4003.appemployee.domain.department;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.appemployee.domain.exceptions.EmployeeAlreadyExistsException;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentTest {
	private static final String DEPARTMENT_NAME = "DummyDept";
	private static final String USER_UID = "1234";
	private List<String> supervisorIds = new ArrayList<String>();

	private Department department;

	@Before
	public void setUp() {
		department = new Department(DEPARTMENT_NAME);
		supervisorIds.add(USER_UID);
	}

	@Test
	public void canInstatiateDepartment() {
		department = new Department();
		assertNotNull(department);
	}

	@Test
	public void canInstatiateDepartmentWithName() {
		department = new Department(DEPARTMENT_NAME);
		assertNotNull(department);
	}

	@Test
	public void addEmployeeAddsEmployeeCorrectlyToTheList() {
		department.addEmployee(USER_UID);
		assertTrue(department.getEmployeeIds().contains(USER_UID));
	}

	@Test(expected = EmployeeAlreadyExistsException.class)
	public void addEmployeeThrowsEmployeeAlreadyExistsExceptionWhenAddingAnExistingId() {
		department.addEmployee(USER_UID);
		department.addEmployee(USER_UID);
	}

	@Test
	public void containsSupervisorReturnsTrueIfSupervisorExists() {
		department.setSupervisorIds(supervisorIds);
		assertTrue(department.containsSupervisor(USER_UID));
	}

	@Test
	public void containsSupervisorReturnsFalseIfSupervisorExists() {
		assertFalse(department.containsSupervisor(USER_UID));
	}

	@Test
	public void removeEmployeeRemovesEmployeeWhenEmployeeExists() {
		department.addEmployee(USER_UID);
		department.removeEmployee(USER_UID);
		assertFalse(department.getEmployeeIds().contains(USER_UID));
	}
}
