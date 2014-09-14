package ca.ulaval.glo4003.appemployee.domain;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ManagerTest {

	@Mock
	private Employee employee;

	@InjectMocks
	private Manager manager;

	@Test
	public void canAddEmployee() {
		int initialNumberOfEmployees = manager.getEmployees().size();

		manager.addEmployee(employee);
		int currentNumberOfEmployees = manager.getEmployees().size();

		assertEquals(initialNumberOfEmployees + 1, currentNumberOfEmployees);
	}

}
