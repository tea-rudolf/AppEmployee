package ca.ulaval.glo4003.appemployee.domain.time;

import static org.junit.Assert.*;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TimeEntryTest {

	private TimeEntry timeEntry;
	private static final String UID = "0001";
	private static final double FIRST_BILLABLE_HOURS = 22;
	private static final LocalDate FIRST_DATE = new LocalDate("2014-02-02");
	private static final String FIRST_USER_EMAIL = "john@hotmail.com";
	private static final String FIRST_TASK_UID = "0005";
	private static final String FIRST_COMMENT = "A Comment.";
	private static final double SECOND_BILLABLE_HOURS = 30.5;
	private static final LocalDate SECOND_DATE = new LocalDate("2014-02-02");
	private static final String SECOND_USER_EMAIL = "john@hotmail.com";
	private static final String SECOND_TASK_UID = "0005";
	private static final String SECOND_COMMENT = "A Comment.";

	@Before
	public void setUp() {
		timeEntry = new TimeEntry(UID, FIRST_BILLABLE_HOURS, FIRST_DATE, FIRST_USER_EMAIL, FIRST_TASK_UID,
				FIRST_COMMENT);
	}

	@Test
	public void canInstantiateTimeEntry() {
		assertNotNull(timeEntry);
	}

	@Test
	public void updateChangesBillableHours() {
		timeEntry.update(SECOND_BILLABLE_HOURS, SECOND_DATE, SECOND_USER_EMAIL, SECOND_TASK_UID, SECOND_COMMENT);
		assertEquals((int) SECOND_BILLABLE_HOURS, (int) timeEntry.getBillableHours());
	}

	@Test
	public void updateChangesDateExpense() {
		timeEntry.update(SECOND_BILLABLE_HOURS, SECOND_DATE, SECOND_USER_EMAIL, SECOND_TASK_UID, SECOND_COMMENT);
		assertEquals(SECOND_DATE, timeEntry.getDate());
	}

	@Test
	public void updateChangesUserEmailExpense() {
		timeEntry.update(SECOND_BILLABLE_HOURS, SECOND_DATE, SECOND_USER_EMAIL, SECOND_TASK_UID, SECOND_COMMENT);
		assertEquals(SECOND_USER_EMAIL, timeEntry.getUserEmail());
	}

	@Test
	public void updateChangesCommentExpense() {
		timeEntry.update(SECOND_BILLABLE_HOURS, SECOND_DATE, SECOND_USER_EMAIL, SECOND_TASK_UID, SECOND_COMMENT);
		assertEquals(SECOND_COMMENT, timeEntry.getComment());
	}

}
