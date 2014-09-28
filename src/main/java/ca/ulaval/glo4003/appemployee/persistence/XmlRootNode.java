package ca.ulaval.glo4003.appemployee.persistence;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import ca.ulaval.glo4003.appemployee.domain.project.Project;
import ca.ulaval.glo4003.appemployee.domain.user.User;

@XmlRootElement(name = "AppEmployee")
public class XmlRootNode {

	private List<Project> projects;
	private List<User> users;

	protected XmlRootNode() {
		// Required for JAXB
	}

	@XmlElementWrapper(name = "Projects")
	@XmlElement(name = "Project")
	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	@XmlElementWrapper(name = "Users")
	@XmlElement(name = "User")
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
