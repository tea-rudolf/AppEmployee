package ca.ulaval.glo4003.appemployee.web.viewmodels;

public class ProjectViewModel {
	public int number; //TODO: Use a value object
	public String name;
	
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
