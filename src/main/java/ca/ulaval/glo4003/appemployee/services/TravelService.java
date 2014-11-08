package ca.ulaval.glo4003.appemployee.services;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.repository.TravelRepository;
import ca.ulaval.glo4003.appemployee.domain.travel.Travel;
import ca.ulaval.glo4003.appemployee.domain.travel.Vehicule;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TravelViewModel;

@Service
public class TravelService {

	private TravelRepository travelRepository;

	@Autowired
	public TravelService(TravelRepository travelRepository) {
		this.travelRepository = travelRepository;
	}

	public Travel findByuId(String uId) throws Exception {
		return travelRepository.findByUid(uId);
	}

	public void store(Travel travel) {
		try {
			travelRepository.store(travel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void updateTravelEntry(String uId, TravelViewModel viewModel) {
		Travel entry = travelRepository.findByUid(uId);
		entry.setComment(viewModel.getComment());
		entry.setDate(new LocalDate(viewModel.getDate()));
		entry.setDistanceTravelled(viewModel.getDistanceTravelled());
		entry.setUserEmail(viewModel.getUserEmail());
		Vehicule v = Vehicule.ENTERPRISE;
		entry.setVehicule(v);

		try {
			travelRepository.store(entry);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	


}
