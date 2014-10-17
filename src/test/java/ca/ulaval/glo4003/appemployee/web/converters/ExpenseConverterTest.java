package ca.ulaval.glo4003.appemployee.web.converters;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.*;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.appemployee.domain.expense.Expense;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ExpenseViewModel;

public class ExpenseConverterTest {
	
	private final static double FIRST_AMOUNT = 100.00;
	private final static String FIRST_ID = "123456";
	private final static double SECOND_AMOUNT = 200.00;
	private final static String SECOND_ID = "789103";
	private final static LocalDate FIRST_DATE = new LocalDate("2014-10-17");
	private final static LocalDate SECOND_DATE = new LocalDate("2014-10-16");
	private final static double EPSILON = 0.001;
	private final static String COMMENT = "comment";
	private final static String USER_EMAIL = "employee@employee.com";
	
	private ExpenseConverter expenseConverter;
	private ExpenseViewModel expenseViewModelMock;
	private Expense expenseMock;
	
	@Before
	public void init(){
		expenseViewModelMock = mock(ExpenseViewModel.class);
		expenseMock = mock(Expense.class);
		expenseConverter = new ExpenseConverter();
	}
	
	@Test
	public void convertExpensesListToViewModelsConvertsAllOfThem() {
		Expense firstExpense = createExpense(FIRST_ID, FIRST_AMOUNT, FIRST_DATE);
		Expense secondExpense = createExpense(SECOND_ID, SECOND_AMOUNT, SECOND_DATE);
		List<Expense> expenses = new ArrayList<Expense>();
		expenses.add(firstExpense);
		expenses.add(secondExpense);

		ExpenseViewModel[] viewModels = expenseConverter.convert(expenses).toArray(new ExpenseViewModel[1]);

		assertEquals(FIRST_AMOUNT, viewModels[0].getAmount(), EPSILON);
		assertEquals(FIRST_ID, viewModels[0].getuId());
		assertEquals(FIRST_DATE.toString(), viewModels[0].getDate());

		assertEquals(SECOND_AMOUNT, viewModels[1].getAmount(), EPSILON);
		assertEquals(SECOND_ID, viewModels[1].getuId());
		assertEquals(SECOND_DATE.toString(), viewModels[1].getDate());
	}
	
	@Test
	public void convertExpenseToExpenseViewModel(){
		when(expenseMock.getAmount()).thenReturn(FIRST_AMOUNT);
		when(expenseMock.getComment()).thenReturn(COMMENT);
		when(expenseMock.getDate()).thenReturn(FIRST_DATE);
		when(expenseMock.getuId()).thenReturn(FIRST_ID);
		when(expenseMock.getUserEmail()).thenReturn(USER_EMAIL);
		
		expenseViewModelMock = expenseConverter.convert(expenseMock);
		
		assertEquals(expenseMock.getAmount(), expenseViewModelMock.getAmount(), EPSILON);
		assertEquals(expenseMock.getComment(), expenseViewModelMock.getComment());
		assertEquals(expenseMock.getDate().toString(), expenseViewModelMock.getDate());
		assertEquals(expenseMock.getuId(), expenseViewModelMock.getuId());
		assertEquals(expenseMock.getUserEmail(), expenseViewModelMock.getUserEmail());
	}
	
	@Test
	public void convertExpenseViewModelToExpense(){
		when(expenseViewModelMock.getAmount()).thenReturn(FIRST_AMOUNT);
		when(expenseViewModelMock.getDate()).thenReturn(FIRST_DATE.toString());
		when(expenseViewModelMock.getUserEmail()).thenReturn(USER_EMAIL);
		when(expenseViewModelMock.getComment()).thenReturn(COMMENT);
		
		expenseMock = expenseConverter.convert(expenseViewModelMock);
		
		assertEquals(expenseViewModelMock.getAmount(), expenseMock.getAmount(), EPSILON);
		assertEquals(expenseViewModelMock.getDate(), expenseMock.getDate().toString());
		assertEquals(expenseViewModelMock.getUserEmail(), expenseMock.getUserEmail());
		assertEquals(expenseViewModelMock.getComment(), expenseMock.getComment());
	}
	
	private Expense createExpense(String number, double amount, LocalDate date) {
		Expense expense = mock(Expense.class);
		given(expense.getAmount()).willReturn(amount);
		given(expense.getuId()).willReturn(number);
		given(expense.getDate()).willReturn(date);
		return expense;
	}

}
