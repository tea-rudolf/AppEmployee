package ca.ulaval.glo4003.appemployee.persistence;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntry;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntryNotFoundException;

public class XMLTimeEntryRepositoryTest {

	private static final String ID = "0001";

	private XMLTimeEntryRepository xmlTimeEntryRepositoryMock;
	private TimeEntry timeEntryMock;

	@Before
	public void init() {
		xmlTimeEntryRepositoryMock = mock(XMLTimeEntryRepository.class);
		timeEntryMock = new TimeEntry();
	}

	@Test
	public void getByUidReturnsCorrectTimeEntry() {
		xmlTimeEntryRepositoryMock.findByUid(ID);
		assertEquals(timeEntryMock.getuId(), ID);
	}

	@Test(expected = TimeEntryNotFoundException.class)
	public void getByUidCallsGetUidMethod() {
		when(xmlTimeEntryRepositoryMock.findByUid(ID)).thenThrow(new TimeEntryNotFoundException());
		xmlTimeEntryRepositoryMock.findByUid(ID);
	}

}
