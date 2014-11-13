package ca.ulaval.glo4003.appemployee.web.viewmodels;

import java.util.ArrayList;
import java.util.Arrays;

import ca.ulaval.glo4003.appemployee.domain.user.Role;

public class UserViewModel {

	private String email;
	private String password;
	private double wage;
	private String role;
	private ArrayList<Role> availableRoles = new ArrayList<Role>(Arrays.asList(Role.values()));

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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public ArrayList<Role> getAvailableRoles() {
		return availableRoles;
	}

	public void setAvailableRoles(ArrayList<Role> availableRoles) {
		this.availableRoles = availableRoles;
	}

}
