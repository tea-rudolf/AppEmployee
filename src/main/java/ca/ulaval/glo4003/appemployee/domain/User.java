package ca.ulaval.glo4003.appemployee.domain;

public abstract class User {
	
	private String username;	
	private String password;

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
