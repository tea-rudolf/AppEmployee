package ca.ulaval.glo4003.appemployee.domain.task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Task {
	private String uId;
	private String name;
	private List<String> authorizedUsers = new ArrayList<String>();

	public Task() {
		this.uId = UUID.randomUUID().toString();
	}

	public Task(String uId) {
		this.uId = uId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public void assignUserToTask(String userId) {
		if (!authorizedUsers.contains(userId)) {
			authorizedUsers.add(userId);
		}			
	}

	public List<String> getAuthorizedUsers() {
		return authorizedUsers;
	}

	public void setAuthorizedUsers(List<String> authorizedUsers) {
		this.authorizedUsers = authorizedUsers;
	}

}
