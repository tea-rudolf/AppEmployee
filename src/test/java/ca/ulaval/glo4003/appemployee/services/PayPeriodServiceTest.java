package ca.ulaval.glo4003.appemployee.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.appemployee.domain.payperiod.NoCurrentPayPeriodException;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.repository.PayPeriodRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.TimeEntryRepository;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntry;
import ca.ulaval.glo4003.appemployee.persistence.RepositoryException;
import ca.ulaval.glo4003.appemployee.web.converters.TimeConverter;

@RunWith(MockitoJUnitRunner.class)
public class PayPeriodServiceTest {

	private static final LocalDate VALID_DATE = new LocalDate();
	private static final LocalDate START_DATE = new LocalDate("2014-10-02");
	private static final LocalDate END_DATE = new LocalDate("2014-10-15");
	private static final LocalDate PREVIOUS_DATE = new LocalDate("2014-10-01");
	private static final LocalDate PREVIOUS_START_DATE = new LocalDate("2014-09-25");

	private PayPeriodRepository payPeriodRepositoryMock;
	private TimeEntryRepository timeEntryRepositoryMock;
	private PayPeriod payPeriodMock;
	private PayPeriod previousPayPeriodMock;
	private PayPeriodService payPeriodServiceMock;
	private TimeEntry timeEntryMock;
	private TimeConverter timeConverterMock;

	@Before
	public void init() {
		payPeriodRepositoryMock = mock(PayPeriodRepository.class);
		timeEntryRepositoryMock = mock(TimeEntryRepository.class);
		payPeriodServiceMock = mock(PayPeriodService.class);
		payPeriodMock = mock(PayPeriod.class);
		previousPayPeriodMock = mock(PayPeriod.class);
		timeEntryMock = mock(TimeEntry.class);
		timeConverterMock = mock(TimeConverter.class);
		payPeriodServiceMock = new PayPeriodService(payPeriodRepositoryMock, timeEntryRepositoryMock, timeConverterMock);
	}

	@Test
	public void storeTimeEntryCallsCorrectMethodInRepository() throws Exception {
		payPeriodServiceMock.storeTimeEntry(timeEntryMock);
		verify(timeEntryRepositoryMock, times(1)).store(timeEntryMock);
	}

	@Test(expected = RepositoryException.class)
	public void storeTimeEntryThrowsExceptionWhenTimeEntryRepositoryWasNotUpdated() throws Exception {
		doThrow(new RepositoryException()).when(timeEntryRepositoryMock).store(timeEntryMock);
		payPeriodServiceMock.storeTimeEntry(timeEntryMock);
	}

	@Test
	public void getCurrentPayPeriodReturnsPayPeriodIfSuccessful() {
		when(payPeriodMock.getStartDate()).thenReturn(START_DATE);
		when(payPeriodMock.getEndDate()).thenReturn(END_DATE);
		when(payPeriodRepositoryMock.findByDate(VALID_DATE)).thenReturn(payPeriodMock);

		PayPeriod samplePayPeriod = payPeriodServiceMock.getCurrentPayPeriod();

		assertEquals(samplePayPeriod.getEndDate(), payPeriodMock.getEndDate());
	}

	@Test
	public void getCurrentPayPeriodCallsCorrectMethodInRepository() throws Exception {
		payPeriodServiceMock.getCurrentPayPeriod();
		verify(payPeriodRepositoryMock, times(1)).findByDate(VALID_DATE);
	}

	@Test(expected = NoCurrentPayPeriodException.class)
	public void getCurrentPayPeriodThrowsExceptionWhenPayPeriodNotFound() {
		when(payPeriodRepositoryMock.findByDate(VALID_DATE)).thenThrow(new NoCurrentPayPeriodException());
		payPeriodServiceMock.getCurrentPayPeriod();
	}

	@Test
	public void getPreviousPayPeriodCallsCorrectMethodInRepository() {
		when(payPeriodMock.getStartDate()).thenReturn(START_DATE);
		when(payPeriodServiceMock.getCurrentPayPeriod()).thenReturn(payPeriodMock);

		payPeriodServiceMock.getPreviousPayPeriod();

		verify(payPeriodRepositoryMock, times(1)).findByDate(PREVIOUS_DATE);
	}

	@Test
	public void getPreviousPayPeriodFindsCorrectPayPeriod() {
		when(previousPayPeriodMock.getEndDate()).thenReturn(PREVIOUS_DATE);
		when(previousPayPeriodMock.getStartDate()).thenReturn(PREVIOUS_START_DATE);
		when(payPeriodMock.getStartDate()).thenReturn(START_DATE);
		when(payPeriodServiceMock.getCurrentPayPeriod()).thenReturn(payPeriodMock);
		when(payPeriodRepositoryMock.findByDate(PREVIOUS_DATE)).thenReturn(previousPayPeriodMock);

		PayPeriod samplePayPeriod = payPeriodServiceMock.getPreviousPayPeriod();

		assertEquals(previousPayPeriodMock.getEndDate(), samplePayPeriod.getEndDate());
	}

	@Test(expected = RepositoryException.class)
	public void updatePayPeriodThrowsExceptionWhenPayPeriodWasNotUpdated() throws Exception {
		doThrow(new RepositoryException()).when(payPeriodRepositoryMock).update(payPeriodMock);
		payPeriodServiceMock.updatePayPeriod(payPeriodMock);
	}

	@Test
	public void updateCurrentPayPeriodCallsCorrectRepositoryMethod() throws Exception {
		payPeriodServiceMock.updatePayPeriod(payPeriodMock);
		verify(payPeriodRepositoryMock, times(1)).update(payPeriodMock);
	}

}
