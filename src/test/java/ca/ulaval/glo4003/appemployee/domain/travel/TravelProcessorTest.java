package ca.ulaval.glo4003.appemployee.domain.travel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ca.ulaval.glo4003.appemployee.domain.exceptions.TravelNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.repository.TravelRepository;
import ca.ulaval.glo4003.appemployee.domain.time.PayPeriod;

public class TravelProcessorTest {

	@Mock
	private TravelRepository travelRepositoryMock;

	@Mock
	private Travel travelMock;

	@Mock
	private PayPeriod payPeriodMock;

	@InjectMocks
	private TravelProcessor travelProcessor;

	private static final String TRAVEL_UID = "0001";
	private static final double DISTANCE = 100.50;
	private static final LocalDate DATE = new LocalDate("2014-12-03");
	private static final String EMAIL = "test@test.com";
	private static final String COMMENT = "dummy comment";
	private static final String VEHICLE = "ENTERPRISE";
	private static final LocalDate PAYPERIOD_START_DATE = new LocalDate("2014-11-24");
	private static final LocalDate PAYPERIOD_END_DATE = new LocalDate("2014-12-07");
	private static final LocalDate BEFORE_PAYPERIOD_START_DATE = new LocalDate("2014-11-23");
	private static final LocalDate AFTER_PAYPERIOD_END_DATE = new LocalDate("2014-12-08");

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		travelProcessor = spy(new TravelProcessor(travelRepositoryMock));
	}

	@Test
	public void canInstantiateTravelProcessor() {
		assertNotNull(travelProcessor);
	}

	@Test(expected = TravelNotFoundException.class)
	public void retrieveTravelByUidThrowsExceptionWhenTravelNotFound() throws Exception {
		when(travelRepositoryMock.findByUid(TRAVEL_UID)).thenReturn(null);
		travelProcessor.retrieveTravelByUid(TRAVEL_UID);
	}

	@Test
	public void retrieveTravelByUidReturnsTravel() throws Exception {
		when(travelRepositoryMock.findByUid(TRAVEL_UID)).thenReturn(travelMock);
		Travel actualTravel = travelProcessor.retrieveTravelByUid(TRAVEL_UID);
		assertEquals(travelMock, actualTravel);
	}

	@Test
	public void createTravelStoresTravelInRepository() throws Exception {
		ArgumentCaptor<Travel> travelArgumentCaptor = ArgumentCaptor.forClass(Travel.class);
		travelProcessor.createTravel(DISTANCE, VEHICLE, DATE, EMAIL, COMMENT);
		verify(travelRepositoryMock, times(1)).store(travelArgumentCaptor.capture());
	}

	@Test(expected = TravelNotFoundException.class)
	public void editTravelThrowsExceptionWhenTravelNotFound() throws Exception {
		when(travelRepositoryMock.findByUid(TRAVEL_UID)).thenReturn(null);
		travelProcessor.editTravel(TRAVEL_UID, DISTANCE, VEHICLE, DATE, EMAIL, COMMENT);
	}

	@Test
	public void editTravelStoresTravelInRepository() throws Exception {
		when(travelRepositoryMock.findByUid(TRAVEL_UID)).thenReturn(travelMock);

		travelProcessor.editTravel(TRAVEL_UID, DISTANCE, VEHICLE, DATE, EMAIL, COMMENT);

		verify(travelMock, times(1)).update(DISTANCE, VEHICLE, DATE, EMAIL, COMMENT);
		verify(travelRepositoryMock, times(1)).store(travelMock);
	}

	@Test
	public void evaluateUserTravelsForPayPeriodReturnsUserTravelList() {
		List<Travel> userTravels = new ArrayList<Travel>();
		userTravels.add(travelMock);
		when(travelRepositoryMock.findAllTravelsByUser(EMAIL)).thenReturn(userTravels);
		when(travelMock.getDate()).thenReturn(DATE);
		when(payPeriodMock.getStartDate()).thenReturn(PAYPERIOD_START_DATE);
		when(payPeriodMock.getEndDate()).thenReturn(PAYPERIOD_END_DATE);

		List<Travel> actualUserTravels = travelProcessor.evaluateUserTravelsForPayPeriod(payPeriodMock, EMAIL);

		assertEquals(userTravels, actualUserTravels);
	}

	@Test
	public void evaluateUserTravelsForPayPeriodDoNotReturnTravelWhenTravelDateBeforePayPeriod() {
		List<Travel> userTravels = new ArrayList<Travel>();
		userTravels.add(travelMock);
		when(travelRepositoryMock.findAllTravelsByUser(EMAIL)).thenReturn(userTravels);
		when(travelMock.getDate()).thenReturn(BEFORE_PAYPERIOD_START_DATE);
		when(payPeriodMock.getStartDate()).thenReturn(PAYPERIOD_START_DATE);
		when(payPeriodMock.getEndDate()).thenReturn(PAYPERIOD_END_DATE);

		List<Travel> actualUserTravels = travelProcessor.evaluateUserTravelsForPayPeriod(payPeriodMock, EMAIL);

		assertEquals(0, actualUserTravels.size());
	}

	@Test
	public void evaluateUserTravelsForPayPeriodDoNotReturnTravelWhenTravelDateAfterPayPeriod() {
		List<Travel> userTravels = new ArrayList<Travel>();
		userTravels.add(travelMock);
		when(travelRepositoryMock.findAllTravelsByUser(EMAIL)).thenReturn(userTravels);
		when(travelMock.getDate()).thenReturn(AFTER_PAYPERIOD_END_DATE);
		when(payPeriodMock.getStartDate()).thenReturn(PAYPERIOD_START_DATE);
		when(payPeriodMock.getEndDate()).thenReturn(PAYPERIOD_END_DATE);

		List<Travel> actualUserTravels = travelProcessor.evaluateUserTravelsForPayPeriod(payPeriodMock, EMAIL);

		assertEquals(0, actualUserTravels.size());
	}
}
