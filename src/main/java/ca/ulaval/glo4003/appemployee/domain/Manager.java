package ca.ulaval.glo4003.appemployee.domain;

import java.util.LinkedList;
import java.util.List;

public class Manager extends User {

	private List<Employee> employees;

	public Manager(String username, String password) {
		this.username = username;
		this.password = password;
		employees = new LinkedList<Employee>();
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void addEmployee(Employee employee) {
		employees.add(employee);
	}

}
