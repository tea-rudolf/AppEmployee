package ca.ulaval.glo4003.appemployee.domain.user;

public class User {

	private String email;
	private String password;
	private double wage;
	private Role role;

	public User() {

	}

	public User(String email, String password, Role role, double wage) {
		this.email = email;
		this.password = password;
		this.role = role;
		this.wage = wage;
	}

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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public boolean validatePassword(String password) {
		return this.password.equals(password);
	}

	public double getWage() {
		return wage;
	}

	public void setWage(double wage) {
		this.wage = wage;
	}
}
