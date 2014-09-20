package ca.ulaval.glo4003.appemployee.domain;

public class User {

	private String username;
	private String password;
	private String email;
	private boolean isLoggedIn;

	public User(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.isLoggedIn = false;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean verifyPassword(String password) throws WrongPassword {
		if (this.password.equals(password)) {
			return true;
		}

		throw new WrongPassword("Password entered is wrong.");
	}
}