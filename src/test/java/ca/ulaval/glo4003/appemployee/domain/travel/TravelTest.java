package ca.ulaval.glo4003.appemployee.domain.travel;

import static org.junit.Assert.*;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class TravelTest {

	private Travel travel;

	private static final double DISTANCE = 100.50;
	private static final LocalDate DATE = new LocalDate("2014-12-03");
	private static final String EMAIL = "test@test.com";
	private static final String COMMENT = "dummy comment";
	private static final String VEHICLE = "ENTERPRISE";

	@Before
	public void setUp() {
		travel = new Travel(DISTANCE, Vehicle.valueOf(VEHICLE), DATE, EMAIL, COMMENT);
	}

	@Test
	public void canInstantiateTravelEntry() {
		assertNotNull(travel);
	}

	@Test
	public void updateChangesDistance() {
		travel.update(DISTANCE, VEHICLE, DATE, EMAIL, COMMENT);
		assertEquals((int) DISTANCE, (int) travel.getDistanceTravelled());
	}

	@Test
	public void updateChangesVehicleType() {
		travel.update(DISTANCE, VEHICLE, DATE, EMAIL, COMMENT);
		assertEquals(Vehicle.valueOf(VEHICLE), travel.getVehicle());
	}

	@Test
	public void updateChangesDate() {
		travel.update(DISTANCE, VEHICLE, DATE, EMAIL, COMMENT);
		assertEquals(DATE, travel.getDate());
	}

	@Test
	public void updateChangesUserEmail() {
		travel.update(DISTANCE, VEHICLE, DATE, EMAIL, COMMENT);
		assertEquals(EMAIL, travel.getUserEmail());
	}

	@Test
	public void updateChangesComment() {
		travel.update(DISTANCE, VEHICLE, DATE, EMAIL, COMMENT);
		assertEquals(COMMENT, travel.getComment());
	}

}
