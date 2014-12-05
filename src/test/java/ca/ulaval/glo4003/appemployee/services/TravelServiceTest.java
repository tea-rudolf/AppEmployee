package ca.ulaval.glo4003.appemployee.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ca.ulaval.glo4003.appemployee.domain.exceptions.TravelNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.repository.TravelRepository;
import ca.ulaval.glo4003.appemployee.domain.travel.Travel;
import ca.ulaval.glo4003.appemployee.domain.travel.TravelProcessor;
import ca.ulaval.glo4003.appemployee.domain.travel.Vehicle;
import ca.ulaval.glo4003.appemployee.web.converters.TravelConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TravelViewModel;

public class TravelServiceTest {

	private static final double DISTANCE_TRAVELLED = 200.00;
	private static final Vehicle USER_VEHICLE = Vehicle.PERSONAL;
	private static final String TRAVEL_DATE = "2014-11-11";
	private static final String VALID_EMAIL = "employee@employee.com";
	private static final String COMMENT = "comment";
	private static final String TRAVEL_UID = "1";

	private List<Travel> travels = new ArrayList<Travel>();
	
	@Mock
	private TravelRepository travelRepositoryMock;

	@Mock
	private TravelConverter travelConverterMock;

	@Mock
	private Travel travelMock;

	@Mock
	private TravelViewModel travelViewModelMock;

	@Mock
	private TimeService payPeriodServiceMock;

	@Mock
	private TravelProcessor travelProcessorMock;

	@InjectMocks
	private TravelService travelService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		travelService = new TravelService(travelConverterMock,
				payPeriodServiceMock, travelProcessorMock);
	}

	@Test
	public void editTravelCallsProcessorMethodCorrectly() throws Exception {
		when(travelViewModelMock.getDistanceTravelled()).thenReturn(DISTANCE_TRAVELLED);
		when(travelViewModelMock.getVehicle()).thenReturn(USER_VEHICLE.toString());
		when(travelViewModelMock.getDate()).thenReturn(TRAVEL_DATE);
		when(travelViewModelMock.getUserEmail()).thenReturn(VALID_EMAIL);
		when(travelViewModelMock.getComment()).thenReturn(COMMENT);
		
		travelService.editTravel(TRAVEL_UID, travelViewModelMock);
		
		verify(travelProcessorMock, times(1)).editTravel(TRAVEL_UID, DISTANCE_TRAVELLED, USER_VEHICLE.toString(), new LocalDate(TRAVEL_DATE), VALID_EMAIL, COMMENT);
	}
	
	@Test
	public void createTravelCallsProcessorMethodCorrectly() throws Exception {
		when(travelViewModelMock.getDistanceTravelled()).thenReturn(DISTANCE_TRAVELLED);
		when(travelViewModelMock.getVehicle()).thenReturn(USER_VEHICLE.toString());
		when(travelViewModelMock.getDate()).thenReturn(TRAVEL_DATE);
		when(travelViewModelMock.getUserEmail()).thenReturn(VALID_EMAIL);
		when(travelViewModelMock.getComment()).thenReturn(COMMENT);
		
		travelService.createTravel(travelViewModelMock);
		
		verify(travelProcessorMock, times(1)).createTravel(DISTANCE_TRAVELLED, USER_VEHICLE.toString(), new LocalDate(TRAVEL_DATE), VALID_EMAIL, COMMENT);
	}
	
	@Test
	public void retrieveUserTravelViewModelsForCurrentPayPeriodCallsConverterMethod() {
		travelService.retrieveUserTravelViewModelsForCurrentPayPeriod(VALID_EMAIL);
		verify(travelConverterMock, times(1)).convert(travels);
	}
	
	@Test
	public void retrieveTravelViewModelReturnsValidViewModel() throws TravelNotFoundException {
		when(travelProcessorMock.retrieveTravelByUid(TRAVEL_UID)).thenReturn(travelMock);
		when(travelConverterMock.convert(travelMock)).thenReturn(travelViewModelMock);
		TravelViewModel returnedViewModel = travelService.retrieveTravelViewModel(TRAVEL_UID);
		assertEquals(returnedViewModel, travelViewModelMock);
	}
	
}
