package ca.ulaval.glo4003.appemployee.web.viewmodels;

import java.util.ArrayList;
import java.util.List;

public class PayPeriodViewModel {

	private String startDate;
	private String endDate;
	private List<String> timeEntryIds = new ArrayList<String>();

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public List<String> getTimeEntryIds() {
		return timeEntryIds;
	}

	public void setTimeEntryIds(List<String> timeEntryIds) {
		this.timeEntryIds = timeEntryIds;
	}

}
