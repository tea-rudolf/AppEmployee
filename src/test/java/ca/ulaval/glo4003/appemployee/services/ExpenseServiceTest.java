package ca.ulaval.glo4003.appemployee.services;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import ca.ulaval.glo4003.appemployee.domain.expense.Expense;
import ca.ulaval.glo4003.appemployee.domain.repository.ExpenseRepository;
import ca.ulaval.glo4003.appemployee.web.converters.ExpenseConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ExpenseViewModel;

public class ExpenseServiceTest {

	private static final String Expense_UID = "0002";
	private static final String NEW_UID = "0003";

	private ExpenseRepository expenseRepositoryMock;
	private ExpenseConverter expenseConverterMock;
	private ExpenseService expenseService;
	private Expense expenseMock;
	private ExpenseViewModel expenseViewModelMock;

	@Before
	public void init() {
		expenseRepositoryMock = mock(ExpenseRepository.class);
		expenseConverterMock = mock(ExpenseConverter.class);
		expenseMock = mock(Expense.class);
		expenseViewModelMock = mock(ExpenseViewModel.class);
		expenseService = new ExpenseService(expenseRepositoryMock, expenseConverterMock);
	}

	@Test
	public void findByUidFindsCorrectExpense() throws Exception {
		when(expenseMock.getuId()).thenReturn(Expense_UID);
		when(expenseRepositoryMock.findByUid(Expense_UID)).thenReturn(expenseMock);
		Expense sampleExpense = expenseService.findByuId(Expense_UID);
		assertEquals(expenseMock.getuId(), sampleExpense.getuId());
	}

	@Test
	public void storeCallsCorrectRepositoryMethod() throws Exception {
		expenseService.store(expenseMock);
		verify(expenseRepositoryMock, times(1)).store(expenseMock);
	}

	@Test
	public void updateCallsCorrectDomainMethod() throws Exception {
		when(expenseConverterMock.convert(expenseViewModelMock)).thenReturn(expenseMock);
		when(expenseViewModelMock.getuId()).thenReturn(NEW_UID);
		expenseService.update(NEW_UID, expenseViewModelMock);
		verify(expenseMock, times(1)).setuId(NEW_UID);
	}

}
