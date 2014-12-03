package ca.ulaval.glo4003.appemployee.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import ca.ulaval.glo4003.appemployee.domain.time.TimeEntry;

@XmlRootElement(name = "timeEntries")
public class TimeEntryXMLAssembler {

	private List<TimeEntry> timeEntries = new ArrayList<TimeEntry>();

	public List<TimeEntry> getTimeEntries() {
		return this.timeEntries;
	}

	public void setTimeEntries(List<TimeEntry> timeEntries) {
		this.timeEntries = timeEntries;
	}

}
