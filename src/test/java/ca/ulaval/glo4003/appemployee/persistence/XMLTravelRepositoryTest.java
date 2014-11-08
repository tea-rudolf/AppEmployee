package ca.ulaval.glo4003.appemployee.persistence;

import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import ca.ulaval.glo4003.appemployee.domain.travel.Travel;

public class XMLTravelRepositoryTest {
	
	private static final String TRAVEL_UID = "id";
	private static final String USER = "test@employee.com";
	private static final int TRAVELS_LIST_SIZE = 1;
	private static final int TEMPORARY_TRAVEL_LIST_SIZE = 2;
	
	private XMLTravelRepository travelRepository;
	private Travel travelMock;
	
	@Before
	public void init() throws Exception{
		travelMock = mock(Travel.class);
		travelRepository = new XMLTravelRepository();
	}
	
	@Test
	public void storeAddsTravelToRepository() throws Exception{
		when(travelMock.getuId()).thenReturn(TRAVEL_UID);
		travelRepository.store(travelMock);
		Travel expectedTravel = travelRepository.findByUid(TRAVEL_UID);
		assertEquals(travelMock, expectedTravel);
	}
	
	@Test
	public void findAllTravelsByUserReturnsCorrectTasks() throws Exception{
		when(travelMock.getuId()).thenReturn(TRAVEL_UID);
		when(travelMock.getUserEmail()).thenReturn(USER);
		travelRepository.store(travelMock);
		List<Travel> sampleTravelList = travelRepository.findAlltravelsByUser(USER);
		assertEquals(TRAVELS_LIST_SIZE, sampleTravelList.size());
	}
	
	@Test
	public void findByUidFindsCorrectTravel() throws Exception{
		when(travelMock.getuId()).thenReturn(TRAVEL_UID);
		travelRepository.store(travelMock);
		Travel expectedTravel = travelRepository.findByUid(TRAVEL_UID);
		assertEquals(travelMock, expectedTravel);
	}
	
	@Test
	public void findAllFindsAllTravels() throws Exception{
		when(travelMock.getuId()).thenReturn(TRAVEL_UID);
		travelRepository.store(travelMock);
		Collection<Travel> expectedCollection = travelRepository.findAll();
		assertEquals(TEMPORARY_TRAVEL_LIST_SIZE, expectedCollection.size());
	}

}
