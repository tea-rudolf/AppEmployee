package ca.ulaval.glo4003.appemployee.domain;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntry;

@RunWith(MockitoJUnitRunner.class)
public class TimeEntryTest {

	private TimeEntry timeEntry;

	@Test
	public void canInstantiateTimeEntry() {
		timeEntry = new TimeEntry();
		assertNotNull(timeEntry);
	}

}
