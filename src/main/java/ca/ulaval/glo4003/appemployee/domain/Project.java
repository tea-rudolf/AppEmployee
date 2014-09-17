package ca.ulaval.glo4003.appemployee.domain;

import java.util.ArrayList;

public class Project {
	private ArrayList<Task> tasks;
	private int number;
	private String name;
	
	public String getName() {
		return name;
	}
	
	public int getNumber() {
		return number;
	}
}
