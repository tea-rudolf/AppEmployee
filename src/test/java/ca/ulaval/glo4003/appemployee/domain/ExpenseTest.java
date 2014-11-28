package ca.ulaval.glo4003.appemployee.domain;

import static org.junit.Assert.*;

import org.joda.time.LocalDate;
import org.junit.Test;

import ca.ulaval.glo4003.appemployee.domain.expense.Expense;

public class ExpenseTest {

	private static final double AMOUNT = 500.50;
	private static final LocalDate DATE = new LocalDate();
	private static final String USER_EMAIL = "test@company.com";
	private static final String COMMENT = "nothing to say";

	private Expense expense;

	@Test
	public void canInstantiateExpense() {
		expense = new Expense(AMOUNT, DATE, USER_EMAIL, COMMENT);
		assertNotNull(expense);
	}

}
