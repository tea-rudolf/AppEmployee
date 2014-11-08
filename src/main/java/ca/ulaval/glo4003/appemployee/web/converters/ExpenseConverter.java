package ca.ulaval.glo4003.appemployee.web.converters;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.appemployee.domain.expense.Expense;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ExpenseViewModel;

@Component
public class ExpenseConverter {

	public Expense convert(ExpenseViewModel expenseViewModel) {
		Expense expense = new Expense();
		expense.setAmount(expenseViewModel.getAmount());
		expense.setDate(new LocalDate(expenseViewModel.getDate()));
		expense.setUserEmail(expenseViewModel.getUserEmail());
		expense.setComment(expenseViewModel.getComment());
		return expense;
	}

	public List<ExpenseViewModel> convert(List<Expense> expenses) {

		List<ExpenseViewModel> expenseViewModels = new ArrayList<ExpenseViewModel>();

		for (Expense expense : expenses) {
			ExpenseViewModel expenseViewModel = convert(expense);
			expenseViewModels.add(expenseViewModel);
		}
		return expenseViewModels;
	}

	public ExpenseViewModel convert(Expense expense) {
		ExpenseViewModel expenseViewModel = new ExpenseViewModel();
		expenseViewModel.setuId(expense.getuId());
		expenseViewModel.setAmount(expense.getAmount());
		expenseViewModel.setComment(expense.getComment());
		expenseViewModel.setDate(expense.getDate().toString());
		expenseViewModel.setuId(expense.getuId());
		expenseViewModel.setUserEmail(expense.getUserEmail());

		return expenseViewModel;
	}

}
