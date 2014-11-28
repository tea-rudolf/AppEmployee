package ca.ulaval.glo4003.appemployee.domain.expense;

import java.util.UUID;

import org.joda.time.LocalDate;

public class Expense {

	private String uid;
	private double amount;
	private LocalDate date;
	private String userEmail;
	private String comment;

	public Expense() {
		this.uid = UUID.randomUUID().toString();
	}

	public Expense(double amount, LocalDate date, String userEmail, String comment) {
		this();
		this.amount = amount;
		this.date = date;
		this.userEmail = userEmail;
		this.comment = comment;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getAmount() {
		return amount;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userId) {
		this.userEmail = userId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
}
