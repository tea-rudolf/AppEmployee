package ca.ulaval.glo4003.appemployee.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;
import org.springframework.stereotype.Repository;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntry;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntryNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntryRepository;

@Repository
@Singleton
public class XmlTimeEntryRepository implements TimeEntryRepository {

	private XmlRepositoryMarshaller xmlRepositoryMarshaller = XmlRepositoryMarshaller.getInstance();
	private List<TimeEntry> timeEntries = new ArrayList<TimeEntry>();

	public XmlTimeEntryRepository() {
		unmarshall();
	}


	private void marshall() {
		XmlRootNode xmlRootNode = xmlRepositoryMarshaller.getRootNode();
		xmlRootNode.setTimeEntries(timeEntries);
		xmlRepositoryMarshaller.marshall();
	}

	private void unmarshall() {
		xmlRepositoryMarshaller.unmarshall();
		XmlRootNode xmlRootNode = xmlRepositoryMarshaller.getRootNode();
		timeEntries = xmlRootNode.getTimeEntries();
	}

	public TimeEntry getByUid(Integer id) {

		for (TimeEntry entry : timeEntries) {
			if (entry.getuId() == id) {
				return entry;
			}
		}

		throw new TimeEntryNotFoundException(String.format("Cannot find time entry with id '%s'.", id));
	}

	public void add(TimeEntry timeEntry) {
		timeEntries.add(timeEntry);
		marshall();
		
	}

	public void update(TimeEntry timeEntry) {
		marshall();
	}
}
