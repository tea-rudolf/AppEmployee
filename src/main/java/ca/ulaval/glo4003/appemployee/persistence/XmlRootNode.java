package ca.ulaval.glo4003.appemployee.persistence;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import ca.ulaval.glo4003.appemployee.domain.expense.Expense;
import ca.ulaval.glo4003.appemployee.domain.payperiod.PayPeriod;
import ca.ulaval.glo4003.appemployee.domain.project.Project;
import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntry;
import ca.ulaval.glo4003.appemployee.domain.user.User;

@XmlRootElement(name = "AppEmployee")
public class XmlRootNode {

	private List<Project> projects;
	private List<User> users;
	private List<Expense> expenses;
	private List<PayPeriod> payPeriods;
	private List<TimeEntry> timeEntries;
	private List<Task> tasks;

	protected XmlRootNode() {
		// Required for JAXB
	}

	@XmlElementWrapper(name = "Projects")
	@XmlElement(name = "Project")
	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	@XmlElementWrapper(name = "Users")
	@XmlElement(name = "User")
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	@XmlElementWrapper(name = "Expenses")
	@XmlElement(name = "Expense")
	public List<Expense> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<Expense> expenses) {
		this.expenses = expenses;
	}

	@XmlElementWrapper(name = "PayPeriods")
	@XmlElement(name = "PayPeriod")
	public List<PayPeriod> getPayPeriods() {
		return payPeriods;
	}

	public void setPayPeriods(List<PayPeriod> payPeriods) {
		this.payPeriods = payPeriods;
	}
	
	@XmlElementWrapper(name = "TimeEntries")
	@XmlElement(name = "TimeEntry")
	public List<TimeEntry> getTimeEntries() {
		return timeEntries;
	}

	public void setTimeEntries(List<TimeEntry> timeEntries) {
		this.timeEntries = timeEntries;
	}
	
	@XmlElementWrapper(name = "Tasks")
	@XmlElement(name = "Task")
	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

}
