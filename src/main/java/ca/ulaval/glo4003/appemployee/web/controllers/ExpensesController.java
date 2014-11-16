package ca.ulaval.glo4003.appemployee.web.controllers;

import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.services.ExpenseService;
import ca.ulaval.glo4003.appemployee.services.PayPeriodService;
import ca.ulaval.glo4003.appemployee.services.UserService;
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
	static final String EDIT_EXPENSE_JSP = "editExpense";
	static final String CREATE_EXPENSE_JSP = "createExpense";

	private ExpenseService expenseService;
	private PayPeriodService payPeriodService;
	private ExpenseConverter expenseConverter;
	private UserService userService;

	@Autowired
	public ExpensesController(ExpenseService expenseService, ExpenseConverter expenseConverter, PayPeriodService payPeriodService, UserService userService) {
		this.expenseService = expenseService;
		this.expenseConverter = expenseConverter;
		this.payPeriodService = payPeriodService;
		this.userService = userService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getExpenses(ModelMap model, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return "redirect:/";
		}

		PayPeriod currentPayPeriod = payPeriodService.retrieveCurrentPayPeriod();
		ExpenseViewModel expenseViewModel = new ExpenseViewModel();
		expenseViewModel.setPayPeriodStartDate(currentPayPeriod.getStartDate().toString());
		expenseViewModel.setPayPeriodEndDate(currentPayPeriod.getEndDate().toString());
		model.addAttribute(EXPENSE_ATTRIBUTE, expenseViewModel);

		Collection<ExpenseViewModel> expensesViewModels = expenseConverter.convert(userService.getExpensesForUserForAPayPeriod(currentPayPeriod, session
				.getAttribute(EMAIL_ATTRIBUTE).toString()));
		model.addAttribute("expenses", expensesViewModels);

		return EXPENSES_JSP;
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String createExpense(Model model, ExpenseViewModel expenseModel, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return "redirect:/";
		}

		PayPeriod currentPayPeriod = payPeriodService.retrieveCurrentPayPeriod();
		ExpenseViewModel expenseViewModel = new ExpenseViewModel();
		expenseViewModel.setPayPeriodStartDate(currentPayPeriod.getStartDate().toString());
		expenseViewModel.setPayPeriodEndDate(currentPayPeriod.getEndDate().toString());

		model.addAttribute(EXPENSE_ATTRIBUTE, expenseViewModel);

		return CREATE_EXPENSE_JSP;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String saveExpense(Model model, ExpenseViewModel expenseForm, HttpSession session) throws Exception {
		expenseForm.setUserEmail(session.getAttribute(EMAIL_ATTRIBUTE).toString());
		expenseService.createExpense(expenseForm);

		return EXPENSES_SUBMIT_JSP;
	}

	@RequestMapping(value = "/{uId}/edit", method = RequestMethod.GET)
	public String editExpense(@PathVariable String uId, Model model, HttpSession session) throws Exception {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return "redirect:/";
		}

		PayPeriod currentPayPeriod = payPeriodService.retrieveCurrentPayPeriod();
		ExpenseViewModel expenseViewModel = expenseConverter.convert(expenseService.retrieveExpenseByUid(uId));
		expenseViewModel.setPayPeriodStartDate(currentPayPeriod.getStartDate().toString());
		expenseViewModel.setPayPeriodEndDate(currentPayPeriod.getEndDate().toString());
		model.addAttribute(EXPENSE_ATTRIBUTE, expenseViewModel);
		model.addAttribute("expense", expenseViewModel);

		return EDIT_EXPENSE_JSP;
	}

	@RequestMapping(value = "/{uId}/edit", method = RequestMethod.POST)
	public String saveEditedExpense(@PathVariable String uId, ExpenseViewModel viewModel, HttpSession session) throws Exception {

		expenseService.updateExpense(viewModel);

		return "redirect:/expenses/";
	}

}
