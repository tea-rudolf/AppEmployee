package ca.ulaval.glo4003.appemployee.domain.timeentry;

import java.util.UUID;

import org.joda.time.LocalDate;

public class TimeEntry {

	private String uId;
	private double billableHours;
	private LocalDate date;
	private String userEmail;
	private String taskuId;
	private String comment;

	public TimeEntry() {
		this.uId = UUID.randomUUID().toString();
	}
	
	public TimeEntry(String uId) {
		this.uId = uId;	
	}

	public double getBillableHours() {
		return billableHours;
	}

	public void setBillableHours(double billableHours) {
		this.billableHours = billableHours;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public String getTaskuId() {
		return taskuId;
	}

	public void setTaskuId(String taskuId) {
		this.taskuId = taskuId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
