package ca.ulaval.glo4003.appemployee.domain.time;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PayPeriodTest {

	private static final int YEAR = 2014;
	private static final int MONTH = 9;
	private static final int START_DAY = 1;
	private static final int END_DAY = 14;
	private static LocalDate START_DATE = new LocalDate(YEAR, MONTH, START_DAY);
	private static LocalDate END_DATE = new LocalDate(YEAR, MONTH, END_DAY);
	private static final String TIME_ENTRY_ID = "0001";

	private PayPeriod payPeriod;

	@Before
	public void setUp() {
		payPeriod = new PayPeriod(START_DATE, END_DATE);
	}

	@Test
	public void canInstantiatePayPeriod() {
		assertNotNull(payPeriod);
	}

	@Test
	public void addTimeEntryAddTimeEntryToPayPeriod() {
		payPeriod.addTimeEntry(TIME_ENTRY_ID);
		assertTrue(payPeriod.getTimeEntryIds().contains(TIME_ENTRY_ID));
	}

}
