package ca.ulaval.glo4003.appemployee.web.converters;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ca.ulaval.glo4003.appemployee.domain.travel.Travel;
import ca.ulaval.glo4003.appemployee.domain.travel.Vehicle;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TravelViewModel;

public class TravelConverterTest {

	private static final String FIRST_ID = "1";
	private static final double FIRST_DISTANCE = 100;
	private static final LocalDate FIRST_DATE = new LocalDate("2014-11-08");
	private static final String SECOND_ID = "2";
	private static final double SECOND_DISTANCE = 200;
	private static final Vehicle TRAVEL_VEHICLE = Vehicle.ENTERPRISE;
	private static final LocalDate SECOND_DATE = new LocalDate("2014-11-07");
	private static final double EPSILON = 0.001;
	private static final String COMMENT = "comment";
	private static final String USER_EMAIL = "emp@company.com";

	@Mock
	private TravelViewModel travelViewModelMock;
	
	@Mock
	private Travel travelMock;
	
	@InjectMocks
	private TravelConverter travelConverter;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		travelConverter = new TravelConverter();
	}

	@Test
	public void convertTravelListsToViewModelsConvertAllOfThem() {
		Travel firstTravel = createTravel(FIRST_ID, FIRST_DISTANCE, FIRST_DATE, TRAVEL_VEHICLE);
		Travel secondTravel = createTravel(SECOND_ID, SECOND_DISTANCE, SECOND_DATE, TRAVEL_VEHICLE);
		List<Travel> travels = new ArrayList<Travel>();
		travels.add(firstTravel);
		travels.add(secondTravel);

		TravelViewModel[] viewModels = travelConverter.convert(travels).toArray(new TravelViewModel[1]);

		assertEquals(FIRST_DISTANCE, viewModels[0].getDistanceTravelled(), EPSILON);
		assertEquals(FIRST_ID, viewModels[0].getuId());
		assertEquals(FIRST_DATE.toString(), viewModels[0].getDate());

		assertEquals(SECOND_DISTANCE, viewModels[1].getDistanceTravelled(), EPSILON);
		assertEquals(SECOND_ID, viewModels[1].getuId());
		assertEquals(SECOND_DATE.toString(), viewModels[1].getDate());
	}

	@Test
	public void convertToTravelConvertsViewModelToTravel() {
		when(travelViewModelMock.getDistanceTravelled()).thenReturn(FIRST_DISTANCE);
		when(travelViewModelMock.getDate()).thenReturn(FIRST_DATE.toString());
		when(travelViewModelMock.getUserEmail()).thenReturn(USER_EMAIL);
		when(travelViewModelMock.getComment()).thenReturn(COMMENT);
		when(travelViewModelMock.getVehicle()).thenReturn(Vehicle.ENTERPRISE.toString());

		travelMock = travelConverter.convert(travelViewModelMock);

		assertEquals(travelViewModelMock.getDistanceTravelled(), travelMock.getDistanceTravelled(), EPSILON);
		assertEquals(travelViewModelMock.getDate(), travelMock.getDate().toString());
		assertEquals(travelViewModelMock.getUserEmail(), travelMock.getUserEmail());
		assertEquals(travelViewModelMock.getComment(), travelMock.getComment());
		assertEquals(travelViewModelMock.getVehicle(), travelMock.getVehicle().toString());
	}

	@Test
	public void convertToViewModelConvertsTravelToViewModel() {
		when(travelMock.getUid()).thenReturn(FIRST_ID);
		when(travelMock.getDistanceTravelled()).thenReturn(FIRST_DISTANCE);
		when(travelMock.getComment()).thenReturn(COMMENT);
		when(travelMock.getDate()).thenReturn(FIRST_DATE);
		when(travelMock.getUserEmail()).thenReturn(USER_EMAIL);
		when(travelMock.getVehicle()).thenReturn(Vehicle.PERSONAL);

		travelViewModelMock = travelConverter.convert(travelMock);

		assertEquals(travelMock.getUid(), travelViewModelMock.getuId());
		assertEquals(travelMock.getDistanceTravelled(), travelViewModelMock.getDistanceTravelled(), EPSILON);
		assertEquals(travelMock.getComment(), travelViewModelMock.getComment());
		assertEquals(travelMock.getDate().toString(), travelViewModelMock.getDate());
		assertEquals(travelMock.getUserEmail(), travelViewModelMock.getUserEmail());
	}

	private Travel createTravel(String id, double distance, LocalDate date, Vehicle vehicule) {
		Travel travel = mock(Travel.class);
		given(travel.getDistanceTravelled()).willReturn(distance);
		given(travel.getUid()).willReturn(id);
		given(travel.getDate()).willReturn(date);
		given(travel.getVehicle()).willReturn(vehicule);
		return travel;
	}

}
