package ca.ulaval.glo4003.appemployee.domain;

import javax.xml.bind.annotation.XmlAttribute;

public class Task {

	private Employee employee;
	private String name;
	private int number;
	
	protected Task() {
		//Required for JAXB
	}
	
	public Task(int number, String name, Employee employee) {
		this.name = name;
		this.number = number;
		this.employee = employee;
	}

	@XmlAttribute(name="Name")
	public String getName() {
		return name;
	}

	public void setName(String taskName) {
		this.name = name;
	}
	
	@XmlAttribute(name="Number")
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

//	@XmlAttribute(employee="Employee")
	public Employee getEmployee(Employee employee) {
		return employee;
	}
	
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}