package ca.ulaval.glo4003.appemployee.persistence;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.appemployee.domain.User;
import ca.ulaval.glo4003.appemployee.domain.UserRepository;

public class XmlUserRepository implements UserRepository {

	private Map<String, User> users = new HashMap<String, User>();

	public XmlUserRepository() {
		users.put("GracyMb", new User("GracyMb@gmail.com", "toto1"));
		users.put("TeaRud", new User("TeaRud@gmail.com", "toto2"));
	}

	@Override
	public void add(User user) {
		users.put(user.getUsername(), user);
	}

	@Override
	public void remove(User user) {
		users.remove(user.getUsername());
	}

	@Override
	public User findByUsername(String username) {
		return users.get(username);
	}

	@Override
	public void update(User user) {
		users.put(user.getUsername(), user);
	}
}
