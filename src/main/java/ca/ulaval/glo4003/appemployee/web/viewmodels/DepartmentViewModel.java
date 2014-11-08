package ca.ulaval.glo4003.appemployee.web.viewmodels;

import java.util.List;

public class DepartmentViewModel {

	private String name;
	private List<String> supervisorIds;
	private List<String> employeeIds;

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

}
