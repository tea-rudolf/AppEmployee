package ca.ulaval.glo4003.appemployee.domain.timeentry;

import org.joda.time.LocalDate;

public class TimeEntry {
	
	private Integer uId;
	private Integer billableHours;
	private LocalDate date;
	private String userId;
	private String taskId;
	
	public TimeEntry(Integer uId) {
		this.uId = uId;		
	}
	
	public Integer getBillableHours() {
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

	public Integer getuId() {
		return uId;
	}

	public void setuId(Integer uId) {
		this.uId = uId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

}
