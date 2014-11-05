package ca.ulaval.glo4003.appemployee.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.appemployee.domain.department.Department;
import ca.ulaval.glo4003.appemployee.domain.exceptions.EmployeeAlreadyExistsException;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentTest {
	private static final String DEPARTMENTNAME = "DummyDept";
	private static final String USER_UID = "1234";
	private static final List<String> supervisorIds = new ArrayList<String>();

	private Department department;

	@Before
	public void setUp() {
		department = new Department(DEPARTMENTNAME);
		supervisorIds.add(USER_UID);
	}

	@Test
	public void canInstatiateDepartmentt() {
		assertNotNull(department);
	}

	@Test
	public void addEmployeeAddsEmployeeCorrectlyToTheList() {
		department.addEmployee(USER_UID);
		assertTrue(department.getEmployeeUids().contains(USER_UID));
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
}
