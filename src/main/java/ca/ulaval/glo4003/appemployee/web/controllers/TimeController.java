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

import ca.ulaval.glo4003.appemployee.domain.repository.TaskRepository;
import ca.ulaval.glo4003.appemployee.services.TimeService;
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
	static final String LOGIN_REDIRECT = "redirect:/";
	static final String ERROR_REDIRECT = "redirect:/time/errorNoTaskSelected";
	static final String TIME_REDIRECT = "redirect:/time/";
	static final String PREVIOUS_TIME_REDIRECT = "redirect:/time/previousTime/";

	private TimeService timeService;

	@Autowired
	public TimeController(TimeService payPeriodService, TimeConverter payPeriodConverter, TaskRepository taskRepository, UserService userService,
			ProjectService projectService) {
		this.timeService = payPeriodService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getTime(ModelMap model, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return LOGIN_REDIRECT;
		}
		
		TimeViewModel form = timeService.retrieveViewModelForCurrentPayPeriod(session.getAttribute(EMAIL_ATTRIBUTE).toString());
		Collection<TimeViewModel> timeEntriesViewModels = timeService.retrieveTimeEntriesViewModelsForCurrentPayPeriod(session.getAttribute(
				EMAIL_ATTRIBUTE).toString());

		model.addAttribute(TIME_ATTRIBUTE, form);
		model.addAttribute(EMAIL_ATTRIBUTE, session.getAttribute(EMAIL_ATTRIBUTE).toString());
		model.addAttribute("timeEntries", timeEntriesViewModels);

		return TIME_SHEET_JSP;
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String createTimeEntry(Model model, TimeViewModel timeViewModel, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return LOGIN_REDIRECT;
		}

		TimeViewModel form = timeService.retrieveViewModelForCurrentPayPeriod(session.getAttribute(EMAIL_ATTRIBUTE).toString());
		model.addAttribute(TIME_ATTRIBUTE, form);

		return CREATE_TIME_JSP;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String saveTimeEntry(Model model, TimeViewModel payPeriodForm, HttpSession session) throws Exception {

		payPeriodForm.setUserEmail(session.getAttribute(EMAIL_ATTRIBUTE).toString());
		timeService.createTimeEntry(payPeriodForm, timeService.retrieveCurrentPayPeriod());

		return TIME_SHEET_SUBMIT_JSP;
	}

	@RequestMapping(value = "/{timeEntryUid}/edit", method = RequestMethod.GET)
	public String editTimeEntry(@PathVariable String timeEntryUid, Model model, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return LOGIN_REDIRECT;
		}

		TimeViewModel form = timeService.retrieveViewModelForCurrentPayPeriod(session.getAttribute(EMAIL_ATTRIBUTE).toString());
		TimeViewModel modelToEdit = timeService.retrieveViewModelForDesiredTimeEntry(timeEntryUid);

		model.addAttribute(TIME_ATTRIBUTE, form);
		modelToEdit.setTimeEntryUid(timeEntryUid);
		model.addAttribute("timeEntry", modelToEdit);

		return EDIT_TIME_ENTRY_JSP;
	}

	@RequestMapping(value = "/{timeEntryUid}/edit", method = RequestMethod.POST)
	public String saveEditedTimeEntry(@PathVariable String timeEntryUid, Model model, TimeViewModel viewModel, HttpSession session) throws Exception {

		viewModel.setUserEmail(session.getAttribute(EMAIL_ATTRIBUTE).toString());
		timeService.updateTimeEntry(viewModel);

		return TIME_REDIRECT;
	}

	@RequestMapping(value = "/previousTime", method = RequestMethod.GET)
	public String getPreviousTime(ModelMap model, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return LOGIN_REDIRECT;
		}

		TimeViewModel form = timeService.retrieveViewModelForPreviousPayPeriod(session.getAttribute(EMAIL_ATTRIBUTE).toString());
		Collection<TimeViewModel> timeEntriesViewModels = timeService.retrieveTimeEntriesViewModelsForPreviousPayPeriod(session.getAttribute(
				EMAIL_ATTRIBUTE).toString());

		model.addAttribute(TIME_ATTRIBUTE, form);
		model.addAttribute(EMAIL_ATTRIBUTE, session.getAttribute(EMAIL_ATTRIBUTE).toString());
		model.addAttribute("timeEntries", timeEntriesViewModels);

		return PREVIOUS_TIME_SHEET_JSP;
	}

	@RequestMapping(value = "/previousTime/add", method = RequestMethod.GET)
	public String createPreviousTimeEntry(Model model, TimeViewModel timeViewModel, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return LOGIN_REDIRECT;
		}

		TimeViewModel form = timeService.retrieveViewModelForPreviousPayPeriod(session.getAttribute(EMAIL_ATTRIBUTE).toString());
		model.addAttribute(TIME_ATTRIBUTE, form);

		return CREATE_PREVIOUS_TIME_JSP;
	}

	@RequestMapping(value = "/previousTime/add", method = RequestMethod.POST)
	public String savePreviousTimeEntry(Model model, TimeViewModel payPeriodForm, HttpSession session) throws Exception {

		timeService.createTimeEntry(payPeriodForm, timeService.retrievePreviousPayPeriod());

		return TIME_SHEET_SUBMIT_JSP;
	}

	@RequestMapping(value = "/previousTime/{timeEntryUid}/edit", method = RequestMethod.GET)
	public String editPreviousTimeEntry(@PathVariable String timeEntryUid, Model model, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return LOGIN_REDIRECT;
		}

		TimeViewModel form = timeService.retrieveViewModelForPreviousPayPeriod(session.getAttribute(EMAIL_ATTRIBUTE).toString());
		TimeViewModel modelToEdit = timeService.retrieveViewModelForDesiredTimeEntry(timeEntryUid);

		model.addAttribute(TIME_ATTRIBUTE, form);
		modelToEdit.setTimeEntryUid(timeEntryUid);
		model.addAttribute("timeEntry", modelToEdit);

		return EDIT_PREVIOUS_TIME_ENTRY_JSP;
	}

	@RequestMapping(value = "/previousTime/{timeEntryUid}/edit", method = RequestMethod.POST)
	public String savePreviousEditedTimeEntry(@PathVariable String timeEntryUid, Model model, TimeViewModel viewModel, HttpSession session) throws Exception {

		if (viewModel.getTaskIdTimeEntry().equals("NONE")) {
			model.addAttribute("message", new MessageViewModel("No task selected", "No task was selected!"));
			return editPreviousTimeEntry(timeEntryUid, model, session);
		}

		timeService.updateTimeEntry(viewModel);

		return PREVIOUS_TIME_REDIRECT;
	}

}
