package ca.ulaval.glo4003.appemployee.web.converters;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ca.ulaval.glo4003.appemployee.domain.time.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.time.TimeEntry;
import ca.ulaval.glo4003.appemployee.services.ProjectService;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TimeViewModel;

public class TimeConverterTest {

	private static final LocalDate START_DATE = new LocalDate(2014, 9, 22);
	private static final LocalDate END_DATE = new LocalDate(2014, 10, 03);
	private static final double HOURS = 7.00;
	private static final String TASK_ID = "id";
	private static final String TIME_ENTRY_ID = "anotherid";
	private static final String SECOND_ID = "timeentryid";
	private static final String TASK_NAME = "task";
	private static final String USER_EMAIL = "employee@employee.com";
	private static final double EPSILON = 0.001;
	private static final String COMMENT = "imacomment";
	private static final double OTHER_HOURS = 8.00;

	@Mock
	private PayPeriod payPeriodMock;

	@Mock
	private TimeViewModel timeViewModelMock;

	@Mock
	private TimeEntry timeEntryMock;

	@Mock
	private ProjectService projectServiceMock;

	@InjectMocks
	private TimeConverter payPeriodConverterMock;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		payPeriodConverterMock = new TimeConverter(projectServiceMock);
	}

	@Test
	public void convertTimeEntriesListToViewModelsConvertsAllOfThem() {
		TimeEntry firstTimeEntry = createTimeEntry(TIME_ENTRY_ID, HOURS,
				START_DATE);
		TimeEntry secondTimeEntry = createTimeEntry(SECOND_ID, OTHER_HOURS,
				END_DATE);
		List<TimeEntry> timeEntries = new ArrayList<TimeEntry>();
		timeEntries.add(firstTimeEntry);
		timeEntries.add(secondTimeEntry);

		TimeViewModel[] viewModels = payPeriodConverterMock
				.convert(timeEntries).toArray(new TimeViewModel[1]);

		assertEquals(TIME_ENTRY_ID, viewModels[0].getTimeEntryUid());
		assertEquals(HOURS, viewModels[0].getHoursTimeEntry(), EPSILON);

		assertEquals(SECOND_ID, viewModels[1].getTimeEntryUid());
		assertEquals(OTHER_HOURS, viewModels[1].getHoursTimeEntry(), EPSILON);
	}

	@Test
	public void convertPayPeriodConvertsIntoViewModel() {
		when(timeEntryMock.getUid()).thenReturn(TIME_ENTRY_ID);
		when(timeEntryMock.getDate()).thenReturn(START_DATE);
		when(timeEntryMock.getBillableHours()).thenReturn(HOURS);
		when(timeEntryMock.getTaskUid()).thenReturn(TASK_ID);
		when(projectServiceMock.getTaskName(TASK_ID)).thenReturn(TASK_NAME);
		when(timeEntryMock.getComment()).thenReturn(COMMENT);

		timeViewModelMock = payPeriodConverterMock.convert(timeEntryMock);

		assertEquals(timeEntryMock.getDate().toString(),
				timeViewModelMock.getDateTimeEntry());
		assertEquals(timeEntryMock.getBillableHours(),
				timeViewModelMock.getHoursTimeEntry(), EPSILON);
		assertEquals(projectServiceMock.getTaskName(TASK_ID),
				timeViewModelMock.getTaskNameTimeEntry());
		assertEquals(timeEntryMock.getComment(),
				timeViewModelMock.getCommentTimeEntry());
	}

	 @Test
	 public void convertCallsCorrectServiceMethod() {
	 timeViewModelMock = payPeriodConverterMock.convert(USER_EMAIL);
	 verify(projectServiceMock, times(1)).getAllTasksByCurrentUserId(USER_EMAIL);
	 }

	private TimeEntry createTimeEntry(String id, double hours, LocalDate date) {
		TimeEntry timeEntry = mock(TimeEntry.class);
		given(timeEntry.getUid()).willReturn(id);
		given(timeEntry.getBillableHours()).willReturn(hours);
		given(timeEntry.getDate()).willReturn(date);
		return timeEntry;
	}
}
