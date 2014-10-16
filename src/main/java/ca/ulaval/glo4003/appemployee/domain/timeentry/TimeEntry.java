package ca.ulaval.glo4003.appemployee.domain.timeentry;

import org.joda.time.LocalDate;

public class TimeEntry {

	private String uId;
	private double billableHours;
	private LocalDate date;
	private String userId;
	private String taskId;

	public TimeEntry(String uId) {
		this.uId = uId;
	}

	public double getBillableHours() {
		return billableHours;
	}

	public void setBillableHours(Integer billableHours) {
		this.billableHours = billableHours;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

}
