package ca.ulaval.glo4003.appemployee.domain.time;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ca.ulaval.glo4003.appemployee.domain.exceptions.PayPeriodNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.exceptions.TimeEntryNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.repository.PayPeriodRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.TimeEntryRepository;

public class TimeProcessorTest {

	@Mock
	private PayPeriodRepository payPeriodRepositoryMock;

	@Mock
	private TimeEntryRepository timeEntryRepositoryMock;

	@Mock
	private PayPeriod payPeriodMock;

	@Mock
	private PayPeriod previousPayPeriodMock;

	@Mock
	private TimeEntry timeEntryMock;

	@InjectMocks
	private TimeProcessor timeProcessor;

	private static final LocalDate CURRENT_DATE = new LocalDate();
	private static final LocalDate PAYPERIOD_START_DATE = new LocalDate("2014-12-08");
	private static final double BILLABLE_HOURS = 30.5;
	private static final String EMAIL = "test@test.com";
	private static final String EMAIL2 = "test2@test.com";
	private static final String TASK_ID = "0001";
	private static final String COMMENT = "dummy comment";
	private static final String TIME_ENTRY_ID = "0001";

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		timeProcessor = spy(new TimeProcessor(payPeriodRepositoryMock, timeEntryRepositoryMock));
	}

	@Test
	public void canInstantiateTimeProcessor() {
		assertNotNull(timeProcessor);
	}

	@Test(expected = PayPeriodNotFoundException.class)
	public void retrieveCurrentPayPeriodThrowsExceptionWhenPayPeriodNotFound() {
		when(payPeriodRepositoryMock.findByDate(CURRENT_DATE)).thenReturn(null);
		timeProcessor.retrieveCurrentPayPeriod();
	}

	@Test
	public void retrieveCurrentPayPeriodReturnsCurrentPayPeriod() {
		when(payPeriodMock.getStartDate()).thenReturn(PAYPERIOD_START_DATE);
		when(payPeriodRepositoryMock.findByDate(CURRENT_DATE)).thenReturn(payPeriodMock);
		PayPeriod actualCurrentPayPeriod = timeProcessor.retrieveCurrentPayPeriod();
		assertEquals(payPeriodMock, actualCurrentPayPeriod);
	}

	@Test(expected = PayPeriodNotFoundException.class)
	public void retrievePreviousPayPeriodThrowsExceptionWhenPayPeriodNotFound() {
		when(timeProcessor.retrieveCurrentPayPeriod()).thenReturn(payPeriodMock);
		when(payPeriodMock.getStartDate()).thenReturn(CURRENT_DATE);
		when(payPeriodRepositoryMock.findByDate(CURRENT_DATE.minusDays(1))).thenReturn(null);
		timeProcessor.retrievePreviousPayPeriod();
	}

	@Test
	public void retrievePreviousPayPeriodReturnsPreviousPayPeriod() {
		when(payPeriodMock.getStartDate()).thenReturn(PAYPERIOD_START_DATE);
		when(previousPayPeriodMock.getEndDate()).thenReturn(PAYPERIOD_START_DATE.minusDays(1));
		when(payPeriodRepositoryMock.findByDate(CURRENT_DATE)).thenReturn(payPeriodMock);
		when(payPeriodRepositoryMock.findByDate(PAYPERIOD_START_DATE.minusDays(1))).thenReturn(previousPayPeriodMock);

		PayPeriod actualPreviousPayPeriod = timeProcessor.retrievePreviousPayPeriod();

		assertEquals(previousPayPeriodMock, actualPreviousPayPeriod);
	}

	@Test
	public void editPayPeriodCallsUpdatePayPeriodRepository() throws Exception {
		timeProcessor.editPayPeriod(payPeriodMock);
		verify(payPeriodRepositoryMock, times(1)).update(payPeriodMock);
	}

	@Test
	public void createTimeEntryStoresTimeEntryInRepository() throws Exception {
		ArgumentCaptor<TimeEntry> timeEntryArgumentCaptor = ArgumentCaptor.forClass(TimeEntry.class);
		timeProcessor.createTimeEntry(payPeriodMock, BILLABLE_HOURS, CURRENT_DATE, EMAIL, TASK_ID, COMMENT);
		verify(timeEntryRepositoryMock, times(1)).store(timeEntryArgumentCaptor.capture());
	}

	@Test
	public void createTimeEntryCallsUpdatePayPeriodRepository() throws Exception {
		timeProcessor.createTimeEntry(payPeriodMock, BILLABLE_HOURS, CURRENT_DATE, EMAIL, TASK_ID, COMMENT);
		verify(payPeriodRepositoryMock, times(1)).update(payPeriodMock);
	}

	@Test(expected = TimeEntryNotFoundException.class)
	public void editTimeEntryThrowsExceptionWhenTimeEntryNotFound() throws Exception {
		when(timeEntryRepositoryMock.findByUid(TIME_ENTRY_ID)).thenReturn(null);
		timeProcessor.editTimeEntry(TIME_ENTRY_ID, BILLABLE_HOURS, CURRENT_DATE, EMAIL, TASK_ID, COMMENT);
	}

	@Test
	public void editTimeEntryUpdateTravelInRepository() throws Exception {
		when(timeEntryRepositoryMock.findByUid(TIME_ENTRY_ID)).thenReturn(timeEntryMock);

		timeProcessor.editTimeEntry(TIME_ENTRY_ID, BILLABLE_HOURS, CURRENT_DATE, EMAIL, TASK_ID, COMMENT);

		verify(timeEntryMock, times(1)).update(BILLABLE_HOURS, CURRENT_DATE, EMAIL, TASK_ID, COMMENT);
		verify(timeEntryRepositoryMock, times(1)).store(timeEntryMock);
	}

	@Test(expected = TimeEntryNotFoundException.class)
	public void retrieveTimeEntryByUidThrowsExceptionWhenTimeEntryNotFound() throws Exception {
		when(timeEntryRepositoryMock.findByUid(TIME_ENTRY_ID)).thenReturn(null);
		timeProcessor.retrieveTimeEntryByUid(TIME_ENTRY_ID);
	}

	@Test
	public void retrieveTimeEntryByUidReturnsTimeEntry() throws Exception {
		when(timeEntryRepositoryMock.findByUid(TIME_ENTRY_ID)).thenReturn(timeEntryMock);
		TimeEntry actualTimeEntry = timeProcessor.retrieveTimeEntryByUid(TIME_ENTRY_ID);
		assertEquals(timeEntryMock, actualTimeEntry);
	}

	@Test
	public void evaluateUserTimeEntriesForPayPeriodReturnsTimeEntriesList() throws Exception {
		List<TimeEntry> timeEntries = new ArrayList<TimeEntry>();
		List<String> timeEntriesId = new ArrayList<String>();
		timeEntries.add(timeEntryMock);
		timeEntriesId.add(TIME_ENTRY_ID);
		when(payPeriodMock.getTimeEntryIds()).thenReturn(timeEntriesId);
		when(timeEntryRepositoryMock.findByUid(TIME_ENTRY_ID)).thenReturn(timeEntryMock);
		when(timeEntryMock.getUserEmail()).thenReturn(EMAIL);

		List<TimeEntry> actualTimeEntries = timeProcessor.evaluateUserTimeEntriesForPayPeriod(payPeriodMock, EMAIL);

		assertEquals(timeEntries, actualTimeEntries);
	}

	@Test
	public void evaluateUserTimeEntriesForPayPeriodDoNotReturnTimeEntryWhenUserTimeEntryNotFound() throws Exception {
		List<TimeEntry> timeEntries = new ArrayList<TimeEntry>();
		List<String> timeEntriesId = new ArrayList<String>();
		timeEntries.add(timeEntryMock);
		timeEntriesId.add(TIME_ENTRY_ID);
		when(payPeriodMock.getTimeEntryIds()).thenReturn(timeEntriesId);
		when(timeEntryRepositoryMock.findByUid(TIME_ENTRY_ID)).thenReturn(timeEntryMock);
		when(timeEntryMock.getUserEmail()).thenReturn(EMAIL2);

		List<TimeEntry> actualTimeEntries = timeProcessor.evaluateUserTimeEntriesForPayPeriod(payPeriodMock, EMAIL);

		assertEquals(0, actualTimeEntries.size());
	}
}
