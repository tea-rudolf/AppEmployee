package ca.ulaval.glo4003.appemployee.web.converters;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.appemployee.domain.travel.Travel;
import ca.ulaval.glo4003.appemployee.domain.travel.Vehicle;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TravelViewModel;

@Component
public class TravelConverter {

	public List<TravelViewModel> convert(List<Travel> travels) {

		List<TravelViewModel> travelViewModels = new ArrayList<TravelViewModel>();

		for (Travel travel : travels) {
			TravelViewModel travelViewModel = convert(travel);
			travelViewModel.setuId(travel.getUid());
			travelViewModels.add(travelViewModel);
		}
		return travelViewModels;
	}

	public TravelViewModel convert(Travel travel) {
		TravelViewModel travelViewModel = new TravelViewModel();
		travelViewModel.setuId(travel.getUid());
		travelViewModel.setDistanceTravelled(travel.getDistanceTravelled());
		travelViewModel.setComment(travel.getComment());
		travelViewModel.setDate(travel.getDate().toString());
		travelViewModel.setUserEmail(travel.getUserEmail());
		travelViewModel.setVehicle(travel.getVehicle().toString());

		return travelViewModel;
	}

}
