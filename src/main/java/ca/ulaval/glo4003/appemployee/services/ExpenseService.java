package ca.ulaval.glo4003.appemployee.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.expense.Expense;
import ca.ulaval.glo4003.appemployee.domain.repository.ExpenseRepository;

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

}
