package ca.ulaval.glo4003.appemployee.domain.time;

import java.util.UUID;

import org.joda.time.LocalDate;

public class TimeEntry {

	private String uid;
	private double billableHours;
	private LocalDate date;
	private String userEmail;
	private String taskUid;
	private String comment;

	public TimeEntry() {
		this.setUid(UUID.randomUUID().toString());
	}

	public TimeEntry(String uid, double billableHours, LocalDate date, String userEmail, String taskUid, String comment) {
		this.uid = uid;
		this.billableHours = billableHours;
		this.date = date;
		this.userEmail = userEmail;
		this.taskUid = taskUid;
		this.comment = comment;
	}

	public TimeEntry(double billableHours, LocalDate date, String userEmail, String taskUid, String comment) {
		this();
		this.billableHours = billableHours;
		this.date = date;
		this.userEmail = userEmail;
		this.taskUid = taskUid;
		this.comment = comment;
	}

	public TimeEntry(String uid) {
		this.setUid(uid);
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

	public String getTaskUid() {
		return taskUid;
	}

	public void setTaskUid(String taskUid) {
		this.taskUid = taskUid;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public void update(double billableHours, LocalDate date, String userEmail, String taskUid, String comment) {
		this.billableHours = billableHours;
		this.date = date;
		this.userEmail = userEmail;
		this.taskUid = taskUid;
		this.comment = comment;
	}
}
