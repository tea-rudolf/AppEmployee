package ca.ulaval.glo4003.appemployee.web.viewmodels;

import java.util.ArrayList;
import java.util.List;

public class AssignationEmployeeDepartmentViewModel {

	private List<String> usersWithNoDepartment = new ArrayList<String>();
	private List<String> departmentsList = new ArrayList<String>();
	private String selectedEmployee;
	private String selectedDepartment;

	public List<String> getDepartmentsList() {
		return departmentsList;
	}

	public void setDepartmentsList(List<String> departmentsList) {
		this.departmentsList = departmentsList;
	}

	public String getSelectedDepartment() {
		return selectedDepartment;
	}

	public void setDepartmentSelected(String selectedDepartment) {
		this.selectedDepartment = selectedDepartment;
	}

	public String getSelectedEmployee() {
		return selectedEmployee;
	}

	public void setSelectedEmployee(String selectedEmployee) {
		this.selectedEmployee = selectedEmployee;
	}

	public List<String> getUsersWithNoDepartment() {
		return usersWithNoDepartment;
	}

	public void setUsersWithNoDepartment(List<String> usersWithNoDepartment) {
		this.usersWithNoDepartment = usersWithNoDepartment;
	}

}
