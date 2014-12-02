package ca.ulaval.glo4003.appemployee.services;

import static org.junit.Assert.*;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.payperiod.TimeProcessor;
import ca.ulaval.glo4003.appemployee.domain.repository.PayPeriodRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.TimeEntryRepository;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntry;
import ca.ulaval.glo4003.appemployee.web.converters.TimeConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TimeViewModel;

@RunWith(MockitoJUnitRunner.class)
public class TimeServiceTest {

	private static final LocalDate CURRENT_DATE = new LocalDate();
	private static final String A_UID = "0001";
	private static final int PAYPERIOD_DURATION = 13;

	@Mock
	private PayPeriodRepository payPeriodRepositoryMock;

	@Mock
	private TimeEntryRepository timeEntryRepositoryMock;

	@Mock
	private PayPeriod payPeriodMock;

	@Mock
	private PayPeriod previousPayPeriodMock;

	@Mock
	private TimeViewModel timeViewModelMock;

	@Mock
	private TimeConverter timeConverterMock;

	@Mock
	private TimeEntry timeEntryMock;

	@Mock
	private UserService userServiceMock;

	@Mock
	private TimeProcessor timeProcessorMock;

	@InjectMocks
	private TimeService payPeriodService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		payPeriodService = new TimeService(timeProcessorMock, timeConverterMock, userServiceMock);
	}

	@Test
	public void canInstantiateService() {
		assertNotNull(payPeriodService);
	}

	// @Test(expected = PayPeriodNotFoundException.class)
	// public void
	// retrieveCurrentPayPeriodThrowsExceptionWhenPayPeriodNotFound() {
	// when(payPeriodRepositoryMock.findByDate(CURRENT_DATE)).thenReturn(null);
	// payPeriodService.retrieveCurrentPayPeriod();
	// }

	// @Test
	// public void retrieveCurrentPayPeriodFindsPayPeriodWhenExists() {
	// when(payPeriodRepositoryMock.findByDate(CURRENT_DATE)).thenReturn(payPeriodMock);
	// PayPeriod currentPayPeriod = payPeriodService.retrieveCurrentPayPeriod();
	// assertEquals(payPeriodMock, currentPayPeriod);
	// }

	// @Test(expected = PayPeriodNotFoundException.class)
	// public void
	// retrievePreviousPayPeriodThrowsExceptionWhenCurrentPayPeriodNotFound() {
	// when(payPeriodRepositoryMock.findByDate(CURRENT_DATE)).thenReturn(null);
	// payPeriodService.retrievePreviousPayPeriod();
	// }

	// @Test
	// public void retrievePreviousPayPeriodCallsRetrieveCurrentPayPeriod() {
	// when(payPeriodRepositoryMock.findByDate(CURRENT_DATE)).thenReturn(payPeriodMock);
	// LocalDate startDate = new
	// LocalDate(CURRENT_DATE.minusDays(PAYPERIOD_DURATION));
	// when(payPeriodMock.getStartDate()).thenReturn(startDate);
	// when(payPeriodRepositoryMock.findByDate(startDate.minusDays(1))).thenReturn(previousPayPeriodMock);
	//
	// PayPeriod previousPayPeriod =
	// payPeriodService.retrievePreviousPayPeriod();
	//
	// assertEquals(previousPayPeriodMock, previousPayPeriod);
	// }

	// @Test(expected = PayPeriodNotFoundException.class)
	// public void updatePayPeriodThrowsExceptionWhenPayPeriodNotFound() throws
	// Exception {
	// doThrow(PayPeriodNotFoundException.class).when(payPeriodRepositoryMock).update(payPeriodMock);
	// payPeriodService.updatePayPeriod(payPeriodMock);
	// }
	//
	// @Test
	// public void updatePayPeriodCallsPayPeriodRepository() throws Exception {
	// payPeriodService.updatePayPeriod(payPeriodMock);
	// verify(payPeriodRepositoryMock, times(1)).update(payPeriodMock);
	// }

	// @Test
	// public void saveTimeEntryCallsTimeEntryRepository() throws Exception {
	// when(timeConverterMock.convert(timeViewModelMock)).thenReturn(timeEntryMock);
	// when(timeEntryMock.getUid()).thenReturn(A_UID);
	// payPeriodService.createTimeEntry(timeViewModelMock, payPeriodMock);
	// verify(timeEntryRepositoryMock, times(1)).store(any(TimeEntry.class));
	// }

}
