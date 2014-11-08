package ca.ulaval.glo4003.appemployee.services;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.repository.TravelRepository;
import ca.ulaval.glo4003.appemployee.domain.travel.Travel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TravelViewModel;

@Service
public class TravelService {

	private TravelRepository TravelRepository;

	@Autowired
	public TravelService(TravelRepository TravelRepository) {
		this.TravelRepository = TravelRepository;
	}

	public Travel findByuId(String uId) throws Exception {
		return TravelRepository.findByUid(uId);
	}
	
	public void store(Travel Travel) throws Exception {
		TravelRepository.store(Travel);
	}

}
