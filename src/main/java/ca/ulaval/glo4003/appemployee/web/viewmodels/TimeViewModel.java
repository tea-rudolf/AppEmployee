package ca.ulaval.glo4003.appemployee.web.viewmodels;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4003.appemployee.domain.task.Task;

public class TimeViewModel {

	private String payPeriodStartDate;
	private String payPeriodEndDate;
	private List<Task> availableTasks = new ArrayList<Task>();
	private double hoursTimeEntry;
	private String dateTimeEntry;
	private String userEmail;
	private String commentTimeEntry;
	private String taskIdTimeEntry;

	public String getPayPeriodStartDate() {
		return payPeriodStartDate;
	}

	public void setPayPeriodStartDate(String payPeriodStartDate) {
		this.payPeriodStartDate = payPeriodStartDate;
	}

	public String getPayPeriodEndDate() {
		return payPeriodEndDate;
	}

	public void setPayPeriodEndDate(String payPeriodEndDate) {
		this.payPeriodEndDate = payPeriodEndDate;
	}
	
	public String getStartDate() {
		return payPeriodStartDate;
	}

	public void setStartDate(String startDate) {
		this.payPeriodStartDate = startDate;
	}

	public String getEndDate() {
		return payPeriodEndDate;
	}

	public void setEndDate(String endDate) {
		this.payPeriodEndDate = endDate;
	}

	public List<Task> getAvailableTasks() {
		return availableTasks;
	}

	public void setAvailableTasks(List<Task> tasks) {
		this.availableTasks = tasks;
	}

	public double getHoursTimeEntry() {
		return hoursTimeEntry;
	}

	public void setHoursTimeEntry(double billableHoursTimeEntry) {
		this.hoursTimeEntry = billableHoursTimeEntry;
	}

	public String getDateTimeEntry() {
		return dateTimeEntry;
	}

	public void setDateTimeEntry(String dateTimeEntry) {
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

	public String getCommentTimeEntry() {
		return commentTimeEntry;
	}

	public void setCommentTimeEntry(String commentTimeEntry) {
		this.commentTimeEntry = commentTimeEntry;
	}

}
