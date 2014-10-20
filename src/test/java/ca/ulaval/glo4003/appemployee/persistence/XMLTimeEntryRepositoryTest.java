package ca.ulaval.glo4003.appemployee.persistence;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntry;


public class XMLTimeEntryRepositoryTest {
	
	private static final String VALID_UID = "1234";
	
	@Mock
	private XMLGenericMarshaller<TimeEntryXMLAssembler> marshallerMock;
	
	@Mock
	private TimeEntry timeEntryMock;

	@InjectMocks
	private XMLTimeEntryRepository repository;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		repository = new XMLTimeEntryRepository(marshallerMock);
		when(timeEntryMock.getuId()).thenReturn(VALID_UID);
	}
	
	@Test
	public void findByIdFindsTimeEntryById() throws Exception {
		TimeEntry dummyTimeEntry = new TimeEntry(VALID_UID);
		repository.store(dummyTimeEntry);
		
		assertEquals(dummyTimeEntry, repository.findByUid(VALID_UID));
	}
	
	@Test
	public void storeAddsTimeEntryToTimeEntryRepository() throws Exception {
		repository.store(timeEntryMock);
		
		assertEquals(timeEntryMock, repository.findByUid(VALID_UID));
	}

}
