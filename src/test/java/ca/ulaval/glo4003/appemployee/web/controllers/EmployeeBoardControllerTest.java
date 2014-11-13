package ca.ulaval.glo4003.appemployee.web.controllers;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class EmployeeBoardControllerTest {
	
	private static final String EMPLOYEE_FORM = "employee";
	
	private EmployeeBoardController employeeBoardController;
	
	@Before
	public void init(){
		employeeBoardController = new EmployeeBoardController();
	}
	
	@Test
	public void getReturnsValidFormIfSuccessful(){
		String returnedForm = employeeBoardController.get();
		assertEquals(EMPLOYEE_FORM, returnedForm);
	}

}
