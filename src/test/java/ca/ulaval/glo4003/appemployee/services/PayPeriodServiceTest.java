package ca.ulaval.glo4003.appemployee.services;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.appemployee.domain.exceptions.PayPeriodNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.repository.PayPeriodRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.TimeEntryRepository;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntry;
import ca.ulaval.glo4003.appemployee.web.converters.TimeConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TimeViewModel;

@RunWith(MockitoJUnitRunner.class)
public class PayPeriodServiceTest {

	private PayPeriodRepository payPeriodRepositoryMock;
	private TimeEntryRepository timeEntryRepositoryMock;
	private PayPeriod payPeriodMock;
	private PayPeriod previousPayPeriodMock;
	private TimeViewModel timeViewModelMock;
	private PayPeriodService payPeriodService;
	private TimeConverter timeConverter;
	private TimeEntry timeEntry;

	private static final LocalDate CURRENT_DATE = new LocalDate();
	private static final String A_UID = "0001";
	private static final int PAYPERIOD_DURATION = 13;

	@Before
	public void setUp() {
		payPeriodRepositoryMock = mock(PayPeriodRepository.class);
		timeEntryRepositoryMock = mock(TimeEntryRepository.class);
		payPeriodMock = mock(PayPeriod.class);
		previousPayPeriodMock = mock(PayPeriod.class);
		timeViewModelMock = mock(TimeViewModel.class);
		timeConverter = mock(TimeConverter.class);
		timeEntry = mock(TimeEntry.class);
		payPeriodService = new PayPeriodService(payPeriodRepositoryMock, timeEntryRepositoryMock, timeConverter);
	}

	@Test
	public void canInstantiateService() {
		assertNotNull(payPeriodService);
	}

	@Test(expected = PayPeriodNotFoundException.class)
	public void retrieveCurrentPayPeriodThrowsExceptionWhenPayPeriodNotFound() {
		when(payPeriodRepositoryMock.findByDate(CURRENT_DATE)).thenReturn(null);
		payPeriodService.retrieveCurrentPayPeriod();
	}

	@Test
	public void retrieveCurrentPayPeriodFindsPayPeriodWhenExists() {
		when(payPeriodRepositoryMock.findByDate(CURRENT_DATE)).thenReturn(payPeriodMock);
		PayPeriod currentPayPeriod = payPeriodService.retrieveCurrentPayPeriod();
		assertEquals(payPeriodMock, currentPayPeriod);
	}

	@Test(expected = PayPeriodNotFoundException.class)
	public void retrievePreviousPayPeriodThrowsExceptionWhenCurrentPayPeriodNotFound() {
		when(payPeriodRepositoryMock.findByDate(CURRENT_DATE)).thenReturn(null);
		payPeriodService.retrievePreviousPayPeriod();
	}

	@Test
	public void retrievePreviousPayPeriodCallsRetrieveCurrentPayPeriod() {
		when(payPeriodRepositoryMock.findByDate(CURRENT_DATE)).thenReturn(payPeriodMock);
		LocalDate startDate = new LocalDate(CURRENT_DATE.minusDays(PAYPERIOD_DURATION));
		when(payPeriodMock.getStartDate()).thenReturn(startDate);
		when(payPeriodRepositoryMock.findByDate(startDate.minusDays(1))).thenReturn(previousPayPeriodMock);

		PayPeriod previousPayPeriod = payPeriodService.retrievePreviousPayPeriod();

		assertEquals(previousPayPeriodMock, previousPayPeriod);
	}

	@Test(expected = PayPeriodNotFoundException.class)
	public void updatePayPeriodThrowsExceptionWhenPayPeriodNotFound() throws Exception {
		doThrow(PayPeriodNotFoundException.class).when(payPeriodRepositoryMock).update(payPeriodMock);
		payPeriodService.updatePayPeriod(payPeriodMock);
	}

	@Test
	public void updatePayPeriodCallsPayPeriodRepository() throws Exception {
		payPeriodService.updatePayPeriod(payPeriodMock);
		verify(payPeriodRepositoryMock, times(1)).update(payPeriodMock);
	}

	@Test
	public void saveTimeEntryCallsTimeEntryRepository() throws Exception {
		when(timeConverter.convert(timeViewModelMock)).thenReturn(timeEntry);
		when(timeEntry.getUid()).thenReturn(A_UID);
		payPeriodService.createTimeEntry(timeViewModelMock, payPeriodMock);
		verify(timeEntryRepositoryMock, times(1)).store(any(TimeEntry.class));
	}

}
