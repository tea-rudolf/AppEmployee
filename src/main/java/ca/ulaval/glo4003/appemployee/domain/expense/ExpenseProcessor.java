package ca.ulaval.glo4003.appemployee.domain.expense;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.appemployee.domain.exceptions.ExpenseNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.repository.ExpenseRepository;
import ca.ulaval.glo4003.appemployee.domain.time.PayPeriod;

@Component
public class ExpenseProcessor {

	private ExpenseRepository expenseRepository;

	@Autowired
	public ExpenseProcessor(ExpenseRepository expenseRepository) {
		this.expenseRepository = expenseRepository;
	}

	public void createExpense(double amount, LocalDate date, String userEmail, String comment) throws Exception {
		Expense expense = new Expense(amount, date, userEmail, comment);
		expenseRepository.store(expense);
	}

	public void editExpense(String uid, double amount, LocalDate date, String userEmail, String comment) throws Exception {
		Expense expense = expenseRepository.findByUid(uid);
		expense.update(amount, date, userEmail, comment);
		expenseRepository.store(expense);
	}

	public Expense retrieveExpenseByUid(String uid) throws ExpenseNotFoundException {
		Expense expense = expenseRepository.findByUid(uid);

		if (expense == null) {
			throw new ExpenseNotFoundException("Expense not found");
		}
		return expense;
	}

	public List<Expense> retrieveUserExpensesForCurrentPayPeriod(String userEmail, PayPeriod currentPayPeriod) {
		ArrayList<Expense> expenses = new ArrayList<Expense>();

		for (Expense expense : expenseRepository.findAll()) {
			if (expense.getUserEmail().equals(userEmail) && expense.getDate().isBefore(currentPayPeriod.getEndDate().plusDays(1))
					&& expense.getDate().isAfter(currentPayPeriod.getStartDate().minusDays(1))) {
				expenses.add(expense);
			}
		}
		return expenses;
	}

}
