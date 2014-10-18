package ca.ulaval.glo4003.appemployee.domain.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import ca.ulaval.glo4003.appemployee.domain.task.TaskAlreadyExistsException;

public class Project {

	private String uId;
	private String name = "";
	private List<String> taskuIds = new ArrayList<String>();
	private List<String> useruIds = new ArrayList<String>();
	private List<String> expenseuIds = new ArrayList<String>();

	public Project() {
		this.uId = UUID.randomUUID().toString();
	}

	public Project(String uId) {
		this.uId = uId;
	}

	public Project(String uId, String name) {
		this.uId = uId;
		this.name = name;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getTaskuIds() {
		return taskuIds;
	}

	public void setTaskuIds(List<String> taskuIds) {
		this.taskuIds = taskuIds;
	}

	public List<String> getUseruIds() {
		return useruIds;
	}

	public void setUseruIds(List<String> useruIds) {
		this.useruIds = useruIds;
	}

	public List<String> getExpenseuIds() {
		return expenseuIds;
	}

	public void setExpenseuIds(List<String> expenseIds) {
		this.expenseuIds = expenseIds;
	}

	public void addTaskuId(String taskuId) {
		if (taskuIds.contains(taskuId)) {
			throw new TaskAlreadyExistsException("Task already assigned to this project.");
		}

		taskuIds.add(taskuId);
	}

	public boolean userIsAlreadyAssigned(String userId) {
		return useruIds.contains(userId);
	}

	public void addUserToProject(String userId) {
		useruIds.add(userId);
	}

}
