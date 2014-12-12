package ca.ulaval.glo4003.appemployee.web.converters;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.time.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.time.TimeEntry;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TimeEntryViewModel;

public class TimeConverterTest {

	private static final LocalDate START_DATE = new LocalDate();
	private static final LocalDate END_DATE = new LocalDate(2014, 10, 03);
	private static final double HOURS = 7.00;
	private static final String EMAIL = "test@test.com";
	private static final String TASK_ID = "id";
	private static final String TIME_ENTRY_ID = "anotherid";
	private static final String SECOND_ID = "timeentryid";
	private static final double EPSILON = 0.001;
	private static final String COMMENT = "dummyComment";
	private static final double OTHER_HOURS = 8.00;
	private static final String TASK_NAME = "dummyTaskName";

	private List<Task> tasks = new ArrayList<Task>();

	@Mock
	private PayPeriod payPeriodMock;

	@Mock
	private TimeEntryViewModel timeViewModelMock;

	@Mock
	private TimeEntry timeEntryMock;

	@InjectMocks
	private TimeConverter timeConverter;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		timeConverter = new TimeConverter();
	}

	@Test
	public void convertTimeEntriesListToViewModelsConvertsAllOfThem() {
		TimeEntry firstTimeEntry = createTimeEntry(TIME_ENTRY_ID, HOURS, START_DATE);
		TimeEntry secondTimeEntry = createTimeEntry(SECOND_ID, OTHER_HOURS, END_DATE);
		List<TimeEntry> timeEntries = new ArrayList<TimeEntry>();
		timeEntries.add(firstTimeEntry);
		timeEntries.add(secondTimeEntry);

		TimeEntryViewModel[] viewModels = timeConverter.convert(timeEntries).toArray(new TimeEntryViewModel[1]);

		assertEquals(TIME_ENTRY_ID, viewModels[0].getUid());
		assertEquals(HOURS, viewModels[0].getHours(), EPSILON);

		assertEquals(SECOND_ID, viewModels[1].getUid());
		assertEquals(OTHER_HOURS, viewModels[1].getHours(), EPSILON);
	}

	@Test
	public void convertTimeEntryWithTasksListReturnsViewModel() {
		when(timeEntryMock.getUid()).thenReturn(TIME_ENTRY_ID);
		when(timeEntryMock.getUserEmail()).thenReturn(EMAIL);
		when(timeEntryMock.getDate()).thenReturn(START_DATE);
		when(timeEntryMock.getBillableHours()).thenReturn(HOURS);
		when(timeEntryMock.getTaskUid()).thenReturn(TASK_ID);
		when(timeEntryMock.getComment()).thenReturn(COMMENT);

		TimeEntryViewModel timeEntryViewModel = timeConverter.convert(timeEntryMock, TASK_NAME, tasks);

		assertEquals(TIME_ENTRY_ID, timeEntryViewModel.getUid());
		assertEquals(EMAIL, timeEntryViewModel.getUserEmail());
		assertEquals(START_DATE.toString(), timeEntryViewModel.getDate());
		assertEquals(HOURS, timeEntryViewModel.getHours(), EPSILON);
		assertEquals(TASK_ID, timeEntryViewModel.getTaskId());
		assertEquals(TASK_NAME, timeEntryViewModel.getTaskName());
		assertEquals(COMMENT, timeEntryViewModel.getComment());
		assertEquals(tasks, timeEntryViewModel.getAvailableTasks());
	}

	@Test
	public void convertUserTasksReturnsViewModel() {
		TimeEntryViewModel timeEntryViewModel = timeConverter.convert(EMAIL, tasks);

		assertEquals(EMAIL, timeEntryViewModel.getUserEmail());
		assertEquals(tasks, timeEntryViewModel.getAvailableTasks());
	}

	private TimeEntry createTimeEntry(String id, double hours, LocalDate date) {
		TimeEntry timeEntry = mock(TimeEntry.class);
		when(timeEntry.getUid()).thenReturn(id);
		when(timeEntry.getBillableHours()).thenReturn(hours);
		when(timeEntry.getDate()).thenReturn(date);
		return timeEntry;
	}
}
