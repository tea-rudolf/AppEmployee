package persistence;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriodNotFoundException;
import ca.ulaval.glo4003.appemployee.persistence.XmlPayPeriodRepository;

public class XmlPayPeriodRepositoryTest {
	
	private static final LocalDate START_DATE = new LocalDate("2014-09-21");
	private static final LocalDate END_DATE = new LocalDate("2014-10-04");
	private static final LocalDate ACTUAL_DATE = new LocalDate("2014-10-02");
	private static final Integer NULL_INT = 0;
	private static final double EPSILON = 0.001;
	
	private XmlPayPeriodRepository xmlPayPeriodRepository;
	private PayPeriod payPeriodMock;
	private PayPeriod payPeriod;
	
	@Before
	public void init(){
		xmlPayPeriodRepository = new XmlPayPeriodRepository();
		payPeriod = new PayPeriod(START_DATE, END_DATE);
		payPeriodMock = mock(PayPeriod.class);
	}
	
	@Test
	public void findPayPeriodReturnsCorrectPayPeriodIfFound(){
		when(payPeriodMock.getStartDate()).thenReturn(START_DATE);
		when(payPeriodMock.getEndDate()).thenReturn(END_DATE);
		xmlPayPeriodRepository.add(payPeriodMock);
		
		payPeriodMock = xmlPayPeriodRepository.findPayPeriod(ACTUAL_DATE);
		
		assertEquals(payPeriodMock.getStartDate(), START_DATE);
	}
	
	@Test(expected = PayPeriodNotFoundException.class)
	public void findPayPeriodThrowsExceptionIfPayPeriodNotFound(){
		when(payPeriodMock.getStartDate()).thenThrow(new PayPeriodNotFoundException());
		xmlPayPeriodRepository.findPayPeriod(ACTUAL_DATE);
	}
	
	@Test
	public void getAllTimeEntryUidsCallsGetTimeEntryIdsMethod(){
		List<Integer> list = xmlPayPeriodRepository.getAllTimeEntryUids(payPeriod);
		assertEquals(list.size(), NULL_INT, EPSILON);
	}
}
