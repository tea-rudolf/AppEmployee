package ca.ulaval.glo4003.appemployee.web.viewmodels;

public class PayPeriodViewModel {

	private String payPeriodStartDate;
	private String payPeriodEndDate;

	public PayPeriodViewModel(String payPeriodStartDate, String payPeriodEndDate) {
		this.payPeriodStartDate = payPeriodStartDate;
		this.payPeriodEndDate = payPeriodEndDate;
	}

	public String getPayPeriodStartDate() {
		return payPeriodStartDate;
	}

	public void setPayPeriodStartDate(String payPeriodStartDate) {
		this.payPeriodStartDate = payPeriodStartDate;
	}

	public String getPayPeriodEndDate() {
		return payPeriodEndDate;
	}

	public void setPayPeriodEndDate(String payPeriodEndDate) {
		this.payPeriodEndDate = payPeriodEndDate;
	}

}
