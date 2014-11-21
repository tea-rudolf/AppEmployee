package ca.ulaval.glo4003.appemployee.web.viewmodels;

import java.util.ArrayList;
import java.util.List;

public class AssignationEmployeDepartmentViewModel {

	private List<String> usersWithNoDepartment = new ArrayList<String>();
	private List<String> departmentsList = new ArrayList<String>();
	private String userSelected;
	private String departmentSelected;

	public List<String> getDepartmentsList() {
		return departmentsList;
	}

	public void setDepartmentsList(List<String> departmentsList) {
		this.departmentsList = departmentsList;
	}

	public String getDepartmentSelected() {
		return departmentSelected;
	}

	public void setDepartmentSelected(String departmentSelected) {
		this.departmentSelected = departmentSelected;
	}

	public String getUserSelected() {
		return userSelected;
	}

	public void setUserSelected(String userSelected) {
		this.userSelected = userSelected;
	}

	public List<String> getUsersWithNoDepartment() {
		return usersWithNoDepartment;
	}

	public void setUsersWithNoDepartment(List<String> usersWithNoDepartment) {
		this.usersWithNoDepartment = usersWithNoDepartment;
	}

}
