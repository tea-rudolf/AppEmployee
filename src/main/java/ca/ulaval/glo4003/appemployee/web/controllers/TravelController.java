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

import ca.ulaval.glo4003.appemployee.domain.exceptions.TravelNotFoundException;
import ca.ulaval.glo4003.appemployee.services.TimeService;
import ca.ulaval.glo4003.appemployee.services.TravelService;
import ca.ulaval.glo4003.appemployee.web.viewmodels.MessageViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.PayPeriodViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TravelViewModel;

@Controller
@RequestMapping(value = "/travel")
@SessionAttributes({ "email" })
public class TravelController {

	static final String EMAIL_ATTRIBUTE = "email";

	private TravelService travelService;
	private TimeService timeService;

	@Autowired
	public TravelController(TravelService travelService, TimeService timeService) {
		this.travelService = travelService;
		this.timeService = timeService;
	}

	@ModelAttribute("payPeriodForm")
	public PayPeriodViewModel retrieveCurrentPayPeriodViewModel() {
		return timeService.retrieveCurrentPayPeriodViewModel();
	}

	@ModelAttribute("travelForm")
	public TravelViewModel retrieveUserTravelViewModel(HttpSession session) {
		return travelService.retrieveUserTravelViewModel(session.getAttribute("email").toString());
	}

	@RequestMapping(method = RequestMethod.GET)
	public String showTravelsList(ModelMap model, HttpSession session) {
		Collection<TravelViewModel> travelViewModels = travelService.retrieveUserTravelViewModelsForCurrentPayPeriod(session.getAttribute(EMAIL_ATTRIBUTE).toString());
		model.addAttribute("travelEntries", travelViewModels);
		return "travel";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String showCreateTravelEntryForm(Model model, TravelViewModel travelViewModel, HttpSession session) {
		return "createTravelEntry";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String createTravelEntry(Model model, TravelViewModel travelForm, HttpSession session) throws Exception {
		try {
			travelService.createTravel(travelForm);
		} catch (Exception e) {
			model.addAttribute("message", new MessageViewModel(e.getClass().getSimpleName(), e.getMessage()));
		}
		return "redirect:/travel/";
	}

	@RequestMapping(value = "/{uid}/edit", method = RequestMethod.GET)
	public String showEditTravelEntryForm(@PathVariable String uid, Model model, HttpSession session) throws TravelNotFoundException {
		try {
			model.addAttribute("travelForm", travelService.retrieveTravelViewModel(uid));
		} catch (TravelNotFoundException e) {
			model.addAttribute("message", new MessageViewModel(e.getClass().getSimpleName(), e.getMessage()));
		}
		return "editTravelEntry";
	}

	@RequestMapping(value = "/{uid}/edit", method = RequestMethod.POST)
	public String editTravelEntry(@PathVariable String uid, Model model, TravelViewModel viewModel, HttpSession session) throws Exception {
		try {
			travelService.editTravel(uid, viewModel);
		} catch (Exception e) {
			model.addAttribute("message", new MessageViewModel(e.getClass().getSimpleName(), e.getMessage()));
		}
		return "redirect:/travel/";
	}

}
