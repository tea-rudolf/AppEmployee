package ca.ulaval.glo4003.appemployee.domain.project;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.joda.time.LocalDate;

@XmlRootElement(name = "Project")
public class Project {
	
	private String number;
	private String name = "";
	private LocalDate startDate;
	private LocalDate endDate;
	private List<String> taskIds = new ArrayList<String>();
	private List<String> userIds = new ArrayList<String>();
	private List<String> expenseIds = new ArrayList<String>();

	protected Project(){
		//required for JAXB
	}
	
	public Project(String number) {
		this.number = number;
	}
	
	public Project(String number, String name) {
		this.number = number;
		this.name = name;
	}
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@XmlAttribute(name = "Name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public List<String> getTaskIds() {
		return taskIds;
	}

	public void setTaskIds(List<String> taskIds) {
		this.taskIds = taskIds;
	}

	public List<String> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}

	public List<String> getExpenseIds() {
		return expenseIds;
	}

	public void setExpenseIds(List<String> expenseIds) {
		this.expenseIds = expenseIds;
	}


}
