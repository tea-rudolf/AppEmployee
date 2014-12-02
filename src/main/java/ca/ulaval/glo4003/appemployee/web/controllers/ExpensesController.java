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

import ca.ulaval.glo4003.appemployee.services.ExpenseService;
import ca.ulaval.glo4003.appemployee.services.TimeService;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ExpenseViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.PayPeriodViewModel;

@Controller
@RequestMapping(value = "/expenses")
@SessionAttributes({ "email" })
public class ExpensesController {

	static final String EMAIL_ATTRIBUTE = "email";

	private ExpenseService expenseService;
	private TimeService payPeriodService;

	@Autowired
	public ExpensesController(ExpenseService expenseService, TimeService payPeriodService) {
		this.expenseService = expenseService;
		this.payPeriodService = payPeriodService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String showExpensesList(ModelMap model, HttpSession session) {
		Collection<ExpenseViewModel> expensesViewModels = expenseService.retrieveExpenseViewModelsListForCurrentPayPeriod(session.getAttribute(EMAIL_ATTRIBUTE)
				.toString());

		model.addAttribute("expenses", expensesViewModels);

		return "expenses";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String showCreateExpenseForm(Model model, ExpenseViewModel expenseModel, HttpSession session) {
		PayPeriodViewModel payPeriodViewModel = payPeriodService.retrieveCurrentPayPeriodViewModel();

		model.addAttribute("expenseForm", new ExpenseViewModel());
		model.addAttribute("payPeriod", payPeriodViewModel);

		return "createExpense";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String createExpense(Model model, ExpenseViewModel expenseForm, HttpSession session) throws Exception {
		expenseForm.setUserEmail(session.getAttribute(EMAIL_ATTRIBUTE).toString());
		expenseService.createExpense(expenseForm);

		return "expensesSubmitted";
	}

	@RequestMapping(value = "/{uid}/edit", method = RequestMethod.GET)
	public String showEditExpenseForm(@PathVariable String uid, Model model, HttpSession session) throws Exception {
		PayPeriodViewModel payPeriodViewModel = payPeriodService.retrieveCurrentPayPeriodViewModel();
		ExpenseViewModel expenseViewModel = expenseService.retrieveExpenseViewModel(uid);

		model.addAttribute("payPeriod", payPeriodViewModel);
		model.addAttribute("expense", expenseViewModel);

		return "editExpense";
	}

	@RequestMapping(value = "/{uId}/edit", method = RequestMethod.POST)
	public String editExpense(@PathVariable String uId, ExpenseViewModel viewModel, HttpSession session) throws Exception {
		expenseService.editExpense(viewModel);

		return "redirect:/expenses/";
	}

}
