package ca.ulaval.glo4003.appemployee.persistence;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.appemployee.domain.User;
import ca.ulaval.glo4003.appemployee.domain.UserRepository;

@Repository
public class MapUserRepository implements UserRepository {

	private Map<String, User> users = new HashMap<String, User>();

	public MapUserRepository() {
		users.put("test@test.com", new User("test@test.com", "1234"));
	}

	@Override
	public void add(User user) {
		users.put(user.getEmail(), user);
	}

	@Override
	public void remove(User user) {
		users.remove(user.getEmail());
	}

	@Override
	public boolean validateCredentials(String email, String password) {
		User user = users.get(email);
		if (user == null) {
			return false;
		}
		return user.validatePassword(password);
	}

}
