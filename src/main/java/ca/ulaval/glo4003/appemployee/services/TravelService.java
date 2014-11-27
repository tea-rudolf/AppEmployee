package ca.ulaval.glo4003.appemployee.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.repository.TravelRepository;
import ca.ulaval.glo4003.appemployee.domain.travel.Travel;
import ca.ulaval.glo4003.appemployee.persistence.RepositoryException;
import ca.ulaval.glo4003.appemployee.web.converters.TravelConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TravelViewModel;

@Service
public class TravelService {

	private TravelRepository travelRepository;
	private TravelConverter travelConverter;
	private TimeService payPeriodService;
	private UserService userService;

	@Autowired
	public TravelService(TravelRepository travelRepository, TravelConverter travelConverter, TimeService payPeriodService, UserService userService) {
		this.travelRepository = travelRepository;
		this.travelConverter = travelConverter;
		this.payPeriodService = payPeriodService;
		this.userService = userService;
	}

	public Travel findByuId(String uId) {
		return travelRepository.findByUid(uId);
	}

	public void store(Travel travel) {
		try {
			travelRepository.store(travel);
		} catch (Exception e) {
			throw new RepositoryException(e.getMessage());
		}

	}

	public void updateTravel(String travelUid, TravelViewModel viewModel) {
		Travel newTravel = travelConverter.convert(viewModel);
		newTravel.setUid(travelUid);
		store(newTravel);
	}

	public void createTravel(TravelViewModel travelForm) {
		Travel newTravelEntry = travelConverter.convert(travelForm);
		store(newTravelEntry);
	}
	
	public TravelViewModel retrieveTravelViewModelForCurrentPayPeriod(String currentUserEmail) {
		PayPeriod currentPayPeriod = payPeriodService.retrieveCurrentPayPeriod();
		TravelViewModel travelViewModel = new TravelViewModel(currentUserEmail, currentPayPeriod.getStartDate().toString(), currentPayPeriod.getEndDate().toString());
		return travelViewModel;
	}
	
	public Collection<TravelViewModel> retrieveTravelViewModelsForCurrentPayPeriod(String currentUserEmail) {
		List<Travel> travels = getTravelEntriesForUserForCurrentPayPeriod(currentUserEmail);
		return travelConverter.convert(travels);
	}
	
	public TravelViewModel retrieveTravelViewModelForExistingTravel(String uid) {
		Travel travel = findByuId(uid);
		PayPeriod currentPayPeriod = payPeriodService.retrieveCurrentPayPeriod();
		TravelViewModel travelViewModel = travelConverter.convert(travel);
		travelViewModel.setPayPeriodStartDate(currentPayPeriod.getStartDate().toString());
		travelViewModel.setPayPeriodEndDate(currentPayPeriod.getEndDate().toString());
		return travelViewModel;
	}
	
	public List<Travel> getTravelEntriesForUserForCurrentPayPeriod(String userEmail) {
		PayPeriod payPeriod = payPeriodService.retrieveCurrentPayPeriod();
		ArrayList<Travel> travels = new ArrayList<Travel>();

		for (Travel travel : travelRepository.findAllTravelsByUser(userEmail)) {
			if (travel.getDate().isBefore(payPeriod.getEndDate().plusDays(1)) && travel.getDate().isAfter(payPeriod.getStartDate().minusDays(1))) {
				travels.add(travel);
			}
		}
		return travels;
	}

}
