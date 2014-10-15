package persistence;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntry;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntryNotFoundException;
import ca.ulaval.glo4003.appemployee.persistence.XmlTimeEntryRepository;

public class XmlTimeEntryRepositoryTest {
	
	private static final Integer ID = 0;
	
	private XmlTimeEntryRepository xmlTimeEntryRepositoryMock;
	private TimeEntry timeEntryMock;
	
	@Before
	public void init(){
		xmlTimeEntryRepositoryMock = mock(XmlTimeEntryRepository.class);
		timeEntryMock = new TimeEntry(ID);
	}

	@Test
	public void getByUidReturnsCorrectTimeEntry(){
		xmlTimeEntryRepositoryMock.getByUid(ID);
		assertEquals(timeEntryMock.getuId(), ID);
	}
	
	@Test(expected = TimeEntryNotFoundException.class)
	public void getByUidCallsGetUidMethod(){
		when(xmlTimeEntryRepositoryMock.getByUid(ID)).thenThrow(new TimeEntryNotFoundException());
		xmlTimeEntryRepositoryMock.getByUid(ID);
	}

}
