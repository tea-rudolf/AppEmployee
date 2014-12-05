package ca.ulaval.glo4003.appemployee.web.viewmodels;

import java.util.ArrayList;
import java.util.List;

public class DepartmentViewModel {

	private String name;
	private List<String> supervisorIds;
	private List<String> employeeIds;
	private List<String> availableUsers = new ArrayList<String>();
	private String selectedUserEmails = "";

	public DepartmentViewModel() {

	}

	public DepartmentViewModel(List<String> availableUsers) {
		this.availableUsers = availableUsers;
	}

	public DepartmentViewModel(String name, List<String> employeeIds, List<String> supervisorIds) {
		this.name = name;
		this.employeeIds = employeeIds;
		this.supervisorIds = supervisorIds;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getSupervisorIds() {
		return supervisorIds;
	}

	public void setSupervisorIds(List<String> supervisorIds) {
		this.supervisorIds = supervisorIds;
	}

	public List<String> getEmployeeIds() {
		return employeeIds;
	}

	public void setEmployeeIds(List<String> employeeIds) {
		this.employeeIds = employeeIds;
	}

	public List<String> getAvailableUsers() {
		return availableUsers;
	}

	public void setAvailableUsers(List<String> availableUsers) {
		this.availableUsers = availableUsers;
	}

	public String getSelectedUserEmails() {
		return selectedUserEmails;
	}

	public void setSelectedUserEmails(String userEmailsSelected) {
		this.selectedUserEmails = userEmailsSelected;
	}

}
