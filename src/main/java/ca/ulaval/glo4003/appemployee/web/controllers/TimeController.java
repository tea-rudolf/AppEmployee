package ca.ulaval.glo4003.appemployee.web.controllers;

import java.util.Collection;
import java.util.List;

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
import ca.ulaval.glo4003.appemployee.domain.repository.TaskRepository;
import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntry;
import ca.ulaval.glo4003.appemployee.services.PayPeriodService;
import ca.ulaval.glo4003.appemployee.services.ProjectService;
import ca.ulaval.glo4003.appemployee.services.UserService;
import ca.ulaval.glo4003.appemployee.web.converters.TimeConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.MessageViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TimeViewModel;

@Controller
@RequestMapping(value = "/time")
@SessionAttributes({ "email" })
public class TimeController {

	static final String EMAIL_ATTRIBUTE = "email";
	static final String TIME_ATTRIBUTE = "timeForm";
	static final String TIME_SHEET_JSP = "time";
	static final String CREATE_TIME_JSP = "createTimeEntry";
	static final String PREVIOUS_TIME_SHEET_JSP = "previousTime";
	static final String CREATE_PREVIOUS_TIME_JSP = "createPreviousTimeEntry";
	static final String TIME_SHEET_SUBMIT_JSP = "timeSheetSubmitted";
	static final String EDIT_TIME_ENTRY_JSP = "editTimeEntry";
	static final String EDIT_PREVIOUS_TIME_ENTRY_JSP = "editPreviousTimeEntry";
	static final String SIMPLE_REDIRECT = "redirect:/";
	static final String ERROR_REDIRECT = "redirect:/time/errorNoTaskSelected";
	static final String TIME_REDIRECT = "redirect:/time/";
	static final String PREVIOUS_TIME_REDIRECT = "redirect:/time/previousTime/";

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
			return SIMPLE_REDIRECT;
		}

		PayPeriod currentPayPeriod = payPeriodService.retrieveCurrentPayPeriod();
		List<Task> tasks = projectService.getAllTasksByUserId(session.getAttribute(EMAIL_ATTRIBUTE).toString());
		TimeViewModel form = timeConverter.convert(currentPayPeriod, tasks);
		model.addAttribute(TIME_ATTRIBUTE, form);
		model.addAttribute(EMAIL_ATTRIBUTE, session.getAttribute(EMAIL_ATTRIBUTE).toString());

		Collection<TimeViewModel> timeEntriesViewModels = timeConverter.convert(userService.getTimeEntriesForUserForAPayPeriod(currentPayPeriod, session
				.getAttribute(EMAIL_ATTRIBUTE).toString()));

		model.addAttribute("timeEntries", timeEntriesViewModels);

		return TIME_SHEET_JSP;

	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String createTimeEntry(Model model, TimeViewModel timeViewModel, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return SIMPLE_REDIRECT;
		}

		PayPeriod currentPayPeriod = payPeriodService.retrieveCurrentPayPeriod();
		List<Task> tasks = projectService.getAllTasksByUserId(session.getAttribute(EMAIL_ATTRIBUTE).toString());

		TimeViewModel form = timeConverter.convert(currentPayPeriod, tasks);
		model.addAttribute(TIME_ATTRIBUTE, form);

		return CREATE_TIME_JSP;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String saveTimeEntry(Model model, TimeViewModel payPeriodForm, HttpSession session) throws Exception {
		if (payPeriodForm.getTaskIdTimeEntry().equals("NONE")) {
			model.addAttribute("message", new MessageViewModel("No task selected", "No task was selected!"));
			return createTimeEntry(model, payPeriodForm, session);
		}
		payPeriodForm.setUserEmail(session.getAttribute(EMAIL_ATTRIBUTE).toString());

		payPeriodService.saveTimeEntry(payPeriodForm);

		return TIME_SHEET_SUBMIT_JSP;

	}

	@RequestMapping(value = "/{timeEntryuId}/edit", method = RequestMethod.GET)
	public String editTimeEntry(@PathVariable String timeEntryuId, Model model, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return SIMPLE_REDIRECT;
		}

		PayPeriod currentPayPeriod = payPeriodService.retrieveCurrentPayPeriod();
		List<Task> tasks = projectService.getAllTasksByUserId(session.getAttribute(EMAIL_ATTRIBUTE).toString());
		TimeViewModel form = timeConverter.convert(currentPayPeriod, tasks);

		model.addAttribute(TIME_ATTRIBUTE, form);
		TimeViewModel modelToEdit = timeConverter.convert(userService.getTimeEntry(timeEntryuId));
		modelToEdit.setTimeEntryuId(timeEntryuId);
		model.addAttribute("timeEntry", modelToEdit);

		return EDIT_TIME_ENTRY_JSP;
	}

	@RequestMapping(value = "/{timeEntryuId}/edit", method = RequestMethod.POST)
	public String saveEditedTimeEntry(@PathVariable String timeEntryuId, Model model, TimeViewModel viewModel, HttpSession session) throws Exception {

		if (viewModel.getTaskIdTimeEntry().equals("NONE")) {
			model.addAttribute("message", new MessageViewModel("No task selected", "No task was selected!"));
			return editTimeEntry(timeEntryuId, model, session);
		}

		viewModel.setUserEmail(session.getAttribute(EMAIL_ATTRIBUTE).toString());
		payPeriodService.saveTimeEntry(viewModel);

		return TIME_REDIRECT;
	}

	@RequestMapping(value = "/previousTime", method = RequestMethod.GET)
	public String getPreviousTime(ModelMap model, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return SIMPLE_REDIRECT;
		}

		PayPeriod payPeriod = payPeriodService.retrievePreviousPayPeriod();
		List<Task> tasks = projectService.getAllTasksByUserId(session.getAttribute(EMAIL_ATTRIBUTE).toString());

		TimeViewModel form = timeConverter.convert(payPeriod, tasks);

		model.addAttribute(TIME_ATTRIBUTE, form);
		model.addAttribute(EMAIL_ATTRIBUTE, session.getAttribute(EMAIL_ATTRIBUTE).toString());

		Collection<TimeViewModel> timeEntriesViewModels = timeConverter.convert(userService.getTimeEntriesForUserForAPayPeriod(payPeriod,
				session.getAttribute(EMAIL_ATTRIBUTE).toString()));
		model.addAttribute("timeEntries", timeEntriesViewModels);

		return PREVIOUS_TIME_SHEET_JSP;

	}

	@RequestMapping(value = "/previousTime/add", method = RequestMethod.GET)
	public String createPreviousTimeEntry(Model model, TimeViewModel timeViewModel, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return SIMPLE_REDIRECT;
		}

		PayPeriod currentPayPeriod = payPeriodService.retrievePreviousPayPeriod();
		List<Task> tasks = projectService.getAllTasksByUserId(session.getAttribute(EMAIL_ATTRIBUTE).toString());
		TimeViewModel form = timeConverter.convert(currentPayPeriod, tasks);
		model.addAttribute(TIME_ATTRIBUTE, form);

		return CREATE_PREVIOUS_TIME_JSP;
	}

	@RequestMapping(value = "/previousTime/add", method = RequestMethod.POST)
	public String savePreviousTimeEntry(Model model, TimeViewModel payPeriodForm, HttpSession session) throws Exception {

		if (payPeriodForm.getTaskIdTimeEntry().equals("NONE")) {
			model.addAttribute("message", new MessageViewModel("No task selected", "No task was selected!"));
			return createPreviousTimeEntry(model, payPeriodForm, session);
		}

		payPeriodForm.setUserEmail(session.getAttribute(EMAIL_ATTRIBUTE).toString());
		TimeEntry newTimeEntry = timeConverter.convert(payPeriodForm);
		payPeriodService.saveTimeEntry(payPeriodForm);

		PayPeriod payPeriod = payPeriodService.retrievePreviousPayPeriod();
		payPeriod.addTimeEntry(newTimeEntry.getUid());
		payPeriodService.updatePayPeriod(payPeriod);

		return TIME_SHEET_SUBMIT_JSP;
	}

	@RequestMapping(value = "/previousTime/{timeEntryuId}/edit", method = RequestMethod.GET)
	public String editPreviousTimeEntry(@PathVariable String timeEntryuId, Model model, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return SIMPLE_REDIRECT;
		}

		PayPeriod payPeriod = payPeriodService.retrievePreviousPayPeriod();
		List<Task> tasks = projectService.getAllTasksByUserId(session.getAttribute(EMAIL_ATTRIBUTE).toString());
		TimeViewModel form = timeConverter.convert(payPeriod, tasks);

		model.addAttribute(TIME_ATTRIBUTE, form);

		TimeViewModel modelToEdit = timeConverter.convert(userService.getTimeEntry(timeEntryuId));
		modelToEdit.setTimeEntryuId(timeEntryuId);
		model.addAttribute("timeEntry", modelToEdit);

		return EDIT_PREVIOUS_TIME_ENTRY_JSP;
	}

	@RequestMapping(value = "/previousTime/{timeEntryuId}/edit", method = RequestMethod.POST)
	public String savePreviousEditedTimeEntry(@PathVariable String timeEntryuId, Model model, TimeViewModel viewModel, HttpSession session) throws Exception {

		if (viewModel.getTaskIdTimeEntry().equals("NONE")) {
			model.addAttribute("message", new MessageViewModel("No task selected", "No task was selected!"));
			return editPreviousTimeEntry(timeEntryuId, model, session);
		}

		viewModel.setUserEmail(session.getAttribute(EMAIL_ATTRIBUTE).toString());
		payPeriodService.saveTimeEntry(viewModel);

		return PREVIOUS_TIME_REDIRECT;
	}
}
