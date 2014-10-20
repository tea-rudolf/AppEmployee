package ca.ulaval.glo4003.appemployee.web.viewmodels;

import ca.ulaval.glo4003.appemployee.domain.user.Role;

public class UserViewModel {

	private String email;
	private String password;
	private double wage;
	private Role role;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getWage() {
		return wage;
	}

	public void setWage(double wage) {
		this.wage = wage;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
