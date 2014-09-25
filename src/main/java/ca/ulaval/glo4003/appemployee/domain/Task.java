package ca.ulaval.glo4003.appemployee.domain;

import javax.xml.bind.annotation.XmlAttribute;

public class Task {

	private String name;
	private String number;
	
	protected Task() {
		//Required for JAXB
	}
	
	public Task(String number, String name) {
		this.name = name;
		this.number = number;
	}

	@XmlAttribute(name="Name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@XmlAttribute(name="Number")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
}