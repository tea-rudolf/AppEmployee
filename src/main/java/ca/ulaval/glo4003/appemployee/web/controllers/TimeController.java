package ca.ulaval.glo4003.appemployee.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.domain.user.UserRepository;
import ca.ulaval.glo4003.appemployee.services.PayPeriodService;
import ca.ulaval.glo4003.appemployee.web.converters.PayPeriodConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.PayPeriodViewModel;

@Controller
@RequestMapping(value = "/time")
@SessionAttributes({ "email" })
public class TimeController {

	static final String EMAIL_ATTRIBUTE = "email";
	static final String PAY_PERIOD_ATTRIBUTE = "payPeriodForm";
	static final String TIME_SHEET_JSP = "timeSheet";
	static final String TIME_SHEET_SUBMIT_JSP = "timeSheetSubmitted";

	private PayPeriodService payPeriodService;
	private PayPeriodConverter payPeriodConverter;
	private UserRepository userRepository;
	private User user;

	@Autowired
	public TimeController(PayPeriodService payPeriodService, PayPeriodConverter payPeriodConverter, UserRepository userRepository) {
		this.payPeriodService = payPeriodService;
		this.payPeriodConverter = payPeriodConverter;
		this.userRepository = userRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getTime(ModelMap model, HttpSession session) {

		user = userRepository.findByEmail(session.getAttribute(EMAIL_ATTRIBUTE).toString());
		PayPeriod currentPayPeriod = payPeriodService.getCurrentPayPeriod();
		PayPeriodViewModel form = payPeriodConverter.convert(currentPayPeriod);
		model.addAttribute(PAY_PERIOD_ATTRIBUTE, form);
		model.addAttribute(EMAIL_ATTRIBUTE, user.getEmail());

		return TIME_SHEET_JSP;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String saveTime(@ModelAttribute(PAY_PERIOD_ATTRIBUTE) PayPeriodViewModel payPeriodForm, HttpSession session) {

		user = userRepository.findByEmail(session.getAttribute(EMAIL_ATTRIBUTE).toString());
		payPeriodService.updateCurrentPayPeriodTimeEntries(payPeriodConverter.convert(payPeriodForm));

		return TIME_SHEET_SUBMIT_JSP;
	}
}
