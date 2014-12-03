package ca.ulaval.glo4003.appemployee.services;

import java.util.Collection;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.exceptions.TravelNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.time.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.travel.Travel;
import ca.ulaval.glo4003.appemployee.domain.travel.TravelProcessor;
import ca.ulaval.glo4003.appemployee.web.converters.TravelConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TravelViewModel;

@Service
public class TravelService {

	private TravelConverter travelConverter;
	private TimeService timeService;
	private TravelProcessor travelProcessor;

	@Autowired
	public TravelService(TravelConverter travelConverter, TimeService payPeriodService, TravelProcessor travelProcessor) {
		this.travelConverter = travelConverter;
		this.timeService = payPeriodService;
		this.travelProcessor = travelProcessor;
	}

	public void editTravel(String travelUid, TravelViewModel travelViewModel) throws Exception {
		travelProcessor.editTravel(travelUid, travelViewModel.getDistanceTravelled(), travelViewModel.getVehicle(), new LocalDate(travelViewModel.getDate()),
				travelViewModel.getUserEmail(), travelViewModel.getComment());
	}

	public void createTravel(TravelViewModel travelViewModel) throws Exception {
		travelProcessor.createTravel(travelViewModel.getDistanceTravelled(), travelViewModel.getVehicle(), new LocalDate(travelViewModel.getDate()),
				travelViewModel.getUserEmail(), travelViewModel.getComment());
	}

	public TravelViewModel retrieveUserTravelViewModel(String userEmail) {
		return new TravelViewModel(userEmail);
	}

	public Collection<TravelViewModel> retrieveUserTravelViewModelsForCurrentPayPeriod(String userEmail) {
		List<Travel> travels = retrieveUserTravelsForCurrentPayPeriod(userEmail);
		return travelConverter.convert(travels);
	}

	public TravelViewModel retrieveTravelViewModel(String uid) throws TravelNotFoundException {
		Travel travel = travelProcessor.retrieveTravelByUid(uid);
		TravelViewModel travelViewModel = travelConverter.convert(travel);
		return travelViewModel;
	}

	private List<Travel> retrieveUserTravelsForCurrentPayPeriod(String userEmail) {
		PayPeriod payPeriod = timeService.retrieveCurrentPayPeriod();
		return travelProcessor.evaluateUserTravelsForPayPeriod(payPeriod, userEmail);
	}

}
