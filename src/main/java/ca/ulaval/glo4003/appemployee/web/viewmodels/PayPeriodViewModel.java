package ca.ulaval.glo4003.appemployee.web.viewmodels;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4003.appemployee.domain.Shift;
import ca.ulaval.glo4003.appemployee.domain.expense.Expense;

public class PayPeriodViewModel {

	private String startDate;
	private String endDate;
	private List<Shift> shifts;
	private List<Expense> expenses;

	public PayPeriodViewModel() {
		shifts = new ArrayList<Shift>();
		expenses = new ArrayList<Expense>();
	}

	public List<Shift> getShifts() {
		return shifts;
	}

	public void setShifts(List<Shift> shifts) {
		this.shifts = shifts;
	}

	public List<Expense> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<Expense> expenses) {
		this.expenses = expenses;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
}
