package ca.ulaval.glo4003.appemployee.web.viewmodels;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import ca.ulaval.glo4003.appemployee.domain.task.Task;

public class PayPeriodViewModel {

	private String startDate;
	private String endDate;
	private List<String> timeEntryIds = new ArrayList<String>();
	private List<Task> availableTasks = new ArrayList<Task>();
	private double billableHoursTimeEntry;
	private LocalDate dateTimeEntry;
	private String userEmail;
	private String taskIdTimeEntry;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public List<Task> getAvailableTasks() {
		return availableTasks;
	}

	public void setAvailableTasks(List<Task> tasks) {
		this.availableTasks = tasks;
	}

	public double getBillableHoursTimeEntry() {
		return billableHoursTimeEntry;
	}

	public void setBillableHoursTimeEntry(double billableHoursTimeEntry) {
		this.billableHoursTimeEntry = billableHoursTimeEntry;
	}

	public LocalDate getDateTimeEntry() {
		return dateTimeEntry;
	}

	public void setDateTimeEntry(LocalDate dateTimeEntry) {
		this.dateTimeEntry = dateTimeEntry;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getTaskIdTimeEntry() {
		return taskIdTimeEntry;
	}

	public void setTaskIdTimeEntry(String taskIdTimeEntry) {
		this.taskIdTimeEntry = taskIdTimeEntry;
	}

	public List<String> getTimeEntryIds() {
		return timeEntryIds;
	}

	public void setTimeEntryIds(List<String> timeEntryIds) {
		this.timeEntryIds = timeEntryIds;
	}

}
