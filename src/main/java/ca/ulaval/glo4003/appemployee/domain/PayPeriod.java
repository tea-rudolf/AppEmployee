package ca.ulaval.glo4003.appemployee.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;

public class PayPeriod {

	private List<Shift> shifts;
	private List<Expenses> expenses;
	private LocalDate startDate;
	private LocalDate endDate;

	public PayPeriod(LocalDate startDate, LocalDate endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
		setShiftsWorked(new ArrayList<Shift>());
		setExpenses(new ArrayList<Expenses>());
		initExpenses();
		initShifts();
	}

	PayPeriod(LocalDate startDate, LocalDate endDate, List<Shift> shifts) {
		this(startDate, endDate);
		this.shifts = shifts;
	}

	public void initShifts() {
		shifts = new ArrayList<Shift>();
		List<LocalDate> dates = new ArrayList<LocalDate>();
		dates = getListOfDates();

		for (LocalDate date : dates) {
			Shift shift = new Shift(date.toString(), 0, " ");
			shifts.add(shift);
		}

	}

	public void initExpenses() {
		expenses = new ArrayList<Expenses>();
		List<LocalDate> dates = new ArrayList<LocalDate>();
		dates = getListOfDates();

		for (LocalDate date : dates) {
			Expenses expense = new Expenses(0, date, "");
			expenses.add(expense);
		}
	}

	public List<LocalDate> getListOfDates() {

		List<LocalDate> dates = new ArrayList<LocalDate>();
		int days = Days.daysBetween(startDate, endDate).getDays();
		for (int i = 0; i < days + 1; i++) {
			LocalDate d = startDate.withFieldAdded(DurationFieldType.days(), i);
			dates.add(d);
		}
		return dates;
	}

	public double getHoursWorked() {
		int totalHoursWorked = 0;
		for (Shift shift : shifts) {
			totalHoursWorked += shift.getHours();
		}
		return totalHoursWorked;
	}

	@XmlElementWrapper(name = "Shifts")
	@XmlElement(name = "Shift")
	public List<Shift> getShiftsWorked() {
		return shifts;
	}

	public void setShiftsWorked(List<Shift> shiftsWorked) {
		this.shifts = shiftsWorked;
	}

	@XmlAttribute(name = "StartDate")
	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	@XmlAttribute(name = "EndDate")
	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	@XmlElementWrapper(name = "Expenses")
	@XmlElement(name = "Expense")
	public List<Expenses> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<Expenses> expenses) {
		this.expenses = expenses;
	}

}
