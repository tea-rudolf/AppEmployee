package ca.ulaval.glo4003.appemployee.web.viewmodels;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

public class ProjectViewModel {
	private String uid;
	private String name;
	private LocalDate startDate;
	private LocalDate endDate;
	private List<String> taskIds = new ArrayList<String>();
	private List<String> userIds = new ArrayList<String>();
	private List<String> expenseIds = new ArrayList<String>();
	private String currentUserEmail = "";
	private String selectedUserEmail = "";
	private List<String> availableUsers = new ArrayList<String>();

	public String getuId() {
		return uid;
	}

	public void setuId(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public List<String> getTaskIds() {
		return taskIds;
	}

	public void setTaskIds(List<String> taskIds) {
		this.taskIds = taskIds;
	}

	public List<String> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}

	public List<String> getExpenseIds() {
		return expenseIds;
	}

	public void setExpenseIds(List<String> expenseIds) {
		this.expenseIds = expenseIds;
	}

	public String getSelectedUserEmail() {
		return selectedUserEmail;
	}
	
	public void setSelectedUserEmail(String selectedUserEmail) {
		this.selectedUserEmail = selectedUserEmail;
	}

	public List<String> getAvailableUsers() {
		return availableUsers;
	}

	public void setAvailableUsers(List<String> list) {
		this.availableUsers = list;
	}

	public String getCurrentUserEmail() {
		return currentUserEmail;
	}

	public void setCurrentUserEmail(String currentUserEmail) {
		this.currentUserEmail = currentUserEmail;
	}

}
