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
import ca.ulaval.glo4003.appemployee.domain.repository.TaskRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.TimeEntryRepository;
import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntry;
import ca.ulaval.glo4003.appemployee.services.PayPeriodService;
import ca.ulaval.glo4003.appemployee.services.ProjectService;
import ca.ulaval.glo4003.appemployee.services.UserService;
import ca.ulaval.glo4003.appemployee.web.converters.TimeConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TimeViewModel;

@Controller
@RequestMapping(value = "/time")
@SessionAttributes({ "email" })
public class TimeController {

	static final String EMAIL_ATTRIBUTE = "email";
	static final String TIME_ATTRIBUTE = "timeForm";
	static final String TIME_SHEET_JSP = "time";
	static final String PREVIOUS_TIME_SHEET_JSP = "previousTime";
	static final String TIME_SHEET_SUBMIT_JSP = "timeSheetSubmitted";
	static final String NO_TASK_SELECTED_JSP = "noTaskSelectedError";

	private PayPeriodService payPeriodService;
	private ProjectService projectService;
	private UserService userService;
	private TimeConverter payPeriodConverter;
	private TimeEntryRepository timeEntryRepository;

	@Autowired
	public TimeController(PayPeriodService payPeriodService, TimeConverter payPeriodConverter, TimeEntryRepository timeEntryRepository,
			TaskRepository taskRepository, UserService userService, ProjectService projectService) {

		this.payPeriodService = payPeriodService;
		this.userService = userService;
		this.payPeriodConverter = payPeriodConverter;
		this.timeEntryRepository = timeEntryRepository;
		this.projectService = projectService;

	}

	@RequestMapping(method = RequestMethod.GET)
	public String getTime(ModelMap model, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return "redirect:/";
		}
		PayPeriod currentPayPeriod = payPeriodService.getCurrentPayPeriod();
		List<TimeEntry> timeEntries = userService.getTimeEntriesForUserForAPayPeriod(currentPayPeriod, session.getAttribute(EMAIL_ATTRIBUTE).toString());
		List<Task> tasks = projectService.getAllTasksByUserId(session.getAttribute(EMAIL_ATTRIBUTE).toString());
		TimeViewModel form = payPeriodConverter.convert(currentPayPeriod, timeEntries, tasks);

		model.addAttribute(TIME_ATTRIBUTE, form);
		model.addAttribute(EMAIL_ATTRIBUTE, session.getAttribute(EMAIL_ATTRIBUTE).toString());
		return TIME_SHEET_JSP;

	}

	@RequestMapping(method = RequestMethod.POST)
	public String saveTime(@ModelAttribute(TIME_ATTRIBUTE) TimeViewModel payPeriodForm, HttpSession session) throws Exception {

		if (payPeriodForm.getTaskIdTimeEntry() == null) {
			return "redirect:/time/errorNoTaskSelected";
		}

		TimeEntry newTimeEntry = payPeriodConverter.convertToTimeEntry(payPeriodForm);
		timeEntryRepository.store(newTimeEntry);
		PayPeriod currentPayPeriod = payPeriodService.getCurrentPayPeriod();
		currentPayPeriod.addTimeEntry(newTimeEntry.getuId());
		payPeriodService.updatePayPeriod(currentPayPeriod);

		return TIME_SHEET_SUBMIT_JSP;

	}

	@RequestMapping(value = "/previousTime", method = RequestMethod.GET)
	public String getPreviousTime(ModelMap model, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return "redirect:/";
		}

		PayPeriod payPeriod = payPeriodService.getPreviousPayPeriod();
		List<TimeEntry> timeEntries = userService.getTimeEntriesForUserForAPayPeriod(payPeriod, session.getAttribute(EMAIL_ATTRIBUTE).toString());
		List<Task> tasks = projectService.getAllTasksByUserId(session.getAttribute(EMAIL_ATTRIBUTE).toString());

		TimeViewModel form = payPeriodConverter.convert(payPeriod, timeEntries, tasks);

		model.addAttribute(TIME_ATTRIBUTE, form);
		model.addAttribute(EMAIL_ATTRIBUTE, session.getAttribute(EMAIL_ATTRIBUTE).toString());

		return PREVIOUS_TIME_SHEET_JSP;

	}

	@RequestMapping(value = "/previousTime", method = RequestMethod.POST)
	public String savePreviousTime(@ModelAttribute(TIME_ATTRIBUTE) TimeViewModel payPeriodForm, HttpSession session) throws Exception {

		if (payPeriodForm.getTaskIdTimeEntry() == null) {
			return "redirect:/time/errorNoTaskSelected";
		}

		TimeEntry newTimeEntry = payPeriodConverter.convertToTimeEntry(payPeriodForm);
		timeEntryRepository.store(newTimeEntry);
		PayPeriod payPeriod = payPeriodService.getPreviousPayPeriod();
		payPeriod.addTimeEntry(newTimeEntry.getuId());
		payPeriodService.updatePayPeriod(payPeriod);

		return TIME_SHEET_SUBMIT_JSP;

	}

	@RequestMapping(value = "/errorNoTaskSelected", method = RequestMethod.GET)
	public String getErrorNoTaskSelected(ModelMap model, HttpSession session) {
		return NO_TASK_SELECTED_JSP;
	}

}
