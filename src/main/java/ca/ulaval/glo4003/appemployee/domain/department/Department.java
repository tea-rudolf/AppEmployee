package ca.ulaval.glo4003.appemployee.domain.department;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4003.appemployee.domain.exceptions.EmployeeAlreadyExistsException;

public class Department {

	private String name = "";
	private List<String> supervisorIds = new ArrayList<String>();
	private List<String> employeeIds = new ArrayList<String>();

	public Department() {
	}

	public Department(String name) {
		this.name = name;
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

	public void setEmployeeIds(List<String> employeeUids) {
		this.employeeIds = employeeUids;
	}

	public void addEmployee(String employeeUid) {
		if (employeeIds.contains(employeeUid)) {
			throw new EmployeeAlreadyExistsException("Employee already assigned to this department.");
		}
		employeeIds.add(employeeUid);
	}

	public void removeEmployee(String employeeUid) {
		employeeIds.remove(employeeUid);
	}

	public boolean containsSupervisor(String supervisorId) {
		return supervisorIds.contains(supervisorId);
	}

}
