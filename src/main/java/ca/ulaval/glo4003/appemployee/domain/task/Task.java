package ca.ulaval.glo4003.appemployee.domain.task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ca.ulaval.glo4003.appemployee.domain.exceptions.EmployeeAlreadyExistsException;

public class Task {
	private String uid;
	private String name;
	private double multiplicativeFactor = 1.0;
	private List<String> authorizedUsers = new ArrayList<String>();

	public Task() {
		this.uid = UUID.randomUUID().toString();
	}

	public Task(String uid) {
		this.uid = uid;
	}

	public Task(String name, List<String> authorizedUsers, double multiplicativeFactor) {
		this();
		this.name = name;
		this.authorizedUsers = authorizedUsers;
		this.multiplicativeFactor = multiplicativeFactor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public void assignUserToTask(String userId) {
		if (authorizedUsers.contains(userId)) {
			throw new EmployeeAlreadyExistsException("Employee already assigned to this task.");
		}

		authorizedUsers.add(userId);
	}

	public List<String> getAuthorizedUsers() {
		return authorizedUsers;
	}

	public void setAuthorizedUsers(List<String> authorizedUsers) {
		this.authorizedUsers = authorizedUsers;
	}

	public boolean userIsAlreadyAssignedToTask(String userId) {
		return authorizedUsers.contains(userId);
	}

	public void update(String taskName, String newUserEmail, double multiplicativeFactor) {
		update(taskName, multiplicativeFactor);
		assignUserToTask(newUserEmail);
	}

	public double getMultiplicativeFactor() {
		return multiplicativeFactor;
	}

	public void setMultiplicativeFactor(double multiplicativeFactor) {
		this.multiplicativeFactor = multiplicativeFactor;
	}

	public void update(String taskName, double multiplicativeFactor) {
		this.name = taskName;
		this.multiplicativeFactor = multiplicativeFactor;
	}

}
