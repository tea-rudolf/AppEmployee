package ca.ulaval.glo4003.appemployee.domain.expense;

import org.joda.time.LocalDate;

public class Expense {

	private String uId;
	private double amount;
	private LocalDate date;
	private String userId;
	private String comment;

	public Expense(String uId) {
		this.uId = uId;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getAmount() {
		return amount;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
