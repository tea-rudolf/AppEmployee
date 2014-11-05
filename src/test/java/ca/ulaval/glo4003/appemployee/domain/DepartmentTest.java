package ca.ulaval.glo4003.appemployee.domain;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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

    private Department department;
    
    @Before
    public void setUp() {
        department = new Department(DEPARTMENTNAME);
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
}
