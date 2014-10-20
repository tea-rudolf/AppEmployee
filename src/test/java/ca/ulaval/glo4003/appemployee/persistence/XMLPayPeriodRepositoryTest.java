package ca.ulaval.glo4003.appemployee.persistence;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriodNotFoundException;

public class XMLPayPeriodRepositoryTest {

	private static final LocalDate START_DATE = new LocalDate("2014-09-29");
	private static final LocalDate END_DATE = new LocalDate("2014-10-12");
	private static final LocalDate ACTUAL_DATE = new LocalDate("2014-10-02");
	private static final LocalDate WRONG_DATE = new LocalDate("2016-10-10");

	private XMLPayPeriodRepository xmlPayPeriodRepository;
	private PayPeriod payPeriodMock;

	@Before
	public void init() throws Exception {
		xmlPayPeriodRepository = new XMLPayPeriodRepository();
		payPeriodMock = mock(PayPeriod.class);
	}

	@Test
	public void findByDateReturnsCorrectPayPeriodIfFound() throws Exception {
		when(payPeriodMock.getStartDate()).thenReturn(START_DATE);
		when(payPeriodMock.getEndDate()).thenReturn(END_DATE);
		xmlPayPeriodRepository.persist(payPeriodMock);

		PayPeriod samplePayPeriod = xmlPayPeriodRepository.findByDate(ACTUAL_DATE);

		assertEquals(samplePayPeriod.getStartDate(), payPeriodMock.getStartDate());
	}

	@Test
	public void persistAddsPayPeriodIntoRepository() throws Exception {
		when(payPeriodMock.getStartDate()).thenReturn(START_DATE);
		when(payPeriodMock.getEndDate()).thenReturn(END_DATE);

		xmlPayPeriodRepository.persist(payPeriodMock);

		assertEquals(xmlPayPeriodRepository.findByDate(ACTUAL_DATE).getStartDate(), payPeriodMock.getStartDate());
	}

	@Test(expected = PayPeriodNotFoundException.class)
	public void findPayPeriodThrowsExceptionIfPayPeriodNotFound() {
		xmlPayPeriodRepository.findByDate(WRONG_DATE);
	}
}
