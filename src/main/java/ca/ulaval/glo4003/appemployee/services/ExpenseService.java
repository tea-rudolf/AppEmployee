package ca.ulaval.glo4003.appemployee.services;

import java.util.Collection;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.expense.Expense;
import ca.ulaval.glo4003.appemployee.domain.expense.ExpenseProcessor;
import ca.ulaval.glo4003.appemployee.domain.time.PayPeriod;
import ca.ulaval.glo4003.appemployee.web.converters.ExpenseConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ExpenseViewModel;

@Service
public class ExpenseService {

	private TimeService timeService;
	private ExpenseProcessor expenseProcessor;
	private ExpenseConverter expenseConverter;

	@Autowired
	public ExpenseService(TimeService timeService, ExpenseProcessor expenseProcessor, ExpenseConverter expenseConverter) {
		this.timeService = timeService;
		this.expenseProcessor = expenseProcessor;
		this.expenseConverter = expenseConverter;
	}

	public Expense retrieveExpenseByUid(String uid) throws Exception {
		return expenseProcessor.retrieveExpenseByUid(uid);
	}

	public void createExpense(ExpenseViewModel viewModel) throws Exception {
		expenseProcessor.createExpense(viewModel.getAmount(), new LocalDate(viewModel.getDate()), viewModel.getUserEmail(), viewModel.getComment());
	}

	public void editExpense(ExpenseViewModel viewModel) throws Exception {
		expenseProcessor.editExpense(viewModel.getUid(), viewModel.getAmount(), new LocalDate(viewModel.getDate()), viewModel.getUserEmail(),
				viewModel.getComment());
	}

	public ExpenseViewModel retrieveExpenseViewModel(String expenseUid) throws Exception {
		Expense expense = expenseProcessor.retrieveExpenseByUid(expenseUid);
		return expenseConverter.convert(expense);
	}

	public Collection<ExpenseViewModel> retrieveExpenseViewModelsListForCurrentPayPeriod(String userEmail) {
		List<Expense> expenses = retrieveUserExpensesForCurrentPayPeriod(userEmail);
		return expenseConverter.convert(expenses);
	}

	private List<Expense> retrieveUserExpensesForCurrentPayPeriod(String userEmail) {
		PayPeriod currentPayPeriod = timeService.retrieveCurrentPayPeriod();
		return expenseProcessor.retrieveUserExpensesForCurrentPayPeriod(userEmail, currentPayPeriod);
	}

}
