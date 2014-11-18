package ca.ulaval.glo4003.appemployee.domain;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import ca.ulaval.glo4003.appemployee.domain.travel.Travel;

public class TravelTest {
	
	private Travel travel;

	@Test
	public void canInstantiateTravelEntry() {
		travel = new Travel();
		assertNotNull(travel);
	}
	
}
