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
	public String getExpenses(ModelMap model, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return "redirect:/";
		}

		Collection<ExpenseViewModel> expensesViewModels = expenseService.retrieveExpenseViewModelsListForCurrentPayPeriod(session.getAttribute(EMAIL_ATTRIBUTE)
				.toString());

		model.addAttribute("expenses", expensesViewModels);

		return "expenses";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String createExpense(Model model, ExpenseViewModel expenseModel, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return "redirect:/";
		}

		PayPeriodViewModel payPeriodViewModel = payPeriodService.retrievePayPeriodViewModel();

		model.addAttribute("expenseForm", new ExpenseViewModel());
		model.addAttribute("payPeriod", payPeriodViewModel);

		return "createExpense";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String saveExpense(Model model, ExpenseViewModel expenseForm, HttpSession session) throws Exception {
		expenseForm.setUserEmail(session.getAttribute(EMAIL_ATTRIBUTE).toString());
		expenseService.createExpense(expenseForm);

		return "expensesSubmitted";
	}

	@RequestMapping(value = "/{uid}/edit", method = RequestMethod.GET)
	public String editExpense(@PathVariable String uid, Model model, HttpSession session) throws Exception {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return "redirect:/";
		}

		PayPeriodViewModel payPeriodViewModel = payPeriodService.retrievePayPeriodViewModel();
		ExpenseViewModel expenseViewModel = expenseService.retrieveExpenseViewModel(uid);

		model.addAttribute("payPeriod", payPeriodViewModel);
		model.addAttribute("expense", expenseViewModel);

		return "editExpense";
	}

	@RequestMapping(value = "/{uId}/edit", method = RequestMethod.POST)
	public String saveEditedExpense(@PathVariable String uId, ExpenseViewModel viewModel, HttpSession session) throws Exception {
		expenseService.updateExpense(viewModel);

		return "redirect:/expenses/";
	}

}
