package ca.ulaval.glo4003.appemployee.web.viewmodels;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4003.appemployee.domain.task.Task;

public class TimeEntryViewModel {

	private List<Task> availableTasks = new ArrayList<Task>();
	private double hours;
	private String date;
	private String userEmail;
	private String comment;
	private String taskId;
	private String taskName;
	private String uid;
	
	public TimeEntryViewModel(String uid, String userEmail, String date, double billableHours, String taskUid, String taskName, 
			String comment, List<Task> availableTasks) {
			this.uid = uid;
			this.userEmail = userEmail;
			this.date = date;
			this.hours = billableHours;
			this.taskId = taskUid;
			this.taskName = taskName;
			this.comment = comment;
			this.availableTasks = availableTasks;
		
	}
	
	public TimeEntryViewModel() {
		
	}

	public List<Task> getAvailableTasks() {
		return availableTasks;
	}
	
	public void setAvailableTasks(List<Task> availableTasks) {
		this.availableTasks = availableTasks;
	}
	
	public double getHours() {
		return hours;
	}
	
	public void setHours(double hours) {
		this.hours = hours;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}


}
