package ca.ulaval.glo4003.appemployee.web.converters;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntry;
import ca.ulaval.glo4003.appemployee.services.ProjectService;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TimeViewModel;

public class PayPeriodConverterTest {

	private static final LocalDate START_DATE = new LocalDate(2014, 9, 22);
	private static final LocalDate END_DATE = new LocalDate(2014, 10, 03);
	private static final double HOURS = 7.00;
	private static final String TASK_ID = "id";
	private static final String TIME_ENTRY_ID = "anotherid";
	private static final String TASK_NAME = "task";
	private static final String USER_EMAIL = "employee@employee.com";
	private static final double EPSILON = 0.001;
	private static final String COMMENT = "imacomment";

	private List<Task> expenses = new ArrayList<Task>();

	private TimeConverter payPeriodConverterMock;
	private PayPeriod payPeriodMock;
	private TimeViewModel timeViewModelMock;
	private TimeEntry timeEntryMock;
	private ProjectService projectServiceMock;

	@Before
	public void init() {
		payPeriodMock = mock(PayPeriod.class);
		timeViewModelMock = mock(TimeViewModel.class);
		timeEntryMock = mock(TimeEntry.class);
		projectServiceMock = mock(ProjectService.class);
		payPeriodConverterMock = new TimeConverter(projectServiceMock);
	}

	@Test
	public void convertViewModelConvertsIntoPayPeriod() {
		when(timeViewModelMock.getHoursTimeEntry()).thenReturn(HOURS);
		when(timeViewModelMock.getDateTimeEntry()).thenReturn(START_DATE.toString());
		when(timeViewModelMock.getTaskIdTimeEntry()).thenReturn(TASK_ID);
		when(timeViewModelMock.getUserEmail()).thenReturn(USER_EMAIL);
		when(timeViewModelMock.getCommentTimeEntry()).thenReturn(COMMENT);

		timeEntryMock = payPeriodConverterMock.convert(timeViewModelMock);

		assertEquals(timeViewModelMock.getHoursTimeEntry(), timeEntryMock.getBillableHours(), EPSILON);
		assertEquals(timeViewModelMock.getDateTimeEntry(), timeEntryMock.getDate().toString());
		assertEquals(timeViewModelMock.getTaskIdTimeEntry(), timeEntryMock.getTaskUid());
		assertEquals(timeViewModelMock.getUserEmail(), timeEntryMock.getUserEmail());
		assertEquals(timeViewModelMock.getCommentTimeEntry(), timeEntryMock.getComment());
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

		assertEquals(timeEntryMock.getDate().toString(), timeViewModelMock.getDateTimeEntry());
		assertEquals(timeEntryMock.getBillableHours(), timeViewModelMock.getHoursTimeEntry(), EPSILON);
		assertEquals(projectServiceMock.getTaskName(TASK_ID), timeViewModelMock.getTaskNameTimeEntry());
		assertEquals(timeEntryMock.getComment(), timeViewModelMock.getCommentTimeEntry());
	}

	@Test
	public void convertPayPeriodConvertsIntoIntoViewModel() {
		when(payPeriodMock.getStartDate()).thenReturn(START_DATE);
		when(payPeriodMock.getEndDate()).thenReturn(END_DATE);

		timeViewModelMock = payPeriodConverterMock.convert(payPeriodMock, expenses);

		assertEquals(payPeriodMock.getStartDate().toString(), timeViewModelMock.getStartDate());
		assertEquals(payPeriodMock.getEndDate().toString(), timeViewModelMock.getEndDate());
	}
}
