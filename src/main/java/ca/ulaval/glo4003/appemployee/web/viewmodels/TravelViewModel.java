package ca.ulaval.glo4003.appemployee.web.viewmodels;

import java.util.ArrayList;
import java.util.Arrays;

import ca.ulaval.glo4003.appemployee.domain.travel.Vehicule;

public class TravelViewModel {

	private String payPeriodStartDate;
	private String payPeriodEndDate;
	private double distanceTravelled;
	private String date;
	private String userEmail;
	private String comment;
	private String uId;
	private String vehicule;
	private ArrayList<Vehicule> availableVehicules = new ArrayList<Vehicule>(Arrays.asList(Vehicule.values()));

	public double getDistanceTravelled() {
		return distanceTravelled;
	}

	public void setDistanceTravelled(double distanceTravelled) {
		this.distanceTravelled = distanceTravelled;
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

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
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

	public String getVehicule() {
		return vehicule;
	}

	public void setVehicule(String vehicule) {
		this.vehicule = vehicule;
	}

	public ArrayList<Vehicule> getAvailableVehicules() {
		return availableVehicules;
	}

	public void setAvailableVehicules(ArrayList<Vehicule> availableVehicules) {
		this.availableVehicules = availableVehicules;
	}

}
