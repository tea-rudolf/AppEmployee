package ca.ulaval.glo4003.appemployee.persistence;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntry;

public class XMLTimeEntryRepositoryTest {

	private static final String ID = "0001";

	private XMLTimeEntryRepository xmlTimeEntryRepositoryMock;
	private TimeEntry timeEntryMock;

	@Before
	public void init() throws Exception {
		xmlTimeEntryRepositoryMock = new XMLTimeEntryRepository();
		timeEntryMock = mock(TimeEntry.class);
	}

	@Test
	public void getByUidReturnsCorrectTimeEntry() {
		timeEntryMock = xmlTimeEntryRepositoryMock.findByUid(ID);
		assertEquals(timeEntryMock.getuId(), ID);
	}
}
