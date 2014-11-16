package ca.ulaval.glo4003.appemployee.web.converters;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.appemployee.domain.travel.Travel;
import ca.ulaval.glo4003.appemployee.domain.travel.Vehicule;
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

	public Travel convert(TravelViewModel travelViewModel) {
		Travel travel = new Travel();
		travel.setDistanceTravelled(travelViewModel.getDistanceTravelled());
		travel.setDate(new LocalDate(travelViewModel.getDate()));
		travel.setUserEmail(travelViewModel.getUserEmail());
		travel.setComment(travelViewModel.getComment());

		if (travelViewModel.getVehicule().equals("PERSONNAL")) {
			travel.setVehicule(Vehicule.PERSONNAL);
		} else {
			travel.setVehicule(Vehicule.ENTERPRISE);
		}
		return travel;
	}

	public TravelViewModel convert(Travel travel) {
		TravelViewModel travelViewModel = new TravelViewModel();
		travelViewModel.setuId(travel.getUid());
		travelViewModel.setDistanceTravelled(travel.getDistanceTravelled());
		travelViewModel.setComment(travel.getComment());
		travelViewModel.setDate(travel.getDate().toString());
		travelViewModel.setUserEmail(travel.getUserEmail());
		travelViewModel.setVehicule(travel.getVehicule().toString());

		return travelViewModel;
	}

}
