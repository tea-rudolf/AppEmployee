package ca.ulaval.glo4003.appemployee.domain;

import java.util.ArrayList;

public class Project {
	private ArrayList<Task> tasks;
	private int number; //TODO: Use a value object
	private String name;
	
	public Project(int number, String name) {
		this.number = number;
		this.name = name;
	}
	
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
