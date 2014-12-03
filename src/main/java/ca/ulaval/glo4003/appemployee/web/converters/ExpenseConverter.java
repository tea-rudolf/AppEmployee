package ca.ulaval.glo4003.appemployee.web.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.appemployee.domain.expense.Expense;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ExpenseViewModel;

@Component
public class ExpenseConverter {

	public List<ExpenseViewModel> convert(List<Expense> expenses) {

		List<ExpenseViewModel> expenseViewModels = new ArrayList<ExpenseViewModel>();

		for (Expense expense : expenses) {
			ExpenseViewModel expenseViewModel = convert(expense);
			expenseViewModel.setUid(expense.getUid());
			expenseViewModels.add(expenseViewModel);
		}
		return expenseViewModels;
	}

	public ExpenseViewModel convert(Expense expense) {
		ExpenseViewModel expenseViewModel = new ExpenseViewModel(
				expense.getUid(), expense.getAmount(), expense.getDate()
						.toString(), expense.getUserEmail(),
				expense.getComment());

		return expenseViewModel;
	}

}
