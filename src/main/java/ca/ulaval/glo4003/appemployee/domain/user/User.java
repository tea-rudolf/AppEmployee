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

	protected User() {

	}

	public User(String username, String password) {
		this.email = username;
		this.password = password;

		init();
	}

	public void afterUnmarshal(Unmarshaller u, Object parent) {
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
	public String getPassword() { // if not required for JAXB flush it
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

	public boolean validatePassword(String password) {
		return this.password.equals(password);
	}
}
