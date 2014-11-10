package ca.ulaval.glo4003.appemployee.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.repository.TravelRepository;
import ca.ulaval.glo4003.appemployee.domain.travel.Travel;

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
			e.printStackTrace();
		}

	}


}
