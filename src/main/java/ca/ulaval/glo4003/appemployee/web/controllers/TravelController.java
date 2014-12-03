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

import ca.ulaval.glo4003.appemployee.services.TimeService;
import ca.ulaval.glo4003.appemployee.services.TravelService;
import ca.ulaval.glo4003.appemployee.web.viewmodels.PayPeriodViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TravelViewModel;

@Controller
@RequestMapping(value = "/travel")
@SessionAttributes({ "email" })
public class TravelController {

	static final String EMAIL_ATTRIBUTE = "email";
	static final String PAYPERIOD_ATTRIBUTE = "payPeriodForm";
	static final String TRAVEL_ATTRIBUTE = "travelForm";

	private TravelService travelService;
	private TimeService timeService;

	@Autowired
	public TravelController(TravelService travelService, TimeService timeService) {
		this.travelService = travelService;
		this.timeService = timeService;
	}

	@ModelAttribute(PAYPERIOD_ATTRIBUTE)
	public PayPeriodViewModel retrieveCurrentPayPeriodViewModel() {
		return timeService.retrieveCurrentPayPeriodViewModel();
	}

	@ModelAttribute(TRAVEL_ATTRIBUTE)
	public TravelViewModel retrieveUserTravelViewModel(HttpSession session) {
		return travelService.retrieveUserTravelViewModel(session.getAttribute(
				EMAIL_ATTRIBUTE).toString());
	}

	@RequestMapping(method = RequestMethod.GET)
	public String showTravelsList(ModelMap model, HttpSession session) {
		Collection<TravelViewModel> travelViewModels = travelService
				.retrieveUserTravelViewModelsForCurrentPayPeriod(session
						.getAttribute(EMAIL_ATTRIBUTE).toString());
		model.addAttribute("travelEntries", travelViewModels);
		return "travel";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String showCreateTravelEntryForm(Model model,
			TravelViewModel travelViewModel, HttpSession session) {
		return "createTravelEntry";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String createTravelEntry(Model model, TravelViewModel travelForm,
			HttpSession session) throws Exception {
		travelService.createTravel(travelForm);
		return "redirect:/travel/";
	}

	@RequestMapping(value = "/{uid}/edit", method = RequestMethod.GET)
	public String showEditTravelEntryForm(@PathVariable String uid,
			Model model, HttpSession session) throws Exception {
		model.addAttribute("travelForm",
				travelService.retrieveTravelViewModel(uid));
		return "editTravelEntry";
	}

	@RequestMapping(value = "/{uId}/edit", method = RequestMethod.POST)
	public String editTravelEntry(@PathVariable String uId, Model model,
			TravelViewModel viewModel, HttpSession session) throws Exception {
		travelService.editTravel(uId, viewModel);
		return "redirect:/travel/";
	}

}
