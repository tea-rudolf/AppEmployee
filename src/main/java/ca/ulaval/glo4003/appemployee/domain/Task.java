package ca.ulaval.glo4003.appemployee.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;

public class Task {

	private Employee employee;
	private String name;
	private String number;
	
	protected Task() {
		//Required for JAXB
	}
	
	public Task(String number, String name, Employee employee) {
		this.name = name;
		this.number = number;
		this.employee = employee;
	}

	@XmlAttribute(name="Name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@XmlID
	@XmlAttribute(name="Number")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
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