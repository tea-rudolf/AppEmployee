package ca.ulaval.glo4003.appemployee.persistence;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.appemployee.domain.expense.Expense;

public class XMLExpenseRepositoryIT {

	private static final String USER_EMAIL = "employee@employee.com";
	private static final int NUMBER_OF_EXPENSES = 3;

	private XMLGenericMarshaller<ExpenseXMLAssembler> marshaller;
	private XMLExpenseRepository repository;

	@Before
	public void setup() throws Exception {
		marshaller = new XMLGenericMarshaller<ExpenseXMLAssembler>(
				ExpenseXMLAssembler.class);
		marshaller.setResourcesLoader(new ResourcesLoader());
		repository = new XMLExpenseRepository(marshaller);
	}

	@Test
	public void givenExistingUserReturnsAllExpensesForThisUser()
			throws Exception {
		repository.store(createExpense());
		repository.store(createExpense());
		repository.store(createExpense());

		List<Expense> expenses = repository.findAllExpensesByUser(USER_EMAIL);

		assertEquals(NUMBER_OF_EXPENSES, expenses.size());

	}

	private Expense createExpense() {
		Expense expense = new Expense();
		expense.setUserEmail(USER_EMAIL);
		return expense;
	}

}
