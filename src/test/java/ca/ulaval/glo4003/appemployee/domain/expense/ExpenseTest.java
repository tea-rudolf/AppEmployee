package ca.ulaval.glo4003.appemployee.domain.expense;

import static org.junit.Assert.*;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class ExpenseTest {

	private static final double AMOUNT = 500.50;
	private static final LocalDate DATE = new LocalDate();
	private static final String USER_EMAIL = "test@company.com";
	private static final String COMMENT = "nothing to say";
	private static final double AMOUNT2 = 300.50;
	private static final LocalDate DATE2 = new LocalDate("2014-12-03");
	private static final String USER_EMAIL2 = "test2@company.com";
	private static final String COMMENT2 = "dummy Comment";

	private Expense expense;

	@Before
	public void setUp() {
		expense = new Expense(AMOUNT, DATE, USER_EMAIL, COMMENT);
	}

	@Test
	public void canInstantiateExpense() {
		assertNotNull(expense);
	}

	@Test
	public void updateChangesAmountExpense() {
		expense.update(AMOUNT2, DATE2, USER_EMAIL2, COMMENT2);
		assertEquals((int) AMOUNT2, (int) expense.getAmount());
	}

	@Test
	public void updateChangesDateExpense() {
		expense.update(AMOUNT2, DATE2, USER_EMAIL2, COMMENT2);
		assertEquals(DATE2, expense.getDate());
	}

	@Test
	public void updateChangesUserEmailExpense() {
		expense.update(AMOUNT2, DATE2, USER_EMAIL2, COMMENT2);
		assertEquals(USER_EMAIL2, expense.getUserEmail());
	}

	@Test
	public void updateChangesCommentExpense() {
		expense.update(AMOUNT2, DATE2, USER_EMAIL2, COMMENT2);
		assertEquals(COMMENT2, expense.getComment());
	}

}
