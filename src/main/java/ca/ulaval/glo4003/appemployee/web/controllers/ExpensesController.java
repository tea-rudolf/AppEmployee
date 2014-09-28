package ca.ulaval.glo4003.appemployee.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import ca.ulaval.glo4003.appemployee.domain.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.services.PayPeriodService;
import ca.ulaval.glo4003.appemployee.web.converters.PayPeriodConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.PayPeriodViewModel;

@Controller
@RequestMapping(value = "/expenses")
@SessionAttributes({ "email" })
public class ExpensesController {

	static final String PAY_PERIOD_ATTRIBUTE = "payPeriodForm";
	static final String EMAIL_ATTRIBUTE = "email";
	static final String EXPENSES_JSP = "expenses";
	static final String EXPENSES_SUBMIT_JSP = "expensesSubmitted";

	private PayPeriodService payPeriodService;
	private PayPeriodConverter payPeriodConverter;
	private User user;

	@Autowired
	public ExpensesController(PayPeriodService payPeriodService, PayPeriodConverter payPeriodConverter) {
		this.payPeriodService = payPeriodService;
		this.payPeriodConverter = payPeriodConverter;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getExpenses(ModelMap model, HttpSession session) {

		user = payPeriodService.getUserByEmail(session.getAttribute(EMAIL_ATTRIBUTE).toString());
		PayPeriod currentPayPeriod = user.getCurrentPayPeriod();
		PayPeriodViewModel form = payPeriodConverter.convert(currentPayPeriod);

		model.addAttribute(PAY_PERIOD_ATTRIBUTE, form);
		model.addAttribute(EMAIL_ATTRIBUTE, user.getEmail());

		return EXPENSES_JSP;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String saveExpenses(@ModelAttribute(PAY_PERIOD_ATTRIBUTE) PayPeriodViewModel payPeriodForm, HttpSession session) {

		user = payPeriodService.getUserByEmail(session.getAttribute(EMAIL_ATTRIBUTE).toString());
		payPeriodService.updateUserCurrentPayPeriodExpenses(user.getEmail(), payPeriodConverter.convert(payPeriodForm));

		return EXPENSES_SUBMIT_JSP;
	}

	@RequestMapping(value = "/logout")
	public String logout(SessionStatus sessionStatus, ModelMap model) {
		sessionStatus.setComplete();
		model.clear();
		return "redirect:/";
	}
}
