package ca.ulaval.glo4003.appemployee.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.repository.TravelRepository;
import ca.ulaval.glo4003.appemployee.domain.travel.Travel;
import ca.ulaval.glo4003.appemployee.persistence.RepositoryException;
import ca.ulaval.glo4003.appemployee.web.converters.TravelConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TravelViewModel;

@Service
public class TravelService {

	private TravelRepository travelRepository;
	private TravelConverter travelConverter;

	@Autowired
	public TravelService(TravelRepository travelRepository, TravelConverter travelConverter) {
		this.travelRepository = travelRepository;
		this.travelConverter = travelConverter;
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

}
