package ca.ulaval.glo4003.appemployee.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.appemployee.domain.repository.TravelRepository;
import ca.ulaval.glo4003.appemployee.domain.travel.Travel;
import ca.ulaval.glo4003.appemployee.web.converters.TravelConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TravelViewModel;

public class TravelServiceTest {

	private static final String TRAVEL_UID = "0002";
	private static final String NEW_UID = "0003";

	private TravelRepository travelRepositoryMock;
	private TravelConverter travelConverterMock;
	private TravelService travelService;
	private Travel travelMock;
	private TravelViewModel travelViewModelMock;

	@Before
	public void init() {
		travelRepositoryMock = mock(TravelRepository.class);
		travelConverterMock = mock(TravelConverter.class);
		travelMock = mock(Travel.class);
		travelViewModelMock = mock(TravelViewModel.class);
		travelService = new TravelService(travelRepositoryMock, travelConverterMock);
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

	@Test
	public void updateCallsCorrectDomainMethod() {
		when(travelConverterMock.convert(travelViewModelMock)).thenReturn(travelMock);
		travelService.update(NEW_UID, travelViewModelMock);
		verify(travelMock, times(1)).setUid(NEW_UID);
	}

}
