package ca.ulaval.glo4003.appemployee.domain.project;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ca.ulaval.glo4003.appemployee.domain.exceptions.EmployeeAlreadyExistsException;
import ca.ulaval.glo4003.appemployee.domain.exceptions.TaskAlreadyExistsException;

public class Project {

	private String uid;
	private String name = "";
	private List<String> taskUids = new ArrayList<String>();
	private List<String> employeeUids = new ArrayList<String>();
	private List<String> expenseUids = new ArrayList<String>();

	public List<String> getTaskUids() {
		return taskUids;
	}

	public void setTaskUids(List<String> taskUids) {
		this.taskUids = taskUids;
	}

	public Project() {
		this.uid = UUID.randomUUID().toString();
	}

	public Project(String name, List<String> taskIds, List<String> userIds,
			List<String> expenseIds) {
		this();
		this.name = name;
		this.taskUids = taskIds;
		this.expenseUids = expenseIds;
		this.employeeUids = userIds;
	}

	public Project(String uid, String name) {
		this.uid = uid;
		this.name = name;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getEmployeeUids() {
		return employeeUids;
	}

	public void setEmployeeUids(List<String> userUids) {
		this.employeeUids = userUids;
	}

	public List<String> getExpenseUids() {
		return expenseUids;
	}

	public void setExpenseUids(List<String> expenseIds) {
		this.expenseUids = expenseIds;
	}

	public void addTaskUid(String taskUid) {
		if (taskUids.contains(taskUid)) {
			throw new TaskAlreadyExistsException("Task already assigned to this project.");
		}
		taskUids.add(taskUid);
	}

	public boolean userIsAssignedToProject(String userId) {
		return employeeUids.contains(userId);
	}

	private void addEmployeeToProject(String userId) {
		if (employeeUids.contains(userId)) {
			throw new EmployeeAlreadyExistsException("Employee already assigned to this project.");
		}
		employeeUids.add(userId);
	}

	public void update(String projectName, String newUserEmail) {
		this.name = projectName;
		addEmployeeToProject(newUserEmail);
	}

}
