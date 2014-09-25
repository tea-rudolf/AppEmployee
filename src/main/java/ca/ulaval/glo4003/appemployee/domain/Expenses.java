package ca.ulaval.glo4003.appemployee.domain;

import org.joda.time.LocalDate;

public class Expenses {

	private double amount;
	private LocalDate date;
	private String comment;
	
	public Expenses(){
		
	}
	
	public Expenses(double amount, LocalDate date, String comment){
		this.amount = amount;
		this.date = date;
		this.comment = comment;
	}
	
	public void setAmount(double amount){
		this.amount = amount;
	}
	
	public double getAmount(){
		return amount;
	}
	
	public void setDate(LocalDate date){
		this.date = date;
	}
	
	public LocalDate getDate(){
		return date;
	}
	
	public void setComment(String comment){
		this.comment = comment;
	}
	
	public String getComment(){
		return comment;
	}
}
