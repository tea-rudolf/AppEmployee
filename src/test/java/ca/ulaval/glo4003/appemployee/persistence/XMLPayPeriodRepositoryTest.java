package ca.ulaval.glo4003.appemployee.persistence;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriodNotFoundException;

public class XMLPayPeriodRepositoryTest {

	private static final LocalDate START_DATE = new LocalDate("2014-09-21");
	private static final LocalDate END_DATE = new LocalDate("2014-10-04");
	private static final LocalDate ACTUAL_DATE = new LocalDate("2014-10-02");

	private XMLPayPeriodRepository xmlPayPeriodRepository;
	private PayPeriod payPeriodMock;

	@Before
	public void init() throws Exception {
		xmlPayPeriodRepository = new XMLPayPeriodRepository();
		payPeriodMock = mock(PayPeriod.class);
	}

	@Test
	public void findPayPeriodReturnsCorrectPayPeriodIfFound() throws Exception {
		when(payPeriodMock.getStartDate()).thenReturn(START_DATE);
		when(payPeriodMock.getEndDate()).thenReturn(END_DATE);
		xmlPayPeriodRepository.persist(payPeriodMock);

		payPeriodMock = xmlPayPeriodRepository.findByDate(ACTUAL_DATE);

		assertEquals(payPeriodMock.getStartDate(), START_DATE);
	}


	@Test(expected = PayPeriodNotFoundException.class)
	public void findPayPeriodThrowsExceptionIfPayPeriodNotFound() {
		when(payPeriodMock.getStartDate()).thenThrow(new PayPeriodNotFoundException());
		xmlPayPeriodRepository.findByDate(ACTUAL_DATE);
	}
}
