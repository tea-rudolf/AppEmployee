package ca.ulaval.glo4003.appemployee.domain.expense;

import javax.xml.bind.annotation.XmlRootElement;

import org.joda.time.LocalDate;

@XmlRootElement(name = "Expense")
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

	//@XmlAttribute(name = "Amount")
	public double getAmount() {
		return amount;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	//@XmlAttribute(name = "Date")
	public LocalDate getDate() {
		return date;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	//@XmlAttribute(name = "Comment")
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
