package ca.ulaval.glo4003.appemployee.web.converters;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import ca.ulaval.glo4003.appemployee.domain.Expenses;
import ca.ulaval.glo4003.appemployee.domain.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.Shift;
import ca.ulaval.glo4003.appemployee.web.converters.PayPeriodConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.PayPeriodViewModel;

public class PayPeriodConverterTest {
	
	private static final LocalDate START_DATE = new LocalDate(2014,9,22);
	private static final LocalDate END_DATE = new LocalDate(2014,10,03);
	
	List<Shift> shifts = new ArrayList<Shift>();
	List<Expenses> expenses = new ArrayList<Expenses>();
	
	PayPeriodConverter payPeriodConverterMock;
	PayPeriod payPeriodMock;
	PayPeriodViewModel payPeriodViewModelMock;
	
	@Before
	public void init(){
		payPeriodConverterMock = mock(PayPeriodConverter.class);
		payPeriodMock = mock(PayPeriod.class);
		payPeriodViewModelMock = mock(PayPeriodViewModel.class);
		payPeriodConverterMock = new PayPeriodConverter();
	}

	@Test
	public void convertViewModelConvertsIntoPayPeriod() {
		when(payPeriodViewModelMock.getStartDate()).thenReturn(START_DATE.toString());
		when(payPeriodViewModelMock.getEndDate()).thenReturn(END_DATE.toString());
		when(payPeriodViewModelMock.getShifts()).thenReturn(shifts);
		when(payPeriodViewModelMock.getExpenses()).thenReturn(expenses);
		
		PayPeriod payPeriod = payPeriodConverterMock.convert(payPeriodViewModelMock);
		
		assertEquals(payPeriodViewModelMock.getStartDate(), payPeriod.getStartDate().toString());
		assertEquals(payPeriodViewModelMock.getEndDate(), payPeriod.getEndDate().toString());
		assertEquals(payPeriodViewModelMock.getExpenses(), payPeriod.getExpenses());
		assertEquals(payPeriodViewModelMock.getShifts(), payPeriod.getShiftsWorked());
	}
	
	@Test
	public void convertPayPeriodConvertsIntoIntoViewModel(){
		when(payPeriodMock.getStartDate()).thenReturn(START_DATE);
		when(payPeriodMock.getEndDate()).thenReturn(END_DATE);
		when(payPeriodMock.getShiftsWorked()).thenReturn(shifts);
		when(payPeriodMock.getExpenses()).thenReturn(expenses);
		
		PayPeriodViewModel viewModel = payPeriodConverterMock.convert(payPeriodMock);
		
		assertEquals(payPeriodMock.getStartDate().toString(), viewModel.getStartDate());
		assertEquals(payPeriodMock.getEndDate().toString(), viewModel.getEndDate());
		assertEquals(payPeriodMock.getExpenses(), viewModel.getExpenses());
		assertEquals(payPeriodMock.getShiftsWorked(), viewModel.getShifts());
	}
}
