package ca.ulaval.glo4003.appemployee.web.converters;

import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.appemployee.domain.Shift;
import ca.ulaval.glo4003.appemployee.domain.expense.Expense;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.web.viewmodels.PayPeriodViewModel;

@Component
public class PayPeriodConverter {

	public PayPeriodConverter() {

	}

	public PayPeriod convert(PayPeriodViewModel payPeriodViewModel) {
		LocalDate startDate = new LocalDate(payPeriodViewModel.getStartDate());
		LocalDate endDate = new LocalDate(payPeriodViewModel.getEndDate());
		List<Shift> shifts = payPeriodViewModel.getShifts();
		List<Expense> expenses = payPeriodViewModel.getExpenses();
		PayPeriod payPeriod = new PayPeriod(startDate, endDate);
		payPeriod.setShiftsWorked(shifts);
		payPeriod.setExpenses(expenses);
		return payPeriod;
	}

	public PayPeriodViewModel convert(PayPeriod currentPayPeriod) {
		PayPeriodViewModel form = new PayPeriodViewModel();
		form.setStartDate(currentPayPeriod.getStartDate().toString());
		form.setEndDate(currentPayPeriod.getEndDate().toString());
		form.setShifts(currentPayPeriod.getShiftsWorked());
		form.setExpenses(currentPayPeriod.getExpenses());
		return form;
	}
}
