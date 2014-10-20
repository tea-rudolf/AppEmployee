package ca.ulaval.glo4003.appemployee.web.converters;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntry;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TimeViewModel;

public class PayPeriodConverterTest {

	private static final LocalDate START_DATE = new LocalDate(2014, 9, 22);
	private static final LocalDate END_DATE = new LocalDate(2014, 10, 03);
	private static final double HOURS = 7.00;
	private static final String TASK_ID = "id";
	private static final String USER_EMAIL = "employee@employee.com";
	private static final double EPSILON = 0.001;

	private List<Task> expenses = new ArrayList<Task>();
	private List<TimeEntry> timeEntries = new ArrayList<TimeEntry>();

	private TimeConverter payPeriodConverterMock;
	private PayPeriod payPeriodMock;
	private TimeViewModel timeViewModelMock;
	private TimeEntry timeEntryMock;

	@Before
	public void init() {
		payPeriodMock = mock(PayPeriod.class);
		timeViewModelMock = mock(TimeViewModel.class);
		timeEntryMock = mock(TimeEntry.class);
		payPeriodConverterMock = new TimeConverter();
	}

	@Test
	public void convertViewModelConvertsIntoPayPeriod() {
		when(timeViewModelMock.getHoursTimeEntry()).thenReturn(HOURS);
		when(timeViewModelMock.getDateTimeEntry()).thenReturn(START_DATE.toString());
		when(timeViewModelMock.getTaskIdTimeEntry()).thenReturn(TASK_ID);
		when(timeViewModelMock.getUserEmail()).thenReturn(USER_EMAIL);

		timeEntryMock = payPeriodConverterMock.convertToTimeEntry(timeViewModelMock);

		assertEquals(timeViewModelMock.getHoursTimeEntry(), timeEntryMock.getBillableHours(), EPSILON);
		assertEquals(timeViewModelMock.getDateTimeEntry(), timeEntryMock.getDate().toString());
		assertEquals(timeViewModelMock.getTaskIdTimeEntry(), timeEntryMock.getTaskuId());
		assertEquals(timeViewModelMock.getUserEmail(), timeEntryMock.getUserEmail());
	}

	@Test
	public void convertPayPeriodConvertsIntoIntoViewModel() {
		when(payPeriodMock.getStartDate()).thenReturn(START_DATE);
		when(payPeriodMock.getEndDate()).thenReturn(END_DATE);

		timeViewModelMock = payPeriodConverterMock.convert(payPeriodMock, timeEntries, expenses);

		assertEquals(payPeriodMock.getStartDate().toString(), timeViewModelMock.getStartDate());
		assertEquals(payPeriodMock.getEndDate().toString(), timeViewModelMock.getEndDate());
	}
}
