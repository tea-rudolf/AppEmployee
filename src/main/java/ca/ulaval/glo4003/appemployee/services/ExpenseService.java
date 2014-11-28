package ca.ulaval.glo4003.appemployee.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.exceptions.ExpenseNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.expense.Expense;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.repository.ExpenseRepository;
import ca.ulaval.glo4003.appemployee.web.converters.ExpenseConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ExpenseViewModel;

@Service
public class ExpenseService {

	private ExpenseRepository expenseRepository;
	private TimeService timeService;
	private ExpenseConverter expenseConverter;

	@Autowired
	public ExpenseService(ExpenseRepository expenseRepository, TimeService timeService, ExpenseConverter expenseConverter) {
		this.expenseRepository = expenseRepository;
		this.timeService = timeService;
		this.expenseConverter = expenseConverter;
	}

	public Expense retrieveExpenseByUid(String uid) throws Exception {
		Expense expense = expenseRepository.findByUid(uid);

		if (expense == null) {
			throw new ExpenseNotFoundException("Expense not found");
		}
		return expense;
	}

	public void createExpense(ExpenseViewModel viewModel) throws Exception {
		Expense expense = new Expense(viewModel.getAmount(), new LocalDate(viewModel.getDate()), viewModel.getUserEmail(), viewModel.getComment());
		expenseRepository.store(expense);
	}

	public void updateExpense(ExpenseViewModel viewModel) throws Exception {
		Expense expense = expenseRepository.findByUid(viewModel.getUid());
		expense.setAmount(viewModel.getAmount());
		expense.setComment(viewModel.getComment());
		expense.setDate(new LocalDate(viewModel.getDate()));
		expense.setUserEmail(viewModel.getUserEmail());
		expenseRepository.store(expense);
	}

	public ExpenseViewModel retrieveExpenseViewModel(String expenseUid) throws Exception {
		Expense expense = retrieveExpenseByUid(expenseUid);
		return expenseConverter.convert(expense);
	}

	public Collection<ExpenseViewModel> retrieveExpenseViewModelsListForCurrentPayPeriod(String userEmail) {
		List<Expense> expenses = retrieveUserExpensesForCurrentPayPeriod(userEmail);
		return expenseConverter.convert(expenses);
	}

	private List<Expense> retrieveUserExpensesForCurrentPayPeriod(String userEmail) {
		PayPeriod currentPayPeriod = timeService.retrieveCurrentPayPeriod();
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
