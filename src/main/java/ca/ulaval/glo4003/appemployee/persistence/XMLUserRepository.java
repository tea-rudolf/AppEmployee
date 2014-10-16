package ca.ulaval.glo4003.appemployee.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.domain.user.UserRepository;

public class XMLUserRepository implements UserRepository {

	private XMLSerializer<UserXMLAssembler> serializer;
	private Map<String, User> users = new HashMap<String, User>();
	private static String USERS_FILEPATH = "/resources/users.xml";

	public XMLUserRepository() throws Exception {
		serializer = new XMLSerializer<UserXMLAssembler>(UserXMLAssembler.class);
		parseXML();
	}

	public XMLUserRepository(XMLSerializer<UserXMLAssembler> serializer) {
		this.serializer = serializer;
	}

	@Override
	public User findByEmail(String email) {
		return users.get(email);
	}

	@Override
	public void store(User user) throws Exception {
		users.put(user.getEmail(), user);
		saveXML();
	}

	private void saveXML() throws Exception {
		UserXMLAssembler userXMLWrapper = new UserXMLAssembler();
		userXMLWrapper.setUsers(new ArrayList<User>(users.values()));
		serializer.serialize(userXMLWrapper, USERS_FILEPATH);
	}

	private void parseXML() throws Exception {
		List<User> deserializedUsers = serializer.deserialize(USERS_FILEPATH).getUsers();
		for (User user : deserializedUsers) {
			users.put(user.getEmail(), user);
		}
	}

}
