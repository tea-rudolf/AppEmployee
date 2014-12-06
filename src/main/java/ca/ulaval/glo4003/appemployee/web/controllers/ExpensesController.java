package ca.ulaval.glo4003.appemployee.web.controllers;

import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	static final String PAYPERIOD_ATTRIBUTE = "payPeriod";

	private ExpenseService expenseService;
	private TimeService timeService;

	@Autowired
	public ExpensesController(ExpenseService expenseService, TimeService payPeriodService) {
		this.expenseService = expenseService;
		this.timeService = payPeriodService;
	}

	@ModelAttribute(PAYPERIOD_ATTRIBUTE)
	public PayPeriodViewModel currentPayPeriodDates(HttpSession session) {
		return timeService.retrieveCurrentPayPeriodViewModel();
	}

	@RequestMapping(method = RequestMethod.GET)
	public String showExpensesList(ModelMap model, HttpSession session) {
		Collection<ExpenseViewModel> expensesViewModels = expenseService.retrieveExpenseViewModelsListForCurrentPayPeriod(session.getAttribute(EMAIL_ATTRIBUTE).toString());
		model.addAttribute("expenses", expensesViewModels);
		return "expenses";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String showCreateExpenseForm(Model model, ExpenseViewModel expenseModel, HttpSession session) {
		model.addAttribute("expenseForm", expenseService.retrieveUserExpenseViewModel(session.getAttribute(EMAIL_ATTRIBUTE).toString()));
		return "createExpense";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String createExpense(Model model, ExpenseViewModel expenseForm, HttpSession session) throws Exception {
		expenseService.createExpense(expenseForm);
		return "redirect:/expenses/";
	}

	@RequestMapping(value = "/{uid}/edit", method = RequestMethod.GET)
	public String showEditExpenseForm(@PathVariable String uid, Model model, HttpSession session) throws Exception {
		ExpenseViewModel expenseViewModel = expenseService.retrieveExpenseViewModel(uid);
		model.addAttribute("expense", expenseViewModel);
		return "editExpense";
	}

	@RequestMapping(value = "/{Uid}/edit", method = RequestMethod.POST)
	public String editExpense(@PathVariable String Uid, ExpenseViewModel viewModel, HttpSession session) throws Exception {
		expenseService.editExpense(viewModel);
		return "redirect:/expenses/" + Uid + "/edit";
	}

}
