package ca.ulaval.glo4003.appemployee.web.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import ca.ulaval.glo4003.appemployee.domain.expense.Expense;
import ca.ulaval.glo4003.appemployee.domain.expense.ExpenseRepository;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.services.PayPeriodService;
import ca.ulaval.glo4003.appemployee.web.converters.ExpenseConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ExpenseViewModel;

@Controller
@RequestMapping(value = "/expenses")
@SessionAttributes({ "email" })
public class ExpensesController {

	static final String EXPENSE_ATTRIBUTE = "expenseForm";
	static final String EMAIL_ATTRIBUTE = "email";
	static final String EXPENSES_JSP = "expenses";
	static final String EXPENSES_SUBMIT_JSP = "expensesSubmitted";

	private ExpenseRepository expenseRepository;
	private PayPeriodService payPeriodService;
	private ExpenseConverter expenseConverter;


	@Autowired
	public ExpensesController(ExpenseRepository expenseRepository, ExpenseConverter expenseConverter,
			PayPeriodService payPeriodService) {
		this.expenseRepository = expenseRepository;
		this.expenseConverter = expenseConverter;
		this.payPeriodService = payPeriodService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getExpenses(ModelMap model, HttpSession session) {
		PayPeriod currentPayPeriod = payPeriodService.getCurrentPayPeriod();
		ExpenseViewModel expenseViewModel = new ExpenseViewModel();
		expenseViewModel.setPayPeriodStartDate(currentPayPeriod.getStartDate().toString());
		expenseViewModel.setPayPeriodEndDate(currentPayPeriod.getStartDate().toString());
		model.addAttribute(EXPENSE_ATTRIBUTE, expenseViewModel);

		return EXPENSES_JSP;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String saveExpenses(@ModelAttribute(EXPENSE_ATTRIBUTE) ExpenseViewModel expenseForm, HttpSession session) {
		try {
			expenseForm.setUserEmail(session.getAttribute(EMAIL_ATTRIBUTE).toString());
			expenseRepository.store(expenseConverter.convert(expenseForm));	
		} catch (Exception e) {
			
		}
		return EXPENSES_SUBMIT_JSP;
	}
}
