package ca.ulaval.glo4003.appemployee.domain;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Project")
public class Project {
	private ArrayList<Task> tasks;
	private int number; //TODO: Use a value object
	
	private String name;
	
	protected Project() {
		//Required for JAXB
	}
	
	public Project(int number, String name) {
		this.number = number;
		this.name = name;
	}
	
	@XmlAttribute(name="Number")
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	@XmlAttribute(name="Name")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
