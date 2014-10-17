package ca.ulaval.glo4003.appemployee.web.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.appemployee.domain.expense.Expense;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ExpenseViewModel;

@Component
public class ExpenseConverter {

	public Expense convert(ExpenseViewModel expenseViewModel) {
		Expense expense = new Expense(expenseViewModel.getuId());
		expense.setAmount(expenseViewModel.getAmount());
		expense.setDate(expenseViewModel.getDate());
		expense.setUserId(expenseViewModel.getUserId());
		expense.setComment(expenseViewModel.getComment());
		
		return expense;
	}

	public List<ExpenseViewModel> convert(List<Expense> expenses) {
	
		List<ExpenseViewModel> expenseViewModels = new  ArrayList<ExpenseViewModel>();
		
		for (Expense expense : expenses) {
			ExpenseViewModel expenseViewModel = convert(expense);
			expenseViewModels.add(expenseViewModel);
		}
		return expenseViewModels;
	}
	
	public ExpenseViewModel convert(Expense expense) {
		ExpenseViewModel expenseViewModel = new ExpenseViewModel();
		expenseViewModel.setAmount(expense.getAmount());
		expenseViewModel.setComment(expense.getComment());
		expenseViewModel.setDate(expense.getDate());
		expenseViewModel.setuId(expense.getuId());
		expenseViewModel.setUserId(expense.getUserId());
		
		return expenseViewModel;	
	}

}
