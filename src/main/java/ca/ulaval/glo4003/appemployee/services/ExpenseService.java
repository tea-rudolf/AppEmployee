package ca.ulaval.glo4003.appemployee.services;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public Expense findByuId(String uId) throws Exception {
		return expenseRepository.findByUid(uId);
	}
	
	public void store(Expense expense) throws Exception {
		expenseRepository.store(expense);
	}

	public void updateExpense(String expenseuId, ExpenseViewModel viewModel) {
		Expense expense = expenseRepository.findByUid(expenseuId);
		expense.setAmount(viewModel.getAmount());
		expense.setComment(viewModel.getComment());
		expense.setDate(new LocalDate(viewModel.getDate()));
		expense.setUserEmail(viewModel.getUserEmail());

		try {
			expenseRepository.store(expense);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
