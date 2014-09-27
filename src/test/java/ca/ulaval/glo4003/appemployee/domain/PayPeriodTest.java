package ca.ulaval.glo4003.appemployee.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class PayPeriodTest {
	
	private static final LocalDate START_DATE = new LocalDate(2014,9,22);
	private static final LocalDate END_DATE = new LocalDate(2014,10,3);
	private static final double SAMPLE_NUMBER_OF_HOURS = 7;
	
	List<Shift> shifts = new ArrayList<Shift>();
	
	Shift shiftMock;
	Expenses expensesMock;
	PayPeriod payPeriodMock;
	
	@Before
	public void init(){
		shiftMock = mock(Shift.class);
		expensesMock = mock(Expenses.class);
		payPeriodMock = new PayPeriod(START_DATE, END_DATE);
	}

	@Test
	public void getTotalHoursWorkedReturnCorrectNumberOfHours() {
		when(shiftMock.getHours()).thenReturn(SAMPLE_NUMBER_OF_HOURS);
		
		fail("Not yet implemented");
	}

}
