package ca.ulaval.glo4003.appemployee.persistence;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Test;

import ca.ulaval.glo4003.appemployee.domain.expense.Expense;
import ca.ulaval.glo4003.appemployee.domain.user.User;

public class XMLExpenseRepositoryTest {

	private static final String USER_ID = "employee@employee.com";
	private static final double AMOUNT = 100.00;

	private XMLUserRepository xmlUserRepositoryMock;
	private XMLExpenseRepository xmlExpenseRepository;
	private Expense expenseMock;
	private User userMock;

	public void init() throws Exception {
		xmlExpenseRepository = new XMLExpenseRepository();
		expenseMock = mock(Expense.class);
		xmlUserRepositoryMock = mock(XMLUserRepository.class);
		userMock = mock(User.class);
		createUser();
	}

	@Test
	public void findAllExpensesForUserReturnsListOfExpenses() throws Exception {
		when(expenseMock.getUserEmail()).thenReturn(USER_ID);
		when(expenseMock.getAmount()).thenReturn(AMOUNT);
		
		xmlExpenseRepository.store(expenseMock);
		
		List<Expense> sampleList = xmlExpenseRepository.findAllExpensesByUser(USER_ID);
		assertTrue(sampleList.contains(expenseMock));
	}
	
	public void createUser() throws Exception{
		userMock.setEmail(USER_ID);
		xmlUserRepositoryMock.store(userMock);
	}
}
