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
import ca.ulaval.glo4003.appemployee.domain.travel.Travel;
import ca.ulaval.glo4003.appemployee.services.PayPeriodService;
import ca.ulaval.glo4003.appemployee.services.TravelService;
import ca.ulaval.glo4003.appemployee.services.UserService;
import ca.ulaval.glo4003.appemployee.web.converters.TravelConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.MessageViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TravelViewModel;

@Controller
@RequestMapping(value = "/travel")
@SessionAttributes({ "email" })
public class TravelController {

	static final String EMAIL_ATTRIBUTE = "email";
	static final String TRAVEL_ATTRIBUTE = "travelForm";
	static final String TRAVEL_JSP = "travel";
	static final String CREATE_TRAVEL_JSP = "createTravelEntry";
	static final String TRAVEL_ENTRY_SUBMIT_JSP = "travelEntrySubmitted";
	static final String EDIT_TRAVEL_ENTRY_JSP = "editTravelEntry";
	static final String SIMPLE_REDIRECT = "redirect:/";
	static final String TRAVEL_REDIRECT = "redirect:/travel/";

	private PayPeriodService payPeriodService;
	private UserService userService;
	private TravelService travelService;
	private TravelConverter travelConverter;

	@Autowired
	public TravelController(PayPeriodService payPeriodService, TaskRepository taskRepository, UserService userService, TravelConverter travelConverter,
			TravelService travelService) {

		this.payPeriodService = payPeriodService;
		this.userService = userService;
		this.travelConverter = travelConverter;
		this.travelService = travelService;

	}

	@RequestMapping(method = RequestMethod.GET)
	public String getTravel(ModelMap model, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return SIMPLE_REDIRECT;
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
	public String createTravelEntry(Model model, TravelViewModel travelViewModel, HttpSession session) {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return SIMPLE_REDIRECT;
		}

		PayPeriod currentPayPeriod = payPeriodService.getCurrentPayPeriod();
		TravelViewModel form = new TravelViewModel();
		form.setPayPeriodStartDate(currentPayPeriod.getStartDate().toString());
		form.setPayPeriodEndDate(currentPayPeriod.getEndDate().toString());

		model.addAttribute(TRAVEL_ATTRIBUTE, form);

		return CREATE_TRAVEL_JSP;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String saveTravelEntry(Model model, TravelViewModel travelForm, HttpSession session) throws Exception {

		if (travelForm.getVehicule().equals("NONE")) {
			model.addAttribute("message", new MessageViewModel("No Vehicule selected", "No vehicule was selected!"));
			return createTravelEntry(model, travelForm, session);
		}

		travelForm.setUserEmail(session.getAttribute(EMAIL_ATTRIBUTE).toString());

		Travel newTravelEntry = travelConverter.convert(travelForm);

		travelService.store(newTravelEntry);

		return TRAVEL_ENTRY_SUBMIT_JSP;

	}

	@RequestMapping(value = "/{uId}/edit", method = RequestMethod.GET)
	public String editTravelEntry(@PathVariable String uId, Model model, HttpSession session) throws Exception {

		if (session.getAttribute(EMAIL_ATTRIBUTE) == null) {
			return SIMPLE_REDIRECT;
		}

		PayPeriod currentPayPeriod = payPeriodService.getCurrentPayPeriod();

		Travel travel = travelService.findByuId(uId);
		TravelViewModel mod = travelConverter.convert(travel);

		mod.setPayPeriodStartDate(currentPayPeriod.getStartDate().toString());
		mod.setPayPeriodEndDate(currentPayPeriod.getEndDate().toString());

		model.addAttribute(TRAVEL_ATTRIBUTE, mod);

		return EDIT_TRAVEL_ENTRY_JSP;
	}

	@RequestMapping(value = "/{uId}/edit", method = RequestMethod.POST)
	public String saveEditedTravelEntry(@PathVariable String uId, Model model, TravelViewModel viewModel, HttpSession session) throws Exception {

		if (viewModel.getVehicule().equals(null) || viewModel.getVehicule().equals("NONE")) {
			model.addAttribute("message", new MessageViewModel("No Vehicule selected", "No vehicule was selected!"));
			return editTravelEntry(uId, model, session);
		}
		viewModel.setUserEmail(session.getAttribute(EMAIL_ATTRIBUTE).toString());
		travelService.update(uId, viewModel);

		return TRAVEL_REDIRECT;
	}

}
