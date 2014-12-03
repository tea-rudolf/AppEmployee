package ca.ulaval.glo4003.appemployee.persistence;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ca.ulaval.glo4003.appemployee.domain.travel.Travel;

public class XMLTravelRepositoryTest {

	private static final String TRAVEL_UID = "0001";
	private static final String USER = "emp@company.com";
	private static final int TRAVELS_LIST_SIZE = 1;
	private static final int TEMPORARY_TRAVEL_LIST_SIZE = 1;

	@InjectMocks
	private XMLTravelRepository travelRepository;

	@Mock
	private XMLGenericMarshaller<TravelXMLAssembler> marshallerMock;

	@Mock
	private Travel travelMock;

	@Before
	public void init() throws Exception {
		MockitoAnnotations.initMocks(this);
		travelRepository = new XMLTravelRepository(marshallerMock);
	}

	@Test
	public void storeAddsTravelToRepository() throws Exception {
		when(travelMock.getUid()).thenReturn(TRAVEL_UID);
		travelRepository.store(travelMock);
		Travel expectedTravel = travelRepository.findByUid(TRAVEL_UID);
		assertEquals(travelMock, expectedTravel);
	}

	@Test
	public void findAllTravelsByUserReturnsCorrectTasks() throws Exception {
		when(travelMock.getUid()).thenReturn(TRAVEL_UID);
		when(travelMock.getUserEmail()).thenReturn(USER);
		travelRepository.store(travelMock);
		List<Travel> sampleTravelList = travelRepository
				.findAllTravelsByUser(USER);
		assertEquals(TRAVELS_LIST_SIZE, sampleTravelList.size());
	}

	@Test
	public void findByUidFindsCorrectTravel() throws Exception {
		when(travelMock.getUid()).thenReturn(TRAVEL_UID);
		travelRepository.store(travelMock);
		Travel expectedTravel = travelRepository.findByUid(TRAVEL_UID);
		assertEquals(travelMock, expectedTravel);
	}

	@Test
	public void findAllFindsAllTravels() throws Exception {
		when(travelMock.getUid()).thenReturn(TRAVEL_UID);
		travelRepository.store(travelMock);
		Collection<Travel> expectedCollection = travelRepository.findAll();
		assertEquals(TEMPORARY_TRAVEL_LIST_SIZE, expectedCollection.size());
	}

}
