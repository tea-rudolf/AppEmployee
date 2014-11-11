package ca.ulaval.glo4003.appemployee.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.expense.Expense;
import ca.ulaval.glo4003.appemployee.domain.repository.ExpenseRepository;
import ca.ulaval.glo4003.appemployee.web.converters.ExpenseConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ExpenseViewModel;

@Service
public class ExpenseService {

	private ExpenseRepository expenseRepository;
	private ExpenseConverter expenseConverter;

	@Autowired
	public ExpenseService(ExpenseRepository expenseRepository, ExpenseConverter expenseConverter) {
		this.expenseRepository = expenseRepository;
		this.expenseConverter = expenseConverter;
	}

	public Expense findByuId(String uId) throws Exception {
		return expenseRepository.findByUid(uId);
	}

	public void store(Expense expense) throws Exception {
		expenseRepository.store(expense);
	}

	public void update(String expenseUid, ExpenseViewModel viewModel) throws Exception {
		Expense newExpense = expenseConverter.convert(viewModel);
		newExpense.setuId(viewModel.getuId());
		store(newExpense);	
	}

}
