package ca.ulaval.glo4003.appemployee.persistence;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.SerializationException;
import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.appemployee.domain.repository.UserRepository;
import ca.ulaval.glo4003.appemployee.domain.user.User;

@Repository
@Singleton
public class XMLUserRepository implements UserRepository {

	private XMLGenericMarshaller<UserXMLAssembler> serializer;
	private Map<String, User> users = new HashMap<String, User>();
	private static String USERS_FILEPATH = "/users.xml";

	public XMLUserRepository() throws JAXBException  {
		serializer = new XMLGenericMarshaller<UserXMLAssembler>(UserXMLAssembler.class);
		parseXML();
	}

	public XMLUserRepository(XMLGenericMarshaller<UserXMLAssembler> serializer) {
		this.serializer = serializer;
	}

	@Override
	public User findByEmail(String email) {
		return users.get(email);
	}

	@Override
	public void store(User user) throws FileNotFoundException, JAXBException, URISyntaxException  {
		users.put(user.getEmail(), user);
		saveXML();
	}

	private void saveXML() throws FileNotFoundException, JAXBException, URISyntaxException {
		UserXMLAssembler userXMLWrapper = new UserXMLAssembler();
		userXMLWrapper.setUsers(new ArrayList<User>(users.values()));
		serializer.marshall(userXMLWrapper, USERS_FILEPATH);
	}

	private void parseXML() throws SerializationException, JAXBException  {
		List<User> deserializedUsers = serializer.unmarshall(USERS_FILEPATH).getUsers();
		for (User user : deserializedUsers) {
			users.put(user.getEmail(), user);
		}
	}

}
