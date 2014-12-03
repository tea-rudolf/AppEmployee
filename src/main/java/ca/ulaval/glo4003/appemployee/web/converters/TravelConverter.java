package ca.ulaval.glo4003.appemployee.web.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.appemployee.domain.travel.Travel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TravelViewModel;

@Component
public class TravelConverter {

	public List<TravelViewModel> convert(List<Travel> travels) {

		List<TravelViewModel> travelViewModels = new ArrayList<TravelViewModel>();

		for (Travel travel : travels) {
			TravelViewModel travelViewModel = convert(travel);
			travelViewModels.add(travelViewModel);
		}
		return travelViewModels;
	}

	public TravelViewModel convert(Travel travel) {
		TravelViewModel travelViewModel = new TravelViewModel(travel.getUid(),
				travel.getDistanceTravelled(), travel.getComment(), travel
						.getDate().toString(), travel.getUserEmail(), travel
						.getVehicle().toString());
		return travelViewModel;
	}

}
