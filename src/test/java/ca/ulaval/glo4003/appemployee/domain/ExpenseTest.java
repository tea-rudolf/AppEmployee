package ca.ulaval.glo4003.appemployee.domain;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import ca.ulaval.glo4003.appemployee.domain.expense.Expense;

public class ExpenseTest {

	private Expense expense;

	@Test
	public void canInstantiateExpense() {
		expense = new Expense();
		assertNotNull(expense);
	}

}
