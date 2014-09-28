package ca.ulaval.glo4003.appemployee.domain.project;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.task.TaskExistsException;
import ca.ulaval.glo4003.appemployee.domain.task.TaskNotFoundException;

@XmlRootElement(name = "Project")
public class Project {
	private List<Task> tasks = new ArrayList<Task>();
	private String number;
	private String name;

	protected Project() {
		// Required for JAXB
	}

	public Project(String number, String name) {
		this.number = number;
		this.name = name;
	}

	@XmlAttribute(name = "Number")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@XmlAttribute(name = "Name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElementWrapper(name = "Tasks")
	@XmlElement(name = "Task")
	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public void addTask(Task task) {
		try {
			getTaskByNumber(task.getNumber());

			throw new TaskExistsException(String.format("Task number '%s' already exists in project number '%s'.", task.getNumber(), this.number));
		} catch (TaskNotFoundException e) {
		}

		this.tasks.add(task);
	}

	public Task getTaskByNumber(String number) {
		for (Task task : tasks) {
			if (task.getNumber().compareTo(number) == 0) {
				return task;
			}
		}

		throw new TaskNotFoundException(String.format("Cannot find task with number '%s'.", number));
	}
}
