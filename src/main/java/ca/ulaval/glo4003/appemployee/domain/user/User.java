package ca.ulaval.glo4003.appemployee.domain.user;


//@XmlRootElement(name = "User")
public class User {

	private String email;
	private String password;
	private Integer wage;
	private String role;

	public User(String username, String password, String role, Integer wage) {
		this.email = username;
		this.password = password;
		this.role = role;
		this.setWage(wage);
	}

	//@XmlAttribute(name = "Email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	//@XmlAttribute(name = "Password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	//@XmlAttribute(name = "Role")
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean validatePassword(String password) {
		return this.password.equals(password);
	}

	public Integer getWage() {
		return wage;
	}

	public void setWage(Integer wage) {
		this.wage = wage;
	}
}
