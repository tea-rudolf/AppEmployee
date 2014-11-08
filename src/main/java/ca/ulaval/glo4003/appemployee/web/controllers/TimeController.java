package ca.ulaval.glo4003.appemployee.web.controllers;

import java.util.Collection;
import java.util.List;

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

import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.repository.TaskRepository;
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
	static final String EDIT_TIME_ENTRY_JSP = "editTimeEntry";
	static final String EDIT_PREVIOUS_TIME_ENTRY_JSP = "editPreviousTimeEntry";
	static final String NO_TASK_SELECTED_JSP = "noTaskSelectedError";

	private PayPeriodService payPeriodService;
	private ProjectService projectService;
	private UserService userService;
	private TimeConverter timeConverter;

	@Autowired
	public TimeController(PayPeriodService payPeriodService, TimeConverter payPeriodConverter, TaskRepository taskRepository, UserService userService,
			ProjectService projectService) {

		this.payPeriodService = payPeriodService;
		this.userService = userService;
		this.timeConverter = payPeriodConverter;
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
		TimeViewModel form = timeConverter.convert(currentPayPeriod, timeEntries, tasks);
		model.addAttribute(TIME_ATTRIBUTE, form);
		model.addAttribute(EMAIL_ATTRIBUTE, session.getAttribute(EMAIL_ATTRIBUTE).toString());

		Collection<TimeViewModel> timeEntriesViewModels = timeConverter.convert(userService.getTimeEntriesForUserForAPayPeriod(currentPayPeriod, session
				.getAttribute(EMAIL_ATTRIBUTE).toString()));
		model.addAttribute("timeEntries", timeEntriesViewModels);

		return TIME_SHEET_JSP;

	}

	@RequestMapping(method = RequestMethod.POST)
	public String saveTime(@ModelAttribute(TIME_ATTRIBUTE) TimeViewModel payPeriodForm, HttpSession session) throws Exception {

		if (payPeriodForm.getTaskIdTimeEntry().equals("NONE")) {
			return "redirect:/time/errorNoTaskSelected";
		}

		payPeriodForm.setUserEmail(session.getAttribute(EMAIL_ATTRIBUTE).toString());
		TimeEntry newTimeEntry = timeConverter.convertToTimeEntry(payPeriodForm);
		payPeriodService.storeTimeEntry(newTimeEntry);

		PayPeriod currentPayPeriod = payPeriodService.getCurrentPayPeriod();
		currentPayPeriod.addTimeEntry(newTimeEntry.getuId());
		payPeriodService.updatePayPeriod(currentPayPeriod);

		return TIME_SHEET_SUBMIT_JSP;

	}

	@RequestMapping(value = "/{timeEntryuId}/edit", method = RequestMethod.GET)
	public String editTimeEntry(@PathVariable String timeEntryuId, Model model, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return "redirect:/";
		}

		PayPeriod currentPayPeriod = payPeriodService.getCurrentPayPeriod();
		List<TimeEntry> timeEntries = userService.getTimeEntriesForUserForAPayPeriod(currentPayPeriod, session.getAttribute(EMAIL_ATTRIBUTE).toString());
		List<Task> tasks = projectService.getAllTasksByUserId(session.getAttribute(EMAIL_ATTRIBUTE).toString());
		TimeViewModel form = timeConverter.convert(currentPayPeriod, timeEntries, tasks);

		model.addAttribute(TIME_ATTRIBUTE, form);
		model.addAttribute("timeEntry", timeConverter.convertToViewModel(userService.getTimeEntry(timeEntryuId)));

		return EDIT_TIME_ENTRY_JSP;
	}

	@RequestMapping(value = "/{timeEntryuId}/edit", method = RequestMethod.POST)
	public String saveEditedTimeEntry(@PathVariable String timeEntryuId, TimeViewModel viewModel, HttpSession session) throws Exception {

		if (viewModel.getTaskIdTimeEntry().equals("NONE")) {
			return "redirect:/time/errorNoTaskSelected";
		}

		userService.updateTimeEntry(timeEntryuId, viewModel);

		return "redirect:/time/";
	}

	@RequestMapping(value = "/previousTime", method = RequestMethod.GET)
	public String getPreviousTime(ModelMap model, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return "redirect:/";
		}

		PayPeriod payPeriod = payPeriodService.getPreviousPayPeriod();
		List<TimeEntry> timeEntries = userService.getTimeEntriesForUserForAPayPeriod(payPeriod, session.getAttribute(EMAIL_ATTRIBUTE).toString());
		List<Task> tasks = projectService.getAllTasksByUserId(session.getAttribute(EMAIL_ATTRIBUTE).toString());

		TimeViewModel form = timeConverter.convert(payPeriod, timeEntries, tasks);

		model.addAttribute(TIME_ATTRIBUTE, form);
		model.addAttribute(EMAIL_ATTRIBUTE, session.getAttribute(EMAIL_ATTRIBUTE).toString());

		Collection<TimeViewModel> timeEntriesViewModels = timeConverter.convert(userService.getTimeEntriesForUserForAPayPeriod(payPeriod,
				session.getAttribute(EMAIL_ATTRIBUTE).toString()));
		model.addAttribute("timeEntries", timeEntriesViewModels);

		return PREVIOUS_TIME_SHEET_JSP;

	}

	@RequestMapping(value = "/previousTime", method = RequestMethod.POST)
	public String savePreviousTime(@ModelAttribute(TIME_ATTRIBUTE) TimeViewModel payPeriodForm, HttpSession session) throws Exception {

		if (payPeriodForm.getTaskIdTimeEntry() == "NONE") {
			return "redirect:/time/errorNoTaskSelected";
		}

		payPeriodForm.setUserEmail(session.getAttribute(EMAIL_ATTRIBUTE).toString());
		TimeEntry newTimeEntry = timeConverter.convertToTimeEntry(payPeriodForm);
		payPeriodService.storeTimeEntry(newTimeEntry);
		PayPeriod payPeriod = payPeriodService.getPreviousPayPeriod();
		payPeriod.addTimeEntry(newTimeEntry.getuId());
		payPeriodService.updatePayPeriod(payPeriod);

		return TIME_SHEET_SUBMIT_JSP;

	}

	@RequestMapping(value = "/previousTime/{timeEntryuId}/edit", method = RequestMethod.GET)
	public String editPreviousTimeEntry(@PathVariable String timeEntryuId, Model model, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return "redirect:/";
		}

		PayPeriod currentPayPeriod = payPeriodService.getCurrentPayPeriod();
		List<TimeEntry> timeEntries = userService.getTimeEntriesForUserForAPayPeriod(currentPayPeriod, session.getAttribute(EMAIL_ATTRIBUTE).toString());
		List<Task> tasks = projectService.getAllTasksByUserId(session.getAttribute(EMAIL_ATTRIBUTE).toString());
		TimeViewModel form = timeConverter.convert(currentPayPeriod, timeEntries, tasks);

		model.addAttribute(TIME_ATTRIBUTE, form);
		model.addAttribute("timeEntry", timeConverter.convertToViewModel(userService.getTimeEntry(timeEntryuId)));

		return EDIT_PREVIOUS_TIME_ENTRY_JSP;
	}

	@RequestMapping(value = "/previousTime/{timeEntryuId}/edit", method = RequestMethod.POST)
	public String savePreviousEditedTimeEntry(@PathVariable String timeEntryuId, TimeViewModel viewModel, HttpSession session) throws Exception {

		if (viewModel.getTaskIdTimeEntry().equals("NONE")) {
			return "redirect:/time/errorNoTaskSelected";
		}

		userService.updateTimeEntry(timeEntryuId, viewModel);

		return "redirect:/time/previousTime/";
	}

	@RequestMapping(value = "/errorNoTaskSelected", method = RequestMethod.GET)
	public String getErrorNoTaskSelected(ModelMap model, HttpSession session) {
		return NO_TASK_SELECTED_JSP;
	}

}
