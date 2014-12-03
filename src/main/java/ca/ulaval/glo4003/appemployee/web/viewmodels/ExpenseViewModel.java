package ca.ulaval.glo4003.appemployee.web.viewmodels;

public class ExpenseViewModel {

	private double amount;
	private String date;
	private String userEmail;
	private String comment;
	private String uid;

	public ExpenseViewModel() {

	}

	public ExpenseViewModel(String uid, double amount, String date,
			String userEmail, String comment) {
		this.uid = uid;
		this.amount = amount;
		this.date = date;
		this.userEmail = userEmail;
		this.comment = comment;
	}

	public ExpenseViewModel(String email) {
		this.userEmail = email;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

}
