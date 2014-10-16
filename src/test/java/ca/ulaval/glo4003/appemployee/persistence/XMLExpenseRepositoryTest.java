//package ca.ulaval.glo4003.appemployee.persistence;
//
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.*;
//
//import java.util.List;
//
//import org.junit.Test;
//
//import ca.ulaval.glo4003.appemployee.domain.expense.Expense;
//
//public class XMLExpenseRepositoryTest {
//
//	private static final String USER_ID = "id";
//	private static final Integer NULL_INT = 0;
//	private static final double EPSILON = 0.001;
//
//	private XMLExpenseRepository xmlExpenseRepository;
//	private Expense expenseMock;
//
//	public void init() throws Exception {
//		xmlExpenseRepository = new XMLExpenseRepository();
//		expenseMock = mock(Expense.class);
//	}
//
//	@Test
//	public void findAllExpensesForUserReturnsListOfExpenses() {
//		when(expenseMock.getUserId()).thenReturn(USER_ID);
//		List<Expense> sampleList = xmlExpenseRepository.findAllExpensesByUser(USER_ID);
//		assertEquals(sampleList.size(), NULL_INT, EPSILON);
//	}
//}
