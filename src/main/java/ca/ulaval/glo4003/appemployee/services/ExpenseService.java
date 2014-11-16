package ca.ulaval.glo4003.appemployee.services;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.exceptions.ExpenseNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.expense.Expense;
import ca.ulaval.glo4003.appemployee.domain.repository.ExpenseRepository;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ExpenseViewModel;

@Service
public class ExpenseService {

	private ExpenseRepository expenseRepository;

	@Autowired
	public ExpenseService(ExpenseRepository expenseRepository) {
		this.expenseRepository = expenseRepository;
	}

	public Expense retrieveExpenseByUid(String uid) throws Exception {
		Expense expense = expenseRepository.findByUid(uid);

		if (expense == null) {
			throw new ExpenseNotFoundException("Expense not found");
		}
		return expense;
	}

	public void createExpense(ExpenseViewModel viewModel) throws Exception {
		Expense expense = new Expense();
		expense.setAmount(viewModel.getAmount());
		expense.setComment(viewModel.getComment());
		expense.setDate(new LocalDate(viewModel.getDate()));
		expense.setUserEmail(viewModel.getUserEmail());
		expenseRepository.store(expense);
	}

	public void updateExpense(ExpenseViewModel viewModel) throws Exception {
		Expense expense = this.expenseRepository.findByUid(viewModel.getUid());
		expense.setAmount(viewModel.getAmount());
		expense.setComment(viewModel.getComment());
		expense.setDate(new LocalDate(viewModel.getDate()));
		expense.setUserEmail(viewModel.getUserEmail());
		expenseRepository.store(expense);
	}
	
}
