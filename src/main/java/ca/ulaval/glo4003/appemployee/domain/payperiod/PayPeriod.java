package ca.ulaval.glo4003.appemployee.domain.payperiod;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

//@XmlRootElement(name = "PayPeriod")
public class PayPeriod {

	private LocalDate startDate;
	private LocalDate endDate;
	private List<String> timeEntryIds = new ArrayList<String>();

	public PayPeriod(LocalDate startDate, LocalDate endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
		
	}

	//@XmlAttribute(name = "StartDate")
	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	//@XmlAttribute(name = "EndDate")
	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public List<String> getTimeEntryIds() {
		return timeEntryIds;
	}

	public void setTimeEntryIds(List<String> timeEntryIds) {
		this.timeEntryIds = timeEntryIds;
	}

}
