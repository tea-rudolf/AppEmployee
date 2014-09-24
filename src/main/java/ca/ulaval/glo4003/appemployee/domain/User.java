package ca.ulaval.glo4003.appemployee.domain;

import java.util.ArrayList;
import java.util.List;

public class User {

	private String email;
	private String password;
	private List<Role> roles = new ArrayList<Role>();

	public User(String username, String password, Role role) {
		this.email = username;
		this.password = password;
		roles.add(role);
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void addRole(Role role) {
		if (!roles.contains(role)) {
			roles.add(role);
		}
	}

	public boolean validatePassword(String password) {
		return this.password.equals(password);
	}

}
