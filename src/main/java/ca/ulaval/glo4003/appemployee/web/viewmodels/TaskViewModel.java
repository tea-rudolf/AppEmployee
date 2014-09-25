package ca.ulaval.glo4003.appemployee.web.viewmodels;

import ca.ulaval.glo4003.appemployee.domain.Employee;

public class TaskViewModel {
	public int number; 
	public String name;
	public Employee employee;
	
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
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
}
