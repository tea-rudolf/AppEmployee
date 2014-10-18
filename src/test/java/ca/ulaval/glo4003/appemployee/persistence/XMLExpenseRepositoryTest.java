//package ca.ulaval.glo4003.appemployee.persistence;
//
//import java.util.List;
//
//import org.junit.*;
//
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.*;
//import ca.ulaval.glo4003.appemployee.domain.expense.Expense;
//import ca.ulaval.glo4003.appemployee.domain.user.User;
//
//public class XMLExpenseRepositoryTest {
//	
//	private static final String USER_EMAIL = "employee@employee.com";
//	
//	private XMLExpenseRepository xmlExpenseRepository;
//	private XMLUserRepository xmlUserRepositoryMock;
//	private Expense expenseMock;
//	private User userMock;
//	
//	@Before
//	public void init() throws Exception{
//		expenseMock = mock(Expense.class);
//		userMock = mock(User.class);
//		xmlUserRepositoryMock = mock(XMLUserRepository.class);
//		xmlExpenseRepository = new XMLExpenseRepository();
//	}
//	
//	@Test
//	public void findAllExpensesByUserReturnsExpensesList(){
//		when(expenseMock.getUserEmail()).thenReturn(USER_EMAIL);
//		when(xmlUserRepositoryMock.findByEmail(USER_EMAIL)).thenReturn(userMock);
//		
//		List<Expense> sampleExpenseList = xmlExpenseRepository.findAllExpensesByUser(USER_EMAIL);
//		
//		assertTrue(sampleExpenseList.contains(expenseMock));
//	}
//	
//
//}
