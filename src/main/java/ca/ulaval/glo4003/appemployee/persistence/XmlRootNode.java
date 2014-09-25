package ca.ulaval.glo4003.appemployee.persistence;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import ca.ulaval.glo4003.appemployee.domain.project.Project;
import ca.ulaval.glo4003.appemployee.domain.task.Task;

@XmlRootElement(name="AppEmployee")
public class XmlRootNode {
	
	private List<Project> projects;
	private List<Task> tasks;
	
	protected XmlRootNode() {
		//Required for JAXB
	}
	
	@XmlElementWrapper(name="Projects")
	@XmlElement(name="Project")
	public List<Project> getProjects() {
		return projects;
	}
	
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	
	@XmlElementWrapper(name="Tasks")
	@XmlElement(name="Task")
	public List<Task> getTasks() {
		return tasks;
	}
	
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
}
