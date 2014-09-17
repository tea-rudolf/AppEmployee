package ca.ulaval.glo4003.appemployee.domain;

public class User {

	protected String username;
	protected String password;

	public User(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
