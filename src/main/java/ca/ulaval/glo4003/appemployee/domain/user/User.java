package ca.ulaval.glo4003.appemployee.domain.user;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.joda.time.LocalDate;

import ca.ulaval.glo4003.appemployee.domain.NoCurrentPayPeriodException;
import ca.ulaval.glo4003.appemployee.domain.PayPeriod;

@XmlRootElement(name = "User")
public class User {

	private String email;
	private String password;
	private List<PayPeriod> payPeriods;
	private Role role;

	protected User() {

	}

	public User(String username, String password, Role role) {
		this.email = username;
		this.password = password;
		this.role = role;

		init();
	}

	public void afterUnmarshall(Unmarshaller u, Object parent) {
		init();
	}

	private void init() {
		if (payPeriods == null || payPeriods.size() == 0) {
			payPeriods = new ArrayList<PayPeriod>();
		}
	}

	@XmlAttribute(name = "Email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@XmlAttribute(name = "Password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@XmlElementWrapper(name = "PayPeriods")
	@XmlElement(name = "PayPeriod")
	public List<PayPeriod> getPayPeriods() {
		return payPeriods;
	}

	public void setPayPeriods(List<PayPeriod> payPeriods) {
		this.payPeriods = payPeriods;
	}

	@XmlAttribute(name = "Role")
	public String getRole() {
		return role.toString();
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public String getRoleName() {
		return role.toString();
	}

	public PayPeriod getCurrentPayPeriod() {
		LocalDate currentDate = new LocalDate();
		for (PayPeriod payPeriod : payPeriods) {
			if (currentDate.isAfter(payPeriod.getStartDate()) && currentDate.isBefore(payPeriod.getEndDate())) {
				return payPeriod;
			}
		}
		throw new NoCurrentPayPeriodException("The user " + this.email + " does not have a current pay period.");
	}

	public void setShiftsCurrentPayPeriod(PayPeriod newPayPeriod) {
		PayPeriod currentPayPeriod = getCurrentPayPeriod();
		currentPayPeriod.setShiftsWorked(newPayPeriod.getShiftsWorked());
	}

	public void setExpensesCurrentPayPeriod(PayPeriod newPayPeriod) {
		PayPeriod currentPayPeriod = getCurrentPayPeriod();
		currentPayPeriod.setExpenses(newPayPeriod.getExpenses());
	}

	public boolean isPasswordValid(String password) {
		return this.password.equals(password);
	}
}
