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
import ca.ulaval.glo4003.appemployee.domain.travel.Travel;
import ca.ulaval.glo4003.appemployee.services.PayPeriodService;
import ca.ulaval.glo4003.appemployee.services.ProjectService;
import ca.ulaval.glo4003.appemployee.services.TravelService;
import ca.ulaval.glo4003.appemployee.services.UserService;
import ca.ulaval.glo4003.appemployee.web.converters.TimeConverter;
import ca.ulaval.glo4003.appemployee.web.converters.TravelConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TravelViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TravelViewModel;

@Controller
@RequestMapping(value = "/travel")
@SessionAttributes({ "email" })
public class TravelController {

	static final String EMAIL_ATTRIBUTE = "email";
	static final String TRAVEL_ATTRIBUTE = "travelForm";
	static final String TRAVEL_JSP = "travel";
	static final String CREATE_TRAVEL_JSP = "createTravelEntry";
	static final String TIME_SHEET_SUBMIT_JSP = "timeSheetSubmitted";
	static final String EDIT_TRAVEL_ENTRY_JSP = "editTravelEntry";

	static final String NO_TASK_SELECTED_JSP = "noTaskSelectedError";

	private PayPeriodService payPeriodService;
	private ProjectService projectService;
	private UserService userService;
	private TravelService travelService;
	private TimeConverter timeConverter;
	private TravelConverter travelConverter;

	@Autowired
	public TravelController(PayPeriodService payPeriodService, TimeConverter payPeriodConverter, TaskRepository taskRepository, UserService userService,
			ProjectService projectService, TravelConverter travelConverter, TravelService travelService) {

		this.payPeriodService = payPeriodService;
		this.userService = userService;
		this.timeConverter = payPeriodConverter;
		this.projectService = projectService;
		this.travelConverter = travelConverter;
		this.travelService = travelService;

	}

	@RequestMapping(method = RequestMethod.GET)
	public String getTravel(ModelMap model, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return "redirect:/";
		}

		PayPeriod currentPayPeriod = payPeriodService.getCurrentPayPeriod();
		List<Travel> travels = userService.getTravelEntriesForUserForAPayPeriod(currentPayPeriod, session.getAttribute(EMAIL_ATTRIBUTE).toString());
		TravelViewModel form = new TravelViewModel();
		form.setPayPeriodStartDate(currentPayPeriod.getStartDate().toString());
		form.setPayPeriodEndDate(currentPayPeriod.getEndDate().toString());
		
		model.addAttribute(TRAVEL_ATTRIBUTE, form);
		model.addAttribute(EMAIL_ATTRIBUTE, session.getAttribute(EMAIL_ATTRIBUTE).toString());

		Collection<TravelViewModel> travelViewModels = travelConverter.convert(travels);

		model.addAttribute("travelEntries", travelViewModels);

		return TRAVEL_JSP;

	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String createTimeEntry(Model model, TravelViewModel TravelViewModel, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return "redirect:/";
		}

		PayPeriod currentPayPeriod = payPeriodService.getCurrentPayPeriod();
		TravelViewModel form = new TravelViewModel();
		form.setPayPeriodStartDate(currentPayPeriod.getStartDate().toString());
		form.setPayPeriodEndDate(currentPayPeriod.getEndDate().toString());
		
		model.addAttribute(TRAVEL_ATTRIBUTE, form);

		return CREATE_TRAVEL_JSP;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String saveTimeEntry(Model model, TravelViewModel travelForm, HttpSession session) throws Exception {

		travelForm.setUserEmail(session.getAttribute(EMAIL_ATTRIBUTE).toString());
		Travel newTravelEntry = travelConverter.convertToTravel(travelForm);
		travelService.store(newTravelEntry);

		return TIME_SHEET_SUBMIT_JSP;

	}

	@RequestMapping(value = "/{uId}/edit", method = RequestMethod.GET)
	public String editTimeEntry(@PathVariable String uId, Model model, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return "redirect:/";
		}

		PayPeriod currentPayPeriod = payPeriodService.getCurrentPayPeriod();
		TravelViewModel form = new TravelViewModel();
		form.setPayPeriodStartDate(currentPayPeriod.getStartDate().toString());
		form.setPayPeriodEndDate(currentPayPeriod.getEndDate().toString());

		model.addAttribute(TRAVEL_ATTRIBUTE, form);
		try {
			model.addAttribute("travelEntry", travelConverter.convertToViewModel(travelService.findByuId(uId)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return EDIT_TRAVEL_ENTRY_JSP;
	}

	@RequestMapping(value = "/{timeEntryuId}/edit", method = RequestMethod.POST)
	public String saveEditedTimeEntry(@PathVariable String uId, TravelViewModel viewModel, HttpSession session) throws Exception {


		travelService.updateTravelEntry(uId, viewModel);

		return "redirect:/travel/";
	}



}