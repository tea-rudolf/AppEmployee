package ca.ulaval.glo4003.appemployee.domain;

public class Shift {
    
    private String comment;
    private String date;
    private Double hours;
    
    public Shift() {

    }
    
    public Shift(String date, double hours, String comment) {
    	this.date = date;
        this.hours = hours;
        this.comment = comment;
    }
    
	public String getComment() {
		return comment;
	}

	public void setComment(String email) {
		this.comment = email;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Double getHours() {
		return hours;
	}

	public void setHours(Double hours) {
		this.hours = hours;
	}

}