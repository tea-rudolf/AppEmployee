package ca.ulaval.glo4003.appemployee.web.viewmodels;

import ca.ulaval.glo4003.appemployee.domain.Employee;

public class TaskViewModel {
	public String number; 
	public String name;
	public Employee employee;
	
	public String source;
	public String projectNumber;
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Employee getEmployee() {
		return employee;
	}
	
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	public String getSource() {
		return source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}
	
	public String getProjectNumber() {
		return projectNumber;
	}
	
	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}
}
