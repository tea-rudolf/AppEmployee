package ca.ulaval.glo4003.appemployee.domain.task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ca.ulaval.glo4003.appemployee.domain.exceptions.EmployeeAlreadyExistsException;

public class Task {
	private String uid;
	private String name;
	private List<String> authorizedUsers = new ArrayList<String>();

	public Task() {
		this.uid = UUID.randomUUID().toString();
	}

	public Task(String uid) {
		this.uid = uid;
	}
	
	public Task(String name, List<String> authorizedUsers) {
		this();
		this.name = name;
		this.authorizedUsers = authorizedUsers;
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

	public boolean userIsAssignedToTask(String userId) {
		return authorizedUsers.contains(userId);
	}
	
//	public void assignUsersToTask(List<String> employeeUids) {
//		for (String employeeUid:employeeUids) {
//			
//		}
//		
//	}

}
