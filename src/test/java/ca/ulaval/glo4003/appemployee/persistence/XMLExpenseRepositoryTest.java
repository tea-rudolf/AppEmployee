package ca.ulaval.glo4003.appemployee.persistence;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Test;

import ca.ulaval.glo4003.appemployee.domain.expense.Expense;
import ca.ulaval.glo4003.appemployee.domain.user.Role;
import ca.ulaval.glo4003.appemployee.domain.user.User;

public class XMLExpenseRepositoryTest {

	private static final String USER_ID = "employee@employee.com";
	private static final double AMOUNT = 100.00;
	private static final double WAGE = 70.00;
	private static final Role ROLE = Role.EMPLOYEE;
	private static final String PASSWORD = "employee";

	private XMLUserRepository xmlUserRepositoryMock;
	private XMLExpenseRepository xmlExpenseRepository;
	private Expense expenseMock;
	private User userMock;
	
	List<Expense> foundExpenses = new ArrayList<Expense>();

	public void init() throws Exception {
		xmlExpenseRepository = new XMLExpenseRepository();
		expenseMock = new Expense();
		expenseMock.setAmount(AMOUNT);
		expenseMock.setUserEmail(USER_ID);
		expenseMock.setDate(new LocalDate("2014-10-10"));
		expenseMock.setuId(USER_ID);
		xmlUserRepositoryMock = mock(XMLUserRepository.class);
		userMock = new User(USER_ID, PASSWORD, ROLE, WAGE);
	}

	@Test
	public void findAllExpensesForUserReturnsListOfExpenses() throws Exception {
		when(xmlUserRepositoryMock.findByEmail(USER_ID)).thenReturn(userMock);

		List<Expense> sampleList = xmlExpenseRepository.findAllExpensesByUser(USER_ID);
		
		assertTrue(sampleList.contains(expenseMock));
	}
}
