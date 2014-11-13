package ca.ulaval.glo4003.appemployee.web.viewmodels;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

public class ProjectViewModel {
	private String uId;
	private String name;
	private LocalDate startDate;
	private LocalDate endDate;
	private List<String> taskIds = new ArrayList<String>();
	private List<String> userIds = new ArrayList<String>();
	private List<String> expenseIds = new ArrayList<String>();
	private String userEmail = "";
	private List<String> availableUsers = new ArrayList<String>();

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
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

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public List<String> getAvailableUsers() {
		return availableUsers;
	}

	public void setAvailableUsers(List<String> list) {
		this.availableUsers = list;
	}

}
