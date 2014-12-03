package ca.ulaval.glo4003.appemployee.domain;

import static org.junit.Assert.assertNotNull;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.appemployee.domain.time.TimeEntry;

@RunWith(MockitoJUnitRunner.class)
public class TimeEntryTest {

	private TimeEntry timeEntry;
	private static final String UID = "0001";
	private static final double HOURS = 22;
	private static final LocalDate DATE = new LocalDate("2014-02-02");
	private static final String USER_EMAIL = "john@hotmail.com";
	private static final String TASK_UID = "0005";
	private static final String COMMENT = "A Comment.";

	@Test
	public void canInstantiateTimeEntry() {
		timeEntry = new TimeEntry();
		assertNotNull(timeEntry);
	}

	@Test
	public void canInstantiateTimeEntryWithParameters() {
		timeEntry = new TimeEntry(UID, HOURS, DATE, USER_EMAIL, TASK_UID,
				COMMENT);
		assertNotNull(timeEntry);
	}

}
