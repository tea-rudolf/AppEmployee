package ca.ulaval.glo4003.appemployee.domain;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class PayPeriodTest {

	private static final LocalDate START_DATE = new LocalDate(2014, 9, 22);
	private static final LocalDate END_DATE = new LocalDate(2014, 10, 3);
	private static final double SAMPLE_NUMBER_OF_HOURS = 7.0;
	private static final double EPSILON = 0.001;
	private static final String SHIFT_DATE = "date";
	private static final String SHIFT_COMMENT = "comment";

	private List<Shift> shifts = new ArrayList<Shift>();

	private Shift shiftMock;
	private Shift shift;
	private PayPeriod payPeriodMock;

	@Before
	public void init() {
		shiftMock = mock(Shift.class);
		shift = new Shift(SHIFT_DATE, SAMPLE_NUMBER_OF_HOURS, SHIFT_COMMENT);
		shifts.add(shift);
		payPeriodMock = new PayPeriod(START_DATE, END_DATE, shifts);
	}

	@Test
	public void getTotalHoursWorkedReturnCorrectNumberOfHours() {
		when(shiftMock.getHours()).thenReturn(SAMPLE_NUMBER_OF_HOURS);
		double hours = payPeriodMock.getHoursWorked();
		assertEquals(shift.getHours(), hours, EPSILON);
	}
}
