package ca.ulaval.glo4003.appemployee.domain.travel;

import java.util.UUID;

import org.joda.time.LocalDate;

public class Travel {

	private String uid;
	private double distanceTravelled;
	private LocalDate date;
	private String userEmail;
	private String comment;
	private Vehicle vehicule;

	public Travel() {
		this.uid = UUID.randomUUID().toString();
	}

	public void setDistanceTravelled(double distanceTravelled) {
		this.distanceTravelled = distanceTravelled;
	}

	public double getDistanceTravelled() {
		return distanceTravelled;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUid() {
		return uid;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}

	public Vehicle getVehicle() {
		return vehicule;
	}

	public void setVehicle(Vehicle vehicule) {
		this.vehicule = vehicule;
	}

}
