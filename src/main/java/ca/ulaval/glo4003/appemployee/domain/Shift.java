package ca.ulaval.glo4003.appemployee.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Shift")
public class Shift {

	private String comment;
	private String date;
	private Double hours;

	protected Shift() {
		//Required for JAXB
	}

	public Shift(String date, double hours, String comment) {
		this.date = date;
		this.hours = hours;
		this.comment = comment;
	}

	@XmlAttribute(name = "Comment")
	public String getComment() {
		return comment;
	}

	public void setComment(String email) {
		this.comment = email;
	}

	@XmlAttribute(name = "Date")
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@XmlAttribute(name = "Hours")
	public Double getHours() {
		return hours;
	}

	public void setHours(Double hours) {
		this.hours = hours;
	}

}