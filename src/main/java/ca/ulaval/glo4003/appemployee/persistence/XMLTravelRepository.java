package ca.ulaval.glo4003.appemployee.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ulaval.glo4003.appemployee.domain.repository.TravelRepository;
import ca.ulaval.glo4003.appemployee.domain.travel.Travel;

public class XMLTravelRepository implements TravelRepository{
	
	private XMLGenericMarshaller<TravelXMLAssembler> serializer;
	private Map<String, Travel> travels = new HashMap<String, Travel>();
	private static String TRAVELS_FILEPATH = "/travels.xml";
	
	public XMLTravelRepository() throws Exception {
		serializer = new XMLGenericMarshaller<TravelXMLAssembler>(TravelXMLAssembler.class);
		parseXML();
	}
	
	public XMLTravelRepository(XMLGenericMarshaller<TravelXMLAssembler> serializer) {
		this.serializer = serializer;
	}

	@Override
	public void store(Travel travel) throws Exception {
		travels.put(travel.getuId(), travel);
		saveXML();
		
	}

	@Override
	public List<Travel> findAlltravelsByUser(String userId) {
		List<Travel> travelsFound = new ArrayList<Travel>();

		for (Travel travel : travels.values()) {
			if (travel.getUserEmail().equals(userId)) {
				travelsFound.add(travel);
			}
		}
		return travelsFound;
	}

	@Override
	public Travel findByUid(String uId) {
		return travels.get(uId);
	}

	@Override
	public Collection<Travel> findAll() {
		return travels.values();
	}
	
	private void saveXML() throws Exception {
		TravelXMLAssembler travelAssembler = new TravelXMLAssembler();
		travelAssembler.setTravels(new ArrayList<Travel>(travels.values()));
		serializer.marshall(travelAssembler, TRAVELS_FILEPATH);
	}

	private void parseXML() throws Exception {
		List<Travel> deserializedTravels = serializer.unmarshall(TRAVELS_FILEPATH).getTravels();
		for (Travel travel : deserializedTravels) {
			travels.put(travel.getuId(), travel);
		}
	}

}
