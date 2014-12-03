package ca.ulaval.glo4003.appemployee.web.viewmodels;

import java.util.ArrayList;
import java.util.List;

public class EmployeeAssignationViewModel {

	private List<String> employeesWithNoDepartment = new ArrayList<String>();
	private List<String> departmentsList = new ArrayList<String>();
	private String selectedEmployee;
	private String selectedDepartment;

	public EmployeeAssignationViewModel() {

	}

	public EmployeeAssignationViewModel(ArrayList<String> departmentsList,
			List<String> employeesWithNoDepartment) {
		this.departmentsList = departmentsList;
		this.employeesWithNoDepartment = employeesWithNoDepartment;
	}

	public List<String> getDepartmentsList() {
		return departmentsList;
	}

	public void setDepartmentsList(List<String> departmentsList) {
		this.departmentsList = departmentsList;
	}

	public String getSelectedEmployee() {
		return selectedEmployee;
	}

	public void setSelectedEmployee(String selectedEmployee) {
		this.selectedEmployee = selectedEmployee;
	}

	public List<String> getEmployeesWithNoDepartment() {
		return employeesWithNoDepartment;
	}

	public void setEmployeesWithNoDepartment(
			List<String> employeesWithNoDepartment) {
		this.employeesWithNoDepartment = employeesWithNoDepartment;
	}

	public String getSelectedDepartment() {
		return selectedDepartment;
	}

	public void setSelectedDepartment(String selectedDepartment) {
		this.selectedDepartment = selectedDepartment;
	}

}
