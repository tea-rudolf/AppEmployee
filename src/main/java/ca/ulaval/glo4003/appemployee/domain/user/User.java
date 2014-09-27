package ca.ulaval.glo4003.appemployee.domain.user;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import ca.ulaval.glo4003.appemployee.domain.NoCurrentPayPeriodException;
import ca.ulaval.glo4003.appemployee.domain.PayPeriod;

public class User {

	private String email;
	private String password;
	private List<PayPeriod> payPeriods;
	
	public User(){
		
		
	}

	public User(String username, String password) {
		this.email = username;
		this.password = password;
		payPeriods = new ArrayList<PayPeriod>();
		
		payPeriods.add(new PayPeriod(new LocalDate(2014,9,21), new LocalDate(2014,10,4)));
		payPeriods.add(new PayPeriod(new LocalDate(2014,10,5), new LocalDate(2014,10,18)));
		payPeriods.add(new PayPeriod(new LocalDate(2014,10,19), new LocalDate(2014,11,1)));
	}

	public PayPeriod getCurrentPayPeriod() {
	LocalDate currentDate = new LocalDate();
		for (PayPeriod payPeriod : payPeriods){
			if (currentDate.isAfter(payPeriod.getStartDate()) && currentDate.isBefore(payPeriod.getEndDate())){
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
	
	public String getEmail() {
		return email;
	}

	public boolean validatePassword(String password) {
		return this.password.equals(password);
	}

	public List<PayPeriod> getPayPeriods() {
		return payPeriods;
	}

	public void setPayPeriods(List<PayPeriod> payPeriods) {
		this.payPeriods = payPeriods;
	}

}