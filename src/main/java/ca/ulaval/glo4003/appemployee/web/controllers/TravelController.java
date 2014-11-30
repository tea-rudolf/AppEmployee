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
import ca.ulaval.glo4003.appemployee.services.TravelService;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TravelViewModel;

@Controller
@RequestMapping(value = "/travel")
@SessionAttributes({ "email" })
public class TravelController {

	static final String EMAIL_ATTRIBUTE = "email";

	private TravelService travelService;

	@Autowired
	public TravelController(TaskRepository taskRepository, TravelService travelService) {
		this.travelService = travelService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String showTravelsList(ModelMap model, HttpSession session) {
		TravelViewModel form = travelService.retrieveTravelViewModelForCurrentPayPeriod(session.getAttribute(EMAIL_ATTRIBUTE).toString());
		Collection<TravelViewModel> travelViewModels = travelService.retrieveTravelViewModelsForCurrentPayPeriod(session.getAttribute(EMAIL_ATTRIBUTE)
				.toString());

		model.addAttribute("travelForm", form);
		model.addAttribute("travelEntries", travelViewModels);

		return "travel";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String showCreateTravelEntryForm(Model model, TravelViewModel travelViewModel, HttpSession session) {
		TravelViewModel form = travelService.retrieveTravelViewModelForCurrentPayPeriod(session.getAttribute(EMAIL_ATTRIBUTE).toString());

		model.addAttribute("travelForm", form);

		return "createTravelEntry";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String createTravelEntry(Model model, TravelViewModel travelForm, HttpSession session) throws Exception {
		travelService.createTravel(travelForm);

		return "travelEntrySubmitted";
	}

	@RequestMapping(value = "/{uid}/edit", method = RequestMethod.GET)
	public String showEditTravelEntryForm(@PathVariable String uid, Model model, HttpSession session) throws Exception {
		TravelViewModel travelViewModel = travelService.retrieveTravelViewModelForExistingTravel(uid);

		model.addAttribute("travelForm", travelViewModel);

		return "editTravelEntry";
	}

	@RequestMapping(value = "/{uId}/edit", method = RequestMethod.POST)
	public String editTravelEntry(@PathVariable String uId, Model model, TravelViewModel viewModel, HttpSession session) throws Exception {
		travelService.updateTravel(uId, viewModel);

		return "redirect:/travel/";
	}

}
