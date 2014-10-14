package ca.ulaval.glo4003.appemployee.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;
import org.springframework.stereotype.Repository;
import ca.ulaval.glo4003.appemployee.domain.expense.Expense;
import ca.ulaval.glo4003.appemployee.domain.expense.ExpenseRepository;

@Repository
@Singleton
public class XmlExpenseRepository implements ExpenseRepository {

	private XmlRepositoryMarshaller xmlRepositoryMarshaller = XmlRepositoryMarshaller.getInstance();
	private List<Expense> expenses = new ArrayList<Expense>();

	public XmlExpenseRepository() {
		unmarshall();
	}

	public void add(Expense expense) {
		expenses.add(expense);
		marshall();
	}

	private void marshall() {
		XmlRootNode xmlRootNode = xmlRepositoryMarshaller.getRootNode();
		xmlRootNode.setExpenses(expenses);
		xmlRepositoryMarshaller.marshall();
	}

	private void unmarshall() {
		xmlRepositoryMarshaller.unmarshall();
		XmlRootNode xmlRootNode = xmlRepositoryMarshaller.getRootNode();
		expenses = xmlRootNode.getExpenses();
	}
	

	public void update(Expense expense) {
		marshall();
	}

	public List<Expense> findAllExpensesForUser(String userId) {
		List<Expense> expensesFound = new ArrayList<Expense>();
		for (Expense expense : expenses) {
			if (expense.getUserId().equals(userId)) {
				expensesFound.add(expense);
			}
		}
		return expensesFound;
	}

}
