package ca.ulaval.glo4003.appemployee.domain;

public class User {

	private String email;
	private String password;

	public User(String username, String password) {
		this.email = username;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public boolean validatePassword(String password) {
		return this.password.equals(password);
	}

}
