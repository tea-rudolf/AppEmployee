package ca.ulaval.glo4003.appemployee.domain.expense;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class ExpenseTest {

	private static final double FIRST_AMOUNT = 500.50;
	private static final LocalDate FIRST_DATE = new LocalDate();
	private static final String FIRST_USER_EMAIL = "test@company.com";
	private static final String FIRST_COMMENT = "nothing to say";

	private static final double SECOND_AMOUNT = 300.50;
	private static final LocalDate SECOND_DATE = new LocalDate("2014-12-03");
	private static final String SECOND_USER_EMAIL = "test2@company.com";
	private static final String SECOND_COMMENT = "dummy Comment";

	private Expense expense;

	@Before
	public void setUp() {
		expense = new Expense(FIRST_AMOUNT, FIRST_DATE, FIRST_USER_EMAIL, FIRST_COMMENT);
	}

	@Test
	public void canInstantiateExpense() {
		assertNotNull(expense);
	}

	@Test
	public void updateChangesAmountExpense() {
		expense.update(SECOND_AMOUNT, SECOND_DATE, SECOND_USER_EMAIL, SECOND_COMMENT);
		assertEquals((int) SECOND_AMOUNT, (int) expense.getAmount());
	}

	@Test
	public void updateChangesDateExpense() {
		expense.update(SECOND_AMOUNT, SECOND_DATE, SECOND_USER_EMAIL, SECOND_COMMENT);
		assertEquals(SECOND_DATE, expense.getDate());
	}

	@Test
	public void updateChangesUserEmailExpense() {
		expense.update(SECOND_AMOUNT, SECOND_DATE, SECOND_USER_EMAIL, SECOND_COMMENT);
		assertEquals(SECOND_USER_EMAIL, expense.getUserEmail());
	}

	@Test
	public void updateChangesCommentExpense() {
		expense.update(SECOND_AMOUNT, SECOND_DATE, SECOND_USER_EMAIL, SECOND_COMMENT);
		assertEquals(SECOND_COMMENT, expense.getComment());
	}

}
