package ca.ulaval.glo4003.appemployee.domain;

import static org.junit.Assert.*;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;

@RunWith(MockitoJUnitRunner.class)
public class PayPeriodTest {

	private static final int YEAR = 2014;
	private static final int MONTH = 9;
	private static final int START_DAY = 1;
	private static final int END_DAY = 14;
	private static LocalDate START_DATE = new LocalDate(YEAR, MONTH, START_DAY);
	private static LocalDate END_DATE = new LocalDate(YEAR, MONTH, END_DAY);

	private PayPeriod payPeriod;

	@Test
	public void canInstantiatePayPeriod() {
		payPeriod = new PayPeriod(START_DATE, END_DATE);
		assertNotNull(payPeriod);
	}

}
