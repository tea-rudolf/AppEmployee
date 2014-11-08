package ca.ulaval.glo4003.appemployee.web.converters;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.appemployee.domain.travel.Travel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TravelViewModel;

@Component
public class TravelConverter {
	
	public List<TravelViewModel> convert(List<Travel> travels) {

		List<TravelViewModel> travelViewModels = new ArrayList<TravelViewModel>();

		for (Travel travel : travels) {
			TravelViewModel travelViewModel = convertToViewModel(travel);
			travelViewModels.add(travelViewModel);
		}
		return travelViewModels;
	}
	
	public Travel convertToTravel(TravelViewModel travelViewModel) {
		Travel travel = new Travel();
		travel.setDistanceTravelled(travelViewModel.getDistanceTravelled());
		travel.setDate(new LocalDate(travelViewModel.getDate()));
		travel.setUserEmail(travelViewModel.getUserEmail());
		travel.setComment(travelViewModel.getComment());
		return travel;
	}
	
	public TravelViewModel convertToViewModel(Travel travel) {
		TravelViewModel travelViewModel = new TravelViewModel();
		travelViewModel.setuId(travel.getuId());
		travelViewModel.setDistanceTravelled(travel.getDistanceTravelled());
		travelViewModel.setComment(travel.getComment());
		travelViewModel.setDate(travel.getDate().toString());
		travelViewModel.setuId(travel.getuId());
		travelViewModel.setUserEmail(travel.getUserEmail());
		return travelViewModel;
	}

}
