package ca.ulaval.glo4003.appemployee.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntry;
import ca.ulaval.glo4003.appemployee.domain.timeentry.TimeEntryRepository;

public class XMLTimeEntryRepository implements TimeEntryRepository {

	private XMLSerializer<TimeEntryXMLAssembler> serializer;
	private Map<String, TimeEntry> timeEntries = new HashMap<String, TimeEntry>();
	private static String TIMEENTRIES_FILEPATH = "/resources/timeEntries.xml";

	public XMLTimeEntryRepository() throws Exception {
		serializer = new XMLSerializer<TimeEntryXMLAssembler>(TimeEntryXMLAssembler.class);
		parseXML();
	}

	public XMLTimeEntryRepository(XMLSerializer<TimeEntryXMLAssembler> serializer) {
		this.serializer = serializer;
	}

	@Override
	public void store(TimeEntry timeEntry) throws Exception {
		timeEntries.put(timeEntry.getuId(), timeEntry);
		saveXML();
	}

	@Override
	public TimeEntry findByUid(String timeEntryId) {
		return timeEntries.get(timeEntryId);
	}

	private void saveXML() throws Exception {
		TimeEntryXMLAssembler timeEntryAssembler = new TimeEntryXMLAssembler();
		timeEntryAssembler.setTimeEntries(new ArrayList<TimeEntry>(timeEntries.values()));
		serializer.serialize(timeEntryAssembler, TIMEENTRIES_FILEPATH);
	}

	private void parseXML() throws Exception {
		List<TimeEntry> deserializedTimeEntries = serializer.deserialize(TIMEENTRIES_FILEPATH).getTimeEntries();
		for (TimeEntry timeEntry : deserializedTimeEntries) {
			timeEntries.put(timeEntry.getuId(), timeEntry);
		}
	}

}
