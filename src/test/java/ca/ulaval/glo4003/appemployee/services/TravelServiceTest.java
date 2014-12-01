package ca.ulaval.glo4003.appemployee.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ca.ulaval.glo4003.appemployee.domain.repository.TravelRepository;
import ca.ulaval.glo4003.appemployee.domain.travel.Travel;
import ca.ulaval.glo4003.appemployee.web.converters.TravelConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TravelViewModel;

public class TravelServiceTest {

	// TODO: fix the tests

	private static final String TRAVEL_UID = "0002";
	private static final String NEW_UID = "0003";

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

	@InjectMocks
	private TravelService travelService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		travelService = new TravelService(travelRepositoryMock, travelConverterMock, payPeriodServiceMock);
	}

	@Test
	public void findByUidFindsCorrectTravel() throws Exception {
		when(travelMock.getUid()).thenReturn(TRAVEL_UID);
		when(travelRepositoryMock.findByUid(TRAVEL_UID)).thenReturn(travelMock);
		Travel sampleTravel = travelService.findByuId(TRAVEL_UID);
		assertEquals(travelMock.getUid(), sampleTravel.getUid());
	}

	@Test
	public void storeCallsCorrectRepositoryMethod() throws Exception {
		travelService.store(travelMock);
		verify(travelRepositoryMock, times(1)).store(travelMock);
	}

//	@Test
//	public void updateCallsCorrectDomainMethod() {
//		when(travelConverterMock.convert(travelViewModelMock)).thenReturn(travelMock);
//		travelService.updateTravel(NEW_UID, travelViewModelMock);
//		verify(travelMock, times(1)).setUid(NEW_UID);
//	}

//	@Test
//	public void createCallsCorrectRepositoryMethod() throws Exception {
//		when(travelConverterMock.convert(travelViewModelMock)).thenReturn(travelMock);
//		travelService.createTravel(travelViewModelMock);
//		verify(travelRepositoryMock, times(1)).store(travelMock);
//	}
}
