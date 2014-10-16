package ca.ulaval.glo4003.appemployee.domain.project;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;

import ca.ulaval.glo4003.appemployee.domain.task.TaskAlreadyExistsException;

public class Project {

	private String uId;
	private String name = "";
	private List<String> taskIds = new ArrayList<String>();
	private List<String> userIds = new ArrayList<String>();
	private List<String> expenseIds = new ArrayList<String>();
	
	public Project() {
		
	}

	public Project(String number) {
		this.uId = number;
	}

	public Project(String number, String name) {
		this.uId = number;
		this.name = name;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	@XmlAttribute(name = "Name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getTaskIds() {
		return taskIds;
	}

	public void setTaskIds(List<String> taskIds) {
		this.taskIds = taskIds;
	}

	public List<String> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}

	public List<String> getExpenseIds() {
		return expenseIds;
	}

	public void setExpenseIds(List<String> expenseIds) {
		this.expenseIds = expenseIds;
	}

	public void addTaskId(String taskId) {
		if (taskIds.contains(taskId)) {
			throw new TaskAlreadyExistsException("Task already assigned to this project.");
		}

		taskIds.add(taskId);
	}

}
