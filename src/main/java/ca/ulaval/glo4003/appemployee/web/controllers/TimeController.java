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

import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.task.TaskRepository;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntry;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntryRepository;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.domain.user.UserRepository;
import ca.ulaval.glo4003.appemployee.services.PayPeriodService;
import ca.ulaval.glo4003.appemployee.services.UserService;
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
	private UserService userService;
	private PayPeriodConverter payPeriodConverter;
	private UserRepository userRepository;
	private TimeEntryRepository timeEntryRepository;
	private User user;

	@Autowired
	public TimeController(PayPeriodService payPeriodService, PayPeriodConverter payPeriodConverter,
			UserRepository userRepository, TimeEntryRepository timeEntryRepository,
			 TaskRepository taskRepository, UserService userService) {
		
		this.payPeriodService = payPeriodService;
		this.userService = userService;
		this.payPeriodConverter = payPeriodConverter;
		this.userRepository = userRepository;
		this.timeEntryRepository = timeEntryRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getTime(ModelMap model, HttpSession session) {

		user = userRepository.findByEmail(session.getAttribute(EMAIL_ATTRIBUTE).toString());
		
		PayPeriod currentPayPeriod = payPeriodService.getCurrentPayPeriod();
		List<TimeEntry> timeEntries = userService.getTimeEntriesForUser(currentPayPeriod, user.getEmail());
		List<Task> tasks =  userService.getTasksForUserForAPayPerod(currentPayPeriod, user.getEmail());
		
		PayPeriodViewModel form = payPeriodConverter.convert(currentPayPeriod, timeEntries, tasks);
		model.addAttribute(PAY_PERIOD_ATTRIBUTE, form);
		model.addAttribute(EMAIL_ATTRIBUTE, user.getEmail());

		return TIME_SHEET_JSP;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String saveTime(@ModelAttribute(PAY_PERIOD_ATTRIBUTE) PayPeriodViewModel payPeriodForm, HttpSession session) throws Exception {
        
		TimeEntry newTimeEntry = payPeriodConverter.convertToTimeEntry(payPeriodForm);
        timeEntryRepository.store(newTimeEntry);
        PayPeriod currentPayPeriod = payPeriodService.getCurrentPayPeriod();
        currentPayPeriod.addTimeEntry(newTimeEntry.getuId());
		payPeriodService.updateCurrentPayPeriod(currentPayPeriod);

		return TIME_SHEET_SUBMIT_JSP;
		
	}
	

}
