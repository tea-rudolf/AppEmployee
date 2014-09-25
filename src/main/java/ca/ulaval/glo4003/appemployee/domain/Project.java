package ca.ulaval.glo4003.appemployee.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Project")
public class Project {
	private List<Task> tasks = new ArrayList<Task>();
	private String number;
	
	private String name;
	
	protected Project() {
		//Required for JAXB
	}
	
	public Project(String number, String name) {
		this.number = number;
		this.name = name;
	}
	
	@XmlAttribute(name="Number")
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	@XmlAttribute(name="Name")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@XmlIDREF
	@XmlAttribute(name="Tasks")
	public List<Task> getTasks() {
		return tasks;
	}
	
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	
	public void addTask(Task task) {
		this.tasks.add(task);
	}
}
