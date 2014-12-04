package ca.ulaval.glo4003.appemployee.domain.travel;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.appemployee.domain.exceptions.TravelNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.repository.TravelRepository;
import ca.ulaval.glo4003.appemployee.domain.time.PayPeriod;

@Component
public class TravelProcessor {

	private TravelRepository travelRepository;

	@Autowired
	public TravelProcessor(TravelRepository travelRepository) {
		this.travelRepository = travelRepository;
	}

	public Travel retrieveTravelByUid(String uid) throws TravelNotFoundException {
		Travel travel = travelRepository.findByUid(uid);

		if (travel == null) {
			throw new TravelNotFoundException("Travel does not exist in repository");
		}
		return travel;
	}

	public void createTravel(double distance, String vehicle, LocalDate localDate, String userEmail, String comment)
			throws Exception {
		Travel travel = new Travel(distance, Vehicle.valueOf(vehicle), localDate, userEmail, comment);
		travelRepository.store(travel);
	}

	public void editTravel(String travelUid, double distance, String vehicle, LocalDate date, String user,
			String comment) throws Exception {
		Travel travel = retrieveTravelByUid(travelUid);
		updateTravel(distance, vehicle, date, user, comment, travel);
	}

	public List<Travel> evaluateUserTravelsForPayPeriod(PayPeriod payPeriod, String userEmail) {
		ArrayList<Travel> travels = new ArrayList<Travel>();

		for (Travel travel : travelRepository.findAllTravelsByUser(userEmail)) {
			if (travel.getDate().isBefore(payPeriod.getEndDate().plusDays(1))
					&& travel.getDate().isAfter(payPeriod.getStartDate().minusDays(1))) {
				travels.add(travel);
			}
		}
		return travels;
	}

	private void updateTravel(double distance, String vehicle, LocalDate date, String user, String comment,
			Travel travel) throws Exception {
		travel.update(distance, vehicle, date, user, comment);
		travelRepository.store(travel);
	}

}
